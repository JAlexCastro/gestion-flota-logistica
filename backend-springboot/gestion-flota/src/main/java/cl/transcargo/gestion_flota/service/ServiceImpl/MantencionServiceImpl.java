package cl.transcargo.gestion_flota.service.ServiceImpl;

import cl.transcargo.gestion_flota.dto.Requests.MantencionRequestDTO;
import cl.transcargo.gestion_flota.dto.Responses.MantencionResponseDTO;
import cl.transcargo.gestion_flota.entity.Mantencion;
import cl.transcargo.gestion_flota.entity.Usuario;
import cl.transcargo.gestion_flota.entity.Vehiculo;
import cl.transcargo.gestion_flota.mapper.MantencionMapper;
import cl.transcargo.gestion_flota.notification.NotificationService;
import cl.transcargo.gestion_flota.repository.RMantencion;
import cl.transcargo.gestion_flota.repository.RUsuario;
import cl.transcargo.gestion_flota.repository.RVehiculo;
import cl.transcargo.gestion_flota.service.IService.IMantencion;
import org.springframework.stereotype.Service;

import java.util.List;
import cl.transcargo.gestion_flota.notification.NotificationService;

@Service
public class MantencionServiceImpl implements IMantencion {

    private final RMantencion repository;
    private final RVehiculo vehiculoRepository;
    private final MantencionMapper mapper;
    private final RUsuario usuarioRepository;
    private final NotificationService notificationService;

    public MantencionServiceImpl(RMantencion repository,
                                 RVehiculo vehiculoRepository,
                                 MantencionMapper mapper,
                                 RUsuario usuarioRepository,
                                 NotificationService notificationService){
        this.repository = repository;
        this.vehiculoRepository = vehiculoRepository;
        this.mapper = mapper;
        this.notificationService = notificationService;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public List<MantencionResponseDTO> listar() {

        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public MantencionResponseDTO obtener(Long id) {

        Mantencion mantencion = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mantención no encontrada"));

        return mapper.toResponse(mantencion);
    }

    @Override
    public MantencionResponseDTO crear(MantencionRequestDTO request) {

        Vehiculo vehiculo = vehiculoRepository.findById(request.getVehiculoId())
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));

        Mantencion mantencion = mapper.toEntity(request, vehiculo);

        mantencion = repository.save(mantencion);

        Integer kilometrosRestantes =
                mantencion.getKilometraje() - vehiculo.getKilometrajeActual();

        if (kilometrosRestantes <= 2000 && kilometrosRestantes >= 0) {

            List<Usuario> destinatarios =
                    usuarioRepository.findByRolIn(
                            List.of("ADMIN", "OPERADOR")
                    );

            for (Usuario usuario : destinatarios) {
                try {
                    notificationService.proximaMantencion(
                            usuario.getUsername(),
                            vehiculo.getPatente(),
                            vehiculo.getKilometrajeActual(),
                            mantencion.getKilometraje(),
                            kilometrosRestantes
                    );
                } catch (Exception e) {
                    System.err.println(
                            "No fue posible enviar el correo a "
                                    + usuario.getUsername()
                    );
                }}
        }

        return mapper.toResponse(mantencion);
    }

    @Override
    public MantencionResponseDTO actualizar(Long id,
                                            MantencionRequestDTO request) {

        Mantencion mantencion = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mantención no encontrada"));

        Vehiculo vehiculo = vehiculoRepository.findById(request.getVehiculoId())
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));

        mapper.updateEntity(mantencion, request, vehiculo);

        mantencion = repository.save(mantencion);

        return mapper.toResponse(mantencion);
    }

    @Override
    public void eliminar(Long id) {

        Mantencion mantencion = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mantención no encontrada"));

        repository.delete(mantencion);
    }

}