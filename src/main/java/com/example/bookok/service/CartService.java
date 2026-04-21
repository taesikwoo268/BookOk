package com.example.bookok.service;

import com.example.bookok.dto.CartItemDTO;
import com.example.bookok.model.*;
import com.example.bookok.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    @Autowired private CartItemRepository cartItemRepository;
    @Autowired private BookRepository bookRepository;

    @Transactional
    public void addToCart(UserEntity user, Long bookId) {
        BookEntity book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        CartItem item = cartItemRepository.findByUserAndBook(user, book);
        if (item!=null) {
            item.setQuantity(item.getQuantity() + 1);
            cartItemRepository.save(item);
        }
        else {
            CartItem newItem = new CartItem();
            newItem.setUser(user);
            newItem.setBook(book);
            newItem.setQuantity(1);
            cartItemRepository.save(newItem);
        }
    }

    public List<CartItemDTO> toCartItemsDTO(UserEntity user) {
        List<CartItem> items = cartItemRepository.findByUser(user);
        List<CartItemDTO> dtos = new ArrayList<>();

        for (CartItem item : items) {
            CartItemDTO dto = new CartItemDTO();
            // Map từ Entity sang DTO
            dto.setCartItemId(item.getId());
            dto.setQuantity(item.getQuantity());

            // Lấy thông tin từ Book
            if (item.getBook() != null) {
                dto.setBookId(item.getBook().getId());
                dto.setBookTitle(item.getBook().getTitle());
                dto.setPrice(item.getBook().getPrice());
                // Tính toán giá trị dòng
                dto.setTotalLinePrice(item.getBook().getPrice() * item.getQuantity());
            }
            dtos.add(dto);
        }
        return dtos;
    }

    public List<CartItem> getCartItems(UserEntity user) {
        return cartItemRepository.findByUser(user);
    }

    @Transactional
    public void removeItem(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }
}