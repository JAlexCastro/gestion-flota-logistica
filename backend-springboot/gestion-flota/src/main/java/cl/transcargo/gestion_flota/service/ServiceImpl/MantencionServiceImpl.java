package cl.transcargo.gestion_flota.service.ServiceImpl;

import cl.transcargo.gestion_flota.dto.Requests.MantencionRequestDTO;
import cl.transcargo.gestion_flota.dto.Responses.MantencionResponseDTO;
import cl.transcargo.gestion_flota.entity.Mantencion;
import cl.transcargo.gestion_flota.entity.Vehiculo;
import cl.transcargo.gestion_flota.mapper.MantencionMapper;
import cl.transcargo.gestion_flota.repository.RMantencion;
import cl.transcargo.gestion_flota.repository.RVehiculo;
import cl.transcargo.gestion_flota.service.IService.IMantencion;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MantencionServiceImpl implements IMantencion {

    private final RMantencion repository;
    private final RVehiculo vehiculoRepository;
    private final MantencionMapper mapper;

    public MantencionServiceImpl(RMantencion repository, RVehiculo vehiculoRepository, MantencionMapper mapper){
        this.repository = repository;
        this.vehiculoRepository = vehiculoRepository;
        this.mapper = mapper;
    }

    @Override
    public List<MantencionResponseDTO> listar() {

        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public MantencionResponseDTO obtener(Long id) {

        Mantencion mantencion = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mantención no encontrada"));

        return mapper.toResponse(mantencion);
    }

    @Override
    public MantencionResponseDTO crear(MantencionRequestDTO request) {

        Vehiculo vehiculo = vehiculoRepository.findById(request.getVehiculoId())
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));

        Mantencion mantencion = mapper.toEntity(request, vehiculo);

        mantencion = repository.save(mantencion);

        return mapper.toResponse(mantencion);
    }

    @Override
    public MantencionResponseDTO actualizar(Long id,
                                            MantencionRequestDTO request) {

        Mantencion mantencion = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mantención no encontrada"));

        Vehiculo vehiculo = vehiculoRepository.findById(request.getVehiculoId())
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));

        mapper.updateEntity(mantencion, request, vehiculo);

        mantencion = repository.save(mantencion);

        return mapper.toResponse(mantencion);
    }

    @Override
    public void eliminar(Long id) {

        Mantencion mantencion = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mantención no encontrada"));

        repository.delete(mantencion);
    }

}