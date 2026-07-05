package cl.transcargo.gestion_flota.service.ServiceImpl;


import cl.transcargo.gestion_flota.dto.Requests.EmisionGasesRequestDTO;
import cl.transcargo.gestion_flota.dto.Responses.EmisionGasesResponseDTO;
import cl.transcargo.gestion_flota.entity.EmisionesGases;
import cl.transcargo.gestion_flota.entity.Vehiculo;
import cl.transcargo.gestion_flota.mapper.EmisionesGasesMapper;
import cl.transcargo.gestion_flota.repository.REmisionesGases;
import cl.transcargo.gestion_flota.repository.RVehiculo;
import cl.transcargo.gestion_flota.service.IService.IEmisionGases;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EmisionesGasesServiceImpl implements IEmisionGases {

    private final REmisionesGases repository;
    private final RVehiculo vehiculoRepository;
    private final EmisionesGasesMapper mapper;

    public EmisionesGasesServiceImpl(REmisionesGases repository,
                                   RVehiculo vehiculoRepository,
                                   EmisionesGasesMapper mapper) {
        this.repository = repository;
        this.vehiculoRepository = vehiculoRepository;
        this.mapper = mapper;
    }

    @Override
    public List<EmisionGasesResponseDTO> listar() {

        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public EmisionGasesResponseDTO obtener(Long id) {

        EmisionesGases emision = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro de emisión no encontrado"));

        return mapper.toResponse(emision);
    }

    @Override
    public EmisionGasesResponseDTO crear(EmisionGasesRequestDTO request) {

        if (repository.existsByVehiculoId(request.getVehiculoId())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "El vehículo ya tiene la emision registrada"
            );
        }

        Vehiculo vehiculo = vehiculoRepository
                .findById(request.getVehiculoId())
                .orElseThrow(() ->
                        new RuntimeException("Vehículo no encontrado"));

        EmisionesGases emision = mapper.toEntity(request, vehiculo);
        EmisionesGases guardado = repository.save(emision);

        return mapper.toResponse(guardado);

    }

    @Override
    public EmisionGasesResponseDTO actualizar(Long id,
                                              EmisionGasesRequestDTO request) {

        EmisionesGases emision = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro de emisión no encontrado"));

        Vehiculo vehiculo = vehiculoRepository.findById(request.getVehiculoId())
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));

        mapper.updateEntity(emision, request, vehiculo);

        emision = repository.save(emision);

        return mapper.toResponse(emision);
    }

    @Override
    public void eliminar(Long id) {

        EmisionesGases emision = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro de emisión no encontrado"));

        repository.delete(emision);
    }

}