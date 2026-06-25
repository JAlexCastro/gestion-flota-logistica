package cl.transcargo.gestion_flota.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "vehiculos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 10)
    private String patente;

    @Column(nullable = false, length = 50)
    private String marca;

    @Column(nullable = false, length = 50)
    private String modelo;

    @Column(nullable = false)
    private Integer anio;

    @Column(name = "kilometraje_actual", nullable = false)
    private Integer kilometrajeActual;

    private String estado;

    @ManyToOne
    @JoinColumn(name = "conductor_id", nullable = false)
    private Conductor conductor;

    @OneToMany(mappedBy = "vehiculo")
    private List<Mantencion> mantenciones;

    @OneToMany(mappedBy = "vehiculo")
    private List<Falla> fallas;

    @OneToMany(mappedBy = "vehiculo")
    private List<Soap> soaps;

    @OneToMany(mappedBy = "vehiculo")
    private List<PermisoCirculacion> permisosCirculacion;

    @OneToMany(mappedBy = "vehiculo")
    private List<RevisionTecnica> revisionesTecnicas;

    @OneToMany(mappedBy = "vehiculo")
    private List<EmisionesGases> emisionesGases;

    @OneToMany(mappedBy = "vehiculo")
    private List<Alertas> alertas;
}