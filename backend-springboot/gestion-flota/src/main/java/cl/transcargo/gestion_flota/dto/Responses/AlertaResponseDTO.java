package cl.transcargo.gestion_flota.dto.Responses;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlertaResponseDTO {

    private Long id;

    private Long vehiculoId;

    private String patente;

    private String tipo;

    private String mensaje;

    private LocalDateTime fechaGeneracion;

    private Boolean leida;

}