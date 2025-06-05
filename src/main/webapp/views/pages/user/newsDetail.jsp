<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="w-full mx-auto bg-white">
    <!-- Tin tức item -->
    <div class="flex gap-4 p-4 border-b hover:bg-gray-50 transition duration-300">

        <div class="w-[120px] h-[120px] flex-shrink-0 overflow-hidden rounded-lg">
            <a href="${pageContext.request.contextPath}/news-detail?id=${param.id}" class="block w-full h-full group">
                <img src="${pageContext.request.contextPath}/images/${param.image}"
                     alt="Ảnh minh họa"
                     class="w-full h-full object-cover transform transition-transform duration-700 ease-in-out group-hover:scale-105"/>
            </a>
        </div>


        <div class="flex-1 space-y-2">
            <a href="${pageContext.request.contextPath}/news-detail?id=${param.id}" class="block group">
                <h2 class="text-lg font-semibold text-gray-800 group-hover:text-blue-600 transform transition-all duration-300 group-hover:scale-[1.02] origin-left">
                    ${param.title}
                </h2>
            </a>

            <p class="text-gray-600 text-sm line-clamp-2">
                ${param.content}
            </p>

            <!-- Thông tin bài viết -->
            <c:set var="dateParts" value="${fn:split(param.postDate, '-')}"/>
            <div class="flex items-center text-xs text-gray-500 space-x-2">
                <span>Ngày đăng: ${dateParts[2]}/${dateParts[1]}/${dateParts[0]}</span>
                <span class="text-gray-300">|</span>
                <span>Tác giả: ${param.author}</span>
                <span class="text-gray-300">|</span>
                <span>Lượt xem: ${param.viewCount}</span>
            </div>
        </div>
    </div>
</div>