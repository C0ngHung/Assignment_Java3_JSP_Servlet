package org.example.assignment_java3.common.dao;

import java.util.List;

/**
 * Giao diện cơ sở dùng cho tất cả các DAO.
 *
 * @param <T>  Kiểu của thực thể (entity), ví dụ: Department, Employee,...
 * @param <ID> Kiểu của khóa chính, ví dụ: String, Integer,...
 */
public interface BaseDAO<T, ID> {

    /**
     * Lấy danh sách tất cả các bản ghi trong bảng tương ứng.
     *
     * @return danh sách tất cả các đối tượng kiểu T
     */
    List<T> findAll();

    /**
     * Tìm một bản ghi theo khóa chính.
     *
     * @param id khóa chính cần tìm
     * @return đối tượng T nếu tìm thấy, ngược lại trả về null
     */
    T findById(ID id);

    /**
     * Thêm mới một bản ghi vào cơ sở dữ liệu.
     *
     * @param entity đối tượng cần thêm
     */
    void create(T entity);

    /**
     * Cập nhật thông tin của một bản ghi đã tồn tại.
     *
     * @param entity đối tượng chứa thông tin cập nhật
     */
    void update(T entity);

    /**
     * Xóa bản ghi theo khóa chính.
     *
     * @param id khóa chính của bản ghi cần xóa
     */
    void deleteById(ID id);
}
