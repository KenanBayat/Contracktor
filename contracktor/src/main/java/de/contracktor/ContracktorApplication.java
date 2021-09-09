package de.contracktor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication//(exclude = {SecurityAutoConfiguration.class})
public class ContracktorApplication implements CommandLineRunner {

	@Autowired
	private InitDatabaseService initDatabaseService;
	
	public static void main(String[] args) {
		SpringApplication.run(ContracktorApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		initDatabaseService.init();
	}
}
