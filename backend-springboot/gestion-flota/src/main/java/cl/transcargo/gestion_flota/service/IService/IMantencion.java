package cl.transcargo.gestion_flota.service.IService;

import cl.transcargo.gestion_flota.dto.Requests.MantencionRequestDTO;
import cl.transcargo.gestion_flota.dto.Responses.MantencionResponseDTO;

import java.util.List;

public interface IMantencion {

    List<MantencionResponseDTO> listar();

    MantencionResponseDTO obtener(Long id);

    MantencionResponseDTO crear(MantencionRequestDTO request);

    MantencionResponseDTO actualizar(Long id, MantencionRequestDTO request);

    void eliminar(Long id);

}