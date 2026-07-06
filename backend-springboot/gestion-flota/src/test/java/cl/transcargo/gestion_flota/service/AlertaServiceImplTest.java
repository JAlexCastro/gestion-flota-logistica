package cl.transcargo.gestion_flota.service;

import cl.transcargo.gestion_flota.dto.Requests.AlertaRequestDTO;
import cl.transcargo.gestion_flota.dto.Responses.AlertaResponseDTO;
import cl.transcargo.gestion_flota.entity.Alertas;
import cl.transcargo.gestion_flota.entity.Vehiculo;
import cl.transcargo.gestion_flota.mapper.AlertaMapper;
import cl.transcargo.gestion_flota.repository.RAlertas;
import cl.transcargo.gestion_flota.repository.RVehiculo;
import cl.transcargo.gestion_flota.service.ServiceImpl.AlertaServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlertaServiceImplTest {

    @Mock
    private RAlertas repository;

    @Mock
    private RVehiculo vehiculoRepository;

    @Mock
    private AlertaMapper mapper;

    @InjectMocks
    private AlertaServiceImpl service;

    @Test
    void deberiaListarAlertas() {

        Alertas alerta = new Alertas();
        alerta.setId(2L);

        AlertaResponseDTO response = new AlertaResponseDTO();
        response.setId(2L);

        when(repository.findAll()).thenReturn(List.of(alerta));
        when(mapper.toResponse(alerta)).thenReturn(response);

        List<AlertaResponseDTO> resultado = service.listar();

        assertEquals(1, resultado.size());

        verify(repository).findAll();
    }

    @Test
    void deberiaObtenerAlerta() {

        Alertas alerta = new Alertas();
        alerta.setId(2L);

        AlertaResponseDTO response = new AlertaResponseDTO();
        response.setId(2L);

        when(repository.findById(2L)).thenReturn(Optional.of(alerta));
        when(mapper.toResponse(alerta)).thenReturn(response);

        AlertaResponseDTO resultado = service.obtener(2L);

        assertEquals(2L, resultado.getId());

        verify(repository).findById(2L);
    }

    @Test
    void deberiaCrearAlerta() {

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(2L);

        AlertaRequestDTO request = new AlertaRequestDTO();
        request.setVehiculoId(2L);
        request.setTipo("SOAP");
        request.setMensaje("SOAP próximo a vencer");
        request.setFechaGeneracion(LocalDateTime.now());
        request.setLeida(false);

        Alertas alerta = new Alertas();

        AlertaResponseDTO response = new AlertaResponseDTO();
        response.setVehiculoId(2L);

        when(vehiculoRepository.findById(2L)).thenReturn(Optional.of(vehiculo));
        when(mapper.toEntity(request, vehiculo)).thenReturn(alerta);
        when(repository.save(alerta)).thenReturn(alerta);
        when(mapper.toResponse(alerta)).thenReturn(response);

        AlertaResponseDTO resultado = service.crear(request);

        assertEquals(2L, resultado.getVehiculoId());

        verify(repository).save(alerta);
    }

    @Test
    void deberiaActualizarAlerta() {

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(2L);

        Alertas alerta = new Alertas();

        AlertaRequestDTO request = new AlertaRequestDTO();
        request.setVehiculoId(2L);
        request.setTipo("Permiso");
        request.setMensaje("Permiso vencido");
        request.setFechaGeneracion(LocalDateTime.now());
        request.setLeida(true);

        AlertaResponseDTO response = new AlertaResponseDTO();
        response.setMensaje("Permiso vencido");

        when(repository.findById(2L)).thenReturn(Optional.of(alerta));
        when(vehiculoRepository.findById(2L)).thenReturn(Optional.of(vehiculo));
        when(repository.save(alerta)).thenReturn(alerta);
        when(mapper.toResponse(alerta)).thenReturn(response);

        AlertaResponseDTO resultado = service.actualizar(2L, request);

        assertEquals("Permiso vencido", resultado.getMensaje());

        verify(mapper).updateEntity(alerta, request, vehiculo);
        verify(repository).save(alerta);
    }

    @Test
    void deberiaEliminarAlerta() {

        Alertas alerta = new Alertas();

        when(repository.findById(2L)).thenReturn(Optional.of(alerta));

        service.eliminar(2L);

        verify(repository).delete(alerta);
    }

    @Test
    void deberiaLanzarExcepcionSiAlertaNoExiste() {

        when(repository.findById(2L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.obtener(2L));

        assertEquals("Alerta no encontrada", ex.getMessage());
    }

    @Test
    void deberiaLanzarExcepcionSiVehiculoNoExisteAlCrear() {

        AlertaRequestDTO request = new AlertaRequestDTO();
        request.setVehiculoId(2L);

        when(vehiculoRepository.findById(2L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.crear(request));

        assertEquals("Vehículo no encontrado", ex.getMessage());
    }

    @Test
    void deberiaLanzarExcepcionSiVehiculoNoExisteAlActualizar() {

        Alertas alerta = new Alertas();

        AlertaRequestDTO request = new AlertaRequestDTO();
        request.setVehiculoId(2L);

        when(repository.findById(2L)).thenReturn(Optional.of(alerta));
        when(vehiculoRepository.findById(2L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.actualizar(2L, request));

        assertEquals("Vehículo no encontrado", ex.getMessage());
    }

    @Test
    void deberiaLanzarExcepcionAlEliminarAlertaInexistente() {

        when(repository.findById(2L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.eliminar(2L));

        assertEquals("Alerta no encontrada", ex.getMessage());
    }

}