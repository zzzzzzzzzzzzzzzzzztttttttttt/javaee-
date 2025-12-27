package org.example.javaeeb1.pojo;

import java.io.Serializable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author rock
 * @since 2025-12-26 08:02:53
 */
@Data
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer categoryId;

    private String categoryName;
    private Integer parentId;
    private Integer sort;


}
