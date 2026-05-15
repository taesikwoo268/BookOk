package com.example.bookok.controller;

import com.example.bookok.dto.CartItemDTO;
import com.example.bookok.model.UserEntity;
import com.example.bookok.repository.UserRepository;
import com.example.bookok.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/web/cart")
public class CartViewController {

    @Autowired private CartService cartService;
    @Autowired private UserRepository userRepository;

    @GetMapping
    public String viewCart(Principal principal, Model model) {
        if (principal == null) return "redirect:/login";

        UserEntity user = userRepository.findByUsername(principal.getName()).orElseThrow();
        List<CartItemDTO> cartItems = cartService.toCartItemsDTO(user);
        Long totalCartPrice = cartService.calculateCartTotal(user);

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", totalCartPrice);

        return "cart";
    }
}