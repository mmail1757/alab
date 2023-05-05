package com.example.alab;

import com.example.alab.entity.Person;
import com.example.alab.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;

@SpringBootApplication
public class JpawsApplication {

	public static void main(String[] args) {

		ApplicationContext app = SpringApplication.run(JpawsApplication.class, args);
		PersonRepository repository = app.getBean(PersonRepository.class);
		List<Person> personList = repository.findByDocuments_DocNumContains("777");
		personList.forEach(item -> {
			System.out.println("Person: " + item.getFirstName() + " " + item.getLastName());
			System.out.println("     Documents:");
			item.getDocuments().forEach(doc -> {
				System.out.println("             Type: " + doc.getDocType() + " Number: " + doc.getDocNum());
			});
		});
	}

}
