package cl.transcargo.gestion_flota.security.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO {

    private String username;

    private String password;

}