package cl.transcargo.gestion_flota.service.IService;

import cl.transcargo.gestion_flota.dto.Requests.SoapRequestDTO;
import cl.transcargo.gestion_flota.dto.Responses.SoapResponseDTO;

import java.awt.*;
import java.util.List;

public interface ISoap {

    List<SoapResponseDTO> listar();

    SoapResponseDTO obtener(Long id);

    SoapResponseDTO crear(SoapRequestDTO request);

    SoapResponseDTO actualizar(Long id, SoapRequestDTO request);

    void eliminar(Long id);

}