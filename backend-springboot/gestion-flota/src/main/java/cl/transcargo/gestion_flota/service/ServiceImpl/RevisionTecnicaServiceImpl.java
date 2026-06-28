package cl.transcargo.gestion_flota.service.ServiceImpl;

import cl.transcargo.gestion_flota.dto.Requests.RevisionTecnicaRequestDTO;
import cl.transcargo.gestion_flota.dto.Responses.RevisionTecnicaResponseDTO;
import cl.transcargo.gestion_flota.entity.RevisionTecnica;
import cl.transcargo.gestion_flota.entity.Vehiculo;
import cl.transcargo.gestion_flota.mapper.RevisionTecnicaMapper;
import cl.transcargo.gestion_flota.repository.RRevisionTecnica;
import cl.transcargo.gestion_flota.repository.RVehiculo;
import cl.transcargo.gestion_flota.service.IService.IRevisionTecnica;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RevisionTecnicaServiceImpl implements IRevisionTecnica {

    private final RRevisionTecnica repository;
    private final RVehiculo vehiculoRepository;
    private final RevisionTecnicaMapper mapper;

    public RevisionTecnicaServiceImpl(RRevisionTecnica repository, RVehiculo vehiculoRepository, RevisionTecnicaMapper mapper ){
        this.mapper = mapper;
        this.repository = repository;
        this.vehiculoRepository = vehiculoRepository;
    }

    @Override
    public List<RevisionTecnicaResponseDTO> listar() {

        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public RevisionTecnicaResponseDTO obtener(Long id) {

        RevisionTecnica revision = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Revisión técnica no encontrada"));

        return mapper.toResponse(revision);
    }

    @Override
    public RevisionTecnicaResponseDTO crear(RevisionTecnicaRequestDTO request) {

        Vehiculo vehiculo = vehiculoRepository.findById(request.getVehiculoId())
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));

        RevisionTecnica revision = mapper.toEntity(request, vehiculo);

        revision = repository.save(revision);

        return mapper.toResponse(revision);
    }

    @Override
    public RevisionTecnicaResponseDTO actualizar(Long id,
                                                 RevisionTecnicaRequestDTO request) {

        RevisionTecnica revision = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Revisión técnica no encontrada"));

        Vehiculo vehiculo = vehiculoRepository.findById(request.getVehiculoId())
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));

        mapper.updateEntity(revision, request, vehiculo);

        revision = repository.save(revision);

        return mapper.toResponse(revision);
    }

    @Override
    public void eliminar(Long id) {

        RevisionTecnica revision = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Revisión técnica no encontrada"));

        repository.delete(revision);
    }

}