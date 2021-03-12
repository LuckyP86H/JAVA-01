package com.xianyanyang.batch;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

/**
 * JDBC数据库批量插入服务
 */
public class JdbcOperation {

    private static String USER_NAME = "root";
    private static String PASSWORD = "foo";
    private static String SQL = "INSERT INTO customer_order( id, customer_id,  order_amount ) VALUES (?, '1',  ? )";
    private static String CONNECTION = "jdbc:mysql://rm-foo.mysql.rds.aliyuncs.com/learn?&rewriteBatchedStatements=true&useCompression=true&useUnicode=true&characterEncoding=utf8&useSSL=false&nullCatalogMeansCurrent=true&serverTimezone=GMT%2B8&autoReconnect=true&useSSL=false";

    public static void batchInsertRows(int count) {
        try (Connection conn = DriverManager.getConnection(CONNECTION, USER_NAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            for (int i = 0; i < count; i++) {
                pstmt.setString(1, UUID.randomUUID().toString());
                pstmt.setString(2, (int) (Math.random() * 100) + "");
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            System.out.println("插入" + count + "条信息成功");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
