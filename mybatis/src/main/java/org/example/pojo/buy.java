package org.example.pojo;

public class buy {
    private  String bookName;
    private  int number;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
    public buy(){}

    @Override
    public String toString() {
        return "buy{" +
                "bookName='" + bookName + '\'' +
                ", number=" + number +
                '}';
    }
    public buy(String bookName, int number) {
        this.bookName = bookName;
        this.number = number;
    }
}
