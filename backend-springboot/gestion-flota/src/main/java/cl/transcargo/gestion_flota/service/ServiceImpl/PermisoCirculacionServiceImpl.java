package cl.transcargo.gestion_flota.service.ServiceImpl;

import cl.transcargo.gestion_flota.dto.Requests.PermisoCirculacionRequestDTO;
import cl.transcargo.gestion_flota.dto.Responses.PermisoCirculacionResponseDTO;
import cl.transcargo.gestion_flota.entity.PermisoCirculacion;
import cl.transcargo.gestion_flota.entity.Vehiculo;
import cl.transcargo.gestion_flota.mapper.PermisoCirculacionMapper;
import cl.transcargo.gestion_flota.repository.RPermisoCirculacion;
import cl.transcargo.gestion_flota.repository.RVehiculo;
import cl.transcargo.gestion_flota.service.IService.IPermisoCirculacion;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermisoCirculacionServiceImpl implements IPermisoCirculacion {

    private final RPermisoCirculacion repository;
    private final RVehiculo vehiculoRepository;
    private final PermisoCirculacionMapper mapper;

    public PermisoCirculacionServiceImpl(RPermisoCirculacion repository,
                                         RVehiculo vehiculoRepository,
                                         PermisoCirculacionMapper mapper) {
        this.repository = repository;
        this.vehiculoRepository = vehiculoRepository;
        this.mapper = mapper;
    }

    @Override
    public List<PermisoCirculacionResponseDTO> listar() {

        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public PermisoCirculacionResponseDTO obtener(Long id) {

        PermisoCirculacion permiso = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permiso de circulación no encontrado"));

        return mapper.toResponse(permiso);
    }

    @Override
    public PermisoCirculacionResponseDTO crear(PermisoCirculacionRequestDTO request) {

        Vehiculo vehiculo = vehiculoRepository.findById(request.getVehiculoId())
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));

        PermisoCirculacion permiso = mapper.toEntity(request, vehiculo);

        permiso = repository.save(permiso);

        return mapper.toResponse(permiso);
    }

    @Override
    public PermisoCirculacionResponseDTO actualizar(Long id,
                                                    PermisoCirculacionRequestDTO request) {

        PermisoCirculacion permiso = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permiso de circulación no encontrado"));

        Vehiculo vehiculo = vehiculoRepository.findById(request.getVehiculoId())
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));

        mapper.updateEntity(permiso, request, vehiculo);

        permiso = repository.save(permiso);

        return mapper.toResponse(permiso);
    }

    @Override
    public void eliminar(Long id) {

        PermisoCirculacion permiso = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permiso de circulación no encontrado"));

        repository.delete(permiso);
    }

}