package org.example.pojo;

public class Book {
    private int id;
    private String name;
    private String author;
    private String press;
    private String amount;

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "bookstore{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", press='" + press + '\'' +
                ", amount='" + amount + '\'' +
                '}';
    }

    public Book(String name, String author, String press, String amount) {
        this.amount = amount;
        this.press = press;
        this.author = author;
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Book(int id, String name, String author, String press, String amount) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.press = press;
        this.amount = amount;
    }
    public Book(){}
}
