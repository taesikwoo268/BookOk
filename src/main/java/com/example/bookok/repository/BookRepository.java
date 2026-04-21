package com.example.bookok.repository;

import com.example.bookok.model.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
    Page<BookEntity> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    Page<BookEntity> findByTitleContainingIgnoreCaseAndPriceBetween(
            String title, Long minPrice, Long maxPrice, Pageable pageable);
    Page<BookEntity> findByTitleContainingIgnoreCaseAndPriceBetweenAndCategory_NameIn(
            String title, Long minPrice, Long maxPrice, List<String> categoryName, Pageable pageable);
}