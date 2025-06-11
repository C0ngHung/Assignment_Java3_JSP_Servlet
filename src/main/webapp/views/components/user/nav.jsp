<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="bg-blue-800 text-white py-3">
    <div class="max-w-7xl mx-auto px-4">
        <ul class="flex space-x-12 justify-center text-[18px]">
            <!-- Trang Chủ -->
            <li class="relative group">
                <a href="${pageContext.request.contextPath}/user/index"
                   class="relative z-10 border-b-2 border-transparent group-hover:border-yellow-400 group-hover:text-yellow-400 transition duration-300 uppercase">
                    Trang Chủ </a>
            </li>
            <%--  Danh sách danh mục    --%>
            <c:forEach items="${categoryList}" var="category">
                <li class="relative group">
                    <a href="${pageContext.request.contextPath}/user/category/${category.id}"
                       class="relative z-10 border-b-2 border-transparent group-hover:border-yellow-400 group-hover:text-yellow-400 transition duration-300 uppercase">
                        ${category.name} </a>
                </li>
            </c:forEach>
            <!-- Đăng Nhập -->
            <li class="relative group">
                <a href="${pageContext.request.contextPath}/login"
                   class="relative z-10 border-b-2 border-transparent group-hover:border-green-400 group-hover:text-green-400 transition duration-300 uppercase">Đăng
                    Nhập</a>
            </li>
        </ul>
    </div>
</div>