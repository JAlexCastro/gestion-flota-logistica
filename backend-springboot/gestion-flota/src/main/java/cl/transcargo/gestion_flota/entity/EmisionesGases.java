package cl.transcargo.gestion_flota.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "emisiones_gases")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmisionesGases {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(
            name = "vehiculo_id",
            nullable = false,
            unique = true
    )
    private Vehiculo vehiculo;

    private LocalDate fechaRevision;

    private LocalDate fechaVencimiento;

    private String resultado;

}