package de.sopro;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SoproSpringApplication implements CommandLineRunner {
	
	public static void main(String[] args) {
		SpringApplication.run(SoproSpringApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
	}

}
