package cl.transcargo.gestion_flota.repository;

import cl.transcargo.gestion_flota.entity.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RVehiculo extends JpaRepository<Vehiculo, Long> {}

