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
public class ConductorResponseDTO {

    private Long id;

    private String rut;

    private String nombre;

    private String telefono;

    private Integer numeroLicencia;

    private String claseLicencia;

    private LocalDate fechaVencimientoLicencia;

    private Long usuarioId;

    private String username;

}