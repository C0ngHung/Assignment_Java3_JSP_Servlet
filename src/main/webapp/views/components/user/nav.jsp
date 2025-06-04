<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="bg-blue-800 text-white py-3">
    <div class="max-w-7xl mx-auto px-4">
        <ul class="flex space-x-12 justify-center text-[18px]">
            <!-- Trang Chủ -->
            <li class="relative group">
                <a href="${pageContext.request.contextPath}/user/index"
                   class="relative z-10 border-b-2 border-transparent group-hover:border-yellow-400 group-hover:text-yellow-400 transition duration-300 uppercase">
                    Trang Chủ </a>
            </li>
            <!-- Văn Hóa -->
            <li class="relative group">
                <a href="${pageContext.request.contextPath}/user/culture"
                   class="relative z-10 border-b-2 border-transparent group-hover:border-green-400 group-hover:text-green-400 transition duration-300 uppercase">
                    Văn Hóa </a>
            </li>
            <!-- Pháp Luật -->
            <li class="relative group">
                <a href="${pageContext.request.contextPath}/user/law"
                   class="relative z-10 border-b-2 border-transparent group-hover:border-yellow-300 group-hover:text-yellow-300 transition duration-300 uppercase">
                    Pháp Luật </a>
            </li>
            <!-- Thể Thao -->
            <li class="relative group">
                <a href="${pageContext.request.contextPath}/user/sport"
                   class="relative z-10 border-b-2 border-transparent group-hover:border-orange-400 group-hover:text-orange-400 transition duration-300 uppercase">
                    Thể Thao </a>
            </li>
            <!-- Đăng Nhập -->
            <li class="relative group">
                <a href="${pageContext.request.contextPath}/login"
                   class="relative z-10 border-b-2 border-transparent group-hover:border-green-400 group-hover:text-green-400 transition duration-300 uppercase">Đăng
                    Nhập</a>
            </li>
        </ul>
    </div>
</div>