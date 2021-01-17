package com.crud.simplecrud;

import com.crud.simplecrud.model.ContactModel;
import com.crud.simplecrud.repository.ContactRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.LongStream;

@SpringBootApplication
public class SimpleCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleCrudApplication.class, args);
	}

	@Bean
	CommandLineRunner init(ContactRepository repository){
		return args -> {
			repository.deleteAll();
			LongStream.range(1, 11)
					.mapToObj(i -> {
						ContactModel c = new ContactModel();
						c.setName("Contact " + i);
						c.setEmail("contact " + i + "@email.com");
						c.setPhone("(11) 11111-1111");
						return c;
					})
					.map(v -> repository.save(v))
					.forEach(System.out::println);
		};
	}
}
