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
public class FallaRequestDTO {

    private Long vehiculoId;

    private LocalDate fecha;

    private String descripcion;

    private String prioridad;

    private String estado;

}