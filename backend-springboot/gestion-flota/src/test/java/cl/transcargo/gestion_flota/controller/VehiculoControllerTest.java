package cl.transcargo.gestion_flota.controller;

import cl.transcargo.gestion_flota.dto.Requests.VehiculoRequestDTO;
import cl.transcargo.gestion_flota.entity.Vehiculo;
import cl.transcargo.gestion_flota.repository.RVehiculo;
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
class VehiculoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RVehiculo vehiculoRepository;

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

        request.setPatente(generarPatente());
        request.setMarca("Toyota");
        request.setModelo("Yaris");
        request.setAnio(2023);
        request.setKilometrajeActual(12000);
        request.setEstado("Activo");

        mockMvc.perform(post("/vehiculos/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk()) // Cambiar a isCreated() cuando el controlador retorne ResponseEntity.status(201)
                .andExpect(jsonPath("$.status").value(201))
                .andExpect(jsonPath("$.message").value("Vehiculo guardado"))
                .andExpect(jsonPath("$.data.marca").value("Toyota"))
                .andExpect(jsonPath("$.data.modelo").value("Yaris"));
    }

    @Test
    void deberiaObtenerVehiculoPorId() throws Exception {

        Vehiculo vehiculo = crearVehiculo();

        mockMvc.perform(get("/vehiculos/get/" + vehiculo.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200));
    }

    @Test
    void deberiaActualizarVehiculo() throws Exception {

        Vehiculo vehiculo = crearVehiculo();

        VehiculoRequestDTO request = new VehiculoRequestDTO();

        request.setPatente(generarPatente());
        request.setMarca("Kia");
        request.setModelo("Rio");
        request.setAnio(2024);
        request.setKilometrajeActual(25000);
        request.setEstado("Activo");

        mockMvc.perform(put("/vehiculos/update/" + vehiculo.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Vehículo actualizado correctamente"))
                .andExpect(jsonPath("$.data.marca").value("Kia"))
                .andExpect(jsonPath("$.data.modelo").value("Rio"));
    }

    @Test
    void deberiaEliminarVehiculo() throws Exception {

        Vehiculo vehiculo = crearVehiculo();

        mockMvc.perform(delete("/vehiculos/delete/" + vehiculo.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Vehículo eliminado correctamente"));
    }

    private Vehiculo crearVehiculo() {

        Vehiculo vehiculo = new Vehiculo();

        vehiculo.setPatente(generarPatente());
        vehiculo.setMarca("Toyota");
        vehiculo.setModelo("Hilux");
        vehiculo.setAnio(2024);
        vehiculo.setKilometrajeActual(1000);
        vehiculo.setEstado("Activo");

        return vehiculoRepository.save(vehiculo);
    }

    private String generarPatente() {

        return UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 8)
                .toUpperCase();
    }
}