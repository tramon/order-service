package com.tramon.order_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
// Визначає назву таблиці в базі даних як "orders".
@Table(name = "orders")
// Lombok-анотації, які автоматично створюють:
// -- гетери, сетери, toString(), equals(), hashCode().
@Data
// -- конструктор без параметрів.
@NoArgsConstructor
// -- конструктор з усіма полями.
@AllArgsConstructor
// -- доз
public class OrderEntity {

    // @Id — позначає поле як первинний ключ.
    // @GeneratedValue(...) — значення id генерується автоматично базою
    //(стратегія IDENTITY означає автоінкремент).
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // Ідентифікатор користувача, який зробив замовлення.
    private Long userId;
    // Назва продукту, який замовляється.
    private String product;
    // Кількість одиниць продукту в цьому замовgradl;eленні.
    private int quantity;


}
