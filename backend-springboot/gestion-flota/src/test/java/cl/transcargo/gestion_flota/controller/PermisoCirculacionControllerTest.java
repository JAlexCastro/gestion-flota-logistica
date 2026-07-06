package cl.transcargo.gestion_flota.controller;

import cl.transcargo.gestion_flota.dto.Requests.PermisoCirculacionRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PermisoCirculacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deberiaListarPermisos() throws Exception {

        mockMvc.perform(get("/permisos-circulacion/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message")
                        .value("Lista de permisos de circulación"));
    }

    @Test
    void deberiaObtenerPermisoPorId() throws Exception {

        mockMvc.perform(get("/permisos-circulacion/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message")
                        .value("Permiso de circulación encontrado"));
    }

    @Test
    void deberiaCrearPermiso() throws Exception {

        PermisoCirculacionRequestDTO request = new PermisoCirculacionRequestDTO();

        request.setVehiculoId(2L);
        request.setFechaEmision(LocalDate.now());
        request.setFechaVencimiento(LocalDate.now().plusYears(1));
        request.setEstado("VIGENTE");

        mockMvc.perform(post("/permisos-circulacion/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(201))
                .andExpect(jsonPath("$.message")
                        .value("Permiso de circulación creado correctamente"));
    }

    @Test
    void deberiaActualizarPermiso() throws Exception {

        PermisoCirculacionRequestDTO request = new PermisoCirculacionRequestDTO();

        request.setVehiculoId(2L);
        request.setFechaEmision(LocalDate.now());
        request.setFechaVencimiento(LocalDate.now().plusYears(2));
        request.setEstado("VENCIDO");

        mockMvc.perform(put("/permisos-circulacion/update/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message")
                        .value("Permiso de circulación actualizado correctamente"));
    }

    @Test
    void deberiaEliminarPermiso() throws Exception {

        mockMvc.perform(delete("/permisos-circulacion/delete/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message")
                        .value("Permiso de circulación eliminado correctamente"));
    }

}