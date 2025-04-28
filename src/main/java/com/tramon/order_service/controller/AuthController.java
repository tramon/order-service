package com.tramon.order_service.controller;

// Імпортування класів:
// -- JwtUtil — для генерації JWT токенів.

// -- Анотації Spring MVC (@RestController, @RequestMapping, @PostMapping,
// @RequestBody) для обробки HTTP-запитів.


import com.tramon.order_service.util.JwtUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

// Позначає клас як REST-контролер, який обробляє HTTP -запити і
// повертає JSON-відповіді.
@RestController
//Всі маршрути в цьому контролері починатимуться з /auth.
@RequestMapping("/auth")
// Оголошення публічного класу AuthController,
// який відповідатиме за логін користувачів.
public class AuthController {
    // Поле jwtUtil для взаємодії з утилітою генерації токенів.
    private final JwtUtil jwtUtil;

    // Конструктор, через який Spring інжектить (передає) об'єкт JwtUtil.
    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    // Вказує, що метод login обробляє POST-запити на адресу /auth/login.
    @PostMapping("/login")
    // Метод login приймає дані користувача у вигляді JSON і
    // автоматично конвертує їх у Map<String, String>.
    // Очікує, що JSON міститиме щонайменше поля username і role.
    public Map<String, String> login(@RequestBody Map<String, String> user) {
        // У цьому прикладі будь-який користувач може увійти без перевірки пароля.
        // У реальному застосунку тут потрібно додати перевірку правильності логіну/пароля.
        // Зчитуються значення імені користувача і ролі з вхідної карти (Map).
        String username = user.get("username");
        String role = user.get("role");
        // Генерується новий JWT токен для вказаного користувача і ролі.
        String token = jwtUtil.generateToken(username, role);
        // Повертається мапа (ключ-значення), де ключ "token",
        // а значення — згенерований токен.
        return Map.of("token", token);
    }

}
