package com.example.bookok.controller;

import com.example.bookok.model.Book;
import com.example.bookok.model.ResponseObject;
import com.example.bookok.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<ResponseObject> getAll() {
        List<Book> list = bookService.getAllBooks();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Truy vấn danh sách thành công", list)
        );
    }

    @PostMapping
    public ResponseEntity<ResponseObject> create(@RequestBody Book book) {
        String msg = bookService.createBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseObject("ok", msg, book)
        );
    }
    @DeleteMapping("/{id}")
    public int deleteById(@PathVariable Long id) {
        return bookService.deleteById(id);
    }
    @PutMapping("/{id}")
    public String update(@PathVariable Long id, @RequestBody Book book) {
        return bookService.updateBook(id,book);
    }
}
