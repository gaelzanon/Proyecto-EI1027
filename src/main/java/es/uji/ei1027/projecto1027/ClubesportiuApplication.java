package es.uji.ei1027.projecto1027;

import java.util.logging.Logger;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;


@SpringBootApplication
public class ClubesportiuApplication {

	private static final Logger log = Logger.getLogger(ClubesportiuApplication.class.getName());

	public static void main(String[] args) {
		// Auto-configura l'aplicació
		new SpringApplicationBuilder(ClubesportiuApplication.class).run(args);
	}
}
