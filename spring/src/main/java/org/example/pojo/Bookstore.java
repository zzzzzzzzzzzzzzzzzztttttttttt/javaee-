package org.example.pojo;

public class Bookstore {
    private Integer id;
    private String name;
    private String author;
    private String press;
    private Integer amount;

    @Override
    public String toString() {
        return "Bookstore{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", press='" + press + '\'' +
                ", amount=" + amount +
                '}';
    }

    public Bookstore() {
    }

    public Bookstore(Integer id, String name, String author, String press, Integer amount) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.press = press;
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
