package db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public static Connection getInstance(){
        // MySQL 연결 정보
        String url = "jdbc:mysql://localhost:3306/baseball?autoReconnect=true";
        String username = "root";
        String password = "root1234";

        Connection connection = null;
        // JDBC 드라이버 로드
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("디버그 : DB 연결 성공");
            return connection;
        } catch (Exception e) {
            System.out.println("디버그 : DB 연결 실패");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
