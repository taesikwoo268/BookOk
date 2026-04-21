package com.example.bookok.controller;

import com.example.bookok.dto.BookDTO;

import com.example.bookok.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookService bookService;

//    @GetMapping
//    public List<BookDTO> getAllBooks() {
//        return bookService.getAllBooks();
//    }

    @GetMapping
    public List<BookDTO> getAllBooks(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long minPrice,
            @RequestParam(required = false) Long maxPrice,
            @RequestParam(required = false) List<String> categories,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return bookService.getAllBooksPaged(keyword, minPrice, maxPrice, categories, page, size).getContent();
    }

    @PostMapping
    public void createBook(@RequestBody BookDTO bookDTO) {
        bookService.createBook(bookDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        bookService.deleteById(id);
    }

    @PutMapping("/{id}")
    public void updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO) {
        bookService.updateBook(id, bookDTO);
    }
}
