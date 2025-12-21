package org.example.pojo;

import lombok.Data;

import java.util.List;

@Data
public class Customer {
    private Integer id;
    private String username;
    private String jobs;
    private String phone;
    private List<buy> buy;
}
