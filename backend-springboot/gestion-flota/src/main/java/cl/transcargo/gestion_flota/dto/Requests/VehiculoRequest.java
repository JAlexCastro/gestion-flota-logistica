package cl.transcargo.gestion_flota.dto.Requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VehiculoRequest {

    private String patente;
    private String marca;
    private String modelo;
    private Integer anio;
    private Integer kilometrajeActual;
    private Long conductorId;

}