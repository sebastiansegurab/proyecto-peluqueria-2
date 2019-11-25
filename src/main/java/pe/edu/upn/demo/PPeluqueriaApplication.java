package pe.edu.upn.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan()
public class PPeluqueriaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PPeluqueriaApplication.class, args);
	}

}
