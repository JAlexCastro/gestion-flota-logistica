package cl.transcargo.gestion_flota.repository;

import cl.transcargo.gestion_flota.entity.Falla;
import cl.transcargo.gestion_flota.entity.Soap;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RSoap extends JpaRepository<Soap, Long> {}
