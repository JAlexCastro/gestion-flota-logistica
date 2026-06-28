package cl.transcargo.gestion_flota.dto.Responses;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SoapResponseDTO {

    private Long id;

    private Long vehiculoId;

    private String patente;

    private String aseguradora;

    private String numeroPoliza;

    private LocalDate fechaEmision;

    private LocalDate fechaVencimiento;

}