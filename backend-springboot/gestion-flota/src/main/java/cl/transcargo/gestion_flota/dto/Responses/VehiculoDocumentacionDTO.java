package cl.transcargo.gestion_flota.dto.Responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehiculoDocumentacionDTO {

    private Long id;

    private String marca;
    private String modelo;
    private String patente;
    private String nombre;

    private RevisionTecnicaResponseDTO revisionTecnica;

    private EmisionGasesResponseDTO emisionGases;

    private SoapResponseDTO soap;

    private PermisoCirculacionResponseDTO permisoCirculacion;

}