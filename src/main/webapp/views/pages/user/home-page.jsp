<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<article class="w-full space-y-4">
    <%--    Hiển thị danh sách bản tin ---%>
    <c:forEach var="news" items="${newsList}">
        <jsp:include page="/views/pages/user/dasboard.jsp">
            <jsp:param name="title" value="${news.title}"/>
            <jsp:param name="content" value="${news.content}"/>
            <jsp:param name="image" value="${news.image}"/>
            <jsp:param name="postDate" value="${news.postDate}"/>
            <jsp:param name="viewCount" value="${news.viewCount}"/>
            <jsp:param name="author" value="${news.author}"/>
        </jsp:include>
    </c:forEach>
</article>

