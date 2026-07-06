package cl.transcargo.gestion_flota.controller;

import cl.transcargo.gestion_flota.dto.Requests.UsuarioRequestDTO;
import cl.transcargo.gestion_flota.entity.Usuario;
import cl.transcargo.gestion_flota.repository.RUsuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RUsuario usuarioRepository;

    @Test
    void deberiaListarUsuarios() throws Exception {

        mockMvc.perform(get("/usuarios/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Lista de usuarios"));
    }

    @Test
    void deberiaCrearUsuario() throws Exception {

        UsuarioRequestDTO request = new UsuarioRequestDTO();

        request.setUsername("admin" + UUID.randomUUID().toString().substring(0, 5));
        request.setPassword("123456");
        request.setRol("ADMIN");

        mockMvc.perform(post("/usuarios/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(201))
                .andExpect(jsonPath("$.message").value("Usuario creado correctamente"))
                .andExpect(jsonPath("$.data.rol").value("ADMIN"));
    }

    @Test
    void deberiaObtenerUsuarioPorId() throws Exception {

        Usuario usuario = crearUsuario();

        mockMvc.perform(get("/usuarios/" + usuario.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Usuario encontrado"));
    }

    @Test
    void deberiaActualizarUsuario() throws Exception {

        Usuario usuario = crearUsuario();

        UsuarioRequestDTO request = new UsuarioRequestDTO();

        request.setUsername("nuevoAdmin");
        request.setPassword("654321");
        request.setRol("ADMIN");

        mockMvc.perform(put("/usuarios/update/" + usuario.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Usuario actualizado correctamente"))
                .andExpect(jsonPath("$.data.username").value("nuevoAdmin"));
    }

    @Test
    void deberiaEliminarUsuario() throws Exception {

        Usuario usuario = crearUsuario();

        mockMvc.perform(delete("/usuarios/delete/" + usuario.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Usuario eliminado correctamente"));
    }

    private Usuario crearUsuario() {

        Usuario usuario = new Usuario();

        usuario.setUsername("user" + UUID.randomUUID().toString().substring(0, 8));
        usuario.setPassword("123456");
        usuario.setRol("ADMIN");

        return usuarioRepository.save(usuario);
    }
}