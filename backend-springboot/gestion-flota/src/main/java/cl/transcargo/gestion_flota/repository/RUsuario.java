package cl.transcargo.gestion_flota.repository;


import cl.transcargo.gestion_flota.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RUsuario extends JpaRepository<Usuario, Long> {}
