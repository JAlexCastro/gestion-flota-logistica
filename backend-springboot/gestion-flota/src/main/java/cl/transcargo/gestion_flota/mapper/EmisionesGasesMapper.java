package cl.transcargo.gestion_flota.mapper;

import cl.transcargo.gestion_flota.dto.Requests.EmisionGasesRequestDTO;
import cl.transcargo.gestion_flota.dto.Responses.EmisionGasesResponseDTO;
import cl.transcargo.gestion_flota.entity.EmisionesGases;
import cl.transcargo.gestion_flota.entity.Vehiculo;
import org.springframework.stereotype.Component;

@Component
public class EmisionesGasesMapper {

    public EmisionesGases toEntity(EmisionGasesRequestDTO request,
                                   Vehiculo vehiculo){

        EmisionesGases emision = new EmisionesGases();

        emision.setVehiculo(vehiculo);
        emision.setFechaRevision(request.getFechaRevision());
        emision.setFechaVencimiento(request.getFechaVencimiento());
        emision.setResultado(request.getResultado());

        return emision;
    }

    public EmisionGasesResponseDTO toResponse(EmisionesGases emision){

        EmisionGasesResponseDTO response = new EmisionGasesResponseDTO();

        response.setId(emision.getId());

        if(emision.getVehiculo() != null){
            response.setVehiculoId(emision.getVehiculo().getId());
            response.setPatente(emision.getVehiculo().getPatente());
        }

        response.setFechaRevision(emision.getFechaRevision());
        response.setFechaVencimiento(emision.getFechaVencimiento());
        response.setResultado(emision.getResultado());

        return response;
    }

    public void updateEntity(EmisionesGases  emision,
                             EmisionGasesRequestDTO request,
                             Vehiculo vehiculo){

        emision.setVehiculo(vehiculo);
        emision.setFechaRevision(request.getFechaRevision());
        emision.setFechaVencimiento(request.getFechaVencimiento());
        emision.setResultado(request.getResultado());

    }

}