package com.example.bookok.model;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    @Column(name = "price")
    private Long price;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    public BookEntity(Long id, String title, String author, CategoryEntity category) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
    }

    public BookEntity() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public CategoryEntity getCategory() { return category; }
    public void setCategory(CategoryEntity category) { this.category = category; }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
}