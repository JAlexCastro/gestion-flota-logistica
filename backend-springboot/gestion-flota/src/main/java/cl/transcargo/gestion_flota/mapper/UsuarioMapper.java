package cl.transcargo.gestion_flota.mapper;

import cl.transcargo.gestion_flota.dto.Requests.UsuarioRequestDTO;
import cl.transcargo.gestion_flota.dto.UsuarioResponseDTO;
import cl.transcargo.gestion_flota.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public Usuario toEntity(UsuarioRequestDTO request){

        Usuario usuario = new Usuario();

        usuario.setUsername(request.getUsername());
        usuario.setPassword(request.getPassword());
        usuario.setRol(request.getRol());

        return usuario;
    }

    public UsuarioResponseDTO toResponse(Usuario usuario){

        UsuarioResponseDTO response = new UsuarioResponseDTO();

        response.setId(usuario.getId());
        response.setUsername(usuario.getUsername());
        response.setRol(usuario.getRol());

        return response;
    }

    public void updateEntity(Usuario usuario, UsuarioRequestDTO request){

        usuario.setUsername(request.getUsername());
        usuario.setPassword(request.getPassword());
        usuario.setRol(request.getRol());

    }

}