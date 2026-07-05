package cl.transcargo.gestion_flota.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "soap")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Soap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vehiculo_id", nullable = false)
    private Vehiculo vehiculo;

    private String aseguradora;
    @Column(unique = true)
    private String numeroPoliza;

    private LocalDate fechaEmision;

    private LocalDate fechaVencimiento;
}