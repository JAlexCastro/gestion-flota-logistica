package cl.transcargo.gestion_flota.mapper;

import cl.transcargo.gestion_flota.dto.Requests.RevisionTecnicaRequestDTO;
import cl.transcargo.gestion_flota.dto.Responses.RevisionTecnicaResponseDTO;
import cl.transcargo.gestion_flota.entity.RevisionTecnica;
import cl.transcargo.gestion_flota.entity.Vehiculo;
import org.springframework.stereotype.Component;

@Component
public class RevisionTecnicaMapper {

    public RevisionTecnica toEntity(RevisionTecnicaRequestDTO request,
                                    Vehiculo vehiculo){

        RevisionTecnica revision = new RevisionTecnica();

        revision.setVehiculo(vehiculo);
        revision.setFechaRevision(request.getFechaRevision());
        revision.setFechaVencimiento(request.getFechaVencimiento());
        revision.setResultado(request.getResultado());

        return revision;
    }

    public RevisionTecnicaResponseDTO toResponse(RevisionTecnica revision){

        RevisionTecnicaResponseDTO response = new RevisionTecnicaResponseDTO();

        response.setId(revision.getId());

        if(revision.getVehiculo() != null){
            response.setVehiculoId(revision.getVehiculo().getId());
            response.setPatente(revision.getVehiculo().getPatente());
        }

        response.setFechaRevision(revision.getFechaRevision());
        response.setFechaVencimiento(revision.getFechaVencimiento());
        response.setResultado(revision.getResultado());

        return response;
    }

    public void updateEntity(RevisionTecnica revision,
                             RevisionTecnicaRequestDTO request,
                             Vehiculo vehiculo){

        revision.setVehiculo(vehiculo);
        revision.setFechaRevision(request.getFechaRevision());
        revision.setFechaVencimiento(request.getFechaVencimiento());
        revision.setResultado(request.getResultado());

    }

}