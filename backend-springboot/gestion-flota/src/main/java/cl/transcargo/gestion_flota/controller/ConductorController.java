package cl.transcargo.gestion_flota.controller;

import cl.transcargo.gestion_flota.dto.ApiResponse;
import cl.transcargo.gestion_flota.dto.Requests.ConductorRequestDTO;
import cl.transcargo.gestion_flota.dto.Responses.ConductorResponseDTO;
import cl.transcargo.gestion_flota.service.IService.IConductor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/conductores")

public class ConductorController {

    private final IConductor service;

    public ConductorController(IConductor service) {
        this.service = service;
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<ConductorResponseDTO>>> listar() {

        ApiResponse<List<ConductorResponseDTO>> response = ApiResponse
                .<List<ConductorResponseDTO>>builder()
                .message("Lista de conductores")
                .status(200)
                .data(service.listar())
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ConductorResponseDTO>> obtener(@PathVariable Long id) {

        ApiResponse<ConductorResponseDTO> response = ApiResponse
                .<ConductorResponseDTO>builder()
                .message("Conductor encontrado")
                .status(200)
                .data(service.obtener(id))
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse<ConductorResponseDTO>> guardar(@RequestBody ConductorRequestDTO body) {

        ApiResponse<ConductorResponseDTO> response = ApiResponse
                .<ConductorResponseDTO>builder()
                .message("Conductor creado correctamente")
                .status(201)
                .data(service.crear(body))
                .build();

        return ResponseEntity.status(201).body(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<ConductorResponseDTO>> actualizar(
            @PathVariable Long id,
            @RequestBody ConductorRequestDTO body) {

        ApiResponse<ConductorResponseDTO> response = ApiResponse
                .<ConductorResponseDTO>builder()
                .message("Conductor actualizado correctamente")
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
                .message("Conductor eliminado correctamente")
                .status(200)
                .data(null)
                .build();

        return ResponseEntity.ok(response);
    }
}