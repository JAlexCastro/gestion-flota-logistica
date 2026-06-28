package cl.transcargo.gestion_flota.service.IService;

import cl.transcargo.gestion_flota.dto.Requests.FallaRequestDTO;
import cl.transcargo.gestion_flota.dto.Responses.FallaResponseDTO;

import java.util.List;

public interface IFalla {

    List<FallaResponseDTO> listar();

    FallaResponseDTO obtener(Long id);

    FallaResponseDTO crear(FallaRequestDTO request);

    FallaResponseDTO actualizar(Long id, FallaRequestDTO request);

    void eliminar(Long id);

}