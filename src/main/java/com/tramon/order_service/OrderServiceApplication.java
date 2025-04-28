package com.tramon.order_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class OrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

	// Анотація Spring, яка позначає, що метод повертає компонент (bean),
	// який буде зареєстрований у Spring-контейнері.
	// Це дозволяє використовувати цей об’єкт в інших частинах програми
	// через ін'єкцію залежностей (@Autowired, @Inject, або через конструктор).
	@Bean
	// Оголошення методу, який створює і повертає екземпляр класу RestTemplate.
	// RestTemplate — це клієнт HTTP, який дозволяє виконувати запити
	// до інших REST API з вашого коду.
	public RestTemplate restTemplate() {
		// Створення нового екземпляру RestTemplate.
		return new RestTemplate();
	}

}
