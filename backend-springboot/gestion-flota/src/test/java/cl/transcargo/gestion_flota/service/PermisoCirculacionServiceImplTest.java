package cl.transcargo.gestion_flota.service;

import cl.transcargo.gestion_flota.dto.Requests.PermisoCirculacionRequestDTO;
import cl.transcargo.gestion_flota.dto.Responses.PermisoCirculacionResponseDTO;
import cl.transcargo.gestion_flota.entity.PermisoCirculacion;
import cl.transcargo.gestion_flota.entity.Vehiculo;
import cl.transcargo.gestion_flota.mapper.PermisoCirculacionMapper;
import cl.transcargo.gestion_flota.repository.RPermisoCirculacion;
import cl.transcargo.gestion_flota.repository.RVehiculo;
import cl.transcargo.gestion_flota.service.ServiceImpl.PermisoCirculacionServiceImpl;
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
class PermisoCirculacionServiceImplTest {

    @Mock
    private RPermisoCirculacion repository;

    @Mock
    private RVehiculo vehiculoRepository;

    @Mock
    private PermisoCirculacionMapper mapper;

    @InjectMocks
    private PermisoCirculacionServiceImpl service;

    @Test
    void deberiaListarPermisos() {

        PermisoCirculacion permiso = new PermisoCirculacion();
        permiso.setId(2L);

        PermisoCirculacionResponseDTO response = new PermisoCirculacionResponseDTO();
        response.setId(2L);

        when(repository.findAll()).thenReturn(List.of(permiso));
        when(mapper.toResponse(permiso)).thenReturn(response);

        List<PermisoCirculacionResponseDTO> resultado = service.listar();

        assertEquals(1, resultado.size());

        verify(repository).findAll();
    }

    @Test
    void deberiaObtenerPermiso() {

        PermisoCirculacion permiso = new PermisoCirculacion();
        permiso.setId(2L);

        PermisoCirculacionResponseDTO response = new PermisoCirculacionResponseDTO();
        response.setId(2L);

        when(repository.findById(2L)).thenReturn(Optional.of(permiso));
        when(mapper.toResponse(permiso)).thenReturn(response);

        PermisoCirculacionResponseDTO resultado = service.obtener(2L);

        assertEquals(2L, resultado.getId());

        verify(repository).findById(2L);
    }

    @Test
    void deberiaCrearPermiso() {

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(2L);

        PermisoCirculacionRequestDTO request = new PermisoCirculacionRequestDTO();
        request.setVehiculoId(2L);
        request.setFechaEmision(LocalDate.now());
        request.setFechaVencimiento(LocalDate.now().plusYears(1));
        request.setEstado("VIGENTE");

        PermisoCirculacion permiso = new PermisoCirculacion();

        PermisoCirculacionResponseDTO response = new PermisoCirculacionResponseDTO();
        response.setVehiculoId(2L);

        when(vehiculoRepository.findById(2L)).thenReturn(Optional.of(vehiculo));
        when(mapper.toEntity(request, vehiculo)).thenReturn(permiso);
        when(repository.save(permiso)).thenReturn(permiso);
        when(mapper.toResponse(permiso)).thenReturn(response);

        PermisoCirculacionResponseDTO resultado = service.crear(request);

        assertEquals(2L, resultado.getVehiculoId());

        verify(repository).save(permiso);
    }

    @Test
    void deberiaActualizarPermiso() {

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(2L);

        PermisoCirculacion permiso = new PermisoCirculacion();

        PermisoCirculacionRequestDTO request = new PermisoCirculacionRequestDTO();
        request.setVehiculoId(2L);
        request.setFechaEmision(LocalDate.now());
        request.setFechaVencimiento(LocalDate.now().plusYears(1));
        request.setEstado("VENCIDO");

        PermisoCirculacionResponseDTO response = new PermisoCirculacionResponseDTO();
        response.setEstado("VENCIDO");

        when(repository.findById(2L)).thenReturn(Optional.of(permiso));
        when(vehiculoRepository.findById(2L)).thenReturn(Optional.of(vehiculo));
        when(repository.save(permiso)).thenReturn(permiso);
        when(mapper.toResponse(permiso)).thenReturn(response);

        PermisoCirculacionResponseDTO resultado = service.actualizar(2L, request);

        assertEquals("VENCIDO", resultado.getEstado());

        verify(mapper).updateEntity(permiso, request, vehiculo);
        verify(repository).save(permiso);
    }

    @Test
    void deberiaEliminarPermiso() {

        PermisoCirculacion permiso = new PermisoCirculacion();

        when(repository.findById(2L)).thenReturn(Optional.of(permiso));

        service.eliminar(2L);

        verify(repository).delete(permiso);
    }

    @Test
    void deberiaLanzarExcepcionSiPermisoNoExiste() {

        when(repository.findById(2L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> service.obtener(2L));

        assertEquals("Permiso de circulación no encontrado", ex.getMessage());
    }

    @Test
    void deberiaLanzarExcepcionSiVehiculoNoExisteAlCrear() {

        PermisoCirculacionRequestDTO request = new PermisoCirculacionRequestDTO();
        request.setVehiculoId(2L);

        when(vehiculoRepository.findById(2L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> service.crear(request));

        assertEquals("Vehículo no encontrado", ex.getMessage());
    }

    @Test
    void deberiaLanzarExcepcionSiVehiculoNoExisteAlActualizar() {

        PermisoCirculacion permiso = new PermisoCirculacion();

        PermisoCirculacionRequestDTO request = new PermisoCirculacionRequestDTO();
        request.setVehiculoId(2L);

        when(repository.findById(2L)).thenReturn(Optional.of(permiso));
        when(vehiculoRepository.findById(2L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> service.actualizar(2L, request));

        assertEquals("Vehículo no encontrado", ex.getMessage());
    }

    @Test
    void deberiaLanzarExcepcionAlEliminarPermisoInexistente() {

        when(repository.findById(2L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> service.eliminar(2L));

        assertEquals("Permiso de circulación no encontrado", ex.getMessage());
    }

}