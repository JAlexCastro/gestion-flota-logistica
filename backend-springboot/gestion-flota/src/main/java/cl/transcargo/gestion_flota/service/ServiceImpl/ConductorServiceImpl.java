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

@Service
public class ConductorServiceImpl implements IConductor {

    private final RConductor repository;
    private final RUsuario usuarioRepository;
    private final ConductorMapper mapper;

    public ConductorServiceImpl(RConductor repository, RUsuario usuarioRepository, ConductorMapper mapper ){
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.mapper = mapper;
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

        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Conductor conductor = mapper.toEntity(request, usuario);

        conductor = repository.save(conductor);

        return mapper.toResponse(conductor);
    }

    @Override
    public ConductorResponseDTO actualizar(Long id,
                                           ConductorRequestDTO request) {

        Conductor conductor = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conductor no encontrado"));

        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        mapper.updateEntity(conductor, request, usuario);

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