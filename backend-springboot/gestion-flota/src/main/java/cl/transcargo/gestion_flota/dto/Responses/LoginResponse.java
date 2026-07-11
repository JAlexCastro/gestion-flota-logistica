package cl.transcargo.gestion_flota.dto.Responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO utilizado para devolver el JWT
 * generado luego del inicio de sesión.
 */
@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {

    private String token;

}