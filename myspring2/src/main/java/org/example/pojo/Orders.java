package org.example.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Orders {
    private  Integer id;
    private Integer customerId;
    private Integer bookId;
    private Integer nums;
    private Date orderTime;
}
