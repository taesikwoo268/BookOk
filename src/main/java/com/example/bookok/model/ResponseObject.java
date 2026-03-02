package com.example.bookok.model;

public class ResponseObject {
    private String status;  // "ok" hoặc "failed"
    private String message; // Thông báo chi tiết
    private Object data;    //Dữ liệu trả về (có thể là List<Book>, 1 Book, hoặc null)

    public ResponseObject(String status, String message,Object data) {
        this.data = data;
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
