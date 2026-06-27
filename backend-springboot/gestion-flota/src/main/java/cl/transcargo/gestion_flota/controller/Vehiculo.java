package cl.transcargo.gestion_flota.controller;

import cl.transcargo.gestion_flota.dto.Responses.VehiculoResponse;
import cl.transcargo.gestion_flota.service.ServiceImpl.VehiculoServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/vehiculo")
public class Vehiculo {

    private final VehiculoServiceImpl service;

    Vehiculo(VehiculoServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/list")
    public List<VehiculoResponse> listaVehiculos(VehiculoResponse list){
        return service.listar();
    }
}
