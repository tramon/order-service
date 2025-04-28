package com.tramon.order_service.dto;

import lombok.Data;

@Data
// Оголошення публічного класу UserDTO, який використовується
// для представлення даних користувача (DTO-модель).
public class UserDTO {
    // Ідентифікатор користувача
    private Long id;
    // Ім’я користувача.
    private String name;
    // Електронна пошта користувача.
    private String email;
}