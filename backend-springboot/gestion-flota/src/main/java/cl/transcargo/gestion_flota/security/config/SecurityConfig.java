package cl.transcargo.gestion_flota.security.config;


import cl.transcargo.gestion_flota.security.service.CustomUserDetailsService;
import cl.transcargo.gestion_flota.security.jwt.JwtFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import jakarta.annotation.PostConstruct;
import java.util.Arrays;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final CustomUserDetailsService userDetailsService;

    @Value("${app.cors.allowed-origins}")
    private String allowedOrigins;

    @PostConstruct
    public void mostrarConfiguracionCors() {

        System.out.println("==========================================");
        System.out.println("ORIGIN_CROSS recibido:");
        System.out.println(allowedOrigins);
        System.out.println("Orígenes permitidos:");

        Arrays.stream(allowedOrigins.split(","))
                .map(String::trim)
                .forEach(System.out::println);

        System.out.println("==========================================");
    }

    public SecurityConfig(JwtFilter jwtFilter,
                          CustomUserDetailsService userDetailsService) {

        this.jwtFilter = jwtFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(
                Arrays.stream(allowedOrigins.split(","))
                        .map(String::trim)
                        .toList()
        );

        configuration.setAllowedMethods(List.of(
                "GET",
                "POST",
                "PUT",
                "DELETE",
                "OPTIONS"
        ));

        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth

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
                        .hasAnyRole("ADMIN", "OPERADOR", "CONDUCTOR")

                        .requestMatchers(HttpMethod.POST, "/vehiculos/**")
                        .hasAnyRole("ADMIN", "OPERADOR")

                        .requestMatchers(HttpMethod.PUT, "/vehiculos/**")
                        .hasAnyRole("ADMIN", "OPERADOR")

                        .requestMatchers(HttpMethod.DELETE, "/vehiculos/**")
                        .hasRole("ADMIN")

                        //----------------------------------
                        // MANTENCIONES
                        //----------------------------------
                        .requestMatchers(HttpMethod.GET, "/mantenciones/**")
                        .hasAnyRole("ADMIN", "OPERADOR", "CONDUCTOR")

                        .requestMatchers(HttpMethod.POST, "/mantenciones/**")
                        .hasAnyRole("ADMIN", "OPERADOR")

                        .requestMatchers(HttpMethod.PUT, "/mantenciones/**")
                        .hasAnyRole("ADMIN", "OPERADOR")

                        .requestMatchers(HttpMethod.DELETE, "/mantenciones/**")
                        .hasRole("ADMIN")

                        //----------------------------------
                        // FALLAS
                        //----------------------------------
                        .requestMatchers(HttpMethod.GET, "/fallas/**")
                        .hasAnyRole("ADMIN", "OPERADOR", "CONDUCTOR")

                        .requestMatchers(HttpMethod.POST, "/fallas/**")
                        .hasAnyRole("ADMIN", "OPERADOR")

                        .requestMatchers(HttpMethod.PUT, "/fallas/**")
                        .hasAnyRole("ADMIN", "OPERADOR")

                        .requestMatchers(HttpMethod.DELETE, "/fallas/**")
                        .hasRole("ADMIN")

                        //----------------------------------
                        // DOCUMENTACIÓN
                        //----------------------------------
                        .requestMatchers(
                                HttpMethod.GET,
                                "/soap/**",
                                "/revisiones-tecnicas/**",
                                "/permisos-circulacion/**",
                                "/emisiones-gases/**",
                                "/documentacion/**"
                        )
                        .hasAnyRole("ADMIN", "OPERADOR", "CONDUCTOR")

                        .requestMatchers(
                                HttpMethod.POST,
                                "/soap/**",
                                "/revisiones-tecnicas/**",
                                "/permisos-circulacion/**",
                                "/emisiones-gases/**"
                        )
                        .hasAnyRole("ADMIN", "OPERADOR")

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/soap/**",
                                "/revisiones-tecnicas/**",
                                "/permisos-circulacion/**",
                                "/emisiones-gases/**"
                        )
                        .hasAnyRole("ADMIN", "OPERADOR")

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/soap/**",
                                "/revisiones-tecnicas/**",
                                "/permisos-circulacion/**",
                                "/emisiones-gases/**"
                        )
                        .hasRole("ADMIN")

                        .anyRequest()
                        .authenticated()
                )

                .authenticationProvider(authenticationProvider())

                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();

        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        return configuration.getAuthenticationManager();
    }
}