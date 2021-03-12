package com.xianyanyang.batch;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author : Bei-Zhen
 * @date : 2020-08-24 0:43
 */
public class JDBC2 {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        conn();
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start) / 1000 + "秒");
    }

    private static String sql = "INSERT INTO shop_customer_order ( id, creator, shop_id, order_no, product_amount, order_amount ) " +
            "VALUES ( UUID(), '1', '1', ?, ?, ? )";
    private static String connection = "jdbc:mysql://ryplus-db.mysql.rds.aliyuncs.com:3306/ry_process_1116?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8&nullCatalogMeansCurrent=true";

    public static void conn() {
        try (Connection conn = DriverManager.getConnection(connection, "root", "CDjKSzPr3Tti3YO3");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (int i = 0; i < 1000000; i++) {
                pstmt.setString(1, (int) (Math.random() * 1000000) + "");
                pstmt.setString(2, (int) (Math.random() * 100) + "");
                pstmt.setString(3, (int) (Math.random() * 100) + "");
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            System.out.println("添加1000000条信息成功！");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}