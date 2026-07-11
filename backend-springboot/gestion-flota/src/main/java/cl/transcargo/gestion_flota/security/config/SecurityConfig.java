package cl.transcargo.gestion_flota.security.config;

import cl.transcargo.gestion_flota.security.jwt.JwtFilter;
import cl.transcargo.gestion_flota.security.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.http.HttpMethod;
import java.util.List;
/**
 * Configuración principal de Spring Security.
 *
 * Define:
 * - Rutas públicas y protegidas.
 * - Filtro JWT.
 * - AuthenticationProvider.
 * - PasswordEncoder.
 */
@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(JwtFilter jwtFilter,
                          CustomUserDetailsService userDetailsService) {

        this.jwtFilter = jwtFilter;
        this.userDetailsService = userDetailsService;

    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(
                List.of("http://localhost:3000"));

        configuration.setAllowedMethods(
                List.of("GET","POST","PUT","DELETE","OPTIONS"));

        configuration.setAllowedHeaders(
                List.of("*"));

        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;

    }

    /**
     * Configuración de seguridad de la aplicación.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors(cors -> {})
                .csrf(csrf -> csrf.disable()
                )

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth

                        // Login
                        .requestMatchers("/auth/**")
                        .permitAll()

                        //----------------------------------
                        // USUARIOS
                        //----------------------------------

                        .requestMatchers("/usuarios/**")
                        .hasRole("ADMIN")

                        //----------------------------------
                        // CONDUCTORES
                        //----------------------------------

                        .requestMatchers(HttpMethod.GET, "/conductores/**")
                        .hasAnyRole("ADMIN", "OPERADOR")

                        .requestMatchers(HttpMethod.POST, "/conductores/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/conductores/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE, "/conductores/**")
                        .hasRole("ADMIN")

                        //----------------------------------
                        // VEHÍCULOS
                        //----------------------------------

                        .requestMatchers(HttpMethod.GET, "/vehiculos/**")
                        .hasAnyRole("ADMIN","OPERADOR","CONDUCTOR")

                        .requestMatchers(HttpMethod.POST, "/vehiculos/**")
                        .hasAnyRole("ADMIN","OPERADOR")

                        .requestMatchers(HttpMethod.PUT, "/vehiculos/**")
                        .hasAnyRole("ADMIN","OPERADOR")

                        .requestMatchers(HttpMethod.DELETE, "/vehiculos/**")
                        .hasRole("ADMIN")

                        //----------------------------------
                        // MANTENCIONES
                        //----------------------------------

                        .requestMatchers(HttpMethod.GET, "/mantenciones/**")
                        .hasAnyRole("ADMIN","OPERADOR","CONDUCTOR")

                        .requestMatchers(HttpMethod.POST, "/mantenciones/**")
                        .hasAnyRole("ADMIN","OPERADOR")

                        .requestMatchers(HttpMethod.PUT, "/mantenciones/**")
                        .hasAnyRole("ADMIN","OPERADOR")

                        .requestMatchers(HttpMethod.DELETE, "/mantenciones/**")
                        .hasRole("ADMIN")

                        //----------------------------------
                        // FALLAS
                        //----------------------------------

                        .requestMatchers(HttpMethod.GET, "/fallas/**")
                        .hasAnyRole("ADMIN","OPERADOR","CONDUCTOR")

                        .requestMatchers(HttpMethod.POST, "/fallas/**")
                        .hasAnyRole("ADMIN","OPERADOR")

                        .requestMatchers(HttpMethod.PUT, "/fallas/**")
                        .hasAnyRole("ADMIN","OPERADOR")

                        .requestMatchers(HttpMethod.DELETE, "/fallas/**")
                        .hasRole("ADMIN")

                        //----------------------------------
                        // DOCUMENTACIÓN
                        //----------------------------------

                        .requestMatchers(HttpMethod.GET,
                                "/soap/**",
                                "/revisiones-tecnicas/**",
                                "/permisos-circulacion/**",
                                "/emisiones-gases/**",
                                "/documentacion/**")
                        .hasAnyRole("ADMIN","OPERADOR","CONDUCTOR")

                        .requestMatchers(HttpMethod.POST,
                                "/soap/**",
                                "/revisiones-tecnicas/**",
                                "/permisos-circulacion/**",
                                "/emisiones-gases/**")
                        .hasAnyRole("ADMIN","OPERADOR")

                        .requestMatchers(HttpMethod.PUT,
                                "/soap/**",
                                "/revisiones-tecnicas/**",
                                "/permisos-circulacion/**",
                                "/emisiones-gases/**")
                        .hasAnyRole("ADMIN","OPERADOR")

                        .requestMatchers(HttpMethod.DELETE,
                                "/soap/**",
                                "/revisiones-tecnicas/**",
                                "/permisos-circulacion/**",
                                "/emisiones-gases/**")
                        .hasRole("ADMIN")

                        //----------------------------------

                        .anyRequest()
                        .authenticated()

                )

                .authenticationProvider(authenticationProvider())

                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }

    /**
     * Indica cómo Spring debe autenticar a los usuarios.
     *
     * Utiliza:
     * - Base de datos (CustomUserDetailsService)
     * - BCrypt para validar la contraseña.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();

        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());

        return provider;

    }

    /**
     * BCrypt utilizado para almacenar y validar contraseñas.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    /**
     * AuthenticationManager utilizado durante el login.
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        return configuration.getAuthenticationManager();
    }
}