package cl.transcargo.gestion_flota.controller;

import cl.transcargo.gestion_flota.dto.ApiResponse;
import cl.transcargo.gestion_flota.dto.Requests.VehiculoRequestDTO;
import cl.transcargo.gestion_flota.dto.Responses.VehiculoResponseDTO;
import cl.transcargo.gestion_flota.service.ServiceImpl.VehiculoServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehiculo")
public class Vehiculo {

    private final VehiculoServiceImpl service;

    Vehiculo(VehiculoServiceImpl service) {
        this.service = service;
    }

    // Listar vehiculo
    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<VehiculoResponseDTO>>> listaVehiculos(){

        ///ApiResponse<List<VehiculoResponseDTO>> response = new ApiResponse<>();
        ApiResponse<List<VehiculoResponseDTO>> response = ApiResponse
                .<List<VehiculoResponseDTO>>builder()
                        .message("Lista vehiculos")
                                .status(200)
                                        .data(service.listar())
                                                .build();

        return ResponseEntity.ok(response);

        //return service.listar();
    }

    // Guardar vehiculo
    @PostMapping("/save")
    public ResponseEntity<ApiResponse<VehiculoResponseDTO>> guardar(@RequestBody VehiculoRequestDTO body){

        ApiResponse<VehiculoResponseDTO> response = ApiResponse
                .<VehiculoResponseDTO>builder()
                .message("Vehiculo guardado")
                .status(201)
                .data(service.crear(body))
                .build();

        return ResponseEntity.ok(response);
    }

    // Obtener por id
    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<VehiculoResponseDTO>> obtenerId(@PathVariable Long id){
        ApiResponse<VehiculoResponseDTO> response = ApiResponse
                .<VehiculoResponseDTO>builder()
                .message("Vehiculo #: " + id)
                .status(200)
                .data(service.obtener(id))
                .build();

        return ResponseEntity.ok(response);
    }

    // Actualizar
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<VehiculoResponseDTO>> actualizar(
            @PathVariable Long id,
            @RequestBody VehiculoRequestDTO body) {

        ApiResponse<VehiculoResponseDTO> response = ApiResponse
                .<VehiculoResponseDTO>builder()
                .message("Vehículo actualizado correctamente")
                .status(200)
                .data(service.actualizar(id, body))
                .build();

        return ResponseEntity.ok(response);
    }



    // Eliminar vehuculo
    @DeleteMapping("delete/{id}")
    public ResponseEntity<ApiResponse<Void>> EliminarVehiculo(@PathVariable Long id){
        service.eliminar(id);
        ApiResponse<Void> response = ApiResponse
                .<Void>builder()
                .message("Vehículo eliminado correctamente")
                .status(200)
                .data(null)
                .build();

        return ResponseEntity.ok(response);
    }

}
