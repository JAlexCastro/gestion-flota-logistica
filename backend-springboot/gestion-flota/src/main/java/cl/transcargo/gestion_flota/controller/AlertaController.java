package cl.transcargo.gestion_flota.controller;

import cl.transcargo.gestion_flota.dto.ApiResponse;
import cl.transcargo.gestion_flota.dto.Requests.AlertaRequestDTO;
import cl.transcargo.gestion_flota.dto.Responses.AlertaResponseDTO;
import cl.transcargo.gestion_flota.service.IService.IAlerta;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alertas")
public class AlertaController {

    private final IAlerta service;

    public AlertaController(IAlerta service) {
        this.service = service;
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<AlertaResponseDTO>>> listar() {

        ApiResponse<List<AlertaResponseDTO>> response = ApiResponse
                .<List<AlertaResponseDTO>>builder()
                .message("Lista de alertas")
                .status(200)
                .data(service.listar())
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AlertaResponseDTO>> obtener(@PathVariable Long id) {

        ApiResponse<AlertaResponseDTO> response = ApiResponse
                .<AlertaResponseDTO>builder()
                .message("Alerta encontrada")
                .status(200)
                .data(service.obtener(id))
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse<AlertaResponseDTO>> guardar(
            @RequestBody AlertaRequestDTO body) {

        ApiResponse<AlertaResponseDTO> response = ApiResponse
                .<AlertaResponseDTO>builder()
                .message("Alerta creada correctamente")
                .status(201)
                .data(service.crear(body))
                .build();

        return ResponseEntity.status(201).body(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<AlertaResponseDTO>> actualizar(
            @PathVariable Long id,
            @RequestBody AlertaRequestDTO body) {

        ApiResponse<AlertaResponseDTO> response = ApiResponse
                .<AlertaResponseDTO>builder()
                .message("Alerta actualizada correctamente")
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
                .message("Alerta eliminada correctamente")
                .status(200)
                .data(null)
                .build();

        return ResponseEntity.ok(response);
    }
}