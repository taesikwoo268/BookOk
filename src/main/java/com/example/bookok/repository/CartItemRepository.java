package com.example.bookok.repository;

import com.example.bookok.model.BookEntity;
import com.example.bookok.model.CartItem;
import com.example.bookok.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    List<CartItem> findByUser(UserEntity user);

    // Tìm một cuốn sách cụ thể trong giỏ của user đó
    CartItem findByUserAndBook(UserEntity user, BookEntity book);

    // Xóa giỏ hàng (khi đặt hàng xong)
    void deleteByUser(UserEntity user);
}
