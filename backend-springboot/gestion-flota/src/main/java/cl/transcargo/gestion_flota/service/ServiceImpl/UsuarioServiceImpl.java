package cl.transcargo.gestion_flota.service.ServiceImpl;

import cl.transcargo.gestion_flota.dto.Requests.UsuarioRequestDTO;
import cl.transcargo.gestion_flota.dto.Responses.UsuarioResponseDTO;
import cl.transcargo.gestion_flota.entity.Usuario;
import cl.transcargo.gestion_flota.mapper.UsuarioMapper;
import cl.transcargo.gestion_flota.repository.RUsuario;
import cl.transcargo.gestion_flota.service.IService.IUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements IUsuario {

    private final RUsuario repository;
    private final UsuarioMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(RUsuario repository, UsuarioMapper mapper,PasswordEncoder passwordEncoder){
        this.repository = repository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UsuarioResponseDTO> listar() {

        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public UsuarioResponseDTO obtener(Long id) {

        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return mapper.toResponse(usuario);
    }

    @Override
    public UsuarioResponseDTO crear(UsuarioRequestDTO request) {

        Usuario usuario = mapper.toEntity(request);

        usuario.setPassword(
                passwordEncoder.encode(request.getPassword()) );

        usuario = repository.save(usuario);

        return mapper.toResponse(usuario);
    }

    @Override
    public UsuarioResponseDTO actualizar(Long id, UsuarioRequestDTO request) {

        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        mapper.updateEntity(usuario, request);

        if (request.getPassword() != null &&
                !request.getPassword().isBlank()) {

            usuario.setPassword(
                    passwordEncoder.encode(request.getPassword())
            );
        } //Si el usuario escribe una nueva contraseña, se cifra.

        usuario = repository.save(usuario);

        return mapper.toResponse(usuario);
    }

    @Override
    public void eliminar(Long id) {

        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        repository.delete(usuario);
    }

}