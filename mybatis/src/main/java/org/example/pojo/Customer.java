package org.example.pojo;

import java.util.List;

public class Customer {
    private int id;
    private String name;
    private String jobs;
    private String phone;
    private List<buy> buy;
    public Customer() {
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", jobs='" + jobs + '\'' +
                ", phone='" + phone + '\'' +
                ", buy=" + buy +
                '}';
    }

    public int getId() {
        return id;
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

    public String getJobs() {
        return jobs;
    }

    public void setJobs(String jobs) {
        this.jobs = jobs;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<buy> getBuy() {
        return buy;
    }

    public void setBuy(List<buy> buy) {
        this.buy = buy;
    }

    public Customer(int id, String name, String jobs, String phone, List<buy> buy) {
        this.id = id;
        this.name = name;
        this.jobs = jobs;
        this.phone = phone;
        this.buy = buy;
    }
}
