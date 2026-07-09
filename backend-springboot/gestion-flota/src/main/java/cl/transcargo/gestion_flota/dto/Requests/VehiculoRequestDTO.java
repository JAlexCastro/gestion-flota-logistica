package cl.transcargo.gestion_flota.dto.Requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VehiculoRequestDTO {

    private String patente;
    private String marca;
    private String modelo;
    private String nombre;
    private Integer anio;
    private Integer kilometrajeActual;
    private String estado;
    //private Long conductorId;

}