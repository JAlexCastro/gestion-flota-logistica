package cl.transcargo.gestion_flota.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "revisiones_tecnicas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RevisionTecnica {

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