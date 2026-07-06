package cl.transcargo.gestion_flota.service;

import cl.transcargo.gestion_flota.dto.Requests.EmisionGasesRequestDTO;
import cl.transcargo.gestion_flota.dto.Responses.EmisionGasesResponseDTO;
import cl.transcargo.gestion_flota.entity.EmisionesGases;
import cl.transcargo.gestion_flota.entity.Vehiculo;
import cl.transcargo.gestion_flota.mapper.EmisionesGasesMapper;
import cl.transcargo.gestion_flota.repository.REmisionesGases;
import cl.transcargo.gestion_flota.repository.RVehiculo;
import cl.transcargo.gestion_flota.service.ServiceImpl.EmisionesGasesServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmisionesGasesServiceImplTest {

    @Mock
    private REmisionesGases repository;

    @Mock
    private RVehiculo vehiculoRepository;

    @Mock
    private EmisionesGasesMapper mapper;

    @InjectMocks
    private EmisionesGasesServiceImpl service;

    @Test
    void deberiaListarEmisiones() {

        EmisionesGases emision = new EmisionesGases();
        emision.setId(1L);

        EmisionGasesResponseDTO response = new EmisionGasesResponseDTO();
        response.setId(1L);

        when(repository.findAll()).thenReturn(List.of(emision));
        when(mapper.toResponse(emision)).thenReturn(response);

        List<EmisionGasesResponseDTO> resultado = service.listar();

        assertEquals(1, resultado.size());

        verify(repository).findAll();
    }

    @Test
    void deberiaObtenerEmision() {

        EmisionesGases emision = new EmisionesGases();
        emision.setId(1L);

        EmisionGasesResponseDTO response = new EmisionGasesResponseDTO();
        response.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(emision));
        when(mapper.toResponse(emision)).thenReturn(response);

        EmisionGasesResponseDTO resultado = service.obtener(1L);

        assertEquals(1L, resultado.getId());

        verify(repository).findById(1L);
    }

    @Test
    void deberiaCrearEmision() {

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(1L);

        EmisionGasesRequestDTO request = new EmisionGasesRequestDTO();
        request.setVehiculoId(1L);
        request.setFechaRevision(LocalDate.now());
        request.setFechaVencimiento(LocalDate.now().plusYears(1));
        request.setResultado("Aprobado");

        EmisionesGases emision = new EmisionesGases();

        EmisionGasesResponseDTO response = new EmisionGasesResponseDTO();
        response.setVehiculoId(1L);

        when(repository.existsByVehiculoId(1L)).thenReturn(false);
        when(vehiculoRepository.findById(1L)).thenReturn(Optional.of(vehiculo));
        when(mapper.toEntity(request, vehiculo)).thenReturn(emision);
        when(repository.save(emision)).thenReturn(emision);
        when(mapper.toResponse(emision)).thenReturn(response);

        EmisionGasesResponseDTO resultado = service.crear(request);

        assertEquals(1L, resultado.getVehiculoId());

        verify(repository).save(emision);
    }

    @Test
    void deberiaActualizarEmision() {

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(1L);

        EmisionesGases emision = new EmisionesGases();

        EmisionGasesRequestDTO request = new EmisionGasesRequestDTO();
        request.setVehiculoId(1L);
        request.setResultado("Rechazado");

        EmisionGasesResponseDTO response = new EmisionGasesResponseDTO();
        response.setResultado("Rechazado");

        when(repository.findById(1L)).thenReturn(Optional.of(emision));
        when(vehiculoRepository.findById(1L)).thenReturn(Optional.of(vehiculo));
        when(repository.save(emision)).thenReturn(emision);
        when(mapper.toResponse(emision)).thenReturn(response);

        EmisionGasesResponseDTO resultado = service.actualizar(1L, request);

        assertEquals("Rechazado", resultado.getResultado());

        verify(mapper).updateEntity(emision, request, vehiculo);
        verify(repository).save(emision);
    }

    @Test
    void deberiaEliminarEmision() {

        EmisionesGases emision = new EmisionesGases();

        when(repository.findById(1L)).thenReturn(Optional.of(emision));

        service.eliminar(1L);

        verify(repository).delete(emision);
    }

    @Test
    void deberiaLanzarExcepcionSiEmisionNoExiste() {

        when(repository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> service.obtener(1L));

        assertEquals("Registro de emisión no encontrado", ex.getMessage());
    }

    @Test
    void deberiaLanzarExcepcionSiVehiculoNoExisteAlCrear() {

        EmisionGasesRequestDTO request = new EmisionGasesRequestDTO();
        request.setVehiculoId(1L);

        when(repository.existsByVehiculoId(1L)).thenReturn(false);
        when(vehiculoRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> service.crear(request));

        assertEquals("Vehículo no encontrado", ex.getMessage());
    }

    @Test
    void deberiaLanzarExcepcionSiVehiculoNoExisteAlActualizar() {

        EmisionesGases emision = new EmisionesGases();

        EmisionGasesRequestDTO request = new EmisionGasesRequestDTO();
        request.setVehiculoId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(emision));
        when(vehiculoRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> service.actualizar(1L, request));

        assertEquals("Vehículo no encontrado", ex.getMessage());
    }

    @Test
    void deberiaLanzarConflictSiVehiculoYaTieneEmision() {

        EmisionGasesRequestDTO request = new EmisionGasesRequestDTO();
        request.setVehiculoId(1L);

        when(repository.existsByVehiculoId(1L)).thenReturn(true);

        ResponseStatusException ex = assertThrows(
                ResponseStatusException.class,
                () -> service.crear(request));

        assertEquals(409, ex.getStatusCode().value());
        assertEquals("409 CONFLICT \"El vehículo ya tiene la emision registrada\"", ex.getMessage());
    }

    @Test
    void deberiaLanzarExcepcionAlEliminarEmisionInexistente() {

        when(repository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> service.eliminar(1L));

        assertEquals("Registro de emisión no encontrado", ex.getMessage());
    }

}