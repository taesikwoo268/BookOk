package com.example.bookok.repository;

import com.example.bookok.model.Book;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepository {
    static final String URL = "jdbc:mysql://localhost:3307/library_db";
    static final String USER = "root";
    static final String PASS = "3636";

    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE 1=1";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Book book = new Book(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("author")
                );
                books.add(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Loi :" + e.getMessage());
        }
        return books;
    }

    public void save(Book book) {
        String sql = "INSERT INTO books (title,author) VALUES (?,?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Loi :" + e.getMessage());
        }
    }

    public int deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            return pstmt.executeUpdate(); // Trả về số dòng bị xóa (1 nếu thành công, 0 nếu không tìm thấy id)

        } catch (SQLException e) {
            throw new RuntimeException("Loi :" + e.getMessage());
        }
    }

    public int update(Long id, Book book) {
        String sql = "UPDATE books SET title = ?, author = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setLong(3, id);

            return pstmt.executeUpdate(); // Trả về số dòng bị ảnh hưởng

        } catch (SQLException e) {
            throw new RuntimeException("Loi :" + e.getMessage());
        }
    }
}
