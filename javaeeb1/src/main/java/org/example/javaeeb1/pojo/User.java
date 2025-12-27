package org.example.javaeeb1.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author rock
 * @since 2025-12-26 08:04:11
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer userId;
    private String username;
    private String password;
    private BigDecimal money;



}
