package com.tramon.order_service.config;

// Імпортуються класи для роботи з JWT (JSON Web Token):
// -- Claims — об'єкт, який містить дані (права) із токена.

import com.tramon.order_service.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


// -- Date використовується для встановлення часу створення і закінчення токена.


// Позначає клас як конфігураційний, тобто Spring оброблятиме його і створить
//відповідні Bean-компоненти .
@Configuration
// Оголошення публічного класу SecurityConfig, який визначає правила безпеки
//для веб-запитів .
public class SecurityConfig {

    // Оголошується приватне фінальне поле для JWT-фільтра,
    // який буде вставлятися у ланцюжок фільтрів.
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // Конструктор із залежністю — Spring автоматично передасть екземпляр
    // JwtAuthenticationFilter сюди через ін’єкцію.
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }


    // Метод filterChain створює і повертає SecurityFilterChain — об'єкт,
    // який описує всю конфігурацію безпеки для HTTP-запитів.
    // Анотація @Bean реєструє його в контексті Spring для автоматичного використання
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws
            Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                // Вимикає захист від CSRF (Cross-Site Request Forgery).
                // Зазвичай CSRF вимикають у мікросервісах і API, які
                // працюють без браузерів

                // Налаштовує правила авторизації:
                // -- дозволяє всі запити, що починаються з /auth/**, без
                // автентифікації(permitAll()).
                // -- всі інші запити (anyRequest()) повинні бути
                // автентифіковані(authenticated()).
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/orders/**").permitAll()
                        .anyRequest().authenticated()
                ).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);


        // Завершує налаштування безпеки і будує об'єкт SecurityFilterChain,
        // який Spring використовуватиме для фільтрації HTTP-запитів.
        return http.build();
    }

}