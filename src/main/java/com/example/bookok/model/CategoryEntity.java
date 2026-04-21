package com.example.bookok.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "categories")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Đã đổi sang Long

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<BookEntity> books;

    public CategoryEntity() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<BookEntity> getBooks() { return books; }
    public void setBooks(List<BookEntity> books) { this.books = books; }
}