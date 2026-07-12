package cl.transcargo.gestion_flota.service.ServiceImpl;

import cl.transcargo.gestion_flota.dto.Requests.ConductorRequestDTO;
import cl.transcargo.gestion_flota.dto.Responses.ConductorResponseDTO;
import cl.transcargo.gestion_flota.entity.Conductor;
import cl.transcargo.gestion_flota.entity.Usuario;
import cl.transcargo.gestion_flota.mapper.ConductorMapper;
import cl.transcargo.gestion_flota.repository.RConductor;
import cl.transcargo.gestion_flota.repository.RUsuario;
import cl.transcargo.gestion_flota.service.IService.IConductor;
import org.springframework.stereotype.Service;

import java.util.List;
import cl.transcargo.gestion_flota.notification.NotificationService;

@Service
public class ConductorServiceImpl implements IConductor {

    private final RConductor repository;
    private final RUsuario usuarioRepository;
    private final ConductorMapper mapper;
    private final NotificationService notificationService;

    public ConductorServiceImpl(RConductor repository, RUsuario usuarioRepository, ConductorMapper mapper, NotificationService notificationService ){
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.mapper = mapper;
        this.notificationService = notificationService;

    }


    @Override
    public List<ConductorResponseDTO> listar() {

        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public ConductorResponseDTO obtener(Long id) {

        Conductor conductor = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conductor no encontrado"));

        return mapper.toResponse(conductor);
    }

    @Override
    public ConductorResponseDTO crear(ConductorRequestDTO request) {

        Conductor conductor = mapper.toEntity(request);
        conductor = repository.save(conductor);

        List<Usuario> destinatarios = usuarioRepository.findByRolIn(
                List.of("ADMIN", "OPERADOR"));
        for (Usuario usuario : destinatarios) {
            try {
                notificationService.nuevoConductor(
                        usuario.getUsername(), // correo
                        conductor.getNombre()
                );
            } catch (Exception e) {
                System.err.println(
                        "No fue posible enviar el correo a "
                                + usuario.getUsername()); }
        }

        return mapper.toResponse(conductor);

    }

    @Override
    public ConductorResponseDTO actualizar(Long id,
                                           ConductorRequestDTO request) {

        Conductor conductor = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conductor no encontrado"));

        mapper.updateEntity(conductor, request);

        conductor = repository.save(conductor);

        return mapper.toResponse(conductor);
    }

    @Override
    public void eliminar(Long id) {

        Conductor conductor = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conductor no encontrado"));

        repository.delete(conductor);
    }

}