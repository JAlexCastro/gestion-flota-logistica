package cl.transcargo.gestion_flota.mapper;

import cl.transcargo.gestion_flota.dto.Requests.VehiculoRequest;
import cl.transcargo.gestion_flota.dto.Responses.VehiculoResponse;
import cl.transcargo.gestion_flota.entity.Vehiculo;
import org.springframework.stereotype.Component;

@Component
public class VehiculoMapper {

    public VehiculoResponse toResponse(Vehiculo vehiculo) {
        VehiculoResponse response = new VehiculoResponse();

        response.setId(vehiculo.getId());
        response.setPatente(vehiculo.getPatente());
        response.setMarca(vehiculo.getMarca());
        response.setModelo(vehiculo.getModelo());

        return response;
    }

    public Vehiculo toEntity(VehiculoRequest request) {
        Vehiculo vehiculo = new Vehiculo();

        vehiculo.setPatente(request.getPatente());
        vehiculo.setMarca(request.getMarca());
        vehiculo.setModelo(request.getModelo());

        return vehiculo;
    }
}