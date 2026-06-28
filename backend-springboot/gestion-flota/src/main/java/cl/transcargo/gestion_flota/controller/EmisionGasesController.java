package cl.transcargo.gestion_flota.controller;

import cl.transcargo.gestion_flota.dto.ApiResponse;
import cl.transcargo.gestion_flota.dto.Requests.EmisionGasesRequestDTO;
import cl.transcargo.gestion_flota.dto.Responses.EmisionGasesResponseDTO;
import cl.transcargo.gestion_flota.service.IService.IEmisionGases;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emisiones-gases")
public class EmisionGasesController {

    private final IEmisionGases service;

    public EmisionGasesController(IEmisionGases service) {
        this.service = service;
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<EmisionGasesResponseDTO>>> listar() {

        ApiResponse<List<EmisionGasesResponseDTO>> response = ApiResponse
                .<List<EmisionGasesResponseDTO>>builder()
                .message("Lista de emisiones de gases")
                .status(200)
                .data(service.listar())
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EmisionGasesResponseDTO>> obtener(@PathVariable Long id) {

        ApiResponse<EmisionGasesResponseDTO> response = ApiResponse
                .<EmisionGasesResponseDTO>builder()
                .message("Registro de emisión encontrado")
                .status(200)
                .data(service.obtener(id))
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse<EmisionGasesResponseDTO>> guardar(
            @RequestBody EmisionGasesRequestDTO body) {

        ApiResponse<EmisionGasesResponseDTO> response = ApiResponse
                .<EmisionGasesResponseDTO>builder()
                .message("Registro de emisión creado correctamente")
                .status(201)
                .data(service.crear(body))
                .build();

        return ResponseEntity.status(201).body(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<EmisionGasesResponseDTO>> actualizar(
            @PathVariable Long id,
            @RequestBody EmisionGasesRequestDTO body) {

        ApiResponse<EmisionGasesResponseDTO> response = ApiResponse
                .<EmisionGasesResponseDTO>builder()
                .message("Registro de emisión actualizado correctamente")
                .status(200)
                .data(service.actualizar(id, body))
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {

        service.eliminar(id);

        ApiResponse<Void> response = ApiResponse
                .<Void>builder()
                .message("Registro de emisión eliminado correctamente")
                .status(200)
                .data(null)
                .build();

        return ResponseEntity.ok(response);
    }

}