package cl.transcargo.gestion_flota.controller;

import cl.transcargo.gestion_flota.dto.ApiResponse;
import cl.transcargo.gestion_flota.dto.Requests.RevisionTecnicaRequestDTO;
import cl.transcargo.gestion_flota.dto.Responses.RevisionTecnicaResponseDTO;
import cl.transcargo.gestion_flota.service.IService.IRevisionTecnica;
import cl.transcargo.gestion_flota.service.ServiceImpl.RevisionTecnicaServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/revisiones-tecnicas")
public class RevisionTecnicaController {

    private final IRevisionTecnica service;

    public RevisionTecnicaController(IRevisionTecnica service){
        this.service = service;
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<RevisionTecnicaResponseDTO>>> listar() {

        ApiResponse<List<RevisionTecnicaResponseDTO>> response = ApiResponse
                .<List<RevisionTecnicaResponseDTO>>builder()
                .message("Lista de revisiones técnicas")
                .status(200)
                .data(service.listar())
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RevisionTecnicaResponseDTO>> obtener(@PathVariable Long id) {

        ApiResponse<RevisionTecnicaResponseDTO> response = ApiResponse
                .<RevisionTecnicaResponseDTO>builder()
                .message("Revisión técnica encontrada")
                .status(200)
                .data(service.obtener(id))
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse<RevisionTecnicaResponseDTO>> guardar(
            @RequestBody RevisionTecnicaRequestDTO body) {

        ApiResponse<RevisionTecnicaResponseDTO> response = ApiResponse
                .<RevisionTecnicaResponseDTO>builder()
                .message("Revisión técnica creada correctamente")
                .status(201)
                .data(service.crear(body))
                .build();

        return ResponseEntity.status(201).body(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<RevisionTecnicaResponseDTO>> actualizar(
            @PathVariable Long id,
            @RequestBody RevisionTecnicaRequestDTO body) {

        ApiResponse<RevisionTecnicaResponseDTO> response = ApiResponse
                .<RevisionTecnicaResponseDTO>builder()
                .message("Revisión técnica actualizada correctamente")
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
                .message("Revisión técnica eliminada correctamente")
                .status(200)
                .data(null)
                .build();

        return ResponseEntity.ok(response);
    }

}