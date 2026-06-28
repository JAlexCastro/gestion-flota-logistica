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
public class PermisoCirculacionRequestDTO {

    private Long vehiculoId;

    private LocalDate fechaEmision;

    private LocalDate fechaVencimiento;

    private String estado;

}