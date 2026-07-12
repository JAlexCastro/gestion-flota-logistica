package cl.transcargo.gestion_flota.repository;

import cl.transcargo.gestion_flota.entity.Mantencion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RMantencion extends JpaRepository<Mantencion, Long> {
    Optional<Mantencion> findByVehiculoId(Long vehiculoId);
}
