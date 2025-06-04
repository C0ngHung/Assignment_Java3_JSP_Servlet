package org.example.assignment_java3.utils;


import org.example.assignment_java3.config.AppConfigReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcHelper {

    // Đọc cấu hình từ file (ví dụ: db.properties)
    private static final String url = AppConfigReader.getDbUrl();
    private static final String dbDriver = AppConfigReader.getDbDriver();
    private static final String dbUsername = AppConfigReader.getDbUsername();
    private static final String dbPassword = AppConfigReader.getDbPassword();

    // Nạp driver JDBC một lần khi class được load
    static {
        try {
            Class.forName(dbDriver);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Không tìm thấy Driver JDBC", e);
        }
    }

    // Hàm tạo kết nối tới database
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, dbUsername, dbPassword);
    }

    // Tạo PreparedStatement hoặc CallableStatement từ SQL và các tham số
    private static PreparedStatement buildStatement(Connection conn, String sql, Object... args) throws SQLException {
        PreparedStatement stmt = sql.trim().startsWith("{")
                ? conn.prepareCall(sql) // Nếu là thủ tục (callable)
                : conn.prepareStatement(sql); // Nếu là SQL thường

        // Gán giá trị cho các tham số ?
        for (int i = 0; i < args.length; i++) {
            stmt.setObject(i + 1, args[i]); // JDBC đếm từ 1
        }

        return stmt;
    }

    // Functional interface để xử lý dữ liệu lấy được từ ResultSet
    @FunctionalInterface
    public interface ResultSetHandler<T> {
        T handle(ResultSet rs) throws SQLException;
    }

    /**
     * Hàm thực hiện truy vấn SELECT và trả kết quả theo cách xử lý của người dùng
     *
     * @param sql     câu truy vấn có thể chứa ?
     * @param handler hàm xử lý kết quả ResultSet
     * @param args    danh sách giá trị thay cho dấu ?
     * @return kết quả theo kiểu T do handler xử lý và trả về
     */
    public static <T> T query(String sql, ResultSetHandler<T> handler, Object... args) {
        try (
                Connection conn = getConnection();
                PreparedStatement stmt = buildStatement(conn, sql, args);
                ResultSet rs = stmt.executeQuery()
        ) {
            return handler.handle(rs); // Giao quyền xử lý resultSet cho người dùng
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi thực hiện truy vấn: " + sql, e);
        }
    }

    /**
     * Hàm thực hiện lệnh INSERT, UPDATE hoặc DELETE
     *
     * @param sql  câu lệnh SQL chứa ?
     * @param args danh sách giá trị thay cho dấu ?
     * @return số dòng bị ảnh hưởng
     */
    public static int update(String sql, Object... args) {
        try (
                Connection conn = getConnection();
                PreparedStatement stmt = buildStatement(conn, sql, args)
        ) {
            return stmt.executeUpdate(); // Trả về số dòng bị thay đổi
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi thực hiện update: " + sql, e);
        }
    }

    /**
     * Hàm kiểm tra kết nối đến database, in ra trạng thái
     */
    public static void main(String[] args) {
        try (Connection conn = JdbcHelper.getConnection()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("✅ Kết nối SQL Server thành công!");
            } else {
                System.out.println("❌ Kết nối thất bại.");
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi kết nối: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
