package cl.transcargo.gestion_flota.mapper;

import cl.transcargo.gestion_flota.dto.Requests.VehiculoRequestDTO;
import cl.transcargo.gestion_flota.dto.Responses.VehiculoResponseDTO;
import cl.transcargo.gestion_flota.entity.Vehiculo;
import org.springframework.stereotype.Component;

@Component
public class VehiculoMapper {

    public VehiculoResponseDTO toResponse(Vehiculo vehiculo) {
        VehiculoResponseDTO response = new VehiculoResponseDTO();

        response.setId(vehiculo.getId());
        response.setPatente(vehiculo.getPatente());
        response.setMarca(vehiculo.getMarca());
        response.setNombre(vehiculo.getNombre());
        response.setModelo(vehiculo.getModelo());
        response.setEstado(vehiculo.getEstado());
        response.setAnio(vehiculo.getAnio());
        response.setKilometrajeActual(vehiculo.getKilometrajeActual());

        // Si tiene conductor asignado. Lo retornara
        if(vehiculo.getConductor()!=null){

            response.setConductorId(
                    vehiculo.getConductor().getId());

            response.setConductorNombre(
                    vehiculo.getConductor().getNombre());
        }

        return response;
    }

    public Vehiculo toEntity(VehiculoRequestDTO request) {
        Vehiculo vehiculo = new Vehiculo();

        vehiculo.setPatente(request.getPatente());
        vehiculo.setMarca(request.getMarca());
        vehiculo.setModelo(request.getModelo());
        vehiculo.setNombre(request.getNombre());
        vehiculo.setAnio(request.getAnio());
        vehiculo.setEstado(request.getEstado());
        vehiculo.setKilometrajeActual(request.getKilometrajeActual());


        return vehiculo;
    }
}