package cl.transcargo.gestion_flota.controller;

import cl.transcargo.gestion_flota.dto.Requests.SoapRequestDTO;
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
class SoapControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deberiaListarSoap() throws Exception {

        mockMvc.perform(get("/soap/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Lista de SOAP"));
    }

    @Test
    void deberiaObtenerSoapPorId() throws Exception {

        mockMvc.perform(get("/soap/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("SOAP encontrado"));
    }

    @Test
    void deberiaCrearSoap() throws Exception {

        SoapRequestDTO request = new SoapRequestDTO();

        request.setVehiculoId(2L);
        request.setAseguradora("BCI Seguros");
        request.setNumeroPoliza("POL-2025-001");
        request.setFechaEmision(LocalDate.now());
        request.setFechaVencimiento(LocalDate.now().plusYears(1));

        mockMvc.perform(post("/soap/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(201))
                .andExpect(jsonPath("$.message").value("SOAP registrado correctamente"));
    }

    @Test
    void deberiaActualizarSoap() throws Exception {

        SoapRequestDTO request = new SoapRequestDTO();

        request.setVehiculoId(4L);
        request.setAseguradora("Mapfre");
        request.setNumeroPoliza("POL-999999");
        request.setFechaEmision(LocalDate.now());
        request.setFechaVencimiento(LocalDate.now().plusYears(1));

        mockMvc.perform(put("/soap/update/4")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("SOAP actualizado correctamente"));
    }

    @Test
    void deberiaEliminarSoap() throws Exception {

        mockMvc.perform(delete("/soap/delete/4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("SOAP eliminado correctamente"));
    }

}