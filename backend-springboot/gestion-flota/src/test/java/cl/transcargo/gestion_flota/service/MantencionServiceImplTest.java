package cl.transcargo.gestion_flota.service;

import cl.transcargo.gestion_flota.dto.Requests.MantencionRequestDTO;
import cl.transcargo.gestion_flota.dto.Responses.MantencionResponseDTO;
import cl.transcargo.gestion_flota.entity.Mantencion;
import cl.transcargo.gestion_flota.entity.Vehiculo;
import cl.transcargo.gestion_flota.mapper.MantencionMapper;
import cl.transcargo.gestion_flota.notification.NotificationService;
import cl.transcargo.gestion_flota.repository.RMantencion;
import cl.transcargo.gestion_flota.repository.RVehiculo;
import cl.transcargo.gestion_flota.service.ServiceImpl.MantencionServiceImpl;
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
class MantencionServiceImplTest {

    @Mock
    private RMantencion repository;

    @Mock
    private RVehiculo vehiculoRepository;

    @Mock
    private MantencionMapper mapper;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private MantencionServiceImpl service;

    @Test
    void deberiaCrearMantencion() {

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(1L);

        MantencionRequestDTO request = new MantencionRequestDTO();
        request.setVehiculoId(1L);
        request.setFecha(LocalDate.now());
        request.setKilometraje(25000);
        request.setTipo("Preventiva");
        request.setDescripcion("Cambio de aceite");
        request.setTaller("Toyota");

        Mantencion mantencion = new Mantencion();

        MantencionResponseDTO response = new MantencionResponseDTO();
        response.setVehiculoId(1L);

        when(vehiculoRepository.findById(1L))
                .thenReturn(Optional.of(vehiculo));

        when(mapper.toEntity(request, vehiculo))
                .thenReturn(mantencion);

        when(repository.save(mantencion))
                .thenReturn(mantencion);

        when(mapper.toResponse(mantencion))
                .thenReturn(response);

        MantencionResponseDTO resultado = service.crear(request);

        assertEquals(1L, resultado.getVehiculoId());

        verify(repository).save(mantencion);
    }

    @Test
    void deberiaListarMantenciones() {

        Mantencion mantencion = new Mantencion();
        mantencion.setId(1L);

        MantencionResponseDTO response = new MantencionResponseDTO();
        response.setId(1L);

        when(repository.findAll()).thenReturn(List.of(mantencion));
        when(mapper.toResponse(mantencion)).thenReturn(response);

        List<MantencionResponseDTO> resultado = service.listar();

        assertEquals(1, resultado.size());

        verify(repository).findAll();
    }

    @Test
    void deberiaObtenerMantencion() {

        Mantencion mantencion = new Mantencion();
        mantencion.setId(1L);

        MantencionResponseDTO response = new MantencionResponseDTO();
        response.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(mantencion));
        when(mapper.toResponse(mantencion)).thenReturn(response);

        MantencionResponseDTO resultado = service.obtener(1L);

        assertEquals(1L, resultado.getId());

        verify(repository).findById(1L);
    }



    @Test
    void deberiaActualizarMantencion() {

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(2L);

        Mantencion mantencion = new Mantencion();
        mantencion.setId(2L);

        MantencionRequestDTO request = new MantencionRequestDTO();
        request.setVehiculoId(2L);
        request.setFecha(LocalDate.now());
        request.setKilometraje(30000);
        request.setTipo("Correctiva");
        request.setDescripcion("Cambio de embrague");
        request.setTaller("Kia");

        MantencionResponseDTO response = new MantencionResponseDTO();
        response.setDescripcion("Cambio de embrague");

        when(repository.findById(2L))
                .thenReturn(Optional.of(mantencion));

        when(vehiculoRepository.findById(2L))
                .thenReturn(Optional.of(vehiculo));

        when(repository.save(mantencion))
                .thenReturn(mantencion);

        when(mapper.toResponse(mantencion))
                .thenReturn(response);

        MantencionResponseDTO resultado = service.actualizar(2L, request);

        assertEquals("Cambio de embrague", resultado.getDescripcion());

        verify(mapper).updateEntity(mantencion, request, vehiculo);
        verify(repository).save(mantencion);
    }

    @Test
    void deberiaEliminarMantencion() {

        Mantencion mantencion = new Mantencion();
        mantencion.setId(2L);

        when(repository.findById(2L))
                .thenReturn(Optional.of(mantencion));

        service.eliminar(2L);

        verify(repository).delete(mantencion);
    }

    @Test
    void deberiaLanzarExcepcionSiMantencionNoExiste() {

        when(repository.findById(2L))
                .thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> service.obtener(2L));

        assertEquals("Mantención no encontrada", ex.getMessage());
    }

    @Test
    void deberiaLanzarExcepcionSiVehiculoNoExisteAlCrear() {

        MantencionRequestDTO request = new MantencionRequestDTO();
        request.setVehiculoId(2L);

        when(vehiculoRepository.findById(2L))
                .thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> service.crear(request));

        assertEquals("Vehículo no encontrado", ex.getMessage());
    }

    @Test
    void deberiaLanzarExcepcionSiVehiculoNoExisteAlActualizar() {

        Mantencion mantencion = new Mantencion();

        MantencionRequestDTO request = new MantencionRequestDTO();
        request.setVehiculoId(2L);

        when(repository.findById(2L))
                .thenReturn(Optional.of(mantencion));

        when(vehiculoRepository.findById(2L))
                .thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> service.actualizar(2L, request));

        assertEquals("Vehículo no encontrado", ex.getMessage());
    }

    @Test
    void deberiaLanzarExcepcionAlEliminarMantencionInexistente() {

        when(repository.findById(2L))
                .thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> service.eliminar(2L));

        assertEquals("Mantención no encontrada", ex.getMessage());
    }
}