package cl.transcargo.gestion_flota.security.jwt;

import cl.transcargo.gestion_flota.security.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro encargado de validar el JWT en cada petición HTTP.
 * Si el token es válido, autentica automáticamente al usuario
 * dentro del contexto de Spring Security.
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    public JwtFilter(JwtService jwtService,
                     CustomUserDetailsService userDetailsService) {

        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;

    }

    /**
     * Esto se ejecuta automáticamente en cada petición HTTP.
     *.
     * Flujo:
     * 1. Obtiene el token del encabezado Authorization.
     * 2. Extrae el nombre del usuario desde el JWT.
     * 3. Consulta el usuario en la base de datos.
     * 4. Valida el token.
     * 5. Si es válido, autentica al usuario en Spring Security.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // Obtiene el encabezado Authorization
        final String authHeader = request.getHeader("Authorization");

        // Si no existe token o no comienza con "Bearer",
        // simplemente continúa con la petición.
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);
            return;

        }

        // Elimina el prefijo "Bearer "
        String token = authHeader.substring(7);

        // Obtiene el username almacenado en el JWT
        String username = jwtService.extraerUsername(token);

        // Si el usuario existe y aún no ha sido autenticado
        if (username != null &&
                SecurityContextHolder.getContext().getAuthentication() == null) {

            // Busca el usuario en la base de datos
            UserDetails userDetails =
                    userDetailsService.loadUserByUsername(username);

            // Verifica que el token sea válido
            if (jwtService.esTokenValido(token, userDetails)) {

                // Crea la autenticación para Spring Security
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                // Agrega información adicional de la petición
                authentication.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );

                // Guarda el usuario autenticado en el contexto
                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authentication);

            }

        }

        // Continúa con el resto de filtros de Spring
        filterChain.doFilter(request, response);

    }

}