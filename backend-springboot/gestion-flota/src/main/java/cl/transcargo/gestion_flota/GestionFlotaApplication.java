package cl.transcargo.gestion_flota;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class GestionFlotaApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionFlotaApplication.class, args);

		//BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		//System.out.println("******* Contrasena Manuel: " + encoder.encode("contrasena"));
		//System.out.println("******* Contrasena Carlos A.: " + encoder.encode("contrasena"));

	}

}
