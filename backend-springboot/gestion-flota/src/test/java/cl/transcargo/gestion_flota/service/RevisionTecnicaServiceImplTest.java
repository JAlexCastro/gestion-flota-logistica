package cl.transcargo.gestion_flota.service;

import cl.transcargo.gestion_flota.dto.Requests.RevisionTecnicaRequestDTO;
import cl.transcargo.gestion_flota.dto.Responses.RevisionTecnicaResponseDTO;
import cl.transcargo.gestion_flota.entity.RevisionTecnica;
import cl.transcargo.gestion_flota.entity.Vehiculo;
import cl.transcargo.gestion_flota.mapper.RevisionTecnicaMapper;
import cl.transcargo.gestion_flota.repository.RRevisionTecnica;
import cl.transcargo.gestion_flota.repository.RVehiculo;
import cl.transcargo.gestion_flota.service.ServiceImpl.RevisionTecnicaServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RevisionTecnicaServiceImplTest {

    @Mock
    private RRevisionTecnica repository;

    @Mock
    private RVehiculo vehiculoRepository;

    @Mock
    private RevisionTecnicaMapper mapper;

    @InjectMocks
    private RevisionTecnicaServiceImpl service;

    @Test
    void deberiaListarRevisiones() {

        RevisionTecnica revision = new RevisionTecnica();
        revision.setId(1L);

        RevisionTecnicaResponseDTO response = new RevisionTecnicaResponseDTO();
        response.setId(1L);

        when(repository.findAll()).thenReturn(List.of(revision));
        when(mapper.toResponse(revision)).thenReturn(response);

        List<RevisionTecnicaResponseDTO> resultado = service.listar();

        assertEquals(1, resultado.size());

        verify(repository).findAll();
    }

    @Test
    void deberiaObtenerRevision() {

        RevisionTecnica revision = new RevisionTecnica();
        revision.setId(1L);

        RevisionTecnicaResponseDTO response = new RevisionTecnicaResponseDTO();
        response.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(revision));
        when(mapper.toResponse(revision)).thenReturn(response);

        RevisionTecnicaResponseDTO resultado = service.obtener(1L);

        assertEquals(1L, resultado.getId());

        verify(repository).findById(1L);
    }

    @Test
    void deberiaCrearRevision() {

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(4L);

        RevisionTecnicaRequestDTO request = new RevisionTecnicaRequestDTO();
        request.setVehiculoId(4L);
        request.setFechaRevision(LocalDate.now());
        request.setFechaVencimiento(LocalDate.now().plusYears(1));
        request.setResultado("Aprobada");

        RevisionTecnica revision = new RevisionTecnica();

        RevisionTecnicaResponseDTO response = new RevisionTecnicaResponseDTO();
        response.setVehiculoId(4L);

        when(vehiculoRepository.findById(4L)).thenReturn(Optional.of(vehiculo));
        when(mapper.toEntity(request, vehiculo)).thenReturn(revision);
        when(repository.save(revision)).thenReturn(revision);
        when(mapper.toResponse(revision)).thenReturn(response);

        RevisionTecnicaResponseDTO resultado = service.crear(request);

        assertEquals(4L, resultado.getVehiculoId());

        verify(repository).save(revision);
    }

    @Test
    void deberiaActualizarRevision() {

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(4L);

        RevisionTecnica revision = new RevisionTecnica();

        RevisionTecnicaRequestDTO request = new RevisionTecnicaRequestDTO();
        request.setVehiculoId(4L);
        request.setFechaRevision(LocalDate.now());
        request.setFechaVencimiento(LocalDate.now().plusYears(1));
        request.setResultado("Rechazada");

        RevisionTecnicaResponseDTO response = new RevisionTecnicaResponseDTO();
        response.setResultado("Rechazada");

        when(repository.findById(1L)).thenReturn(Optional.of(revision));
        when(vehiculoRepository.findById(4L)).thenReturn(Optional.of(vehiculo));
        when(repository.save(revision)).thenReturn(revision);
        when(mapper.toResponse(revision)).thenReturn(response);

        RevisionTecnicaResponseDTO resultado = service.actualizar(1L, request);

        assertEquals("Rechazada", resultado.getResultado());

        verify(mapper).updateEntity(revision, request, vehiculo);
        verify(repository).save(revision);
    }

    @Test
    void deberiaEliminarRevision() {

        RevisionTecnica revision = new RevisionTecnica();

        when(repository.findById(1L)).thenReturn(Optional.of(revision));

        service.eliminar(1L);

        verify(repository).delete(revision);
    }

    @Test
    void deberiaLanzarExcepcionSiRevisionNoExiste() {

        when(repository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.obtener(1L));

        assertEquals("Revisión técnica no encontrada", ex.getMessage());
    }

    @Test
    void deberiaLanzarExcepcionSiVehiculoNoExisteAlCrear() {

        RevisionTecnicaRequestDTO request = new RevisionTecnicaRequestDTO();
        request.setVehiculoId(4L);

        when(vehiculoRepository.findById(4L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.crear(request));

        assertEquals("Vehículo no encontrado", ex.getMessage());
    }

    @Test
    void deberiaLanzarExcepcionSiVehiculoNoExisteAlActualizar() {

        RevisionTecnica revision = new RevisionTecnica();

        RevisionTecnicaRequestDTO request = new RevisionTecnicaRequestDTO();
        request.setVehiculoId(4L);

        when(repository.findById(1L)).thenReturn(Optional.of(revision));
        when(vehiculoRepository.findById(4L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.actualizar(1L, request));

        assertEquals("Vehículo no encontrado", ex.getMessage());
    }

    @Test
    void deberiaLanzarExcepcionAlEliminarRevisionInexistente() {

        when(repository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.eliminar(1L));

        assertEquals("Revisión técnica no encontrada", ex.getMessage());
    }

}