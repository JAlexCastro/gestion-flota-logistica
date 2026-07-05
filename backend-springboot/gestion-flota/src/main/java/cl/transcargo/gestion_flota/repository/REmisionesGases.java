package cl.transcargo.gestion_flota.repository;

import cl.transcargo.gestion_flota.entity.EmisionesGases;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface REmisionesGases extends JpaRepository<EmisionesGases, Long> {
    Optional<EmisionesGases> findByVehiculoId(Long vehiculoId);
    boolean existsByVehiculoId(Long vehiculoId);
}
