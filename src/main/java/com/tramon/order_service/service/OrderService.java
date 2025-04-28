package com.tramon.order_service.service;

import com.tramon.order_service.dto.UserDTO;
import com.tramon.order_service.entity.OrderEntity;
import com.tramon.order_service.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    // Оголошення залежностей:
    // -- для збереження замовлень у БД.
    private final OrderRepository orderRepository;
    // -- для отримання даних користувача з іншого мікросервісу
    private final UserClient userClient;

    // Конструктор з ін’єкцією залежностей.
    // Spring автоматично передає реалізації під час створення цього сервісу.
    public OrderService(OrderRepository orderRepository, UserClient
            userClient) {
        this.orderRepository = orderRepository;
        this.userClient = userClient;
    }

    // Метод createOrder приймає об'єкт замовлення (OrderEntity)
    // і створює новий запис у базі даних.
    public OrderEntity createOrder(OrderEntity order) {
        // Через UserClient виконується HTTP-запит до мікросервісу
        //користувачів
        // для перевірки, чи існує користувач з переданим userId.
        UserDTO user = userClient.getUser(order.getUserId());
        // Якщо користувач не знайдений — генерується виняток, і замовлення
        //не буде створено.
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        // Якщо користувач знайдений — замовлення зберігається в базі через
        //orderRepository.
        return orderRepository.save(order);
    }

    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }
}

