package cl.transcargo.gestion_flota.service.ServiceImpl;

import cl.transcargo.gestion_flota.dto.Requests.VehiculoRequestDTO;
import cl.transcargo.gestion_flota.dto.Responses.VehiculoResponseDTO;
import cl.transcargo.gestion_flota.entity.Conductor;
import cl.transcargo.gestion_flota.entity.Vehiculo;
import cl.transcargo.gestion_flota.mapper.VehiculoMapper;
import cl.transcargo.gestion_flota.repository.RConductor;
import cl.transcargo.gestion_flota.repository.RVehiculo;
import cl.transcargo.gestion_flota.service.IService.IVehiculo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehiculoServiceImpl implements IVehiculo {

    // Inyeccion de dependencia
    private final VehiculoMapper mapper;
    private final RVehiculo repository;
    private final RConductor conductorRepository;

    public VehiculoServiceImpl(RVehiculo repository,
                               VehiculoMapper mapper,
                               RConductor conductorRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.conductorRepository = conductorRepository;
    }

    // Listar
    @Override
    public List<VehiculoResponseDTO> listar() {

        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    // Obtener por id
    @Override
    public VehiculoResponseDTO obtener(Long id) {

        VehiculoResponseDTO response = new VehiculoResponseDTO();

        Vehiculo vehiculo = repository.findById(id).orElseThrow();

        response.setId(vehiculo.getId());
        response.setPatente(vehiculo.getPatente());
        response.setModelo(vehiculo.getModelo());
        response.setNombre(vehiculo.getNombre());
        response.setMarca(vehiculo.getMarca());

        response.setAnio(vehiculo.getAnio());

        response.setKilometrajeActual(vehiculo.getKilometrajeActual());
        response.setEstado(vehiculo.getEstado());
        //response.setConductor(vehiculo.getConductor().getNombre());

        return response;
    }

    // Crear
    @Override
    public VehiculoResponseDTO crear(VehiculoRequestDTO request) {

        Vehiculo vehiculo = mapper.toEntity(request);
        vehiculo = repository.save(vehiculo);

        return mapper.toResponse(vehiculo);

    }


    //Actualizar
    @Override
    public VehiculoResponseDTO actualizar(Long id, VehiculoRequestDTO request) {

        Vehiculo vehiculo = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));

        vehiculo.setPatente(request.getPatente());
        vehiculo.setMarca(request.getMarca());
        vehiculo.setNombre(request.getNombre());
        vehiculo.setModelo(request.getModelo());
        vehiculo.setAnio(request.getAnio());
        vehiculo.setEstado(request.getEstado());
        vehiculo.setKilometrajeActual(request.getKilometrajeActual());

        vehiculo = repository.save(vehiculo);

        return mapper.toResponse(vehiculo);
    }

    @Override
    public void eliminar(Long id) {

        Vehiculo vehiculo = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));

        repository.delete(vehiculo);
    }

    @Override
    public VehiculoResponseDTO asignarConductor(Long vehiculoId, Long conductorId){

        Vehiculo vehiculo = repository.findById(vehiculoId)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));

        Conductor conductor = conductorRepository.findById(conductorId)
                .orElseThrow(() -> new RuntimeException("Conductor no encontrado"));

        vehiculo.setConductor(conductor);

        repository.save(vehiculo);

        return mapper.toResponse(vehiculo);
    }

}