package org.example.assignment_java3.utils;


import org.example.assignment_java3.config.AppConfigReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCHelper {

    // Khai báo URL, username, password lấy từ file config
    private static final String url = AppConfigReader.getDbUrl();
    private static final String dbUsername = AppConfigReader.getDbUsername();
    private static final String dbPassword = AppConfigReader.getDbPassword();

    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Không tìm thấy Driver JDBC", e);
        }
    }

    // Connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, dbUsername, dbPassword);
    }

    /**
     * Xây dựng PreparedStatement
     *
     * @param sql là câu lệnh SQL chứa có thể chứa tham số. Nó có thể là một lời
     *      * gọi thủ tục lưu
     *      * @param args là danh sách các giá trị được cung cấp cho các tham số trong
     *      * câu lệnh sql
     *      * @return PreparedStatement tạo được
     *      * @throws java.sql.SQLException lỗi sai cú pháp
     */
    public static PreparedStatement preparedStatement(Connection conn, String sql, Object...args) throws SQLException {
        PreparedStatement stmt = sql.trim().startsWith("{") ?
                conn.prepareCall(sql) : conn.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            stmt.setObject(i + 1, args[i]);
        }
        return stmt;
    };

    /**
    * Thực hiện câu lệnh SQL truy vấn (SELECT) hoặc thủ tục lưu truy vấn dữ liệu
    * @param sql là câu lệnh SQL chứa có thể chứa tham số. Nó có thể là một lời gọi thủ tục lưu
    * @param args là danh sách các giá trị được cung cấp cho các tham số trong câu lệnh sql
    */
    public static ResultSet executeQuery(String sql, Object...args) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement stmt = preparedStatement(conn, sql, args);
        return stmt.executeQuery();
    };

    /**
    * Thực hiện câu lệnh SQL thao tác (INSERT, UPDATE, DELETE) hoặc thủ tục lưu thao tác dữ liệu
    * @param sql là câu lệnh SQL chứa có thể chứa tham số. Nó có thể là một lời gọi thủ tục lưu
    * @param args là danh sách các giá trị được cung cấp cho các tham số trong câu lệnh sql
    * INSERT, UPDATE, DELETE → trả về số dòng bị ảnh hưởng
    */

    public static int executeUpdate(String sql, Object...args) {
        try (Connection conn = getConnection();
            PreparedStatement stmt = preparedStatement(conn, sql, args)) {
            return stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(" Lỗi khi thực thi update: " + sql, e);
        }
    }

    /**
     *
     * @param args
     * Hàm test thử kết nối
     */
    public static void main(String[] args) {
        try (Connection conn = JDBCHelper.getConnection()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("Kết nối SQL Server thành công!");
            } else {
                System.out.println("Kết nối thất bạn.");
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi kết nối: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
