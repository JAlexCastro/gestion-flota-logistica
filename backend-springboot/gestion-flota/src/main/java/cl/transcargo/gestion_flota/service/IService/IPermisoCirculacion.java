package cl.transcargo.gestion_flota.service.IService;

import cl.transcargo.gestion_flota.dto.Requests.PermisoCirculacionRequestDTO;
import cl.transcargo.gestion_flota.dto.Responses.PermisoCirculacionResponseDTO;

import java.util.List;

public interface IPermisoCirculacion {

    List<PermisoCirculacionResponseDTO> listar();

    PermisoCirculacionResponseDTO obtener(Long id);

    PermisoCirculacionResponseDTO crear(PermisoCirculacionRequestDTO request);

    PermisoCirculacionResponseDTO actualizar(Long id,
                                             PermisoCirculacionRequestDTO request);

    void eliminar(Long id);

}