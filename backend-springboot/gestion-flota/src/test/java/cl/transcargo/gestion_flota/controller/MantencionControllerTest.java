package cl.transcargo.gestion_flota.controller;

import cl.transcargo.gestion_flota.dto.Requests.MantencionRequestDTO;
import cl.transcargo.gestion_flota.entity.Mantencion;
import cl.transcargo.gestion_flota.entity.Vehiculo;
import cl.transcargo.gestion_flota.repository.RMantencion;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MantencionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RMantencion mantencionRepository;

    @Autowired
    private RVehiculo vehiculoRepository;

    @Test
    void deberiaListarMantenciones() throws Exception {

        mockMvc.perform(get("/mantenciones/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Lista de mantenciones"));
    }

    @Test
    void deberiaCrearMantencion() throws Exception {

        Vehiculo vehiculo = vehiculoRepository.findById(4L).orElseThrow();

        MantencionRequestDTO request = new MantencionRequestDTO();
        request.setVehiculoId(vehiculo.getId());
        request.setFecha(LocalDate.now());
        request.setDescripcion("Cambio de aceite");
        request.setKilometraje(25000);
        request.setTipo("Preventiva");
        request.setTaller("Taller Central");

        mockMvc.perform(post("/mantenciones/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(201))
                .andExpect(jsonPath("$.message").value("Mantención creada correctamente"));
    }

    @Test
    void deberiaObtenerMantencionPorId() throws Exception {

        Vehiculo vehiculo = vehiculoRepository.findById(4L).orElseThrow();

        Mantencion mantencion = new Mantencion();
        mantencion.setVehiculo(vehiculo);
        mantencion.setFecha(LocalDate.now());
        mantencion.setDescripcion("Cambio de aceite");
        mantencion.setKilometraje(25000);
        mantencion.setTipo("Preventiva");
        mantencion.setTaller("Taller Central");

        mantencion = mantencionRepository.save(mantencion);

        mockMvc.perform(get("/mantenciones/" + mantencion.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Mantención encontrada"));
    }

    @Test
    void deberiaActualizarMantencion() throws Exception {

        Vehiculo vehiculo = vehiculoRepository.findById(4L).orElseThrow();

        Mantencion mantencion = new Mantencion();
        mantencion.setVehiculo(vehiculo);
        mantencion.setFecha(LocalDate.now());
        mantencion.setDescripcion("Cambio inicial");
        mantencion.setKilometraje(20000);
        mantencion.setTipo("Preventiva");
        mantencion.setTaller("Taller 1");

        mantencion = mantencionRepository.save(mantencion);

        MantencionRequestDTO request = new MantencionRequestDTO();
        request.setVehiculoId(vehiculo.getId());
        request.setFecha(LocalDate.now());
        request.setDescripcion("Cambio de filtros");
        request.setKilometraje(30000);
        request.setTipo("Preventiva");
        request.setTaller("Taller Central");

        mockMvc.perform(put("/mantenciones/update/" + mantencion.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Mantención actualizada correctamente"));
    }

    @Test
    void deberiaEliminarMantencion() throws Exception {

        Vehiculo vehiculo = vehiculoRepository.findById(4L).orElseThrow();

        Mantencion mantencion = new Mantencion();
        mantencion.setVehiculo(vehiculo);
        mantencion.setFecha(LocalDate.now());
        mantencion.setDescripcion("Eliminar");
        mantencion.setKilometraje(15000);
        mantencion.setTipo("Correctiva");
        mantencion.setTaller("Taller Central");

        mantencion = mantencionRepository.save(mantencion);

        mockMvc.perform(delete("/mantenciones/delete/" + mantencion.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Mantención eliminada correctamente"));
    }

}