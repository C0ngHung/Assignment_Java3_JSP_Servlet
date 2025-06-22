package org.example.assignment_java3.service;

import org.example.assignment_java3.common.service.BaseService;
import org.example.assignment_java3.entity.News;

import java.util.List;

public interface NewsService extends BaseService<News, String> {

    /**
     * Lấy danh sách 5 tin tức có lượt xem cao nhất.
     *
     * @return danh sách top 5 tin tức theo lượt xem
     */
    List<News> getTop5ViewsCount();

    /**
     * Lấy danh sách 5 tin tức mới nhất theo ngày đăng.
     *
     * @return danh sách top 5 tin mới nhất
     */
    List<News> getTop5NewsLatest();

    /**
     * Lấy danh sách 5 tin tức có ID nằm trong danh sách truyền vào và sắp xếp theo thứ tự ID trong danh sách.
     *
     * @param ids danh sách ID cần lấy
     * @return danh sách tin tức
     */
    List<News> getTop5NewsViewed(List<String> ids);

    /**
     * Lấy tất cả tin tức được đánh dấu là "hiển thị trang chủ" (`home = true`).
     *
     * @return danh sách tin tức nổi bật
     */
    List<News> getAllNewsByHome();

    /**
     * Cập nhật lượt xem của bài viết (tăng +1).
     *
     * @param id ID bài viết
     * @return true nếu thành công
     */
    boolean updateViewCount(String id);

    /**
     * Lấy danh sách tin theo loại tin.
     *
     * @param categoryId ID của loại tin
     * @return danh sách tin tức
     */
    List<News> getNewsByCategory(String categoryId);

    /**
     * Lấy danh sách tin theo tác giả.
     *
     * @param author tên tác giả
     * @return danh sách tin tức
     */
    List<News> getNewsByAuthor(String author);

    /**
     * Kiem tra xem danh muc co tin nao khong
     * @param categoryId
     * @return
     */
    boolean existsByCategoryId(String categoryId);

    /**
     * Kiểm tra xem tác giả có tin tức nào không.
     *
     * @param authorId ID của tác giả
     * @return true nếu tác giả có ít nhất một bài viết
     */
    boolean existsByAuthorId(String authorId);
}
