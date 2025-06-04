<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="w-full mx-auto py-4 bg-white space-y-4 px-4 md:px-6 lg:px-8">

    <!-- Tiêu đề bài viết -->
    <a href="#" class="block text-center transition duration-300">
        <h1 class="text-2xl md:text-2xl font-semibold uppercase text-gray-800 hover:text-blue-600">
            ${param.title}
        </h1>
    </a>

    <!-- Hình ảnh -->
    <a href="#" class="block">
        <div class="flex justify-center overflow-hidden rounded-lg">
            <img src="${pageContext.request.contextPath}/images/${param.image}"
                 alt="Ảnh minh họa"
                 class="rounded-lg max-h-[400px] w-auto object-cover transform transition duration-500 hover:scale-105"/>
        </div>
    </a>

    <!-- Tác giả & lượt xem -->
    <c:set var="dateParts" value="${fn:split(param.postDate, '-')}"/>
    <div class="flex flex-wrap justify-between text-sm text-gray-600 italic border-t pt-2">
        <span>Ngày đăng: ${dateParts[2]}/${dateParts[1]}/${dateParts[0]}</span>
        <span>Tác giả: ${param.author}</span>
        <span>Lượt xem: ${param.viewCount}</span>
    </div>
</div>
