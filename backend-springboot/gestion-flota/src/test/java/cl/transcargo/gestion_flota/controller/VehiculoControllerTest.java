package cl.transcargo.gestion_flota.controller;

import cl.transcargo.gestion_flota.dto.Requests.VehiculoRequestDTO;
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
class VehiculoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deberiaListarVehiculos() throws Exception {

        mockMvc.perform(get("/vehiculos/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Lista vehiculos"));
    }

    @Test
    void deberiaCrearVehiculo() throws Exception {

        VehiculoRequestDTO request = new VehiculoRequestDTO();

        request.setPatente("AA1111");
        request.setMarca("Toyota");
        request.setModelo("Yaris");
        request.setAnio(2023);
        request.setKilometrajeActual(12000);
        request.setEstado("Activo");

        mockMvc.perform(post("/vehiculos/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())   // Cambiar a isCreated() cuando corrijas el controlador
                .andExpect(jsonPath("$.status").value(201))
                .andExpect(jsonPath("$.message").value("Vehiculo guardado"))
                .andExpect(jsonPath("$.data.patente").value("AA1111"))
                .andExpect(jsonPath("$.data.marca").value("Toyota"))
                .andExpect(jsonPath("$.data.modelo").value("Yaris"));
    }

    @Test
    void deberiaObtenerVehiculoPorId() throws Exception {

        mockMvc.perform(get("/vehiculos/get/4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200));
    }

    @Test
    void deberiaActualizarVehiculo() throws Exception {

        VehiculoRequestDTO request = new VehiculoRequestDTO();

        request.setPatente("BB2222");
        request.setMarca("Kia");
        request.setModelo("Rio");
        request.setAnio(2024);
        request.setKilometrajeActual(25000);
        request.setEstado("Activo");

        mockMvc.perform(put("/vehiculos/update/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Vehículo actualizado correctamente"))
                .andExpect(jsonPath("$.data.patente").value("BB2222"))
                .andExpect(jsonPath("$.data.marca").value("Kia"))
                .andExpect(jsonPath("$.data.modelo").value("Rio"));
    }

    @Test
    void deberiaEliminarVehiculo() throws Exception {

        mockMvc.perform(delete("/vehiculos/delete/4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Vehículo eliminado correctamente"));
    }

}