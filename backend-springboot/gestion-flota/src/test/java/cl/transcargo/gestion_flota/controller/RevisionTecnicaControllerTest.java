package cl.transcargo.gestion_flota.controller;

import cl.transcargo.gestion_flota.dto.Requests.RevisionTecnicaRequestDTO;
import cl.transcargo.gestion_flota.entity.RevisionTecnica;
import cl.transcargo.gestion_flota.entity.Vehiculo;
import cl.transcargo.gestion_flota.repository.RRevisionTecnica;
import cl.transcargo.gestion_flota.repository.RVehiculo;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
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
@WithMockUser(
        username = "jalejandro.ecom@gmail.com",
        roles = {"ADMIN"}
)
class RevisionTecnicaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RVehiculo vehiculoRepository;

    @Autowired
    private RRevisionTecnica revisionRepository;

    @Test
    void deberiaListarRevisiones() throws Exception {

        mockMvc.perform(get("/revisiones-tecnicas/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Lista de revisiones técnicas"));
    }

    @Test
    void deberiaCrearRevision() throws Exception {

        Vehiculo vehiculo = vehiculoRepository.findById(4L).orElseThrow();

        RevisionTecnicaRequestDTO request = new RevisionTecnicaRequestDTO();
        request.setVehiculoId(vehiculo.getId());
        request.setFechaRevision(LocalDate.now());
        request.setFechaVencimiento(LocalDate.now().plusYears(1));
        request.setResultado("Aprobada");

        mockMvc.perform(post("/revisiones-tecnicas/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(201))
                .andExpect(jsonPath("$.message").value("Revisión técnica creada correctamente"));
    }

    @Test
    void deberiaObtenerRevision() throws Exception {

        Vehiculo vehiculo = vehiculoRepository.findById(4L).orElseThrow();

        RevisionTecnica revision = new RevisionTecnica();
        revision.setVehiculo(vehiculo);
        revision.setFechaRevision(LocalDate.now());
        revision.setFechaVencimiento(LocalDate.now().plusYears(1));
        revision.setResultado("Aprobada");

        revision = revisionRepository.save(revision);

        mockMvc.perform(get("/revisiones-tecnicas/" + revision.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Revisión técnica encontrada"));
    }

    @Test
    void deberiaActualizarRevision() throws Exception {

        Vehiculo vehiculo = vehiculoRepository.findById(4L).orElseThrow();

        RevisionTecnica revision = new RevisionTecnica();
        revision.setVehiculo(vehiculo);
        revision.setFechaRevision(LocalDate.now());
        revision.setFechaVencimiento(LocalDate.now().plusYears(1));
        revision.setResultado("Pendiente");

        revision = revisionRepository.save(revision);

        RevisionTecnicaRequestDTO request = new RevisionTecnicaRequestDTO();
        request.setVehiculoId(vehiculo.getId());
        request.setFechaRevision(LocalDate.now());
        request.setFechaVencimiento(LocalDate.now().plusYears(1));
        request.setResultado("Aprobada");

        mockMvc.perform(put("/revisiones-tecnicas/update/" + revision.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Revisión técnica actualizada correctamente"));
    }

    @Test
    void deberiaEliminarRevision() throws Exception {

        Vehiculo vehiculo = vehiculoRepository.findById(4L).orElseThrow();

        RevisionTecnica revision = new RevisionTecnica();
        revision.setVehiculo(vehiculo);
        revision.setFechaRevision(LocalDate.now());
        revision.setFechaVencimiento(LocalDate.now().plusYears(1));
        revision.setResultado("Aprobada");

        revision = revisionRepository.save(revision);

        mockMvc.perform(delete("/revisiones-tecnicas/delete/" + revision.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Revisión técnica eliminada correctamente"));
    }
}