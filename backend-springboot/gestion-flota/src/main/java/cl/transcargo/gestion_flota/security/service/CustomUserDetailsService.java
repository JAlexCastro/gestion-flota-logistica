package cl.transcargo.gestion_flota.security.service;

import cl.transcargo.gestion_flota.entity.Usuario;
import cl.transcargo.gestion_flota.repository.RUsuario;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio encargado de cargar los usuarios desde la base de datos.
 *
 * Spring Security utiliza esta clase durante el proceso de autenticación
 * para obtener la información del usuario y sus permisos.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final RUsuario usuarioRepository;

    public CustomUserDetailsService(RUsuario usuarioRepository) {

        this.usuarioRepository = usuarioRepository;

    }

    /**
     * Busca un usuario por su nombre de usuario.
     *
     * Si el usuario existe, se crea un objeto UserDetails
     * que Spring Security utilizará para autenticarlo.
     */
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Usuario no encontrado")
                );

        return org.springframework.security.core.userdetails.User
                .builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .authorities(
                        List.of(
                                new SimpleGrantedAuthority(
                                        "ROLE_" + usuario.getRol()
                                )
                        )
                )
                .build();

    }

}