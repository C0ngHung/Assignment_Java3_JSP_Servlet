<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Trang Đọc Giả</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <style>
      .relative > a::after {
        content: '';
        position: absolute;
        left: 0;
        right: 0;
        bottom: -15px;
        height: 15px;
        pointer-events: auto;
      }
    </style>
</head>
<body class="bg-gray-100 text-gray-800">
    <header>
        <div class="w-full mx-auto flex justify-center bg-white shadow">
            <img src="https://caodang.fpt.edu.vn/wp-content/uploads/image3-1.png"
                 alt="Ảnh minh họa"
                 class="h-[250px] w-full object-cover" />
        </div>
    </header>
    <nav>
        <div class="bg-[#0D6EFD] text-white py-3">
            <div class="max-w-7xl mx-auto px-4">
                <ul class="flex space-x-12 justify-center text-[18px]">

                    <!-- Trang Chủ -->
                    <li class="relative group">
                      <a href="${pageContext.request.contextPath}/news/index"
                         class="relative z-10 border-b-2 border-transparent group-hover:border-[#ED9E61] group-hover:text-[#ED9E61] transition duration-300 uppercase">
                        Trang Chủ
                      </a>
                    </li>

                    <!-- Tin Tức -->
                    <li class="relative group">
                        <a href="${pageContext.request.contextPath}/news/culture"
                           class="relative z-10 border-b-2 border-transparent group-hover:border-[#78ED61] group-hover:text-[#78ED61] transition duration-300 uppercase">
                            Tin Tức
                        </a>
                    </li>

                    <!-- Loại Tin -->
                    <li class="relative group">
                        <a href="${pageContext.request.contextPath}/news/law"
                           class="relative z-10 border-b-2 border-transparent group-hover:border-[#fee728] group-hover:text-[#fee728] transition duration-300 uppercase">
                            Loại Tin
                        </a>
                    </li>

                    <!-- Người dùng -->
                    <li class="relative group">
                        <a href="${pageContext.request.contextPath}/news/sport"
                           class="relative z-10 border-b-2 border-transparent group-hover:border-[#ff942b] group-hover:text-[#ff942b] transition duration-300 uppercase">
                            Người dùng
                        </a>
                    </li>

                    <!-- Newsletter -->
                    <li class="relative group">
                        <a href="${pageContext.request.contextPath}/news/newsletter"
                           class="relative z-10 border-b-2 border-transparent group-hover:border-[#ff942b] group-hover:text-[#ff942b] transition duration-300 uppercase">
                            Newsletter
                        </a>
                    </li>

                </ul>
            </div>
        </div>
    </nav>
    <div class="flex max-w-[1200px] mx-auto h-[400px]">
      <main class="w-full h-full">
          <div class="max-w-7xl mx-auto p-4 bg-white rounded shadow h-[400px] flex items-center justify-center">
                <jsp:include page="/pages/${page}" />
          </div>
      </main>
    </div>
    <footer>
        <div class="container p-4 bg-gray-800 text-white text-center">
            <p class="text-sm">
                    Chào Mừng Bạn Đến Với Môn Học Java 3 JSP/Servlet © 2025. All Rights Reserved.
            </p>
        </div>
    </footer>
</body>
</html>