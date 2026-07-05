package cl.transcargo.gestion_flota.repository;

import cl.transcargo.gestion_flota.entity.PermisoCirculacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RPermisoCirculacion extends JpaRepository<PermisoCirculacion, Long> {
    Optional<PermisoCirculacion> findByVehiculoId(Long vehiculoId);
}
