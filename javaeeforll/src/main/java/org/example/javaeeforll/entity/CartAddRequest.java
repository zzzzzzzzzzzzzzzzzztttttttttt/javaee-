package org.example.javaeeforll.entity;

public class CartAddRequest {
    private Integer userId;
    private Integer goodsId;
    private Integer num = 1; // 默认值1

    // 必须添加getter/setter，否则Spring无法解析参数
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}