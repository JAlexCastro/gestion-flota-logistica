package cl.transcargo.gestion_flota.service.IService;

import cl.transcargo.gestion_flota.dto.Requests.RevisionTecnicaRequestDTO;
import cl.transcargo.gestion_flota.dto.Responses.RevisionTecnicaResponseDTO;

import java.util.List;

public interface IRevisionTecnica {

    List<RevisionTecnicaResponseDTO> listar();

    RevisionTecnicaResponseDTO obtener(Long id);

    RevisionTecnicaResponseDTO crear(RevisionTecnicaRequestDTO request);

    RevisionTecnicaResponseDTO actualizar(Long id, RevisionTecnicaRequestDTO request);

    void eliminar(Long id);

}