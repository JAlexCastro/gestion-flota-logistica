package cl.transcargo.gestion_flota.mapper;

import cl.transcargo.gestion_flota.dto.Requests.PermisoCirculacionRequestDTO;
import cl.transcargo.gestion_flota.dto.Responses.PermisoCirculacionResponseDTO;
import cl.transcargo.gestion_flota.entity.PermisoCirculacion;
import cl.transcargo.gestion_flota.entity.Vehiculo;
import org.springframework.stereotype.Component;

@Component
public class PermisoCirculacionMapper {

    public PermisoCirculacion toEntity(PermisoCirculacionRequestDTO request,
                                       Vehiculo vehiculo){

        PermisoCirculacion permiso = new PermisoCirculacion();

        permiso.setVehiculo(vehiculo);
        permiso.setFechaEmision(request.getFechaEmision());
        permiso.setFechaVencimiento(request.getFechaVencimiento());
        permiso.setEstado(request.getEstado());

        return permiso;
    }

    public PermisoCirculacionResponseDTO toResponse(PermisoCirculacion permiso){

        PermisoCirculacionResponseDTO response = new PermisoCirculacionResponseDTO();

        response.setId(permiso.getId());

        if(permiso.getVehiculo() != null){
            response.setVehiculoId(permiso.getVehiculo().getId());
            response.setPatente(permiso.getVehiculo().getPatente());
        }

        response.setFechaEmision(permiso.getFechaEmision());
        response.setFechaVencimiento(permiso.getFechaVencimiento());
        response.setEstado(permiso.getEstado());

        return response;
    }

    public void updateEntity(PermisoCirculacion permiso,
                             PermisoCirculacionRequestDTO request,
                             Vehiculo vehiculo){

        permiso.setVehiculo(vehiculo);
        permiso.setFechaEmision(request.getFechaEmision());
        permiso.setFechaVencimiento(request.getFechaVencimiento());
        permiso.setEstado(request.getEstado());

    }

}