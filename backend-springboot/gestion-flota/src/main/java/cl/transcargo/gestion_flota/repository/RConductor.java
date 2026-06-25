package cl.transcargo.gestion_flota.repository;

import cl.transcargo.gestion_flota.entity.Conductor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RConductor extends JpaRepository<Conductor, Long> {}
