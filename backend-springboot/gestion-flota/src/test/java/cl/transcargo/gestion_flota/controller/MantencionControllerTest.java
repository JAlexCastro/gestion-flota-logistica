package cl.transcargo.gestion_flota.controller;

import cl.transcargo.gestion_flota.dto.Requests.MantencionRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MantencionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deberiaListarMantenciones() throws Exception {

        mockMvc.perform(get("/mantenciones/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Lista de mantenciones"));
    }

    @Test
    void deberiaCrearMantencion() throws Exception {

        MantencionRequestDTO request = new MantencionRequestDTO();

        request.setFecha(LocalDate.now());
        request.setDescripcion("Cambio de aceite");
        request.setKilometraje(25000);
        request.setTipo("Preventiva");
        request.setVehiculoId(2L);

        mockMvc.perform(post("/mantenciones/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(201))
                .andExpect(jsonPath("$.message").value("Mantención creada correctamente"));
    }

    @Test
    void deberiaObtenerMantencionPorId() throws Exception {

        mockMvc.perform(get("/mantenciones/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Mantención encontrada"));
    }

    @Test
    void deberiaActualizarMantencion() throws Exception {

        MantencionRequestDTO request = new MantencionRequestDTO();

        request.setFecha(LocalDate.now());
        request.setDescripcion("Cambio de filtros");
        request.setKilometraje(30000);
        request.setTipo("Preventiva");
        request.setVehiculoId(2L);

        mockMvc.perform(put("/mantenciones/update/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Mantención actualizada correctamente"));
    }

    @Test
    void deberiaEliminarMantencion() throws Exception {

        mockMvc.perform(delete("/mantenciones/delete/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Mantención eliminada correctamente"));
    }
}