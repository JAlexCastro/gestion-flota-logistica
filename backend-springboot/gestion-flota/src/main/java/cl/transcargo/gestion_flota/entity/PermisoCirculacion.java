package cl.transcargo.gestion_flota.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;


@Table(name = "permisos_circulacion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PermisoCirculacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vehiculo_id", nullable = false, unique = true)
    private Vehiculo vehiculo;

    private LocalDate fechaEmision;

    private LocalDate fechaVencimiento;

    private String estado;
}