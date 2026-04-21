package com.example.bookok.controller;

import com.example.bookok.dto.BookDTO;
import com.example.bookok.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/web")
public class BookWebController {

    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    public String getAllBooks(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long minPrice,
            @RequestParam(required = false) Long maxPrice,
            @RequestParam(required = false) List<String> categories,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        Page<BookDTO> bookPage = bookService.getAllBooksPaged(keyword, minPrice, maxPrice, categories, page, size);

        model.addAttribute("books", bookPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", bookPage.getTotalPages());
        model.addAttribute("keyword", keyword);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        model.addAttribute("allCategories", bookService.getAllCategories()); // Danh sách để render checkbox
        model.addAttribute("selectedCategories", categories); // Danh sách những cái đã tích

        return "index";
    }

    @PostMapping("/books/add")
    public String createBook(@ModelAttribute BookDTO bookDTO) {
        bookService.createBook(bookDTO);
        return "redirect:/web/books";
    }

    @GetMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteById(id);
        return "redirect:/web/books";
    }
    @GetMapping("/books/add")
    public String showAddForm(Model model) {
        model.addAttribute("book", new BookDTO());
        model.addAttribute("categories", bookService.getAllCategories()); // Gửi danh sách categories
        return "add-book";
    }

    @GetMapping("/books/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookService.getBookById(id));
        model.addAttribute("categories", bookService.getAllCategories()); // Gửi danh sách categories
        return "edit-book";
    }

    // Xử lý cập nhật
    @PostMapping("/books/update/{id}")
    public String updateBook(@PathVariable Long id, @ModelAttribute BookDTO bookDTO) {
        bookService.updateBook(id, bookDTO);
        return "redirect:/web/books";
    }
}