package org.example.assignment_java3.utils;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class ImageUtil {

    /**
     * Lưu file ảnh upload vào thư mục /images của webapp và trả về tên file.
     *
     * @param imagePart      Part từ form upload
     * @param servletContext dùng để lấy đường dẫn thực
     * @return tên file đã lưu hoặc null nếu không có ảnh
     */
    public static String save(Part imagePart, ServletContext servletContext) throws IOException {
        if (imagePart == null || imagePart.getSize() == 0) {
            return null;
        }

        // Lấy tên file gốc
        String fileName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();

        // Đường dẫn lưu ảnh
        String uploadPath = servletContext.getRealPath("/images");
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        // Ghi file
        String fullPath = uploadPath + File.separator + fileName;
        imagePart.write(fullPath);

        return fileName;
    }
}
