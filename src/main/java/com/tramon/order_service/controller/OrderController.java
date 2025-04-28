package com.tramon.order_service.controller;

import com.tramon.order_service.entity.OrderEntity;
import com.tramon.order_service.service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
// Встановлює базовий шлях для всіх запитів до цього контролера: /orders.
@RequestMapping("/orders")
// Оголошення публічного класу OrderController, який обробляє запити,
// пов'язані з замовленнями.
public class OrderController {
    // Залежність для сервісу замовлень, який містить основну бізнес-логіку.
    private final OrderService orderService;

    // Конструктор з ін’єкцією залежності OrderService, яку Spring
    // автоматично передає.
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Позначає метод як такий, що обробляє HTTP POST-запити на шлях /orders.
    @PostMapping
    // Метод createOrder приймає JSON з тіла запиту, який автоматично
    // перетворюється у OrderEntity за допомогою анотації @RequestBody.
    public OrderEntity createOrder(@RequestBody OrderEntity order) {
        // Передає об’єкт замовлення до OrderService для обробки.
        // Повертає результат — створене замовлення.
        return orderService.createOrder(order);
    }
}
