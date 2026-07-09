package cl.transcargo.gestion_flota.service.IService;

import cl.transcargo.gestion_flota.dto.Requests.VehiculoRequestDTO;
import cl.transcargo.gestion_flota.dto.Responses.VehiculoResponseDTO;

import java.util.List;

public interface IVehiculo {

    List<VehiculoResponseDTO> listar();

    VehiculoResponseDTO obtener(Long id);

    VehiculoResponseDTO crear(VehiculoRequestDTO request);

    VehiculoResponseDTO actualizar(Long id, VehiculoRequestDTO request);

    void eliminar(Long id);

    VehiculoResponseDTO asignarConductor(Long vehiculoId, Long conductorId);

}