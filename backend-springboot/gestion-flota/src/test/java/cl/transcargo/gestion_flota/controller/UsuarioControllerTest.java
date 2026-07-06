package cl.transcargo.gestion_flota.controller;

import cl.transcargo.gestion_flota.dto.Requests.UsuarioRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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

        request.setUsername("admin");
        request.setPassword("123456");
        request.setRol("ADMIN");

        mockMvc.perform(post("/usuarios/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(201))
                .andExpect(jsonPath("$.message").value("Usuario creado correctamente"))
                .andExpect(jsonPath("$.data.username").value("admin"))
                .andExpect(jsonPath("$.data.rol").value("ADMIN"));
    }

    @Test
    void deberiaObtenerUsuarioPorId() throws Exception {

        mockMvc.perform(get("/usuarios/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Usuario encontrado"));
    }

    @Test
    void deberiaActualizarUsuario() throws Exception {

        UsuarioRequestDTO request = new UsuarioRequestDTO();

        request.setUsername("nuevoAdmin");
        request.setPassword("654321");
        request.setRol("ADMIN");

        mockMvc.perform(put("/usuarios/update/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Usuario actualizado correctamente"))
                .andExpect(jsonPath("$.data.username").value("nuevoAdmin"));
    }

    @Test
    void deberiaEliminarUsuario() throws Exception {

        mockMvc.perform(delete("/usuarios/delete/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Usuario eliminado correctamente"));
    }

}