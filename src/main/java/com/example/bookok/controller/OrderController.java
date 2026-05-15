package com.example.bookok.controller;

import com.example.bookok.model.Order;
import com.example.bookok.model.UserEntity;
import com.example.bookok.repository.UserRepository;
import com.example.bookok.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired private OrderService orderService;
    @Autowired private UserRepository userRepository;

    @PostMapping("/checkout")
    public String checkout(Principal principal) {
        UserEntity user = userRepository.findByUsername(principal.getName()).orElseThrow();
        try {
            Order order = orderService.createOrder(user);
            return "Đặt hàng thành công! Mã đơn hàng: " + order.getId();
        } catch (Exception e) {
            return "Lỗi: " + e.getMessage();
        }
    }
}