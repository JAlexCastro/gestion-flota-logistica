package cl.transcargo.gestion_flota.service.ServiceImpl;

import cl.transcargo.gestion_flota.dto.Requests.VehiculoRequest;
import cl.transcargo.gestion_flota.dto.Responses.VehiculoResponse;
import cl.transcargo.gestion_flota.mapper.VehiculoMapper;
import cl.transcargo.gestion_flota.repository.RVehiculo;
import cl.transcargo.gestion_flota.service.IService.IVehiculo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehiculoServiceImpl implements IVehiculo {

    private final VehiculoMapper mapper;
    private final RVehiculo repository;

    public VehiculoServiceImpl(RVehiculo repository,
                               VehiculoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<VehiculoResponse> listar() {

        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public VehiculoResponse obtener(Long id) {
        return null;
    }

    @Override
    public VehiculoResponse crear(VehiculoRequest request) {
        return null;
    }

    @Override
    public VehiculoResponse actualizar(Long id, VehiculoRequest request) {
        return null;
    }

    @Override
    public void eliminar(Long id) {

    }
}