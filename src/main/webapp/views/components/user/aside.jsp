<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="max-w-xs mx-auto bg-white rounded-lg border border-gray-200 shadow-sm h-full relative">
    <!-- Top 5 Tin Mới Nhất -->
    <div class="p-5 border-b border-gray-100">
        <h2 class="text-lg font-semibold text-gray-800 mb-4 flex items-center">
            <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 text-blue-600 mr-2" fill="none" viewBox="0 0 24 24"
                 stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                      d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"/>
            </svg>
            5 Bản Tin Mới Nhất
        </h2>
        <ul class="space-y-3">
            <c:forEach var="listTop5NewsLatest" items="${listTop5NewsLatest}">
                <li class="group">
                    <a href="${pageContext.request.contextPath}/news-detail?id=${listTop5NewsLatest.id}&categoryId=${listTop5NewsLatest.categoryId}"
                       class="flex items-start space-x-2 text-gray-600 group-hover:text-blue-600 transition-colors duration-200">
                        <span class="text-blue-600 mt-1">•</span>
                        <span class="flex-1 line-clamp-2">${listTop5NewsLatest.title}</span>
                    </a>
                </li>
            </c:forEach>
        </ul>
    </div>

    <!-- Top 5 Tin Tức Xem Nhiều Nhất -->
    <div class="p-5 border-b border-gray-100">
        <h2 class="text-lg font-semibold text-gray-800 mb-4 flex items-center">
            <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 text-blue-600 mr-2" fill="none" viewBox="0 0 24 24"
                 stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                      d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"/>
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                      d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z"/>
            </svg>
            5 Bản Tin Xem Nhiều Nhất
        </h2>
        <ul class="space-y-3">
            <c:forEach var="listTop5ViewsCount" items="${listTop5ViewsCount}">
                <li class="group">
                    <a href="${pageContext.request.contextPath}/news-detail?id=${listTop5ViewsCount.id}&categoryId=${listTop5ViewsCount.categoryId}"
                       class="flex items-start space-x-2 text-gray-600 group-hover:text-blue-600 transition-colors duration-200">
                        <span class="text-blue-600 mt-1">•</span>
                        <span class="flex-1 line-clamp-2">${listTop5ViewsCount.title}</span>
                    </a>
                </li>
            </c:forEach>
        </ul>
    </div>

    <!-- Recently Viewed -->
    <div class="p-5 border-b border-gray-100">
        <a href="${pageContext.request.contextPath}/user/top5-seen"
           class="flex items-center text-gray-800 hover:text-blue-600 transition-colors duration-200 font-semibold">
            <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 text-blue-600 mr-2" fill="none" viewBox="0 0 24 24"
                 stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                      d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"/>
            </svg>
            5 Bản Tin Bạn Đã Xem
        </a>
    </div>

    <!-- Đăng ký nhận tin mới -->
    <div class="p-5 bg-gray-50 rounded-b-lg">
        <form class="space-y-3">
            <div class="relative">
                <input type="email"
                       placeholder="Nhập email đăng ký nhận tin mới"
                       class="w-full px-4 py-2.5 rounded-lg text-sm text-gray-700 bg-white border border-gray-300
                              focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent
                              transition-all duration-200"
                       required/>
            </div>
            <button type="submit"
                    class="w-full bg-blue-600 text-white px-4 py-2.5 rounded-lg font-medium
                           hover:bg-blue-700 focus:ring-4 focus:ring-blue-200
                           transition-all duration-200 text-sm">
                Đăng ký nhận tin
            </button>
        </form>
    </div>
</div>