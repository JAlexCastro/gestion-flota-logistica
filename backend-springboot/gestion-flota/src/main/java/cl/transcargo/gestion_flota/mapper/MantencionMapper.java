package cl.transcargo.gestion_flota.mapper;

import cl.transcargo.gestion_flota.dto.Requests.MantencionRequestDTO;
import cl.transcargo.gestion_flota.dto.Responses.MantencionResponseDTO;
import cl.transcargo.gestion_flota.entity.Mantencion;
import cl.transcargo.gestion_flota.entity.Vehiculo;
import org.springframework.stereotype.Component;

@Component
public class MantencionMapper {

    public Mantencion toEntity(MantencionRequestDTO request,
                               Vehiculo vehiculo){

        Mantencion mantencion = new Mantencion();

        mantencion.setVehiculo(vehiculo);
        mantencion.setFecha(request.getFecha());
        mantencion.setKilometraje(request.getKilometraje());
        mantencion.setTipo(request.getTipo());
        mantencion.setDescripcion(request.getDescripcion());
        mantencion.setTaller(request.getTaller());

        return mantencion;
    }

    public MantencionResponseDTO toResponse(Mantencion mantencion){

        MantencionResponseDTO response = new MantencionResponseDTO();

        response.setId(mantencion.getId());

        if(mantencion.getVehiculo() != null){
            response.setVehiculoId(mantencion.getVehiculo().getId());
            response.setPatente(mantencion.getVehiculo().getPatente());
        }

        response.setFecha(mantencion.getFecha());
        response.setKilometraje(mantencion.getKilometraje());
        response.setTipo(mantencion.getTipo());
        response.setDescripcion(mantencion.getDescripcion());
        response.setTaller(mantencion.getTaller());
        response.setPatente(mantencion.getVehiculo().getPatente());

        return response;
    }

    public void updateEntity(Mantencion mantencion,
                             MantencionRequestDTO request,
                             Vehiculo vehiculo){

        mantencion.setVehiculo(vehiculo);
        mantencion.setFecha(request.getFecha());
        mantencion.setKilometraje(request.getKilometraje());
        mantencion.setTipo(request.getTipo());
        mantencion.setDescripcion(request.getDescripcion());
        mantencion.setTaller(request.getTaller());

    }

}