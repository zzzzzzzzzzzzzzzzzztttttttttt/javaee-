package com.itheima.dao;

import org.springframework.jdbc.core.JdbcTemplate;

public class BookDao {
    private JdbcTemplate jdbcTemplate;
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public void deleteById(int id) {
        String sql = "delete from bookstore WHERE id = ?";
        jdbcTemplate.update(sql,id);
    }

}