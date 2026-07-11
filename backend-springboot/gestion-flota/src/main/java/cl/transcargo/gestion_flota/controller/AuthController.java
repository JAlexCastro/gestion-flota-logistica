package cl.transcargo.gestion_flota.controller;

import cl.transcargo.gestion_flota.dto.Requests.LoginRequest;
import cl.transcargo.gestion_flota.dto.Responses.LoginResponse;
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

    public AuthController(AuthenticationManager authenticationManager,
                          JwtService jwtService) {

        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;

    }

    /**
     * Autentica al usuario y devuelve un JWT.
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

        String token = jwtService.generarToken(user);

        return ResponseEntity.ok(

                new LoginResponse(token)

        );

    }

}