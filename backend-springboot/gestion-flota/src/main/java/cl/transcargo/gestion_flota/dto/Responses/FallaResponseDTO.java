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
public class FallaResponseDTO {

    private Long id;

    private Long vehiculoId;

    private String patente;

    private LocalDate fecha;

    private String descripcion;

    private String prioridad;

    private String estado;

}