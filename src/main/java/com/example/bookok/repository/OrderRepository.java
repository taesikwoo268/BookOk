package com.example.bookok.repository;

import com.example.bookok.model.Order;
import com.example.bookok.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserOrderByOrderDateDesc(UserEntity user);
}