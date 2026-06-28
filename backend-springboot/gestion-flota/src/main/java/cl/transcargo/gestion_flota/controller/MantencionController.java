package cl.transcargo.gestion_flota.controller;

import cl.transcargo.gestion_flota.dto.ApiResponse;
import cl.transcargo.gestion_flota.dto.Requests.MantencionRequestDTO;
import cl.transcargo.gestion_flota.dto.Responses.MantencionResponseDTO;
import cl.transcargo.gestion_flota.service.IService.IMantencion;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mantenciones")
@RequiredArgsConstructor
public class MantencionController {

    private final IMantencion service;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<MantencionResponseDTO>>> listar() {

        ApiResponse<List<MantencionResponseDTO>> response = ApiResponse
                .<List<MantencionResponseDTO>>builder()
                .message("Lista de mantenciones")
                .status(200)
                .data(service.listar())
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MantencionResponseDTO>> obtener(@PathVariable Long id) {

        ApiResponse<MantencionResponseDTO> response = ApiResponse
                .<MantencionResponseDTO>builder()
                .message("Mantención encontrada")
                .status(200)
                .data(service.obtener(id))
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse<MantencionResponseDTO>> guardar(
            @RequestBody MantencionRequestDTO body) {

        ApiResponse<MantencionResponseDTO> response = ApiResponse
                .<MantencionResponseDTO>builder()
                .message("Mantención creada correctamente")
                .status(201)
                .data(service.crear(body))
                .build();

        return ResponseEntity.status(201).body(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<MantencionResponseDTO>> actualizar(
            @PathVariable Long id,
            @RequestBody MantencionRequestDTO body) {

        ApiResponse<MantencionResponseDTO> response = ApiResponse
                .<MantencionResponseDTO>builder()
                .message("Mantención actualizada correctamente")
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
                .message("Mantención eliminada correctamente")
                .status(200)
                .data(null)
                .build();

        return ResponseEntity.ok(response);
    }
}