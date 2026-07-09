package cl.transcargo.gestion_flota.service.ServiceImpl;

import cl.transcargo.gestion_flota.dto.Responses.*;
import cl.transcargo.gestion_flota.entity.Vehiculo;
import cl.transcargo.gestion_flota.mapper.EmisionesGasesMapper;
import cl.transcargo.gestion_flota.mapper.PermisoCirculacionMapper;
import cl.transcargo.gestion_flota.mapper.RevisionTecnicaMapper;
import cl.transcargo.gestion_flota.mapper.SoapMapper;
import cl.transcargo.gestion_flota.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentacionService {

    private final RVehiculo vehiculoRepository;

    private final RRevisionTecnica revisionRepository;

    private final REmisionesGases emisionesRepository;

    private final RSoap soapRepository;

    private final RPermisoCirculacion permisoRepository;

    private final RevisionTecnicaMapper revisionMapper;

    private final EmisionesGasesMapper emisionesMapper;

    private final SoapMapper soapMapper;

    private final PermisoCirculacionMapper permisoMapper;

    public List<VehiculoDocumentacionDTO> listar() {

        List<Vehiculo> vehiculos = vehiculoRepository.findAll();

        return vehiculos.stream().map(v -> {

            RevisionTecnicaResponseDTO revision = revisionRepository
                    .findByVehiculoId(v.getId())
                    .map(revisionMapper::toResponse)
                    .orElse(null);

            EmisionGasesResponseDTO emisiones = emisionesRepository
                    .findByVehiculoId(v.getId())
                    .map(emisionesMapper::toResponse)
                    .orElse(null);

            SoapResponseDTO soap = soapRepository
                    .findByVehiculoId(v.getId())
                    .map(soapMapper::toResponse)
                    .orElse(null);

            PermisoCirculacionResponseDTO permiso = permisoRepository
                    .findByVehiculoId(v.getId())
                    .map(permisoMapper::toResponse)
                    .orElse(null);

            return VehiculoDocumentacionDTO.builder()

                    .id(v.getId())
                    .marca(v.getMarca())
                    .modelo(v.getModelo())
                    .patente(v.getPatente())
                    .nombre(v.getNombre())

                    .revisionTecnica(revision)
                    .emisionGases(emisiones)
                    .soap(soap)
                    .permisoCirculacion(permiso)

                    .build();

        }).toList();

    }

}