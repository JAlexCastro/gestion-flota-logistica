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

    @ManyToOne
    @JoinColumn(name = "vehiculo_id", nullable = false)
    private Vehiculo vehiculo;

    private LocalDate fechaRevision;

    private LocalDate fechaVencimiento;

    private String resultado;
}