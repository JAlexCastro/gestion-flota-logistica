package cl.transcargo.gestion_flota.controller;

import cl.transcargo.gestion_flota.dto.ApiResponse;
import cl.transcargo.gestion_flota.dto.Requests.UsuarioRequestDTO;
import cl.transcargo.gestion_flota.dto.UsuarioResponseDTO;
import cl.transcargo.gestion_flota.service.IService.IUsuario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final IUsuario service;

    public UsuarioController(IUsuario service) {
        this.service = service;
    }

    // Listar usuarios
    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<UsuarioResponseDTO>>> listar() {

        ApiResponse<List<UsuarioResponseDTO>> response = ApiResponse
                .<List<UsuarioResponseDTO>>builder()
                .message("Lista de usuarios")
                .status(200)
                .data(service.listar())
                .build();

        return ResponseEntity.ok(response);
    }

    // Obtener usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UsuarioResponseDTO>> obtener(@PathVariable Long id) {

        ApiResponse<UsuarioResponseDTO> response = ApiResponse
                .<UsuarioResponseDTO>builder()
                .message("Usuario encontrado")
                .status(200)
                .data(service.obtener(id))
                .build();

        return ResponseEntity.ok(response);
    }

    // Crear usuario
    @PostMapping("/save")
    public ResponseEntity<ApiResponse<UsuarioResponseDTO>> guardar(
            @RequestBody UsuarioRequestDTO body) {

        ApiResponse<UsuarioResponseDTO> response = ApiResponse
                .<UsuarioResponseDTO>builder()
                .message("Usuario creado correctamente")
                .status(201)
                .data(service.crear(body))
                .build();

        return ResponseEntity.status(201).body(response);
    }

    // Actualizar usuario
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<UsuarioResponseDTO>> actualizar(
            @PathVariable Long id,
            @RequestBody UsuarioRequestDTO body) {

        ApiResponse<UsuarioResponseDTO> response = ApiResponse
                .<UsuarioResponseDTO>builder()
                .message("Usuario actualizado correctamente")
                .status(200)
                .data(service.actualizar(id, body))
                .build();

        return ResponseEntity.ok(response);
    }

    // Eliminar usuario
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {

        service.eliminar(id);

        ApiResponse<Void> response = ApiResponse
                .<Void>builder()
                .message("Usuario eliminado correctamente")
                .status(200)
                .data(null)
                .build();

        return ResponseEntity.ok(response);
    }

}