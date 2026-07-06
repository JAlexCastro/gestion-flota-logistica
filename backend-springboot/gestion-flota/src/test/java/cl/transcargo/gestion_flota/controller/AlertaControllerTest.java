package cl.transcargo.gestion_flota.controller;

import cl.transcargo.gestion_flota.dto.Requests.AlertaRequestDTO;
import cl.transcargo.gestion_flota.repository.RAlertas;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AlertaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RAlertas alertaRepository;


    @Test
    void deberiaListarAlertas() throws Exception {

        mockMvc.perform(get("/alertas/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Lista de alertas"));
    }

    @Test
    void deberiaObtenerAlerta() throws Exception {

        System.out.println("ANTES DEL GET:");
        System.out.println(alertaRepository.findById(1L));

        mockMvc.perform(get("/alertas/2"))
                .andExpect(status().isOk());
    }





    @Test
    void deberiaCrearAlerta() throws Exception {

        AlertaRequestDTO request = new AlertaRequestDTO();

        request.setVehiculoId(2L);
        request.setTipo("SOAP");
        request.setMensaje("SOAP próximo a vencer");
        request.setFechaGeneracion(LocalDateTime.now());
        request.setLeida(false);

        mockMvc.perform(post("/alertas/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(201))
                .andExpect(jsonPath("$.message").value("Alerta creada correctamente"));
    }

    @Test
    void deberiaActualizarAlerta() throws Exception {

        AlertaRequestDTO request = new AlertaRequestDTO();

        request.setVehiculoId(2L);
        request.setTipo("Permiso");
        request.setMensaje("Permiso vencido");
        request.setFechaGeneracion(LocalDateTime.now());
        request.setLeida(true);

        mockMvc.perform(put("/alertas/update/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Alerta actualizada correctamente"));
    }

    @Test
    void deberiaEliminarAlerta() throws Exception {

        mockMvc.perform(delete("/alertas/delete/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Alerta eliminada correctamente"));
    }

}