package cl.transcargo.gestion_flota.service;

import cl.transcargo.gestion_flota.dto.Requests.FallaRequestDTO;
import cl.transcargo.gestion_flota.dto.Responses.FallaResponseDTO;
import cl.transcargo.gestion_flota.entity.Falla;
import cl.transcargo.gestion_flota.entity.Vehiculo;
import cl.transcargo.gestion_flota.mapper.FallaMapper;
import cl.transcargo.gestion_flota.repository.RFalla;
import cl.transcargo.gestion_flota.repository.RVehiculo;
import cl.transcargo.gestion_flota.service.ServiceImpl.FallaServiceImpl;
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
class FallaServiceImplTest {

    @Mock
    private RFalla repository;

    @Mock
    private RVehiculo vehiculoRepository;

    @Mock
    private FallaMapper mapper;

    @InjectMocks
    private FallaServiceImpl service;

    @Test
    void deberiaListarFallas() {

        Falla falla = new Falla();
        falla.setId(1L);

        FallaResponseDTO response = new FallaResponseDTO();
        response.setId(1L);

        when(repository.findAll()).thenReturn(List.of(falla));
        when(mapper.toResponse(falla)).thenReturn(response);

        List<FallaResponseDTO> resultado = service.listar();

        assertEquals(1, resultado.size());

        verify(repository).findAll();
    }

    @Test
    void deberiaObtenerFalla() {

        Falla falla = new Falla();
        falla.setId(10L);

        FallaResponseDTO response = new FallaResponseDTO();
        response.setId(10L);

        when(repository.findById(10L)).thenReturn(Optional.of(falla));
        when(mapper.toResponse(falla)).thenReturn(response);

        FallaResponseDTO resultado = service.obtener(10L);

        assertEquals(10L, resultado.getId());

        verify(repository).findById(10L);
    }

    @Test
    void deberiaCrearFalla() {

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(5L);

        FallaRequestDTO request = new FallaRequestDTO();
        request.setVehiculoId(5L);
        request.setFecha(LocalDate.now());
        request.setDescripcion("Fuga de aceite");
        request.setPrioridad("Alta");
        request.setEstado("Pendiente");

        Falla falla = new Falla();

        FallaResponseDTO response = new FallaResponseDTO();
        response.setVehiculoId(5L);

        when(vehiculoRepository.findById(5L)).thenReturn(Optional.of(vehiculo));
        when(mapper.toEntity(request, vehiculo)).thenReturn(falla);
        when(repository.save(falla)).thenReturn(falla);
        when(mapper.toResponse(falla)).thenReturn(response);

        FallaResponseDTO resultado = service.crear(request);

        assertEquals(5L, resultado.getVehiculoId());

        verify(repository).save(falla);
    }

    @Test
    void deberiaActualizarFalla() {

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(5L);

        Falla falla = new Falla();

        FallaRequestDTO request = new FallaRequestDTO();
        request.setVehiculoId(5L);
        request.setFecha(LocalDate.now());
        request.setDescripcion("Motor");
        request.setPrioridad("Crítica");
        request.setEstado("En reparación");

        FallaResponseDTO response = new FallaResponseDTO();
        response.setEstado("En reparación");

        when(repository.findById(10L)).thenReturn(Optional.of(falla));
        when(vehiculoRepository.findById(5L)).thenReturn(Optional.of(vehiculo));
        when(repository.save(falla)).thenReturn(falla);
        when(mapper.toResponse(falla)).thenReturn(response);

        FallaResponseDTO resultado = service.actualizar(10L, request);

        assertEquals("En reparación", resultado.getEstado());

        verify(mapper).updateEntity(falla, request, vehiculo);
        verify(repository).save(falla);
    }

    @Test
    void deberiaEliminarFalla() {

        Falla falla = new Falla();

        when(repository.findById(10L)).thenReturn(Optional.of(falla));

        service.eliminar(10L);

        verify(repository).delete(falla);
    }

    @Test
    void deberiaLanzarExcepcionSiFallaNoExiste() {

        when(repository.findById(10L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.obtener(10L));

        assertEquals("Falla no encontrada", ex.getMessage());
    }

    @Test
    void deberiaLanzarExcepcionSiVehiculoNoExisteAlCrear() {

        FallaRequestDTO request = new FallaRequestDTO();
        request.setVehiculoId(5L);

        when(vehiculoRepository.findById(5L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.crear(request));

        assertEquals("Vehículo no encontrado", ex.getMessage());
    }

    @Test
    void deberiaLanzarExcepcionSiVehiculoNoExisteAlActualizar() {

        Falla falla = new Falla();

        FallaRequestDTO request = new FallaRequestDTO();
        request.setVehiculoId(5L);

        when(repository.findById(10L)).thenReturn(Optional.of(falla));
        when(vehiculoRepository.findById(5L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.actualizar(10L, request));

        assertEquals("Vehículo no encontrado", ex.getMessage());
    }

    @Test
    void deberiaLanzarExcepcionAlEliminarFallaInexistente() {

        when(repository.findById(10L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.eliminar(10L));

        assertEquals("Falla no encontrada", ex.getMessage());
    }

}