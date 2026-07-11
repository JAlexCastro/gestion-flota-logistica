package cl.transcargo.gestion_flota.dto.Requests;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO utilizado para recibir las credenciales
 * enviadas desde el frontend.
 */
@Getter
@Setter
public class LoginRequest {

    private String username;

    private String password;

}