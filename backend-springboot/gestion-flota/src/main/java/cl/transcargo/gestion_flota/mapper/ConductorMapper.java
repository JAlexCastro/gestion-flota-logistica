package cl.transcargo.gestion_flota.mapper;

import cl.transcargo.gestion_flota.dto.Requests.ConductorRequestDTO;
import cl.transcargo.gestion_flota.dto.Responses.ConductorResponseDTO;
import cl.transcargo.gestion_flota.entity.Conductor;
import cl.transcargo.gestion_flota.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class ConductorMapper {

    public Conductor toEntity(ConductorRequestDTO request){

        Conductor conductor = new Conductor();

        conductor.setRut(request.getRut());
        conductor.setNombre(request.getNombre());
        conductor.setTelefono(request.getTelefono());
        conductor.setNumeroLicencia(request.getNumeroLicencia());
        conductor.setClaseLicencia(request.getClaseLicencia());
        conductor.setFechaVencimientoLicencia(request.getFechaVencimientoLicencia());
        ///conductor.setUsuario(usuario);

        return conductor;
    }

    public ConductorResponseDTO toResponse(Conductor conductor){

        ConductorResponseDTO response = new ConductorResponseDTO();

        response.setId(conductor.getId());
        response.setRut(conductor.getRut());
        response.setNombre(conductor.getNombre());
        response.setTelefono(conductor.getTelefono());
        response.setNumeroLicencia(conductor.getNumeroLicencia());
        response.setClaseLicencia(conductor.getClaseLicencia());
        response.setFechaVencimientoLicencia(conductor.getFechaVencimientoLicencia());

//        if(conductor.getUsuario() != null){
//            response.setUsuarioId(conductor.getUsuario().getId());
//            response.setUsername(conductor.getUsuario().getUsername());
//        }

        return response;
    }

    public void updateEntity(Conductor conductor,
                             ConductorRequestDTO request) {

        conductor.setRut(request.getRut());
        conductor.setNombre(request.getNombre());
        conductor.setTelefono(request.getTelefono());
        conductor.setNumeroLicencia(request.getNumeroLicencia());
        conductor.setFechaVencimientoLicencia(request.getFechaVencimientoLicencia());
    }

}