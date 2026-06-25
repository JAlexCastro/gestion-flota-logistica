package cl.transcargo.gestion_flota.repository;

import cl.transcargo.gestion_flota.entity.Mantencion;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RMantencion extends JpaRepository<Mantencion, Long> {}
