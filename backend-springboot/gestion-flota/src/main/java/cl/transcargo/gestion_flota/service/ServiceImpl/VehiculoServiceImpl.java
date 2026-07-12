package cl.transcargo.gestion_flota.service.ServiceImpl;

import cl.transcargo.gestion_flota.dto.Requests.VehiculoRequestDTO;
import cl.transcargo.gestion_flota.dto.Responses.VehiculoResponseDTO;
import cl.transcargo.gestion_flota.entity.Conductor;
import cl.transcargo.gestion_flota.entity.Mantencion;
import cl.transcargo.gestion_flota.entity.Usuario;
import cl.transcargo.gestion_flota.entity.Vehiculo;
import cl.transcargo.gestion_flota.mapper.VehiculoMapper;
import cl.transcargo.gestion_flota.notification.NotificationService;
import cl.transcargo.gestion_flota.repository.RConductor;
import cl.transcargo.gestion_flota.repository.RUsuario;
import cl.transcargo.gestion_flota.repository.RMantencion;
import cl.transcargo.gestion_flota.repository.RUsuario;
import cl.transcargo.gestion_flota.repository.RVehiculo;
import cl.transcargo.gestion_flota.service.IService.IVehiculo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehiculoServiceImpl implements IVehiculo {

    // Inyeccion de dependencia
    private final VehiculoMapper mapper;
    private final RVehiculo repository;
    private final RConductor conductorRepository;
    private final RMantencion mantencionRepository;
    private final RUsuario usuarioRepository;
    private final NotificationService notificationService;


    public VehiculoServiceImpl(RVehiculo repository,
                               VehiculoMapper mapper,
                               RConductor conductorRepository,
                               RMantencion mantencionRepository,
                               RUsuario usuarioRepository,
                               NotificationService notificationService
                               ) {
        this.repository = repository;
        this.mapper = mapper;
        this.conductorRepository = conductorRepository;
        this.mantencionRepository = mantencionRepository;
        this.usuarioRepository = usuarioRepository;
        this.notificationService = notificationService;
    }

    // Listar
    @Override
    public List<VehiculoResponseDTO> listar() {

        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    // Obtener por id
    @Override
    public VehiculoResponseDTO obtener(Long id) {

        VehiculoResponseDTO response = new VehiculoResponseDTO();

        Vehiculo vehiculo = repository.findById(id).orElseThrow();

        response.setId(vehiculo.getId());
        response.setPatente(vehiculo.getPatente());
        response.setModelo(vehiculo.getModelo());
        response.setNombre(vehiculo.getNombre());
        response.setMarca(vehiculo.getMarca());

        response.setAnio(vehiculo.getAnio());

        response.setKilometrajeActual(vehiculo.getKilometrajeActual());
        response.setEstado(vehiculo.getEstado());
        //response.setConductor(vehiculo.getConductor().getNombre());

        return response;
    }

    // Crear
    @Override
    public VehiculoResponseDTO crear(VehiculoRequestDTO request) {

        Vehiculo vehiculo = mapper.toEntity(request);
        vehiculo = repository.save(vehiculo);

        return mapper.toResponse(vehiculo);

    }


    //Actualizar
    @Override
    public VehiculoResponseDTO actualizar(Long id, VehiculoRequestDTO request) {

        Vehiculo vehiculo = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));

        vehiculo.setPatente(request.getPatente());
        vehiculo.setMarca(request.getMarca());
        vehiculo.setNombre(request.getNombre());
        vehiculo.setModelo(request.getModelo());
        vehiculo.setAnio(request.getAnio());
        vehiculo.setEstado(request.getEstado());
        vehiculo.setKilometrajeActual(request.getKilometrajeActual());

        vehiculo = repository.save(vehiculo);

        //// NOTIFICAR
        Optional<Mantencion> optionalMantencion =
                mantencionRepository.findByVehiculoId(vehiculo.getId());

        if (optionalMantencion.isPresent()) {
            Mantencion mantencion = optionalMantencion.get();

            Integer kilometrosRestantes =
                    mantencion.getKilometraje()
                            - vehiculo.getKilometrajeActual();

            if (kilometrosRestantes <= 2000 &&
                    kilometrosRestantes >= 0) {

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
                    }
                }
            }
        }
        return mapper.toResponse(vehiculo);
    }

    @Override
    public void eliminar(Long id) {

        Vehiculo vehiculo = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));

        repository.delete(vehiculo);
    }

    @Override
    public VehiculoResponseDTO asignarConductor(Long vehiculoId, Long conductorId){

        Vehiculo vehiculo = repository.findById(vehiculoId)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));

        Conductor conductor = conductorRepository.findById(conductorId)
                .orElseThrow(() -> new RuntimeException("Conductor no encontrado"));

        vehiculo.setConductor(conductor);

        repository.save(vehiculo);

        return mapper.toResponse(vehiculo);
    }

}