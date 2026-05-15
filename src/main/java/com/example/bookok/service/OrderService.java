package com.example.bookok.service;

import com.example.bookok.model.*;
import com.example.bookok.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired private OrderRepository orderRepository;
    @Autowired private CartItemRepository cartItemRepository;

    @Transactional
    public Order createOrder(UserEntity user) {
        // 1. Lấy tất cả item trong giỏ của user
        List<CartItem> cartItems = cartItemRepository.findByUser(user);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Giỏ hàng đang trống, không thể đặt hàng!");
        }

        // 2. Tạo đối tượng Order mới
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");

        // 3. Tạo danh sách OrderItem từ CartItem
        List<OrderItem> orderItems = new ArrayList<>();
        long totalAmount = 0;

        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setBook(cartItem.getBook());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPriceAtPurchase(cartItem.getBook().getPrice());

            totalAmount += (long) cartItem.getBook().getPrice() * cartItem.getQuantity();
            orderItems.add(orderItem);
        }

        order.setTotalAmount(totalAmount);
        order.setOrderItems(orderItems);

        // 4. Lưu vào Database (nhờ CascadeType.ALL nên OrderItem sẽ được lưu theo)
        Order savedOrder = orderRepository.save(order);

        // 5. Xóa sạch giỏ hàng sau khi đặt thành công
        cartItemRepository.deleteAll(cartItems);

        return savedOrder;
    }
}