package cl.transcargo.gestion_flota.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "conductores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Conductor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 15)
    private String rut;

    @Column(nullable = false, length = 100)
    private String nombre;

    private String telefono;

    @Column(name = "numero_licencia", nullable = false)
    private Integer numeroLicencia;

    @Column(name = "clase_licencia")
    private String claseLicencia;

    @Column(name = "fecha_vencimiento_licencia")
    private LocalDate fechaVencimientoLicencia;

    @OneToOne
    @JoinColumn(name = "usuario_id", unique = true)
    private Usuario usuario;

    @OneToMany(mappedBy = "conductor")
    private java.util.List<Vehiculo> vehiculos;
}