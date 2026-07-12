package cl.transcargo.gestion_flota.controller;

import cl.transcargo.gestion_flota.dto.Requests.ConductorRequestDTO;
import cl.transcargo.gestion_flota.entity.Conductor;
import cl.transcargo.gestion_flota.notification.NotificationService;
import cl.transcargo.gestion_flota.repository.RConductor;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;

import org.springframework.security.test.context.support.WithMockUser;

import org.springframework.test.web.servlet.MockMvc;

import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@WithMockUser(
        username = "jalejandro.ecom@gmail.com",
        roles = {"ADMIN"}
)
class ConductorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RConductor conductorRepository;

    /**
     * Mock del servicio de notificaciones.
     * Evita el envío real de correos durante los tests.
     */
    @MockBean
    private NotificationService notificationService;

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
        request.setNumeroLicencia(111111);
        request.setClaseLicencia("A4");
        request.setFechaVencimientoLicencia(LocalDate.of(2028, 5, 20));

        mockMvc.perform(

                        post("/conductores/save")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))

                )

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
        conductor.setNumeroLicencia(111111);
        conductor.setClaseLicencia("A5");
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
        conductor.setNumeroLicencia(111111);
        conductor.setClaseLicencia("A5");
        conductor.setFechaVencimientoLicencia(LocalDate.of(2029, 5, 20));

        conductor = conductorRepository.save(conductor);

        ConductorRequestDTO request = new ConductorRequestDTO();

        request.setRut("22.222.222-2");
        request.setNombre("Pedro González");
        request.setTelefono("999999999");
        request.setNumeroLicencia(111111);
        request.setClaseLicencia("A4");
        request.setFechaVencimientoLicencia(LocalDate.of(2030, 10, 15));

        mockMvc.perform(

                        put("/conductores/update/" + conductor.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))

                )

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
        conductor.setNumeroLicencia(1111111);
        conductor.setClaseLicencia("A5");
        conductor.setFechaVencimientoLicencia(LocalDate.of(2029, 5, 20));

        conductor = conductorRepository.save(conductor);

        mockMvc.perform(delete("/conductores/delete/" + conductor.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Conductor eliminado correctamente"));

    }

}