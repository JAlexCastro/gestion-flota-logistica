package cl.transcargo.gestion_flota.repository;

import cl.transcargo.gestion_flota.entity.Falla;
import cl.transcargo.gestion_flota.entity.Soap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RSoap extends JpaRepository<Soap, Long> {
    Optional<Soap> findByVehiculoId(Long vehiculoId);
}
