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
public class ConductorRequestDTO {

    private String rut;

    private String nombre;

    private String telefono;

    private String numeroLicencia;

    private LocalDate fechaVencimientoLicencia;

    private Long usuarioId;

}