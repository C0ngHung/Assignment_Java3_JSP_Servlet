package org.example.assignment_java3.common.service;

import java.util.List;
import java.util.Optional;

/**
 * BaseService dùng cho tầng Service để xử lý logic trung gian giữa Controller và DAO.
 *
 * @param <T>  Entity
 * @param <ID> Loại khóa chính (String, Integer, ...)
 */
public interface BaseService<T, ID> {

    /**
     * Lấy toàn bộ bản ghi.
     *
     * @return danh sách đối tượng
     */
    List<T> findAll();

    /**
     * Tìm bản ghi theo ID.
     *
     * @param id ID cần tìm
     * @return Optional chứa đối tượng nếu tồn tại
     */
    Optional<T> findById(ID id);

    /**
     * Thêm một bản ghi mới.
     *
     * @param entity đối tượng cần thêm
     * @return true nếu thêm thành công
     */
    boolean insert(T entity);

    /**
     * Cập nhật bản ghi hiện có.
     *
     * @param entity đối tượng chứa thông tin cập nhật
     * @return true nếu cập nhật thành công
     */
    boolean update(T entity);

    /**
     * Xóa bản ghi theo ID.
     *
     * @param id ID bản ghi cần xóa
     * @return true nếu xóa thành công
     */
    boolean delete(ID id);

    /**
     * Kiểm tra bản ghi có tồn tại hay không.
     *
     * @param id ID cần kiểm tra
     * @return true nếu tồn tại
     */
    boolean exists(ID id);
}
