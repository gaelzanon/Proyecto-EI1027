package es.uji.ei1027.proyecto1027;

import java.util.logging.Logger;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;


@SpringBootApplication
public class ProyectoEI1027Application {

	private static final Logger log = Logger.getLogger(ProyectoEI1027Application.class.getName());

	public static void main(String[] args) {
		// Auto-configura l'aplicació
		new SpringApplicationBuilder(ProyectoEI1027Application.class).run(args);
	}
}
