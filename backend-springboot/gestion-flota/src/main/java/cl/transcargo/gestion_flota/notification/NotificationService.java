package cl.transcargo.gestion_flota.notification;

import cl.transcargo.gestion_flota.entity.Mantencion;
import cl.transcargo.gestion_flota.entity.Usuario;
import cl.transcargo.gestion_flota.entity.Vehiculo;
import cl.transcargo.gestion_flota.repository.RUsuario;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio encargado de construir los mensajes de notificación.
 *
 * Todos los servicios del sistema deben utilizar esta clase
 * para enviar correos electrónicos.
 */
@Service
public class NotificationService {

    private final EmailService emailService;
    private final RUsuario usuarioRepository;

    public NotificationService(EmailService emailService, RUsuario usuarioRepository) {

        this.emailService = emailService;
        this.usuarioRepository = usuarioRepository;

    }

    /**
     * Notifica el registro de un nuevo conductor.
     */
    public void nuevoConductor(String destinatario,
                               String nombreConductor) {

        String asunto = "Nuevo conductor registrado";

        String mensaje = """

                Se ha registrado un nuevo conductor.

                Nombre:
                %s

                Sistema de Gestión de Flota.
                """
                .formatted(nombreConductor);

        emailService.enviarCorreo(

                destinatario,
                asunto,
                mensaje

        );

    }

    /**
     * Notifica la asignación de un conductor a un vehículo.
     */
    public void asignacionConductor(String destinatario,
                                    String conductor,
                                    String patente) {

        String asunto = "Asignación de conductor";

        String mensaje = """

                Se ha asignado un conductor.

                Conductor:
                %s

                Vehículo:
                %s

                Sistema de Gestión de Flota.
                """
                .formatted(conductor, patente);

        emailService.enviarCorreo(

                destinatario,
                asunto,
                mensaje

        );

    }

    /**
     * Notifica el registro de una nueva falla.
     */
    public void nuevaFalla(String destinatario,
                           String patente,
                           String descripcion,
                           String prioridad) {

        String asunto = "Nueva falla registrada";

        String mensaje = """

                Se ha registrado una nueva falla.

                Vehículo:
                %s

                Prioridad:
                %s

                Descripción:
                %s

                Sistema de Gestión de Flota.
                """
                .formatted(

                        patente,
                        prioridad,
                        descripcion

                );

        emailService.enviarCorreo(

                destinatario,
                asunto,
                mensaje

        );

    }

    /**
     * Notifica que un vehículo está próximo a mantención.
     */
    public void proximaMantencion(String destinatario,
                                  String patente,
                                  Integer kilometrajeActual,
                                  Integer proximaMantencion,
                                  Integer kilometrosRestantes) {

        String asunto = "Vehículo próximo a mantención";

        String mensaje = """

                El vehículo %s requiere una mantención próximamente.

                Kilometraje actual:
                %d km

                Próxima mantención:
                %d km

                Restan:
                %d km

                Sistema de Gestión de Flota.
                """
                .formatted(

                        patente,
                        kilometrajeActual,
                        proximaMantencion,
                        kilometrosRestantes

                );

        emailService.enviarCorreo(

                destinatario,
                asunto,
                mensaje

        );

    }
    public void verificarProximaMantencion(Vehiculo vehiculo,
                                           Mantencion mantencion) {

        Integer kilometrosRestantes =
                mantencion.getKilometraje()
                        - vehiculo.getKilometrajeActual();

        if (kilometrosRestantes > 2000 || kilometrosRestantes < 0) {
            return;
        }

        List<Usuario> destinatarios =
                usuarioRepository.findByRolIn(
                        List.of("ADMIN", "OPERADOR")
                );

        for (Usuario usuario : destinatarios) {

            try {

                proximaMantencion(
                        usuario.getUsername(),
                        vehiculo.getPatente(),
                        vehiculo.getKilometrajeActual(),
                        mantencion.getKilometraje(),
                        kilometrosRestantes
                );

            } catch (Exception e) {

                System.err.println(e.getMessage());

            }

        }

    }

}