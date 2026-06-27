package cl.transcargo.gestion_flota.repository;

import cl.transcargo.gestion_flota.entity.EmisionesGases;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RREmisionesGases extends JpaRepository<EmisionesGases, Long> {}
