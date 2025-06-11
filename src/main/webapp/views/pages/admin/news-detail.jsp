<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<article class="min-h-screen bg-gray-50 py-8">
    <div class="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8">
        <!-- Breadcrumb navigation -->
        <nav class="mb-8" aria-label="Breadcrumb">
            <div class="flex items-center space-x-2 text-sm text-gray-500">
                <a href="${pageContext.request.contextPath}/${sessionScope.currentUserRole}/index"
                   class="hover:text-blue-600 transition-colors duration-200">
                    <svg class="w-4 h-4 mr-1 inline" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 19l-7-7m0 0l7-7m-7 7h18"/>
                    </svg>
                    Danh sách tin tức
                </a>
                <svg class="w-4 h-4 text-gray-400" fill="currentColor" viewBox="0 0 20 20">
                    <path fill-rule="evenodd" d="M7.293 14.707a1 1 0 010-1.414L10.586 10 7.293 6.707a1 1 0 111.414-1.414l4 4a1 1 0 010 1.414l-4 4a1 1 0 01-1.414 0z" clip-rule="evenodd"/>
                </svg>
                <span class="text-gray-700 font-medium">Chi tiết bài viết</span>
            </div>
        </nav>

        <!-- Main content -->
        <div class="bg-white rounded-2xl shadow-sm border border-gray-100 overflow-hidden">
            <!-- Article header -->
            <header class="px-8 pt-8 pb-6 border-b border-gray-100">
                <h1 class="text-3xl sm:text-4xl font-bold text-gray-900 leading-tight mb-6 text-center">
                    ${news.title}
                </h1>

                <!-- Article metadata -->
                <c:set var="dateParts" value="${fn:split(news.postDate, '-')}"/>
                <div class="flex flex-wrap items-center justify-center gap-6 text-sm text-gray-600">
                    <div class="flex items-center gap-2">
                        <svg class="w-5 h-5 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z"/>
                        </svg>
                        <span class="font-medium">${dateParts[2]}/${dateParts[1]}/${dateParts[0]}</span>
                    </div>

                    <div class="flex items-center gap-2">
                        <svg class="w-5 h-5 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"/>
                        </svg>
                        <span>Tác giả: <span class="font-medium text-gray-800">${news.author}</span></span>
                    </div>

                    <div class="flex items-center gap-2">
                        <svg class="w-5 h-5 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"/>
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z"/>
                        </svg>
                        <span>${news.viewCount} lượt xem</span>
                    </div>
                </div>
            </header>

            <!-- Article content -->
            <div class="px-8 py-8">
                <div class="prose prose-lg max-w-none">
                    <!-- Chia nội dung theo khoảng trắng -->
                    <c:set var="words" value="${fn:split(news.content, ' ')}"/>
                    <c:set var="totalWords" value="${fn:length(words)}"/>
                    <c:set var="halfWords" value="${totalWords / 2}"/>

                    <!-- Phần nội dung đầu -->
                    <div class="text-gray-700 leading-8 mb-8 text-justify">
                        <p class="text-lg first-letter:text-6xl first-letter:font-bold first-letter:text-gray-900 first-letter:mr-2 first-letter:float-left first-letter:leading-[3.5rem]">
                            <c:forEach var="word" items="${words}" varStatus="status">
                                <c:if test="${status.count <= halfWords}">
                                    ${word}
                                </c:if>
                            </c:forEach>
                        </p>
                    </div>

                    <!-- Ảnh ở giữa với caption -->
                    <figure class="my-12">
                        <div class="relative group overflow-hidden rounded-xl bg-gray-100">
                            <img src="${pageContext.request.contextPath}/images/${news.image}"
                                 alt="${news.title}"
                                 class="w-full h-auto max-h-[500px] object-cover transition-transform duration-700 group-hover:scale-105"
                                 loading="lazy"/>

                            <!-- Overlay gradient for better caption readability -->
                            <div class="absolute inset-0 bg-gradient-to-t from-black/20 to-transparent opacity-0 group-hover:opacity-100 transition-opacity duration-300"></div>
                        </div>
                        <figcaption class="text-center text-sm text-gray-500 mt-4 italic">
                            Ảnh minh họa: ${news.title}
                        </figcaption>
                    </figure>

                    <!-- Phần nội dung cuối -->
                    <div class="text-gray-700 leading-8 text-justify">
                        <p class="text-lg">
                            <c:forEach var="word" items="${words}" varStatus="status">
                                <c:if test="${status.count > halfWords}">
                                    ${word}
                                </c:if>
                            </c:forEach>
                        </p>
                    </div>
                </div>
            </div>
        </div>

        <!-- Related articles section -->
        <section class="mt-12" aria-labelledby="related-news-heading">
            <div class="bg-white rounded-2xl shadow-sm border border-gray-100 p-8">
                <h2 id="related-news-heading" class="text-2xl font-bold text-gray-900 mb-6 flex items-center gap-3">
                    <svg class="w-6 h-6 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 20H5a2 2 0 01-2-2V6a2 2 0 012-2h10a2 2 0 012 2v1m2 13a2 2 0 01-2-2V7m2 13a2 2 0 002-2V9a2 2 0 00-2-2h-2m-4-3H9M7 16h6M7 8h6v4H7V8z"/>
                    </svg>
                    Tin cùng loại
                </h2>

                <div class="grid gap-4 sm:grid-cols-2 lg:grid-cols-3">
                    <c:forEach var="relatedNews" items="${newsList}">
                        <c:if test="${relatedNews.id != news.id}">
                            <article class="group relative bg-gray-50 hover:bg-blue-50 rounded-xl p-4 transition-all duration-300 hover:shadow-md border border-transparent hover:border-blue-100">
                                <a href="${pageContext.request.contextPath}/${sessionScope.currentUserRole}/news-detail?id=${relatedNews.id}&categoryId=${relatedNews.categoryId}"
                                   class="block">
                                    <h3 class="font-semibold text-gray-900 group-hover:text-blue-700 line-clamp-2 leading-tight mb-2 transition-colors duration-200">
                                        ${relatedNews.title}
                                    </h3>

                                    <p class="text-sm text-gray-600 line-clamp-2 mb-3">
                                        ${relatedNews.content}
                                    </p>

                                    <div class="flex items-center justify-between text-xs text-gray-500">
                                        <span>${relatedNews.author}</span>
                                        <span class="flex items-center gap-1">
                                            <svg class="w-3 h-3" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"/>
                                            </svg>
                                            ${relatedNews.viewCount}
                                        </span>
                                    </div>

                                    <!-- Read more indicator -->
                                    <div class="absolute top-4 right-4 opacity-0 group-hover:opacity-100 transition-opacity duration-200">
                                        <svg class="w-4 h-4 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7"/>
                                        </svg>
                                    </div>
                                </a>
                            </article>
                        </c:if>
                    </c:forEach>
                </div>

                <!-- Empty state for related articles -->
                <c:if test="${fn:length(newsList) <= 1}">
                    <div class="text-center py-8">
                        <div class="w-16 h-16 mx-auto mb-4 bg-gray-100 rounded-full flex items-center justify-center">
                            <svg class="w-8 h-8 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"/>
                            </svg>
                        </div>
                        <p class="text-gray-500">Hiện tại chưa có bài viết liên quan nào khác.</p>
                    </div>
                </c:if>
            </div>
        </section>

        <!-- Back to top button -->
        <div class="fixed bottom-8 right-8 z-50">
            <button onclick="window.scrollTo({top: 0, behavior: 'smooth'})"
                    class="bg-blue-600 hover:bg-blue-700 text-white p-3 rounded-full shadow-lg hover:shadow-xl transition-all duration-300 group"
                    title="Lên đầu trang"
                    aria-label="Cuộn lên đầu trang">
                <svg class="w-5 h-5 transition-transform duration-200 group-hover:-translate-y-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 10l7-7m0 0l7 7m-7-7v18"/>
                </svg>
            </button>
        </div>
    </div>
</article>