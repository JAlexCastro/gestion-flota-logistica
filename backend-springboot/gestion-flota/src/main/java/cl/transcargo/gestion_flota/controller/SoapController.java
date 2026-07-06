package cl.transcargo.gestion_flota.controller;

import cl.transcargo.gestion_flota.dto.ApiResponse;
import cl.transcargo.gestion_flota.dto.Requests.SoapRequestDTO;
import cl.transcargo.gestion_flota.dto.Responses.SoapResponseDTO;
import cl.transcargo.gestion_flota.service.IService.ISoap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
    @RequestMapping("/soap")
    public class SoapController {

        private final ISoap service;

        public SoapController(ISoap service) {
            this.service = service;
        }

        @GetMapping("/list")
        public ResponseEntity<ApiResponse<List<SoapResponseDTO>>> listar() {

            ApiResponse<List<SoapResponseDTO>> response = ApiResponse
                    .<List<SoapResponseDTO>>builder()
                    .message("Lista de SOAP")
                    .status(200)
                    .data(service.listar())
                    .build();

            return ResponseEntity.ok(response);
        }

        @GetMapping("/{id}")
        public ResponseEntity<ApiResponse<SoapResponseDTO>> obtener(@PathVariable Long id) {

            ApiResponse<SoapResponseDTO> response = ApiResponse
                    .<SoapResponseDTO>builder()
                    .message("SOAP encontrado")
                    .status(200)
                    .data(service.obtener(id))
                    .build();

            return ResponseEntity.ok(response);
        }

        @PostMapping("/save")
        public ResponseEntity<ApiResponse<SoapResponseDTO>> guardar(
                @RequestBody SoapRequestDTO body) {

            ApiResponse<SoapResponseDTO> response = ApiResponse
                    .<SoapResponseDTO>builder()
                    .message("SOAP registrado correctamente")
                    .status(201)
                    .data(service.crear(body))
                    .build();

            return ResponseEntity.status(201).body(response);
        }

        @PutMapping("/update/{id}")
        public ResponseEntity<ApiResponse<SoapResponseDTO>> actualizar(
                @PathVariable Long id,
                @RequestBody SoapRequestDTO body) {

            ApiResponse<SoapResponseDTO> response = ApiResponse
                    .<SoapResponseDTO>builder()
                    .message("SOAP actualizado correctamente")
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
                    .message("SOAP eliminado correctamente")
                    .status(200)
                    .data(null)
                    .build();

            return ResponseEntity.ok(response);
        }

    }