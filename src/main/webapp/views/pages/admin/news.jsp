<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<article class="w-full max-w-4xl mx-auto space-y-6 px-4 sm:px-6 lg:px-8">
    <%--    Hiển thị danh sách bản tin ---%>
    <c:forEach var="news" items="${newsList}">
        <div class="group relative bg-white rounded-xl shadow-sm hover:shadow-lg transition-all duration-300 ease-out border border-gray-100 hover:border-gray-200 overflow-hidden">
            <!-- Tin tức item -->
            <div class="flex flex-col sm:flex-row gap-4 sm:gap-6 p-6">
                <!-- Hình ảnh -->
                <div class="w-full sm:w-40 h-48 sm:h-32 flex-shrink-0 overflow-hidden rounded-lg bg-gray-100">
                    <a href="${pageContext.request.contextPath}/${sessionScope.currentUserRole}/news-detail?id=${news.id}&categoryId=${news.categoryId}"
                       class="block w-full h-full group-hover:scale-[1.02] transition-transform duration-500 ease-out"
                       aria-label="Xem chi tiết bài viết: ${news.title}">
                        <img src="${pageContext.request.contextPath}/images/${news.image}"
                             alt="${news.title}"
                             class="w-full h-full object-cover"
                             loading="lazy"/>
                    </a>
                </div>

                <!-- Nội dung -->
                <div class="flex-1 space-y-3 min-w-0">
                    <!-- Tiêu đề -->
                    <a href="${pageContext.request.contextPath}/${sessionScope.currentUserRole}/news-detail?id=${news.id}&categoryId=${news.categoryId}"
                       class="block group/title">
                        <h2 class="text-xl sm:text-lg font-bold text-gray-900 group-hover/title:text-blue-600 transition-colors duration-200 line-clamp-2 leading-tight">
                                ${news.title}
                        </h2>
                    </a>

                    <!-- Tóm tắt nội dung -->
                    <p class="text-gray-600 text-sm sm:text-base line-clamp-2 sm:line-clamp-3 leading-relaxed">
                            ${news.content}
                    </p>

                    <!-- Metadata -->
                    <c:set var="dateParts" value="${fn:split(news.postDate, '-')}"/>
                    <div class="flex flex-wrap items-center gap-x-4 gap-y-2 text-xs sm:text-sm text-gray-500">
                        <div class="flex items-center gap-1.5">
                            <svg class="w-4 h-4 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z"/>
                            </svg>
                            <span class="font-medium">${dateParts[2]}/${dateParts[1]}/${dateParts[0]}</span>
                        </div>

                        <div class="flex items-center gap-1.5">
                            <svg class="w-4 h-4 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"/>
                            </svg>
                            <span>${news.author}</span>
                        </div>

                        <div class="flex items-center gap-1.5">
                            <svg class="w-4 h-4 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"/>
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z"/>
                            </svg>
                            <span>${news.viewCount} lượt xem</span>
                        </div>
                    </div>

                    <!-- Read more button - chỉ hiện trên mobile để UX tốt hơn -->
                    <div class="pt-2 sm:hidden">
                        <a href="${pageContext.request.contextPath}/${sessionScope.currentUserRole}/news-detail?id=${news.id}&categoryId=${news.categoryId}"
                           class="inline-flex items-center gap-2 text-blue-600 hover:text-blue-700 text-sm font-medium transition-colors duration-200">
                            <span>Đọc tiếp</span>
                            <svg class="w-4 h-4 transition-transform duration-200 group-hover:translate-x-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7"/>
                            </svg>
                        </a>
                    </div>
                </div>
            </div>

            <!-- Subtle hover indicator -->
            <div class="absolute inset-x-0 bottom-0 h-1 bg-gradient-to-r from-blue-500 to-purple-500 transform scale-x-0 group-hover:scale-x-100 transition-transform duration-300 ease-out origin-left"></div>
        </div>
    </c:forEach>

    <!-- Empty state nếu không có tin tức -->
    <c:if test="${empty newsList}">
        <div class="text-center py-16">
            <div class="w-24 h-24 mx-auto mb-6 bg-gray-100 rounded-full flex items-center justify-center">
                <svg class="w-12 h-12 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"/>
                </svg>
            </div>
            <h3 class="text-lg font-semibold text-gray-900 mb-2">Chưa có bài viết nào</h3>
            <p class="text-gray-500">Hiện tại chưa có bài viết nào được đăng trong danh mục này.</p>
        </div>
    </c:if>
</article>

