package cl.transcargo.gestion_flota.controller;

import cl.transcargo.gestion_flota.dto.Requests.VehiculoRequestDTO;
import cl.transcargo.gestion_flota.entity.Vehiculo;
import cl.transcargo.gestion_flota.notification.NotificationService;
import cl.transcargo.gestion_flota.repository.RVehiculo;
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

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@WithMockUser(
        username = "jalejandro.ecom@gmail.com",
        roles = {"ADMIN"}
)
class VehiculoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RVehiculo vehiculoRepository;

    @MockBean
    private NotificationService notificationService;

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
        request.setNombre("Camión Toyota");
        request.setMarca("Toyota");
        request.setModelo("Yaris");
        request.setAnio(2023);
        request.setKilometrajeActual(12000);
        request.setEstado("Activo");

        mockMvc.perform(
                        post("/vehiculos/save")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isCreated())
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
        request.setNombre("Camión Kia");
        request.setMarca("Kia");
        request.setModelo("Rio");
        request.setAnio(2024);
        request.setKilometrajeActual(25000);
        request.setEstado("Activo");

        mockMvc.perform(
                        put("/vehiculos/update/" + vehiculo.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message")
                        .value("Vehículo actualizado correctamente"))
                .andExpect(jsonPath("$.data.marca").value("Kia"))
                .andExpect(jsonPath("$.data.modelo").value("Rio"));

    }

    @Test
    void deberiaEliminarVehiculo() throws Exception {

        Vehiculo vehiculo = crearVehiculo();

        mockMvc.perform(delete("/vehiculos/delete/" + vehiculo.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message")
                        .value("Vehículo eliminado correctamente"));

    }

    private Vehiculo crearVehiculo() {

        Vehiculo vehiculo = new Vehiculo();

        vehiculo.setPatente(generarPatente());
        vehiculo.setNombre("Camión Toyota");
        vehiculo.setMarca("Toyota");
        vehiculo.setModelo("Hilux");
        vehiculo.setAnio(2024);
        vehiculo.setKilometrajeActual(1000);
        vehiculo.setEstado("Activo");

        return vehiculoRepository.save(vehiculo);

    }

    private String generarPatente() {

        String letras = UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 4)
                .toUpperCase();

        int numeros = (int) (Math.random() * 90) + 10;

        return letras + numeros;

    }

}