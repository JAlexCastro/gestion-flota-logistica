package cl.transcargo.gestion_flota.service.IService;

import cl.transcargo.gestion_flota.dto.Requests.EmisionGasesRequestDTO;
import cl.transcargo.gestion_flota.dto.Responses.EmisionGasesResponseDTO;

import java.util.List;

public interface IEmisionGases {

    List<EmisionGasesResponseDTO> listar();

    EmisionGasesResponseDTO obtener(Long id);

    EmisionGasesResponseDTO crear(EmisionGasesRequestDTO request);

    EmisionGasesResponseDTO actualizar(Long id, EmisionGasesRequestDTO request);

    void eliminar(Long id);

}