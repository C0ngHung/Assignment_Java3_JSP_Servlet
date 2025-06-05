<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<article class="w-full max-w-7xl mx-auto">
    <!-- Tin chính -->
    <div class="bg-white p-4 rounded-lg shadow-sm">
        <div class="flex gap-6">
            <!-- Ảnh bên trái -->
            <div class="w-[200px] h-[150px] flex-shrink-0 overflow-hidden rounded-lg">
                <img src="${pageContext.request.contextPath}/images/${news.image}"
                     alt="${news.title}"
                     class="w-full h-full object-cover"/>
            </div>

            <div class="flex-1">
                <!-- Tiêu đề -->
                <div class="flex justify-between items-start gap-4 mb-3">
                    <h1 class="text-xl font-bold flex-1">
                        ${news.title}
                    </h1>


                </div>

                <!-- Nội dung -->
                <div class="text-gray-600 text-sm">
                    ${news.content}
                </div>
                <%--  Thông tin bài viết--%>
                <c:set var="dateParts" value="${fn:split(news.postDate, '-')}"/>
                    <div class="text-sm text-gray-500 whitespace-nowrap">
                        Ngày đăng: ${dateParts[2]}/${dateParts[1]}/${dateParts[0]} |
                        Tác giả: ${news.author}
                    </div>
            </div>
        </div>
    </div>

    <!-- Tin cùng loại -->
    <div class="mt-8">
        <h2 class="text-lg font-bold text-blue-700 mb-4 pb-2 border-b-2 border-blue-700">
            Tin cùng loại
        </h2>

        <div class="space-y-4">
            <c:forEach var="relatedNews" items="${newsList}">
                <div class="group">
                    <a href="${pageContext.request.contextPath}/news-detail?id=${relatedNews.id}&categoryId=${relatedNews.categoryId}"
                       class="flex gap-4 p-3 hover:bg-gray-50 rounded-lg transition duration-300">
                        <div class="flex-1">
                            <h3 class="text-sm font-medium text-gray-800 group-hover:text-blue-600 line-clamp-2 mb-2">
                                ${relatedNews.title}
                            </h3>
                        </div>
                    </a>
                </div>
            </c:forEach>
        </div>
    </div>
</article>