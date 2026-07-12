package cl.transcargo.gestion_flota.service;

import cl.transcargo.gestion_flota.dto.Requests.ConductorRequestDTO;
import cl.transcargo.gestion_flota.dto.Responses.ConductorResponseDTO;
import cl.transcargo.gestion_flota.entity.Conductor;
import cl.transcargo.gestion_flota.mapper.ConductorMapper;
import cl.transcargo.gestion_flota.repository.RConductor;
import cl.transcargo.gestion_flota.repository.RUsuario;
import cl.transcargo.gestion_flota.service.ServiceImpl.ConductorServiceImpl;
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
class ConductorServiceImplTest {

    @Mock
    private RConductor repository;

    @Mock
    private RUsuario usuarioRepository;

    @Mock
    private ConductorMapper mapper;

    @InjectMocks
    private ConductorServiceImpl service;

    @Test
    void deberiaListarConductores() {

        Conductor conductor = new Conductor();
        conductor.setId(1L);
        conductor.setNombre("Juan Pérez");

        ConductorResponseDTO response = new ConductorResponseDTO();
        response.setId(1L);
        response.setNombre("Juan Pérez");

        when(repository.findAll()).thenReturn(List.of(conductor));
        when(mapper.toResponse(conductor)).thenReturn(response);

        List<ConductorResponseDTO> resultado = service.listar();

        assertEquals(1, resultado.size());
        assertEquals("Juan Pérez", resultado.get(0).getNombre());

        verify(repository).findAll();
    }

    @Test
    void deberiaObtenerConductor() {

        Conductor conductor = new Conductor();
        conductor.setId(1L);
        conductor.setRut("11.111.111-1");
        conductor.setNombre("Juan Pérez");
        conductor.setTelefono("987654321");
        conductor.setNumeroLicencia(111111);
        conductor.setFechaVencimientoLicencia(LocalDate.of(2028, 5, 20));

        ConductorResponseDTO response = new ConductorResponseDTO();
        response.setId(1L);
        response.setNombre("Juan Pérez");

        when(repository.findById(1L)).thenReturn(Optional.of(conductor));
        when(mapper.toResponse(conductor)).thenReturn(response);

        ConductorResponseDTO resultado = service.obtener(1L);

        assertEquals("Juan Pérez", resultado.getNombre());

        verify(repository).findById(1L);
    }

    @Test
    void deberiaCrearConductor() {

        ConductorRequestDTO request = new ConductorRequestDTO();
        request.setRut("11.111.111-1");
        request.setNombre("Juan Pérez");
        request.setTelefono("987654321");
        request.setNumeroLicencia(111111);
        request.setFechaVencimientoLicencia(LocalDate.of(2028, 5, 20));

        Conductor conductor = new Conductor();
        conductor.setNombre("Juan Pérez");

        ConductorResponseDTO response = new ConductorResponseDTO();
        response.setNombre("Juan Pérez");

        when(mapper.toEntity(request)).thenReturn(conductor);
        when(repository.save(conductor)).thenReturn(conductor);
        when(mapper.toResponse(conductor)).thenReturn(response);

        ConductorResponseDTO resultado = service.crear(request);

        assertEquals("Juan Pérez", resultado.getNombre());

        verify(repository).save(conductor);
    }

    @Test
    void deberiaActualizarConductor() {

        Conductor conductor = new Conductor();
        conductor.setId(1L);

        ConductorRequestDTO request = new ConductorRequestDTO();
        request.setRut("22.222.222-2");
        request.setNombre("Pedro González");
        request.setTelefono("999999999");
        request.setNumeroLicencia(111111);
        request.setFechaVencimientoLicencia(LocalDate.of(2030, 10, 15));

        ConductorResponseDTO response = new ConductorResponseDTO();
        response.setNombre("Pedro González");

        when(repository.findById(1L)).thenReturn(Optional.of(conductor));
        when(repository.save(conductor)).thenReturn(conductor);
        when(mapper.toResponse(conductor)).thenReturn(response);

        ConductorResponseDTO resultado = service.actualizar(1L, request);

        assertEquals("Pedro González", resultado.getNombre());

        verify(mapper).updateEntity(conductor, request);
        verify(repository).save(conductor);
    }

    @Test
    void deberiaEliminarConductor() {

        Conductor conductor = new Conductor();
        conductor.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(conductor));

        service.eliminar(1L);

        verify(repository).delete(conductor);
    }

    @Test
    void deberiaLanzarExcepcionSiConductorNoExiste() {

        when(repository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.obtener(1L)
        );

        assertEquals("Conductor no encontrado", exception.getMessage());
    }

    @Test
    void deberiaLanzarExcepcionAlActualizarConductorInexistente() {

        when(repository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.actualizar(1L, new ConductorRequestDTO())
        );

        assertEquals("Conductor no encontrado", exception.getMessage());
    }

    @Test
    void deberiaLanzarExcepcionAlEliminarConductorInexistente() {

        when(repository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.eliminar(1L)
        );

        assertEquals("Conductor no encontrado", exception.getMessage());
    }
}