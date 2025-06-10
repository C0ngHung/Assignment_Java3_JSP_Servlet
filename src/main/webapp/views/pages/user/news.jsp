<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<article class="w-full space-y-4">
    <%--    Hiển thị danh sách bản tin ---%>
    <c:forEach var="news" items="${newsList}">
        <div class="w-full mx-auto bg-white">
            <!-- Tin tức item -->
            <div class="flex gap-4 p-4 border-b hover:bg-gray-50 transition duration-300">

                <div class="w-[120px] h-[120px] flex-shrink-0 overflow-hidden rounded-lg">
                    <a href="${pageContext.request.contextPath}/user/news-detail?id=${news.id}&categoryId=${news.categoryId}"
                       class="block w-full h-full group">
                        <img src="${pageContext.request.contextPath}/images/${news.image}"
                             alt="Ảnh minh họa"
                             class="w-full h-full object-cover transform transition-transform duration-700 ease-in-out group-hover:scale-105"/>
                    </a>
                </div>

                <div class="flex-1 space-y-2">
                    <a href="${pageContext.request.contextPath}/user/news-detail?id=${news.id}&categoryId=${news.categoryId}" class="block group">
                        <h2 class="text-lg font-semibold text-gray-800 group-hover:text-blue-600 transform transition-all duration-300 group-hover:scale-[1.02] origin-left">
                                ${news.title}
                        </h2>
                    </a>

                    <p class="text-gray-600 text-sm line-clamp-2">
                            ${news.content}
                    </p>

                    <!-- Thông tin bài viết -->
                    <c:set var="dateParts" value="${fn:split(news.postDate, '-')}"/>
                    <div class="flex items-center text-xs text-gray-500 space-x-2">
                        <span>Ngày đăng: ${dateParts[2]}/${dateParts[1]}/${dateParts[0]}</span>
                        <span class="text-gray-300">|</span>
                        <span>Tác giả: ${news.author}</span>
                        <span class="text-gray-300">|</span>
                        <span>Lượt xem: ${news.viewCount}</span>
                    </div>
                </div>
            </div>
        </div>
    </c:forEach>
</article>

