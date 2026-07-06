package cl.transcargo.gestion_flota.controller;

import cl.transcargo.gestion_flota.dto.Requests.FallaRequestDTO;
import cl.transcargo.gestion_flota.entity.Falla;
import cl.transcargo.gestion_flota.entity.Vehiculo;
import cl.transcargo.gestion_flota.repository.RFalla;
import cl.transcargo.gestion_flota.repository.RVehiculo;
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
class FallaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RFalla fallaRepository;

    @Autowired
    private RVehiculo vehiculoRepository;

    @Test
    void deberiaListarFallas() throws Exception {

        mockMvc.perform(get("/fallas/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Lista de fallas"));
    }

    @Test
    void deberiaCrearFalla() throws Exception {

        Vehiculo vehiculo = vehiculoRepository.findById(4L).orElseThrow();

        FallaRequestDTO request = new FallaRequestDTO();
        request.setVehiculoId(vehiculo.getId());
        request.setFecha(LocalDate.now());
        request.setDescripcion("Fuga de aceite");
        request.setPrioridad("Alta");
        request.setEstado("Pendiente");

        mockMvc.perform(post("/fallas/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(201))
                .andExpect(jsonPath("$.message").value("Falla registrada correctamente"));
    }

    @Test
    void deberiaObtenerFalla() throws Exception {

        Vehiculo vehiculo = vehiculoRepository.findById(4L).orElseThrow();

        Falla falla = new Falla();
        falla.setVehiculo(vehiculo);
        falla.setFecha(LocalDate.now());
        falla.setDescripcion("Motor");
        falla.setPrioridad("Alta");
        falla.setEstado("Pendiente");

        falla = fallaRepository.save(falla);

        mockMvc.perform(get("/fallas/" + falla.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Falla encontrada"));
    }

    @Test
    void deberiaActualizarFalla() throws Exception {

        Vehiculo vehiculo = vehiculoRepository.findById(4L).orElseThrow();

        Falla falla = new Falla();
        falla.setVehiculo(vehiculo);
        falla.setFecha(LocalDate.now());
        falla.setDescripcion("Inicial");
        falla.setPrioridad("Media");
        falla.setEstado("Pendiente");

        falla = fallaRepository.save(falla);

        FallaRequestDTO request = new FallaRequestDTO();
        request.setVehiculoId(vehiculo.getId());
        request.setFecha(LocalDate.now());
        request.setDescripcion("Actualizada");
        request.setPrioridad("Alta");
        request.setEstado("En reparación");

        mockMvc.perform(put("/fallas/update/" + falla.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Falla actualizada correctamente"));
    }

    @Test
    void deberiaEliminarFalla() throws Exception {

        Vehiculo vehiculo = vehiculoRepository.findById(4L).orElseThrow();

        Falla falla = new Falla();
        falla.setVehiculo(vehiculo);
        falla.setFecha(LocalDate.now());
        falla.setDescripcion("Eliminar");
        falla.setPrioridad("Baja");
        falla.setEstado("Pendiente");

        falla = fallaRepository.save(falla);

        mockMvc.perform(delete("/fallas/delete/" + falla.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Falla eliminada correctamente"));
    }

}