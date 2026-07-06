package cl.transcargo.gestion_flota.controller;

import cl.transcargo.gestion_flota.dto.Requests.ConductorRequestDTO;
import cl.transcargo.gestion_flota.entity.Conductor;
import cl.transcargo.gestion_flota.repository.RConductor;
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
class ConductorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RConductor conductorRepository;

    @Test
    void deberiaListarConductores() throws Exception {

        mockMvc.perform(get("/conductores/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Lista de conductores"));
    }

    @Test
    void deberiaCrearConductor() throws Exception {

        ConductorRequestDTO request = new ConductorRequestDTO();

        request.setRut("11.111.111-1");
        request.setNombre("Juan Pérez");
        request.setTelefono("987654321");
        request.setNumeroLicencia("LIC123456");
        request.setFechaVencimientoLicencia(LocalDate.of(2028, 5, 20));

        mockMvc.perform(post("/conductores/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(201))
                .andExpect(jsonPath("$.message").value("Conductor creado correctamente"))
                .andExpect(jsonPath("$.data.rut").value("11.111.111-1"))
                .andExpect(jsonPath("$.data.nombre").value("Juan Pérez"));
    }

    @Test
    void deberiaObtenerConductorPorId() throws Exception {

        Conductor conductor = new Conductor();
        conductor.setRut("12.345.678-9");
        conductor.setNombre("Carlos Soto");
        conductor.setTelefono("987654321");
        conductor.setNumeroLicencia("LIC111111");
        conductor.setFechaVencimientoLicencia(LocalDate.of(2029, 5, 20));

        conductor = conductorRepository.save(conductor);

        mockMvc.perform(get("/conductores/" + conductor.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Conductor encontrado"))
                .andExpect(jsonPath("$.data.nombre").value("Carlos Soto"));
    }

    @Test
    void deberiaActualizarConductor() throws Exception {

        Conductor conductor = new Conductor();
        conductor.setRut("12.345.678-9");
        conductor.setNombre("Carlos Soto");
        conductor.setTelefono("987654321");
        conductor.setNumeroLicencia("LIC111111");
        conductor.setFechaVencimientoLicencia(LocalDate.of(2029, 5, 20));

        conductor = conductorRepository.save(conductor);

        ConductorRequestDTO request = new ConductorRequestDTO();
        request.setRut("22.222.222-2");
        request.setNombre("Pedro González");
        request.setTelefono("999999999");
        request.setNumeroLicencia("LIC999999");
        request.setFechaVencimientoLicencia(LocalDate.of(2030, 10, 15));

        mockMvc.perform(put("/conductores/update/" + conductor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Conductor actualizado correctamente"))
                .andExpect(jsonPath("$.data.nombre").value("Pedro González"));
    }

    @Test
    void deberiaEliminarConductor() throws Exception {

        Conductor conductor = new Conductor();
        conductor.setRut("12.345.678-9");
        conductor.setNombre("Carlos Soto");
        conductor.setTelefono("987654321");
        conductor.setNumeroLicencia("LIC111111");
        conductor.setFechaVencimientoLicencia(LocalDate.of(2029, 5, 20));

        conductor = conductorRepository.save(conductor);

        mockMvc.perform(delete("/conductores/delete/" + conductor.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Conductor eliminado correctamente"));
    }

}