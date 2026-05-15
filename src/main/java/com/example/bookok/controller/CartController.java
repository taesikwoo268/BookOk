package com.example.bookok.controller;

import com.example.bookok.dto.CartItemDTO;
import com.example.bookok.model.CartItem;
import com.example.bookok.model.UserEntity;
import com.example.bookok.repository.UserRepository;
import com.example.bookok.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private UserRepository userRepository;

    // Lấy giỏ hàng
    @GetMapping
    public List<CartItemDTO> getCart(Principal principal) {
        UserEntity user = userRepository.findByUsername(principal.getName()).orElseThrow();
        return cartService.toCartItemsDTO(user);
    }

    // Thêm vào giỏ
    @PostMapping("/add")
    public String addToCart(@RequestParam Long bookId,
                            @RequestParam(defaultValue = "1") int quantity,
                            Principal principal) {
        UserEntity user = userRepository.findByUsername(principal.getName()).orElseThrow();
        cartService.addToCart(user, quantity, bookId);
        return "Added to cart successfully!";
    }

    // Xóa khỏi giỏ
    @DeleteMapping("/remove/{itemId}")
    public String removeItem(@PathVariable Long itemId) {
        cartService.removeItem(itemId);
        return "Item removed!";
    }
    @GetMapping("/total")
    public Long getCartTotal(Principal principal) {
        UserEntity user = userRepository.findByUsername(principal.getName()).orElseThrow();
        return cartService.calculateCartTotal(user);
    }
}