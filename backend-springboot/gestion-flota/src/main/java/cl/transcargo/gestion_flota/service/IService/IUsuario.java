package cl.transcargo.gestion_flota.service.IService;

import cl.transcargo.gestion_flota.dto.Requests.UsuarioRequestDTO;
import cl.transcargo.gestion_flota.dto.Responses.UsuarioResponseDTO;


import java.util.List;

public interface IUsuario {

    List<UsuarioResponseDTO> listar();

    UsuarioResponseDTO obtener(Long id);

    UsuarioResponseDTO crear(UsuarioRequestDTO request);

    UsuarioResponseDTO actualizar(Long id, UsuarioRequestDTO request);

    void eliminar(Long id);

}