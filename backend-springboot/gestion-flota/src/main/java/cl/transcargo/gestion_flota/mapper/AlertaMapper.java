package cl.transcargo.gestion_flota.mapper;

import cl.transcargo.gestion_flota.dto.Requests.AlertaRequestDTO;
import cl.transcargo.gestion_flota.dto.Responses.AlertaResponseDTO;
import cl.transcargo.gestion_flota.entity.Alertas;
import cl.transcargo.gestion_flota.entity.Vehiculo;
import org.springframework.stereotype.Component;

@Component
public class AlertaMapper {

    public Alertas toEntity(AlertaRequestDTO request,
                            Vehiculo vehiculo){

        Alertas alerta = new Alertas();

        alerta.setVehiculo(vehiculo);
        alerta.setTipo(request.getTipo());
        alerta.setMensaje(request.getMensaje());
        alerta.setFechaGeneracion(request.getFechaGeneracion());
        alerta.setLeida(request.getLeida());

        return alerta;
    }

    public AlertaResponseDTO toResponse(Alertas alerta){

        AlertaResponseDTO response = new AlertaResponseDTO();

        response.setId(alerta.getId());

        if(alerta.getVehiculo() != null){
            response.setVehiculoId(alerta.getVehiculo().getId());
            response.setPatente(alerta.getVehiculo().getPatente());
        }

        response.setTipo(alerta.getTipo());
        response.setMensaje(alerta.getMensaje());
        response.setFechaGeneracion(alerta.getFechaGeneracion());
        response.setLeida(alerta.getLeida());

        return response;
    }

    public void updateEntity(Alertas alerta,
                             AlertaRequestDTO request,
                             Vehiculo vehiculo){

        alerta.setVehiculo(vehiculo);
        alerta.setTipo(request.getTipo());
        alerta.setMensaje(request.getMensaje());
        alerta.setFechaGeneracion(request.getFechaGeneracion());
        alerta.setLeida(request.getLeida());

    }

}