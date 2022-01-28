package ua.com.foxminded.university;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

import ua.com.foxminded.university.model.IdEntity;

@SpringBootApplication
//@ComponentScan({"ua.com.foxminded.university"})
//@EntityScan({"ua.com.foxminded.university"})
public class UniversityApplication {

	public static void main(String[] args) {
		SpringApplication.run(UniversityApplication.class, args);
		
	}
}
