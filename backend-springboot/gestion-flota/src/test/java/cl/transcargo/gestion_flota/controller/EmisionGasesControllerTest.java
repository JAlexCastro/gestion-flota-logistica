package cl.transcargo.gestion_flota.controller;

import cl.transcargo.gestion_flota.dto.Requests.EmisionGasesRequestDTO;
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
class EmisionGasesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deberiaListarEmisiones() throws Exception {

        mockMvc.perform(get("/emisiones-gases/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Lista de emisiones de gases"));
    }

    @Test
    void deberiaCrearEmision() throws Exception {

        EmisionGasesRequestDTO request = new EmisionGasesRequestDTO();

        request.setVehiculoId(2L);
        request.setResultado("Aprobado");
        request.setFechaVencimiento(LocalDate.now().plusYears(2));

        mockMvc.perform(post("/emisiones-gases/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(201))
                .andExpect(jsonPath("$.message")
                        .value("Registro de emisión creado correctamente"));
    }

    @Test
    void deberiaObtenerEmisionPorId() throws Exception {

        mockMvc.perform(get("/emisiones-gases/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message")
                        .value("Registro de emisión encontrado"));
    }

    @Test
    void deberiaActualizarEmision() throws Exception {

        EmisionGasesRequestDTO request = new EmisionGasesRequestDTO();

        request.setVehiculoId(2L);
        request.setResultado("Rechazado");
        request.setFechaVencimiento(LocalDate.now().plusYears(2));

        mockMvc.perform(put("/emisiones-gases/update/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message")
                        .value("Registro de emisión actualizado correctamente"));
    }

    @Test
    void deberiaEliminarEmision() throws Exception {

        mockMvc.perform(delete("/emisiones-gases/delete/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message")
                        .value("Registro de emisión eliminado correctamente"));
    }

}