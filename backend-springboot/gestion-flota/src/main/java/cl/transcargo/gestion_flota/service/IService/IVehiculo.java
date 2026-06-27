package cl.transcargo.gestion_flota.service.IService;

import cl.transcargo.gestion_flota.dto.Requests.VehiculoRequest;
import cl.transcargo.gestion_flota.dto.Responses.VehiculoResponse;

import java.util.List;

public interface IVehiculo {

    List<VehiculoResponse> listar();

    VehiculoResponse obtener(Long id);

    VehiculoResponse crear(VehiculoRequest request);

    VehiculoResponse actualizar(Long id, VehiculoRequest request);

    void eliminar(Long id);

}