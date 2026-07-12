package cl.transcargo.gestion_flota.service;

import cl.transcargo.gestion_flota.dto.Requests.VehiculoRequestDTO;
import cl.transcargo.gestion_flota.dto.Responses.VehiculoResponseDTO;
import cl.transcargo.gestion_flota.entity.Vehiculo;
import cl.transcargo.gestion_flota.mapper.VehiculoMapper;
import cl.transcargo.gestion_flota.notification.NotificationService;
import cl.transcargo.gestion_flota.repository.RMantencion;
import cl.transcargo.gestion_flota.repository.RUsuario;
import cl.transcargo.gestion_flota.repository.RVehiculo;
import cl.transcargo.gestion_flota.service.ServiceImpl.VehiculoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehiculoServiceImplTest {

    @Mock
    private RVehiculo repository;

    @Mock
    private VehiculoMapper mapper;

    @InjectMocks
    private VehiculoServiceImpl service;

    @Mock
    private RMantencion mantencionRepository;

    @Mock
    private RUsuario usuarioRepository;

    @Mock
    private NotificationService notificationService;

    @Test
    void deberiaListarVehiculos() {

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(1L);
        vehiculo.setPatente("ABCD11");

        VehiculoResponseDTO response = new VehiculoResponseDTO();
        response.setId(1L);
        response.setPatente("ABCD11");

        when(repository.findAll()).thenReturn(List.of(vehiculo));
        when(mapper.toResponse(vehiculo)).thenReturn(response);

        List<VehiculoResponseDTO> resultado = service.listar();

        assertEquals(1, resultado.size());
        assertEquals("ABCD11", resultado.get(0).getPatente());

        verify(repository).findAll();
    }

    @Test
    void deberiaObtenerVehiculo() {

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(1L);
        vehiculo.setPatente("ABCD11");
        vehiculo.setMarca("Toyota");
        vehiculo.setModelo("Yaris");
        vehiculo.setAnio(2022);
        vehiculo.setKilometrajeActual(15000);
        vehiculo.setEstado("Activo");

        when(repository.findById(1L)).thenReturn(Optional.of(vehiculo));
        VehiculoResponseDTO resultado = service.obtener(1L);
        assertEquals("ABCD11", resultado.getPatente());
        assertEquals("Toyota", resultado.getMarca());
        verify(repository).findById(1L);
    }

    @Test
    void deberiaCrearVehiculo() {
        VehiculoRequestDTO request = new VehiculoRequestDTO();
        request.setPatente("ABCD11");
        request.setMarca("Toyota");
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setPatente("ABCD11");
        VehiculoResponseDTO response = new VehiculoResponseDTO();
        response.setPatente("ABCD11");
        when(mapper.toEntity(request)).thenReturn(vehiculo);
        when(repository.save(vehiculo)).thenReturn(vehiculo);
        when(mapper.toResponse(vehiculo)).thenReturn(response);

        VehiculoResponseDTO resultado = service.crear(request);
        assertEquals("ABCD11", resultado.getPatente());
        verify(repository).save(vehiculo);}

    @Test
    void deberiaActualizarVehiculo() {

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(1L);
        vehiculo.setPatente("AAAA11");

        VehiculoRequestDTO request = new VehiculoRequestDTO();
        request.setPatente("BBBB22");
        request.setMarca("Kia");
        request.setModelo("Rio");
        request.setAnio(2023);
        request.setKilometrajeActual(20000);
        request.setEstado("Activo");

        VehiculoResponseDTO response = new VehiculoResponseDTO();
        response.setPatente("BBBB22");

        when(repository.findById(1L))
                .thenReturn(Optional.of(vehiculo));

        when(repository.save(any(Vehiculo.class)))
                .thenReturn(vehiculo);

        when(mapper.toResponse(any(Vehiculo.class)))
                .thenReturn(response);

        // No existe mantención asociada
        when(mantencionRepository.findByVehiculoId(1L))
                .thenReturn(Optional.empty());

        VehiculoResponseDTO resultado = service.actualizar(1L, request);

        assertEquals("BBBB22", resultado.getPatente());

        verify(repository).save(any(Vehiculo.class));

        verify(notificationService, never())
                .proximaMantencion(any(), any(), any(), any(), any());

    }

    @Test
    void deberiaEliminarVehiculo() {

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(1L);

        when(repository.findById(1L))
                .thenReturn(Optional.of(vehiculo));

        service.eliminar(1L);

        verify(repository).delete(vehiculo);

    }

    @Test
    void deberiaLanzarExcepcionSiVehiculoNoExiste() {

        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> service.actualizar(1L, new VehiculoRequestDTO()));
    }

}