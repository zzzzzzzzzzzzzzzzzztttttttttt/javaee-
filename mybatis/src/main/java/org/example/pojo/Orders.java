package org.example.pojo;

public class Orders {
    private Integer id;
    private Integer customerId;
    private Integer number;
    private Book book;
    public Orders() {
    }

    public Orders(Integer id, Integer customerId, Integer number, Book book) {
        this.id = id;
        this.customerId = customerId;
        this.number = number;
        this.book = book;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", number=" + number +
                ", book=" + book +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}