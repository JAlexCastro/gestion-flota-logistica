package cl.transcargo.gestion_flota.service;

import cl.transcargo.gestion_flota.dto.Requests.SoapRequestDTO;
import cl.transcargo.gestion_flota.dto.Responses.SoapResponseDTO;
import cl.transcargo.gestion_flota.entity.Soap;
import cl.transcargo.gestion_flota.entity.Vehiculo;
import cl.transcargo.gestion_flota.mapper.SoapMapper;
import cl.transcargo.gestion_flota.repository.RSoap;
import cl.transcargo.gestion_flota.repository.RVehiculo;
import cl.transcargo.gestion_flota.service.ServiceImpl.SoapServiceImpl;
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
class SoapServiceImplTest {

    @Mock
    private RSoap repository;

    @Mock
    private RVehiculo vehiculoRepository;

    @Mock
    private SoapMapper mapper;

    @InjectMocks
    private SoapServiceImpl service;

    @Test
    void deberiaListarSoap() {

        Soap soap = new Soap();
        soap.setId(2L);

        SoapResponseDTO response = new SoapResponseDTO();
        response.setId(2L);

        when(repository.findAll()).thenReturn(List.of(soap));
        when(mapper.toResponse(soap)).thenReturn(response);

        List<SoapResponseDTO> resultado = service.listar();

        assertEquals(1, resultado.size());

        verify(repository).findAll();
    }

    @Test
    void deberiaObtenerSoap() {

        Soap soap = new Soap();
        soap.setId(2L);

        SoapResponseDTO response = new SoapResponseDTO();
        response.setId(2L);

        when(repository.findById(2L)).thenReturn(Optional.of(soap));
        when(mapper.toResponse(soap)).thenReturn(response);

        SoapResponseDTO resultado = service.obtener(2L);

        assertEquals(2L, resultado.getId());

        verify(repository).findById(2L);
    }

    @Test
    void deberiaCrearSoap() {

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(2L);

        SoapRequestDTO request = new SoapRequestDTO();
        request.setVehiculoId(2L);
        request.setAseguradora("BCI Seguros");
        request.setNumeroPoliza("POL123");
        request.setFechaEmision(LocalDate.now());
        request.setFechaVencimiento(LocalDate.now().plusYears(1));

        Soap soap = new Soap();

        SoapResponseDTO response = new SoapResponseDTO();
        response.setVehiculoId(2L);

        when(vehiculoRepository.findById(2L)).thenReturn(Optional.of(vehiculo));
        when(mapper.toEntity(request, vehiculo)).thenReturn(soap);
        when(repository.save(soap)).thenReturn(soap);
        when(mapper.toResponse(soap)).thenReturn(response);

        SoapResponseDTO resultado = service.crear(request);

        assertEquals(2L, resultado.getVehiculoId());

        verify(repository).save(soap);
    }

    @Test
    void deberiaActualizarSoap() {

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(2L);

        Soap soap = new Soap();

        SoapRequestDTO request = new SoapRequestDTO();
        request.setVehiculoId(2L);
        request.setAseguradora("Mapfre");
        request.setNumeroPoliza("POL999");
        request.setFechaEmision(LocalDate.now());
        request.setFechaVencimiento(LocalDate.now().plusYears(1));

        SoapResponseDTO response = new SoapResponseDTO();
        response.setAseguradora("Mapfre");

        when(repository.findById(2L)).thenReturn(Optional.of(soap));
        when(vehiculoRepository.findById(2L)).thenReturn(Optional.of(vehiculo));
        when(repository.save(soap)).thenReturn(soap);
        when(mapper.toResponse(soap)).thenReturn(response);

        SoapResponseDTO resultado = service.actualizar(2L, request);

        assertEquals("Mapfre", resultado.getAseguradora());

        verify(mapper).updateEntity(soap, request, vehiculo);
        verify(repository).save(soap);
    }

    @Test
    void deberiaEliminarSoap() {

        Soap soap = new Soap();

        when(repository.findById(2L)).thenReturn(Optional.of(soap));

        service.eliminar(2L);

        verify(repository).delete(soap);
    }

    @Test
    void deberiaLanzarExcepcionSiSoapNoExiste() {

        when(repository.findById(2L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> service.obtener(2L));

        assertEquals("SOAP no encontrado", ex.getMessage());
    }

    @Test
    void deberiaLanzarExcepcionSiVehiculoNoExisteAlCrear() {

        SoapRequestDTO request = new SoapRequestDTO();
        request.setVehiculoId(2L);

        when(vehiculoRepository.findById(2L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> service.crear(request));

        assertEquals("Vehículo no encontrado", ex.getMessage());
    }

    @Test
    void deberiaLanzarExcepcionSiVehiculoNoExisteAlActualizar() {

        Soap soap = new Soap();

        SoapRequestDTO request = new SoapRequestDTO();
        request.setVehiculoId(2L);

        when(repository.findById(2L)).thenReturn(Optional.of(soap));
        when(vehiculoRepository.findById(2L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> service.actualizar(2L, request));

        assertEquals("Vehículo no encontrado", ex.getMessage());
    }

    @Test
    void deberiaLanzarExcepcionAlEliminarSoapInexistente() {

        when(repository.findById(2L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> service.eliminar(2L));

        assertEquals("SOAP no encontrado", ex.getMessage());
    }

}