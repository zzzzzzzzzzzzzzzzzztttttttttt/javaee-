package org.example.javaeeforll.entity;

import lombok.Data;

@Data
public class Category {
    private Integer categoryId;
    private String categoryName;
    private Integer parentId; // 0为一级分类
    private Integer sort; // 排序优先级
}