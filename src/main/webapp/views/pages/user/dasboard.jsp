<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="w-full mx-auto py-6 bg-white shadow-sm rounded-xl space-y-6 px-4 md:px-8 lg:px-12 max-w-7xl">
    <!-- Tiêu đề bài viết -->
    <a href="#" class="group block text-center transition-all duration-300 ease-in-out">
        <h1 class="text-2xl md:text-3xl lg:text-4xl font-bold text-gray-800 group-hover:text-blue-600 transition-colors duration-300 tracking-tight">
            ${param.title}
        </h1>
    </a>

    <!-- Hình ảnh -->
    <div class="relative group">
        <div class="aspect-w-16 aspect-h-9 overflow-hidden rounded-xl shadow-lg">
            <img src="${pageContext.request.contextPath}/images/${param.image}"
                 alt="Ảnh minh họa"
                 class="w-full h-full object-cover transform transition-transform duration-700 ease-in-out group-hover:scale-105"/>
        </div>
        <div class="absolute inset-0 bg-gradient-to-t from-black/20 to-transparent opacity-0 group-hover:opacity-100 transition-opacity duration-300"></div>
    </div>

    <!-- Thông tin bài viết -->
    <c:set var="dateParts" value="${fn:split(param.postDate, '-')}"/>
    <div class="flex flex-wrap items-center justify-between text-sm text-gray-600 italic border-t pt-2">
        <span class="text-gray-600">Ngày đăng: ${dateParts[2]}/${dateParts[1]}/${dateParts[0]}</span>
        <div class="flex items-center space-x-2">
            <span>Tác giả: ${param.author}</span>
            <span class="text-gray-400">|</span>
            <span>Lượt xem: ${param.viewCount}</span>
        </div>
    </div>

</div>