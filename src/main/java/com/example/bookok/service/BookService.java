package com.example.bookok.service;

import com.example.bookok.model.Book;
import com.example.bookok.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
    public String createBook(Book book) {
        if (book.getTitle() == null || book.getTitle().isEmpty()) {
            return "Error: Ten sach khong duoc de trong!";
        }
        bookRepository.save(book);
        return "Them sach thanh cong!";
    }
    public int deleteById(Long id) {
        return bookRepository.deleteById(id);
    }
    public String updateBook(Long id, Book book) {
        int res = bookRepository.update(id,book);
        if (res > 0) {
            return "Cap nhat sach co id: " + id + " thanh cong";
        }
        return "Cap nhat that bai";
    }
}
