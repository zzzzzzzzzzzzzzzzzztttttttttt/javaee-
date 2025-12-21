package org.example.javaeeforll.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderNumUtil {
    // 生成唯一订单号：时间戳+用户ID
    public static String generateOrderNum(Integer userId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return sdf.format(new Date()) + userId;
    }
}