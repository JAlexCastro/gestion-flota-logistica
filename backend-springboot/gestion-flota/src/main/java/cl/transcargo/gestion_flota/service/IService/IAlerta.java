package cl.transcargo.gestion_flota.service.IService;

import cl.transcargo.gestion_flota.dto.Requests.AlertaRequestDTO;
import cl.transcargo.gestion_flota.dto.Responses.AlertaResponseDTO;

import java.util.List;

public interface IAlerta {

    List<AlertaResponseDTO> listar();

    AlertaResponseDTO obtener(Long id);

    AlertaResponseDTO crear(AlertaRequestDTO request);

    AlertaResponseDTO actualizar(Long id, AlertaRequestDTO request);

    void eliminar(Long id);

}