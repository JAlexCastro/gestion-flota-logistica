package cl.transcargo.gestion_flota.repository;


import cl.transcargo.gestion_flota.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RUsuario extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
    List<Usuario> findByRolIn(List<String> roles);


}
