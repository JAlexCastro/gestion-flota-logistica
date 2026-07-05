package cl.transcargo.gestion_flota.repository;

import cl.transcargo.gestion_flota.entity.RevisionTecnica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RRevisionTecnica extends JpaRepository<RevisionTecnica, Long> {
    Optional<RevisionTecnica> findByVehiculoId(Long vehiculoId);
}
