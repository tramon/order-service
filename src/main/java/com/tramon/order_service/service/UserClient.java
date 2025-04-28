package com.tramon.order_service.service;

import com.tramon.order_service.dto.UserDTO;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
// -- HTTP-клієнт, який використовується для звернення до інших RESTсервісів.
import org.springframework.web.client.RestTemplate;

import java.util.Map;


// Позначає Клас UserClient як Spring-сервіс.
// Це означає, що Spring автоматично створить об'єкт цього класу і помістить
//його в контекст застосунку.
// Цей клас реалізує клієнта для запиту інформації про користувача з іншого
//мікросервісу.
@Service
public class UserClient {

    private final RestTemplate restTemplate;
    private static final String USER_SERVICE_URL = "http://localhost:8081";

    public UserClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // 1. Метод для автоматичного отримання токену
    public String getToken() {
        String authUrl = USER_SERVICE_URL + "/auth/login";

        // Створюємо тіло запиту
        Map<String, String> loginRequest = Map.of(
                "username", "Andrii",  // або передавати параметром
                "role", "ROLE_USER"     // або передавати параметром
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(loginRequest, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(authUrl, request, Map.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return (String) response.getBody().get("token");
        }
        throw new RuntimeException("Не вдалося отримати токен із user-service");
    }

    // 2. Метод для отримання юзера по id із автоматичним токеном
    public UserDTO getUser(Long userId) {
        String token = getToken(); // <<< Спочатку отримуємо токен

        String url = USER_SERVICE_URL + "/users/" + userId;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<UserDTO> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                UserDTO.class
        );

        return response.getBody();
    }
}