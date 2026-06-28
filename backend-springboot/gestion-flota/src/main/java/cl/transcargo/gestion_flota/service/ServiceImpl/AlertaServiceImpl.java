package cl.transcargo.gestion_flota.service.ServiceImpl;

import cl.transcargo.gestion_flota.dto.Requests.AlertaRequestDTO;
import cl.transcargo.gestion_flota.dto.Responses.AlertaResponseDTO;
import cl.transcargo.gestion_flota.entity.Alertas;
import cl.transcargo.gestion_flota.entity.Vehiculo;
import cl.transcargo.gestion_flota.mapper.AlertaMapper;
import cl.transcargo.gestion_flota.repository.RAlertas;
import cl.transcargo.gestion_flota.repository.RVehiculo;
import cl.transcargo.gestion_flota.service.IService.IAlerta;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertaServiceImpl implements IAlerta {

    private final RAlertas repository;
    private final RVehiculo vehiculoRepository;
    private final AlertaMapper mapper;

    public AlertaServiceImpl(RAlertas repository,
                             RVehiculo vehiculoRepository,
                             AlertaMapper mapper) {
        this.repository = repository;
        this.vehiculoRepository = vehiculoRepository;
        this.mapper = mapper;
    }

    @Override
    public List<AlertaResponseDTO> listar() {

        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public AlertaResponseDTO obtener(Long id) {

        Alertas alerta = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alerta no encontrada"));

        return mapper.toResponse(alerta);
    }

    @Override
    public AlertaResponseDTO crear(AlertaRequestDTO request) {

        Vehiculo vehiculo = vehiculoRepository.findById(request.getVehiculoId())
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));

        Alertas alerta = mapper.toEntity(request, vehiculo);

        alerta = repository.save(alerta);

        return mapper.toResponse(alerta);
    }

    @Override
    public AlertaResponseDTO actualizar(Long id,
                                        AlertaRequestDTO request) {

        Alertas alerta = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alerta no encontrada"));

        Vehiculo vehiculo = vehiculoRepository.findById(request.getVehiculoId())
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));

        mapper.updateEntity(alerta, request, vehiculo);

        alerta = repository.save(alerta);

        return mapper.toResponse(alerta);
    }

    @Override
    public void eliminar(Long id) {

        Alertas alerta = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alerta no encontrada"));

        repository.delete(alerta);
    }

}