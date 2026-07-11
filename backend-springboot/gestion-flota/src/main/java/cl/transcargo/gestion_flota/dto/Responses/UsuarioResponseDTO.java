package cl.transcargo.gestion_flota.dto.Responses;

import lombok.*;

@Data
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponseDTO {

    private Long id;

    private String username;

    private String rol;

    private String name;

}