package cl.transcargo.gestion_flota.dto.Requests;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlertaRequestDTO {

    private Long vehiculoId;

    private String tipo;

    private String mensaje;

    private LocalDateTime fechaGeneracion;

    private Boolean leida;

}