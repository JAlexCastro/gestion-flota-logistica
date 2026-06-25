package cl.transcargo.gestion_flota.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "mantenciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Mantencion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vehiculo_id", nullable = false)
    private Vehiculo vehiculo;

    private LocalDate fecha;

    private Integer kilometraje;

    private String tipo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    private String taller;
}