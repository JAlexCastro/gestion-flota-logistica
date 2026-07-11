package cl.transcargo.gestion_flota.controller;

import cl.transcargo.gestion_flota.dto.Requests.LoginRequest;
import cl.transcargo.gestion_flota.dto.Responses.LoginResponse;
import cl.transcargo.gestion_flota.entity.Usuario;
import cl.transcargo.gestion_flota.repository.RUsuario;
import cl.transcargo.gestion_flota.security.jwt.JwtService;

import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador encargado del inicio de sesión.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final RUsuario usuarioRepository;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtService jwtService,
                          RUsuario usuarioRepository) {

        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.usuarioRepository = usuarioRepository;

    }

    /**
     * Autentica al usuario y devuelve un JWT junto con su información.
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(

                        new UsernamePasswordAuthenticationToken(

                                request.getUsername(),
                                request.getPassword()

                        )

                );

        UserDetails user =
                (UserDetails) authentication.getPrincipal();

        // Genera el JWT
        String token = jwtService.generarToken(user);

        // Obtiene la entidad Usuario desde la base de datos
        Usuario usuario = usuarioRepository
                .findByUsername(user.getUsername())
                .orElseThrow(() ->
                        new RuntimeException("Usuario no encontrado."));

        // Devuelve el token y la información del usuario
        return ResponseEntity.ok(

                new LoginResponse(
                        token,
                        usuario.getUsername(),
                        usuario.getName(),
                        usuario.getRol()

                )

        );

    }

}