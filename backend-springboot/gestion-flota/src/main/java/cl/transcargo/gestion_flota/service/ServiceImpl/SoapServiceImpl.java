package cl.transcargo.gestion_flota.service.ServiceImpl;

import cl.transcargo.gestion_flota.dto.Requests.SoapRequestDTO;
import cl.transcargo.gestion_flota.dto.Responses.SoapResponseDTO;
import cl.transcargo.gestion_flota.entity.Soap;
import cl.transcargo.gestion_flota.entity.Vehiculo;
import cl.transcargo.gestion_flota.mapper.SoapMapper;
import cl.transcargo.gestion_flota.repository.RSoap;
import cl.transcargo.gestion_flota.repository.RVehiculo;
import cl.transcargo.gestion_flota.service.IService.ISoap;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SoapServiceImpl implements ISoap {

    private final RSoap repository;
    private final RVehiculo vehiculoRepository;
    private final SoapMapper mapper;

    public SoapServiceImpl(RSoap repository,
                           RVehiculo vehiculoRepository,
                           SoapMapper mapper) {
        this.repository = repository;
        this.vehiculoRepository = vehiculoRepository;
        this.mapper = mapper;
    }

    @Override
    public List<SoapResponseDTO> listar() {

        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public SoapResponseDTO obtener(Long id) {

        Soap soap = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("SOAP no encontrado"));

        return mapper.toResponse(soap);
    }

    @Override
    public SoapResponseDTO crear(SoapRequestDTO request) {

        Vehiculo vehiculo = vehiculoRepository.findById(request.getVehiculoId())
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));

        Soap soap = mapper.toEntity(request, vehiculo);

        soap = repository.save(soap);

        return mapper.toResponse(soap);
    }

    @Override
    public SoapResponseDTO actualizar(Long id, SoapRequestDTO request) {

        Soap soap = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("SOAP no encontrado"));

        Vehiculo vehiculo = vehiculoRepository.findById(request.getVehiculoId())
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));

        mapper.updateEntity(soap, request, vehiculo);

        soap = repository.save(soap);

        return mapper.toResponse(soap);
    }

    @Override
    public void eliminar(Long id) {

        Soap soap = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("SOAP no encontrado"));

        repository.delete(soap);
    }
}

