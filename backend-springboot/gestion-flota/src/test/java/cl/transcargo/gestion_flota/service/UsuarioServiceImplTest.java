package cl.transcargo.gestion_flota.service;

import cl.transcargo.gestion_flota.dto.Requests.UsuarioRequestDTO;
import cl.transcargo.gestion_flota.dto.Responses.UsuarioResponseDTO;
import cl.transcargo.gestion_flota.entity.Usuario;
import cl.transcargo.gestion_flota.mapper.UsuarioMapper;
import cl.transcargo.gestion_flota.repository.RUsuario;
import cl.transcargo.gestion_flota.service.ServiceImpl.UsuarioServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) //Le dice a JUnit que utilice Mockito.
class UsuarioServiceImplTest {

    @Mock // Mockito crea un repositorio falso.
    private RUsuario repository;

    @Mock
    private UsuarioMapper mapper;

    @InjectMocks
    private UsuarioServiceImpl service;

    @Test
    void deberiaListarUsuarios() {

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUsername("admin");
        usuario.setRol("ADMIN");

        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(1L);
        dto.setUsername("admin");
        dto.setRol("ADMIN");

        when(repository.findAll())
                .thenReturn(List.of(usuario));

        when(mapper.toResponse(usuario))
                .thenReturn(dto);

        List<UsuarioResponseDTO> resultado = service.listar();

        assertEquals(1, resultado.size());
        assertEquals("admin", resultado.get(0).getUsername());

        verify(repository).findAll();
    }

    @Test
    void deberiaObtenerUsuarioPorId() {

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUsername("admin");

        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(1L);
        dto.setUsername("admin");

        when(repository.findById(1L))
                .thenReturn(Optional.of(usuario));

        when(mapper.toResponse(usuario))
                .thenReturn(dto);

        UsuarioResponseDTO resultado = service.obtener(1L);

        assertEquals("admin", resultado.getUsername());

        verify(repository).findById(1L);
    }

    @Test
    void deberiaLanzarExcepcionSiNoExisteUsuario() {

        when(repository.findById(1L))
                .thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> service.obtener(1L)
        );

        assertEquals("Usuario no encontrado", ex.getMessage());
    }

    @Test
    void deberiaCrearUsuario() {

        UsuarioRequestDTO request = new UsuarioRequestDTO();
        request.setUsername("admin");
        request.setPassword("123");
        request.setRol("ADMIN");

        Usuario usuario = new Usuario();
        usuario.setUsername("admin");

        UsuarioResponseDTO response = new UsuarioResponseDTO();
        response.setUsername("admin");

        when(mapper.toEntity(request))
                .thenReturn(usuario);

        when(repository.save(usuario))
                .thenReturn(usuario);

        when(mapper.toResponse(usuario))
                .thenReturn(response);

        UsuarioResponseDTO resultado = service.crear(request);

        assertEquals("admin", resultado.getUsername());

        verify(repository).save(usuario);
    }

    @Test
    void deberiaEliminarUsuario() {

        Usuario usuario = new Usuario();
        usuario.setId(1L);

        when(repository.findById(1L))
                .thenReturn(Optional.of(usuario));

        service.eliminar(1L);

        verify(repository).delete(usuario);
    }

}

