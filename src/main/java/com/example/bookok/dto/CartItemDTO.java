package com.example.bookok.dto;

public class CartItemDTO {
    private Long cartItemId;
    private Long bookId;
    private String bookTitle;
    private Long price;
    private int quantity;
    private Long totalLinePrice; // Tổng tiền cho cuốn sách này (price * quantity)

    public CartItemDTO() {}

    public CartItemDTO(Long cartItemId, Long bookId, String bookTitle, Long price, int quantity) {
        this.cartItemId = cartItemId;
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.price = price;
        this.quantity = quantity;
        this.totalLinePrice = price * quantity;
    }

    // Getters and Setters
    public Long getCartItemId() { return cartItemId; }
    public void setCartItemId(Long cartItemId) { this.cartItemId = cartItemId; }
    public Long getBookId() { return bookId; }
    public void setBookId(Long bookId) { this.bookId = bookId; }
    public String getBookTitle() { return bookTitle; }
    public void setBookTitle(String bookTitle) { this.bookTitle = bookTitle; }
    public Long getPrice() { return price; }
    public void setPrice(Long price) { this.price = price; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public Long getTotalLinePrice() { return totalLinePrice; }
    public void setTotalLinePrice(Long totalLinePrice) { this.totalLinePrice = totalLinePrice; }
}