package cl.transcargo.gestion_flota.controller;

import cl.transcargo.gestion_flota.dto.ApiResponse;
import cl.transcargo.gestion_flota.dto.Responses.VehiculoDocumentacionDTO;
import cl.transcargo.gestion_flota.service.ServiceImpl.DocumentacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/documentacion")
@RequiredArgsConstructor
public class DocumentacionController {

    private final DocumentacionService service;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<VehiculoDocumentacionDTO>>> listar(){

        ApiResponse<List<VehiculoDocumentacionDTO>> response =
                ApiResponse.<List<VehiculoDocumentacionDTO>>builder()

                        .message("Lista documentación")

                        .status(200)

                        .data(service.listar())

                        .build();

        return ResponseEntity.ok(response);

    }

}