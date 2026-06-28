package cl.transcargo.gestion_flota.mapper;

import cl.transcargo.gestion_flota.dto.Requests.FallaRequestDTO;
import cl.transcargo.gestion_flota.dto.Responses.FallaResponseDTO;
import cl.transcargo.gestion_flota.entity.Falla;
import cl.transcargo.gestion_flota.entity.Vehiculo;
import org.springframework.stereotype.Component;

@Component
public class FallaMapper {

    public Falla toEntity(FallaRequestDTO request, Vehiculo vehiculo){

        Falla falla = new Falla();

        falla.setVehiculo(vehiculo);
        falla.setFecha(request.getFecha());
        falla.setDescripcion(request.getDescripcion());
        falla.setPrioridad(request.getPrioridad());
        falla.setEstado(request.getEstado());

        return falla;
    }

    public FallaResponseDTO toResponse(Falla falla){

        FallaResponseDTO response = new FallaResponseDTO();

        response.setId(falla.getId());

        if(falla.getVehiculo() != null){
            response.setVehiculoId(falla.getVehiculo().getId());
            response.setPatente(falla.getVehiculo().getPatente());
        }

        response.setFecha(falla.getFecha());
        response.setDescripcion(falla.getDescripcion());
        response.setPrioridad(falla.getPrioridad());
        response.setEstado(falla.getEstado());

        return response;
    }

    public void updateEntity(Falla falla,
                             FallaRequestDTO request,
                             Vehiculo vehiculo){

        falla.setVehiculo(vehiculo);
        falla.setFecha(request.getFecha());
        falla.setDescripcion(request.getDescripcion());
        falla.setPrioridad(request.getPrioridad());
        falla.setEstado(request.getEstado());

    }

}