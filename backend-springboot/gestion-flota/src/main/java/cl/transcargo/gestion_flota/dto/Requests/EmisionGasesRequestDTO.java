package cl.transcargo.gestion_flota.dto.Requests;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmisionGasesRequestDTO {

    private Long vehiculoId;

    private LocalDate fechaRevision;

    private LocalDate fechaVencimiento;

    private String resultado;

}