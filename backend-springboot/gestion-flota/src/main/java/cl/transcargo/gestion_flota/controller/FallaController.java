package cl.transcargo.gestion_flota.controller;

import cl.transcargo.gestion_flota.dto.ApiResponse;
import cl.transcargo.gestion_flota.dto.Requests.FallaRequestDTO;
import cl.transcargo.gestion_flota.dto.Responses.FallaResponseDTO;
import cl.transcargo.gestion_flota.service.IService.IFalla;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fallas")
public class FallaController {

    private final IFalla service;

    public FallaController(IFalla service) {
        this.service = service;
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<FallaResponseDTO>>> listar() {

        ApiResponse<List<FallaResponseDTO>> response = ApiResponse
                .<List<FallaResponseDTO>>builder()
                .message("Lista de fallas")
                .status(200)
                .data(service.listar())
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<FallaResponseDTO>> obtener(@PathVariable Long id) {

        ApiResponse<FallaResponseDTO> response = ApiResponse
                .<FallaResponseDTO>builder()
                .message("Falla encontrada")
                .status(200)
                .data(service.obtener(id))
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse<FallaResponseDTO>> guardar(@RequestBody FallaRequestDTO body) {

        ApiResponse<FallaResponseDTO> response = ApiResponse
                .<FallaResponseDTO>builder()
                .message("Falla registrada correctamente")
                .status(201)
                .data(service.crear(body))
                .build();

        return ResponseEntity.status(201).body(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<FallaResponseDTO>> actualizar(
            @PathVariable Long id,
            @RequestBody FallaRequestDTO body) {

        ApiResponse<FallaResponseDTO> response = ApiResponse
                .<FallaResponseDTO>builder()
                .message("Falla actualizada correctamente")
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
                .message("Falla eliminada correctamente")
                .status(200)
                .data(null)
                .build();

        return ResponseEntity.ok(response);
    }

}
