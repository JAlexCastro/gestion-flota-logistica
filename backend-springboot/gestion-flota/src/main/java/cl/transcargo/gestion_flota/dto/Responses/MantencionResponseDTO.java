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
public class MantencionResponseDTO {

    private Long id;
    private Long vehiculoId;
    private String patente;
    private LocalDate fecha;
    private Integer kilometraje;
    private Integer kilometrajeActual;
    private Integer kilometrajeRestantes;
    private String tipo;
    private String descripcion;
    private String taller;
    private String estado;
}