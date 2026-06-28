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
public class MantencionRequestDTO {

    private Long vehiculoId;

    private LocalDate fecha;

    private Integer kilometraje;

    private String tipo;

    private String descripcion;

    private String taller;

}