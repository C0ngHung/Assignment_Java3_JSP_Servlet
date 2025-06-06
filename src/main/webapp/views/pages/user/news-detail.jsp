<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<article class="w-full max-w-7xl mx-auto p-4">
    <!-- Nội dung bài viết -->
    <div class="bg-white rounded-lg">
        <!-- Tiêu đề -->
        <h1 class="text-xl font-bold text-black-600 mb-4 flex items-center justify-center">
            ${news.title}
        </h1>

        <div class="flex gap-4">
            <!-- Ảnh -->
            <div class="w-[200px] h-[150px] flex-shrink-0 bg-blue-400 rounded-lg overflow-hidden">
                <img src="${pageContext.request.contextPath}/images/${news.image}"
                     alt="${news.title}"
                     class="w-full h-full object-cover"/>
            </div>

            <!-- Nội dung -->
            <div class="flex-1">
                <div class="text-gray-600 text-sm">
                    ${news.content}
                </div>
            </div>
        </div>

        <!-- Thông tin bài viết -->
        <c:set var="dateParts" value="${fn:split(news.postDate, '-')}"/>
        <div class="text-sm text-gray-500 mt-3 text-right italic">
            Ngày đăng: ${dateParts[2]}/${dateParts[1]}/${dateParts[0]} | Tác giả: ${news.author}
        </div>
    </div>

    <!-- Tin cùng loại -->
    <div class="mt-8">
        <h2 class="text-lg font-bold text-black-600 mb-4">
            Tin cùng loại
        </h2>

        <ul class="list-disc list-inside space-y-2 text-blue-600">
            <c:forEach var="relatedNews" items="${newsList}">
                <li>
                    <a href="${pageContext.request.contextPath}/news-detail?id=${relatedNews.id}&categoryId=${relatedNews.categoryId}"
                       class="hover:underline">
                        ${relatedNews.title}
                    </a>
                </li>
            </c:forEach>
        </ul>
    </div>
</article>