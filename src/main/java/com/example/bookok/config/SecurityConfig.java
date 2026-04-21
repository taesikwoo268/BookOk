package com.example.bookok.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/api/**")
                )

                // 2. Cấu hình phân quyền
                .authorizeHttpRequests(auth -> auth
                        // API giỏ hàng bắt buộc phải đăng nhập (để lấy được Principal)
                        .requestMatchers("/api/cart/**").authenticated()

                        // Các trang web (giỏ hàng, mua hàng...) bắt buộc đăng nhập
                        .requestMatchers("/web/cart/**").authenticated()

                        // Mở cửa cho trang chủ, đăng nhập, CSS, JS
                        .requestMatchers("/web/books/**", "/login", "/css/**", "/js/**").permitAll()
                        .anyRequest().permitAll()
                )

                // 3. Cấu hình đăng nhập Web (Form)
                .formLogin(form -> form
                        .permitAll()
                )

                // 4. Cấu hình đăng nhập API (Basic Auth)
                // Đây là bước giúp Postman truyền được User vào Principal
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}