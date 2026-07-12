package cl.transcargo.gestion_flota.service.ServiceImpl;

import cl.transcargo.gestion_flota.dto.Requests.FallaRequestDTO;
import cl.transcargo.gestion_flota.dto.Responses.FallaResponseDTO;
import cl.transcargo.gestion_flota.entity.Falla;
import cl.transcargo.gestion_flota.entity.Usuario;
import cl.transcargo.gestion_flota.entity.Vehiculo;
import cl.transcargo.gestion_flota.mapper.FallaMapper;
import cl.transcargo.gestion_flota.notification.NotificationService;
import cl.transcargo.gestion_flota.repository.RFalla;
import cl.transcargo.gestion_flota.repository.RUsuario;
import cl.transcargo.gestion_flota.repository.RVehiculo;
import cl.transcargo.gestion_flota.service.IService.IFalla;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FallaServiceImpl implements IFalla {

    private final RFalla repository;
    private final RVehiculo vehiculoRepository;
    private final FallaMapper mapper;
    private final RUsuario usuarioRepository;
    private final NotificationService notificationService;

    public FallaServiceImpl(RFalla repository,
                            RVehiculo vehiculoRepository,
                            FallaMapper mapper,
                            RUsuario usuarioRepository,
                            NotificationService notificationService) {
        this.repository = repository;
        this.vehiculoRepository = vehiculoRepository;
        this.mapper = mapper;
        this.usuarioRepository = usuarioRepository;
        this.notificationService = notificationService;
    }

    @Override
    public List<FallaResponseDTO> listar() {

        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public FallaResponseDTO obtener(Long id) {

        Falla falla = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Falla no encontrada"));

        return mapper.toResponse(falla);
    }

    @Override
    public FallaResponseDTO crear(FallaRequestDTO request) {

        Vehiculo vehiculo = vehiculoRepository.findById(request.getVehiculoId())
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));

        Falla falla = mapper.toEntity(request, vehiculo);
        falla = repository.save(falla);


        List<Usuario> destinatarios = usuarioRepository.findByRolIn(
                List.of("ADMIN", "OPERADOR")
        );

        for (Usuario usuario : destinatarios) {

            try {
                notificationService.nuevaFalla(
                        usuario.getUsername(),
                        falla.getVehiculo().getPatente(),
                        falla.getDescripcion(),
                        falla.getPrioridad()
                );
            } catch (Exception e) {
                System.err.println(
                        "No fue posible enviar el correo a "
                                + usuario.getUsername());
            }
        }
        return mapper.toResponse(falla); }

    @Override
    public FallaResponseDTO actualizar(Long id, FallaRequestDTO request) {

        Falla falla = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Falla no encontrada"));

        Vehiculo vehiculo = vehiculoRepository.findById(request.getVehiculoId())
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));

        mapper.updateEntity(falla, request, vehiculo);

        falla = repository.save(falla);

        return mapper.toResponse(falla);
    }

    @Override
    public void eliminar(Long id) {

        Falla falla = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Falla no encontrada"));

        repository.delete(falla);
    }

}