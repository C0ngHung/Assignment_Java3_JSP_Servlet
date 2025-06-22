package org.example.assignment_java3.dao;

import org.example.assignment_java3.common.dao.BaseDAO;
import org.example.assignment_java3.entity.Category;

import java.util.List;

/**
 * DAO cho bảng Category, kế thừa từ BaseDAO
 */
public interface CategoryDAO extends BaseDAO<Category, String> {

    /**
     * Tìm danh sách Category theo tên (ví dụ mở rộng thêm).
     *
     * @param keyword từ khóa tên cần tìm
     * @return danh sách category thỏa điều kiện
     */
    List<Category> searchByName(String keyword);

    // Bạn có thể thêm các phương thức đặc thù ở đây nếu cần
}
