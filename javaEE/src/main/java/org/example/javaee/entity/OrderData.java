package org.example.javaee.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
@Data
public class OrderData {
    private String username;
    @JsonProperty("orderItems")
    private List<UserCart> carts;
    private Double totalPrice;
}
