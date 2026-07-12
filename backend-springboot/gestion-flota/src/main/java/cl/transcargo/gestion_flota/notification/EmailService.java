package cl.transcargo.gestion_flota.notification;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Servicio encargado únicamente del envío de correos electrónicos.
 *
 * No conoce la lógica del negocio (fallas, conductores, mantenciones, etc.).
 * Solamente recibe un destinatario, asunto y mensaje.
 */
@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String remitente;

    public EmailService(JavaMailSender mailSender) {

        this.mailSender = mailSender;

    }

    /**
     * Envía un correo electrónico.
     *
     * @param destinatario Correo del receptor.
     * @param asunto Asunto del mensaje.
     * @param mensaje Contenido del correo.
     */
    public void enviarCorreo(String destinatario,
                             String asunto,
                             String mensaje) {

        SimpleMailMessage email = new SimpleMailMessage();

        email.setFrom(remitente);

        email.setTo(destinatario);

        email.setSubject(asunto);

        email.setText(mensaje);

        mailSender.send(email);

    }

}