package com.xuecheng.system.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionTest {
    public static void main(String[] args) {
        // 数据库连接URL
        String url = "jdbc:mysql://192.168.101.128:3306/xuecheng_system";
        // 数据库用户名
        String username = "root";
        // 数据库密码
        String password = "glj";

        try {
            // 加载驱动（对于Java 6及以上版本，这一步通常是可选的）
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 建立连接
            Connection connection = DriverManager.getConnection(url, username, password);
            if (connection!= null) {
                System.out.println("数据库连接成功！");
                connection.close();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("找不到数据库驱动。");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("数据库连接失败。");
        }
    }
}