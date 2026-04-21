package com.example.bookok.service;

import com.example.bookok.dto.BookDTO;
import com.example.bookok.model.BookEntity;
import com.example.bookok.model.CategoryEntity;
import com.example.bookok.repository.BookRepository;
import com.example.bookok.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(entity -> modelMapper.map(entity, BookDTO.class))
                .collect(Collectors.toList());
    }

    public Page<BookDTO> getAllBooksPaged(String keyword, int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by("id").ascending());

        Page<BookEntity> entityPage;
        if (keyword != null && !keyword.isEmpty()) {
            entityPage = bookRepository.findByTitleContainingIgnoreCase(keyword, pageable);
        } else {
            entityPage = bookRepository.findAll(pageable);
        }

        return entityPage.map(entity -> modelMapper.map(entity, BookDTO.class));
    }
    public Page<BookDTO> getAllBooksPaged(String keyword, Long minPrice, Long maxPrice, List<String> categories, int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by("id").descending());

        long min = (minPrice == null) ? 0L : minPrice;
        long max = (maxPrice == null) ? 1000000000L : maxPrice;
        String key = (keyword == null) ? "" : keyword;
        if (categories == null || categories.isEmpty()) {

            return bookRepository.findByTitleContainingIgnoreCaseAndPriceBetween(key, min, max, pageable)
                    .map(entity -> modelMapper.map(entity, BookDTO.class));
        }

        return bookRepository.findByTitleContainingIgnoreCaseAndPriceBetweenAndCategory_NameIn(
                        key, min, max, categories, pageable)
                .map(entity -> modelMapper.map(entity, BookDTO.class));
    }

    public void createBook(BookDTO bookDTO) {
        // 1. Chuyển DTO sang Entity (lúc này category bên trong có thể bị null hoặc thiếu thông tin)
        BookEntity book = modelMapper.map(bookDTO, BookEntity.class);

        // 2. Tìm Category thật từ DB dựa trên tên người dùng đã chọn
        CategoryEntity category = categoryRepository.findByName(bookDTO.getCategoryName())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thể loại: " + bookDTO.getCategoryName()));

        // 3. Gán Category "thật" (đã được quản lý bởi Hibernate) vào Book
        book.setCategory(category);

        // 4. Lưu
        bookRepository.save(book);
    }

    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    public BookDTO getBookById(Long id) {
        BookEntity entity = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sách với ID: " + id));
        return modelMapper.map(entity, BookDTO.class);
    }

    // 2. Cập nhật thông tin sách
    public void updateBook(Long id, BookDTO bookDTO) {
        // Kiểm tra xem sách có tồn tại không trước khi sửa
        if (bookRepository.existsById(id)) {
            BookEntity book = modelMapper.map(bookDTO, BookEntity.class);
            CategoryEntity category = categoryRepository.findByName(bookDTO.getCategoryName())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy thể loại: " + bookDTO.getCategoryName()));
            book.setCategory(category);
            book.setId(id); // Quan trọng: Phải gán ID để JPA biết là Update chứ không phải Insert
            bookRepository.save(book);
        }
    }

    public List<CategoryEntity> getAllCategories() {
        return categoryRepository.findAll();
    }
}