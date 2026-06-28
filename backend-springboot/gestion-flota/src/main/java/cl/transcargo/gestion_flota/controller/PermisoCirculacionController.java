package cl.transcargo.gestion_flota.controller;

import cl.transcargo.gestion_flota.dto.ApiResponse;
import cl.transcargo.gestion_flota.dto.Requests.PermisoCirculacionRequestDTO;
import cl.transcargo.gestion_flota.dto.Responses.PermisoCirculacionResponseDTO;
import cl.transcargo.gestion_flota.service.IService.IPermisoCirculacion;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permisos-circulacion")
public class PermisoCirculacionController {

    private final IPermisoCirculacion service;

    public PermisoCirculacionController(IPermisoCirculacion service){
        this.service = service;
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<PermisoCirculacionResponseDTO>>> listar() {

        ApiResponse<List<PermisoCirculacionResponseDTO>> response = ApiResponse
                .<List<PermisoCirculacionResponseDTO>>builder()
                .message("Lista de permisos de circulación")
                .status(200)
                .data(service.listar())
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PermisoCirculacionResponseDTO>> obtener(@PathVariable Long id) {

        ApiResponse<PermisoCirculacionResponseDTO> response = ApiResponse
                .<PermisoCirculacionResponseDTO>builder()
                .message("Permiso de circulación encontrado")
                .status(200)
                .data(service.obtener(id))
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse<PermisoCirculacionResponseDTO>> guardar(
            @RequestBody PermisoCirculacionRequestDTO body) {

        ApiResponse<PermisoCirculacionResponseDTO> response = ApiResponse
                .<PermisoCirculacionResponseDTO>builder()
                .message("Permiso de circulación creado correctamente")
                .status(201)
                .data(service.crear(body))
                .build();

        return ResponseEntity.status(201).body(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<PermisoCirculacionResponseDTO>> actualizar(
            @PathVariable Long id,
            @RequestBody PermisoCirculacionRequestDTO body) {

        ApiResponse<PermisoCirculacionResponseDTO> response = ApiResponse
                .<PermisoCirculacionResponseDTO>builder()
                .message("Permiso de circulación actualizado correctamente")
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
                .message("Permiso de circulación eliminado correctamente")
                .status(200)
                .data(null)
                .build();

        return ResponseEntity.ok(response);
    }
}