package cl.transcargo.gestion_flota.mapper;

import cl.transcargo.gestion_flota.dto.Requests.SoapRequestDTO;
import cl.transcargo.gestion_flota.dto.Responses.SoapResponseDTO;
import cl.transcargo.gestion_flota.entity.Soap;
import cl.transcargo.gestion_flota.entity.Vehiculo;
import org.springframework.stereotype.Component;

@Component
public class SoapMapper {

    public Soap toEntity(SoapRequestDTO request,
                         Vehiculo vehiculo){

        Soap soap = new Soap();

        soap.setVehiculo(vehiculo);
        soap.setAseguradora(request.getAseguradora());
        soap.setNumeroPoliza(request.getNumeroPoliza());
        soap.setFechaEmision(request.getFechaEmision());
        soap.setFechaVencimiento(request.getFechaVencimiento());

        return soap;
    }

    public SoapResponseDTO toResponse(Soap soap){

        SoapResponseDTO response = new SoapResponseDTO();

        response.setId(soap.getId());

        if(soap.getVehiculo() != null){
            response.setVehiculoId(soap.getVehiculo().getId());
            response.setPatente(soap.getVehiculo().getPatente());
        }

        response.setAseguradora(soap.getAseguradora());
        response.setNumeroPoliza(soap.getNumeroPoliza());
        response.setFechaEmision(soap.getFechaEmision());
        response.setFechaVencimiento(soap.getFechaVencimiento());

        return response;
    }

    public void updateEntity(Soap soap,
                             SoapRequestDTO request,
                             Vehiculo vehiculo){

        soap.setVehiculo(vehiculo);
        soap.setAseguradora(request.getAseguradora());
        soap.setNumeroPoliza(request.getNumeroPoliza());
        soap.setFechaEmision(request.getFechaEmision());
        soap.setFechaVencimiento(request.getFechaVencimiento());

    }

}