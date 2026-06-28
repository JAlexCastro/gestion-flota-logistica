package cl.transcargo.gestion_flota.service.IService;

import cl.transcargo.gestion_flota.dto.Requests.ConductorRequestDTO;
import cl.transcargo.gestion_flota.dto.Responses.ConductorResponseDTO;

import java.util.List;

public interface IConductor {

    List<ConductorResponseDTO> listar();

    ConductorResponseDTO obtener(Long id);

    ConductorResponseDTO crear(ConductorRequestDTO request);

    ConductorResponseDTO actualizar(Long id, ConductorRequestDTO request);

    void eliminar(Long id);

}