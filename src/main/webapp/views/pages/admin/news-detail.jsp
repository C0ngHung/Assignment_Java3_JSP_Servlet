<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<article class="w-full max-w-7xl mx-auto p-4">
    <!-- Nội dung bài viết -->
    <div class="bg-white rounded-lg">
        <!-- Tiêu đề -->
        <h1 class="text-xl font-bold mb-4 flex items-center justify-center"> ${news.title} </h1>
        <div class="space-y-4">
            <!-- Chia nội dung theo khoảng trắng -->
            <c:set var="words" value="${fn:split(news.content, ' ')}"/>
            <c:set var="totalWords" value="${fn:length(words)}"/>
            <c:set var="halfWords" value="${totalWords / 2}"/>
            <!-- Phần nội dung đầu -->
            <div class="text-sm leading-5">
                <c:forEach var="word" items="${words}" varStatus="status">
                    <c:if test="${status.count <= halfWords}">
                        ${word}
                    </c:if>
                </c:forEach>
            </div>
            <!-- Ảnh ở giữa -->
            <div class="w-full h-[400px] flex items-center justify-center overflow-hidden rounded-lg my-6">
                <img src="${pageContext.request.contextPath}/images/${news.image}" alt="${news.title}"
                     class="w-full h-full object-cover"/>
            </div>
            <!-- Phần nội dung cuối -->
            <div class="text-sm leading-5">
                <c:forEach var="word" items="${words}" varStatus="status">
                    <c:if test="${status.count > halfWords}">
                        ${word}
                    </c:if>
                </c:forEach>
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
        <h2 class="text-lg font-bold text-black-600 mb-4"> Tin cùng loại </h2>
        <ul class="list-disc list-inside space-y-2 text-blue-600">
            <c:forEach var="relatedNews" items="${newsList}">
                <li>
                    <a href="${pageContext.request.contextPath}/${sessionScope.currentUserRole}/news-detail?id=${relatedNews.id}&categoryId=${relatedNews.categoryId}"
                       class="hover:underline"> ${relatedNews.title} </a>
                </li>
            </c:forEach>
        </ul>
    </div>
</article>