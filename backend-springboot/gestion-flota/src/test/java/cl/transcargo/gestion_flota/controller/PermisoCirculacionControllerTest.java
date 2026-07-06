package cl.transcargo.gestion_flota.controller;

import cl.transcargo.gestion_flota.dto.Requests.PermisoCirculacionRequestDTO;
import cl.transcargo.gestion_flota.entity.PermisoCirculacion;
import cl.transcargo.gestion_flota.entity.Vehiculo;
import cl.transcargo.gestion_flota.repository.RPermisoCirculacion;
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
class PermisoCirculacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RPermisoCirculacion permisoRepository;

    @Autowired
    private RVehiculo vehiculoRepository;

    @Test
    void deberiaListarPermisos() throws Exception {

        mockMvc.perform(get("/permisos-circulacion/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message")
                        .value("Lista de permisos de circulación"));
    }

    @Test
    void deberiaCrearPermiso() throws Exception {

        Vehiculo vehiculo = crearVehiculo();

        PermisoCirculacionRequestDTO request = new PermisoCirculacionRequestDTO();
        request.setVehiculoId(vehiculo.getId());
        request.setFechaEmision(LocalDate.now());
        request.setFechaVencimiento(LocalDate.now().plusYears(1));
        request.setEstado("VIGENTE");

        mockMvc.perform(post("/permisos-circulacion/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(201))
                .andExpect(jsonPath("$.message")
                        .value("Permiso de circulación creado correctamente"));
    }

    @Test
    void deberiaObtenerPermisoPorId() throws Exception {

        Vehiculo vehiculo = crearVehiculo();

        PermisoCirculacion permiso = new PermisoCirculacion();
        permiso.setVehiculo(vehiculo);
        permiso.setFechaEmision(LocalDate.now());
        permiso.setFechaVencimiento(LocalDate.now().plusYears(1));
        permiso.setEstado("VIGENTE");

        permiso = permisoRepository.save(permiso);

        mockMvc.perform(get("/permisos-circulacion/" + permiso.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message")
                        .value("Permiso de circulación encontrado"));
    }

    @Test
    void deberiaActualizarPermiso() throws Exception {

        Vehiculo vehiculo = crearVehiculo();

        PermisoCirculacion permiso = new PermisoCirculacion();
        permiso.setVehiculo(vehiculo);
        permiso.setFechaEmision(LocalDate.now());
        permiso.setFechaVencimiento(LocalDate.now().plusYears(1));
        permiso.setEstado("VIGENTE");

        permiso = permisoRepository.save(permiso);

        PermisoCirculacionRequestDTO request = new PermisoCirculacionRequestDTO();
        request.setVehiculoId(vehiculo.getId());
        request.setFechaEmision(LocalDate.now());
        request.setFechaVencimiento(LocalDate.now().plusYears(2));
        request.setEstado("VENCIDO");

        mockMvc.perform(put("/permisos-circulacion/update/" + permiso.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message")
                        .value("Permiso de circulación actualizado correctamente"));
    }

    @Test
    void deberiaEliminarPermiso() throws Exception {

        Vehiculo vehiculo = crearVehiculo();

        PermisoCirculacion permiso = new PermisoCirculacion();
        permiso.setVehiculo(vehiculo);
        permiso.setFechaEmision(LocalDate.now());
        permiso.setFechaVencimiento(LocalDate.now().plusYears(1));
        permiso.setEstado("VIGENTE");

        permiso = permisoRepository.save(permiso);

        mockMvc.perform(delete("/permisos-circulacion/delete/" + permiso.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message")
                        .value("Permiso de circulación eliminado correctamente"));
    }

    /**
     * Crea un vehículo nuevo para cada prueba.
     */


    private Vehiculo crearVehiculo() {

        Vehiculo vehiculo = new Vehiculo();

        // Genera una patente única de 8 caracteres (VARCHAR(10))
        String patente = UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 8)
                .toUpperCase();

        vehiculo.setPatente(patente);
        vehiculo.setMarca("Toyota");
        vehiculo.setModelo("Hilux");
        vehiculo.setAnio(2024);
        vehiculo.setKilometrajeActual(1000);
        vehiculo.setEstado("ACTIVO");

        return vehiculoRepository.save(vehiculo);
    }

}