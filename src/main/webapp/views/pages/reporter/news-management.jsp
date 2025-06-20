<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
    <title>Quản Lý Tin Tức</title>
</head>
<body class="bg-gradient-to-br from-slate-50 via-blue-50 to-indigo-50 min-h-screen">
<!-- Header Section -->
<header class="bg-white/80 backdrop-blur-md shadow-sm border-b border-gray-200/50 sticky top-0 z-20">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-6">
        <div class="flex items-center justify-between">
            <div class="flex items-center space-x-4">
                <div class="bg-gradient-to-br from-blue-600 to-indigo-700 p-3 rounded-2xl shadow-lg">
                    <svg xmlns="http://www.w3.org/2000/svg" class="h-8 w-8 text-white" fill="none" viewBox="0 0 24 24"
                         stroke="currentColor">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                              d="M19 20H5a2 2 0 01-2-2V6a2 2 0 012-2h10a2 2 0 012 2v1m2 13a2 2 0 01-2-2V7m2 13a2 2 0 002-2V9a2 2 0 00-2-2h-2m-4-3H9M7 16h6M7 8h6v4H7V8z"/>
                    </svg>
                </div>
                <div>
                    <h1 class="text-3xl font-bold bg-gradient-to-r from-gray-900 to-gray-700 bg-clip-text text-transparent">
                        Quản Lý Tin Tức
                    </h1>
                    <p class="text-gray-600 mt-1">Tạo, chỉnh sửa và quản lý các bài viết tin tức</p>
                </div>
            </div>

            <!-- Stats -->
            <div class="hidden md:flex items-center space-x-6">
                <div class="text-center bg-gradient-to-br from-blue-50 to-indigo-50 px-4 py-3 rounded-xl">
                    <div class="text-2xl font-bold text-blue-600">${fn:length(newsList)}</div>
                    <div class="text-xs text-gray-500 font-medium">Bài viết</div>
                </div>
                <div class="text-center bg-gradient-to-br from-emerald-50 to-green-50 px-4 py-3 rounded-xl">
                    <div class="text-2xl font-bold text-emerald-600">${fn:length(categoryList)}</div>
                    <div class="text-xs text-gray-500 font-medium">Danh mục</div>
                </div>
            </div>
        </div>
    </div>
</header>

<main class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
    <div class="grid grid-cols-1 xl:grid-cols-3 gap-8">
        <!-- Form Section -->
        <div class="xl:col-span-1">
            <div class="bg-white/90 backdrop-blur-md rounded-2xl shadow-xl border border-white/20 overflow-hidden sticky top-24">
                <!-- Form Header -->
                <div class="bg-gradient-to-r from-blue-600 via-blue-700 to-indigo-700 px-6 py-5">
                    <h2 class="text-xl font-bold text-white flex items-center">
                        <div class="bg-white/20 p-2 rounded-lg mr-3">
                            <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24"
                                 stroke="currentColor">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                      d="M12 6v6m0 0v6m0-6h6m-6 0H6"/>
                            </svg>
                        </div>
                        Thông Tin Bài Viết
                    </h2>
                </div>

                <!-- Form Content -->
                <div class="p-6">
                    <form action="${pageContext.request.contextPath}/reporter/news-management" method="post"
                          enctype="multipart/form-data" class="space-y-6">
                        <!-- ID Field -->
                        <div class="space-y-2">
                            <label class="block text-sm font-semibold text-gray-700 flex items-center">
                                <svg class="w-4 h-4 mr-2 text-indigo-600" fill="none" stroke="currentColor"
                                     viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                          d="M7 7h.01M7 3h5c.512 0 1.024.195 1.414.586l7 7a2 2 0 010 2.828l-7 7a2 2 0 01-2.828 0l-7-7A1.994 1.994 0 013 12V7a4 4 0 014-4z"/>
                                </svg>
                                ID <span class="text-red-500">*</span>
                            </label>
                            <div class="relative group">
                                <c:choose>
                                    <c:when test="${not empty editNews}">
                                        <!-- Edit mode - readonly -->
                                        <input type="text" name="id" value="${editNews.id}" readonly
                                               class="w-full px-4 py-3 border-2 border-gray-200 rounded-xl bg-gray-50/50 cursor-not-allowed text-gray-600 font-medium"/>
                                        <div class="absolute inset-y-0 right-0 flex items-center pr-3 pointer-events-none">
                                            <svg class="w-5 h-5 text-gray-400" fill="none" stroke="currentColor"
                                                 viewBox="0 0 24 24">
                                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                                      d="M12 15v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2zm10-10V7a4 4 0 00-8 0v4h8z"/>
                                            </svg>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <!-- Create mode - editable -->
                                        <input type="text" name="id" required
                                               class="w-full px-4 py-3 border-2 border-gray-200 rounded-xl focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500 transition-all duration-300 bg-gray-50/50 group-hover:bg-white"
                                               placeholder="Nhập ID bài viết..."/>
                                        <div class="absolute inset-y-0 right-0 flex items-center pr-3 pointer-events-none">
                                            <svg class="w-5 h-5 text-gray-400 group-focus-within:text-indigo-500 transition-colors"
                                                 fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                                      d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z"/>
                                            </svg>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>

                        <!-- Title -->
                        <div class="space-y-2">
                            <label class="block text-sm font-semibold text-gray-700 flex items-center">
                                <svg class="w-4 h-4 mr-2 text-blue-600" fill="none" stroke="currentColor"
                                     viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                          d="M7 7h.01M7 3h5c.512 0 1.024.195 1.414.586l7 7a2 2 0 010 2.828l-7 7a2 2 0 01-2.828 0l-7-7A1.994 1.994 0 013 12V7a4 4 0 014-4z"/>
                                </svg>
                                Tiêu đề <span class="text-red-500">*</span>
                            </label>
                            <div class="relative group">
                                <input type="text" name="title" required
                                       class="w-full px-4 py-3 border-2 border-gray-200 rounded-xl focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition-all duration-300 bg-gray-50/50 group-hover:bg-white"
                                       placeholder="Nhập tiêu đề bài viết..." value="${editNews.title}"/>
                                <div class="absolute inset-y-0 right-0 flex items-center pr-3 pointer-events-none">
                                    <svg class="w-5 h-5 text-gray-400 group-focus-within:text-blue-500 transition-colors"
                                         fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                              d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z"/>
                                    </svg>
                                </div>
                            </div>
                        </div>

                        <!-- Post Date -->
                        <div class="space-y-2">
                            <label class="block text-sm font-semibold text-gray-700 flex items-center">
                                <svg class="w-4 h-4 mr-2 text-emerald-600" fill="none" stroke="currentColor"
                                     viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                          d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z"/>
                                </svg>
                                Ngày đăng <span class="text-red-500">*</span>
                            </label>
                            <input type="date" name="postDate" required
                                   class="w-full px-4 py-3 border-2 border-gray-200 rounded-xl focus:ring-2 focus:ring-emerald-500 focus:border-emerald-500 transition-all duration-300 bg-gray-50/50 hover:bg-white"
                                   value="<fmt:formatDate value="${editNews.postDate}" pattern="yyyy-MM-dd"/>"/>
                        </div>

                        <!-- View Count -->
                        <div class="space-y-2">
                            <label class="block text-sm font-semibold text-gray-700 flex items-center">
                                <svg class="w-4 h-4 mr-2 text-purple-600" fill="none" stroke="currentColor"
                                     viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                          d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"/>
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                          d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z"/>
                                </svg>
                                Lượt xem
                            </label>
                            <input type="number" name="viewCount" min="0"
                                   class="w-full px-4 py-3 border-2 border-gray-200 rounded-xl focus:ring-2 focus:ring-purple-500 focus:border-purple-500 transition-all duration-300 bg-gray-50/50 hover:bg-white"
                                   placeholder="0" value="${editNews.viewCount}"/>
                        </div>

                        <!-- Category -->
                        <div class="space-y-2">
                            <label class="block text-sm font-semibold text-gray-700 flex items-center">
                                <svg class="w-4 h-4 mr-2 text-orange-600" fill="none" stroke="currentColor"
                                     viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                          d="M7 7h.01M7 3h5c.512 0 1.024.195 1.414.586l7 7a2 2 0 010 2.828l-7 7a2 2 0 01-2.828 0l-7-7A1.994 1.994 0 013 12V7a4 4 0 014-4z"/>
                                </svg>
                                Loại Tin <span class="text-red-500">*</span>
                            </label>
                            <div class="relative">
                                <select name="categoryId" required
                                        class="w-full px-4 py-3 border-2 border-gray-200 rounded-xl focus:ring-2 focus:ring-orange-500 focus:border-orange-500 transition-all duration-300 bg-gray-50/50 hover:bg-white appearance-none">
                                    <option value="">Chọn loại tin...</option>
                                    <c:forEach items="${categoryList}" var="cat">
                                        <option value="${cat.id}" ${cat.id == editNews.categoryId ? "selected" : ""}>${cat.name}</option>
                                    </c:forEach>
                                </select>
                                <div class="absolute inset-y-0 right-0 flex items-center pr-3 pointer-events-none">
                                    <svg class="w-5 h-5 text-gray-400" fill="none" stroke="currentColor"
                                         viewBox="0 0 24 24">
                                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                              d="M19 9l-7 7-7-7"/>
                                    </svg>
                                </div>
                            </div>
                        </div>

                        <!-- Image Upload -->
                        <div class="space-y-2">
                            <label class="block text-sm font-semibold text-gray-700 flex items-center">
                                <svg class="w-4 h-4 mr-2 text-indigo-600" fill="none" stroke="currentColor"
                                     viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                          d="M4 16l4.586-4.586a2 2 0 012.828 0L16 16m-2-2l1.586-1.586a2 2 0 012.828 0L20 14m-6-6h.01M6 20h12a2 2 0 002-2V6a2 2 0 00-2-2H6a2 2 0 00-2 2v12a2 2 0 002 2z"/>
                                </svg>
                                Hình ảnh
                            </label>

                            <!-- Nếu có editNews và có ảnh -->
                            <c:if test="${not empty editNews and not empty editNews.image}">
                                <div class="relative group w-full">
                                    <label for="imageInput" class="cursor-pointer block">
                                        <div class="relative overflow-hidden rounded-xl border-2 border-gray-200 bg-gradient-to-br from-gray-50 to-gray-100 group-hover:border-indigo-300 transition-all duration-300">
                                            <img id="imagePreview"
                                                 src="${pageContext.request.contextPath}/images/${editNews.image}"
                                                 alt="Ảnh bài viết"
                                                 class="w-full h-48 object-cover group-hover:scale-105 transition-transform duration-500"/>
                                            <div class="absolute inset-0 bg-gradient-to-t from-black/50 via-transparent to-transparent opacity-0 group-hover:opacity-100 transition-all duration-300 flex items-end justify-center pb-4">
                                                    <span class="bg-white/90 text-gray-800 text-sm px-4 py-2 rounded-lg font-medium backdrop-blur-sm border border-white/20">
                                                        <svg class="w-4 h-4 inline mr-2" fill="none"
                                                             stroke="currentColor" viewBox="0 0 24 24">
                                                            <path stroke-linecap="round" stroke-linejoin="round"
                                                                  stroke-width="2"
                                                                  d="M3 9a2 2 0 012-2h.93a2 2 0 001.664-.89l.812-1.22A2 2 0 0110.07 4h3.86a2 2 0 011.664.89l.812 1.22A2 2 0 0018.07 7H19a2 2 0 012 2v9a2 2 0 01-2 2H5a2 2 0 01-2-2V9z"/>
                                                            <path stroke-linecap="round" stroke-linejoin="round"
                                                                  stroke-width="2"
                                                                  d="M15 13a3 3 0 11-6 0 3 3 0 016 0z"/>
                                                        </svg>
                                                        Thay đổi ảnh
                                                    </span>
                                            </div>
                                        </div>
                                    </label>
                                </div>
                            </c:if>

                            <!-- Nếu không có editNews hoặc không có ảnh -->
                            <c:if test="${empty editNews or empty editNews.image}">
                                <div id="uploadPlaceholder"
                                     class="group border-2 border-dashed border-gray-300 rounded-xl p-8 text-center hover:border-indigo-400 hover:bg-gradient-to-br hover:from-indigo-50 hover:to-blue-50 transition-all duration-300 cursor-pointer">
                                    <label for="imageInput" class="cursor-pointer block">
                                        <div class="space-y-4">
                                            <div class="bg-gradient-to-br from-indigo-100 to-blue-100 rounded-full w-16 h-16 flex items-center justify-center mx-auto group-hover:scale-110 transition-transform duration-300">
                                                <svg xmlns="http://www.w3.org/2000/svg" class="h-8 w-8 text-indigo-600"
                                                     fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                                    <path stroke-linecap="round" stroke-linejoin="round"
                                                          stroke-width="2"
                                                          d="M7 16a4 4 0 01-.88-7.903A5 5 0 1115.9 6L16 6a5 5 0 011 9.9M15 13l-3-3m0 0l-3 3m3-3v12"/>
                                                </svg>
                                            </div>
                                            <div>
                                                <p class="text-sm font-semibold text-gray-700">Nhấn để chọn ảnh</p>
                                                <p class="text-xs text-gray-500 mt-1">PNG, JPG, GIF tối đa 10MB</p>
                                            </div>
                                        </div>
                                    </label>
                                </div>
                                <div id="previewContainer" class="hidden w-full">
                                    <div class="relative overflow-hidden rounded-xl border-2 border-emerald-200 bg-gradient-to-br from-emerald-50 to-green-50">
                                        <img id="imagePreview" src="#" alt="Ảnh xem trước"
                                             class="w-full h-48 object-cover"/>
                                        <div class="absolute top-3 right-3 bg-emerald-100 text-emerald-800 px-3 py-1 rounded-lg text-xs font-bold border border-emerald-200">
                                            <svg class="w-3 h-3 inline mr-1" fill="none" stroke="currentColor"
                                                 viewBox="0 0 24 24">
                                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                                      d="M5 13l4 4L19 7"/>
                                            </svg>
                                            Ảnh mới
                                        </div>
                                    </div>
                                </div>
                            </c:if>

                            <!-- Input file luôn có mặt -->
                            <input type="file" name="image" accept="image/*" class="hidden" id="imageInput"
                                   onchange="previewImage(event)"/>
                        </div>

                        <!-- Content -->
                        <div class="space-y-2">
                            <label class="block text-sm font-semibold text-gray-700 flex items-center">
                                <svg class="w-4 h-4 mr-2 text-teal-600" fill="none" stroke="currentColor"
                                     viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                          d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"/>
                                </svg>
                                Nội dung <span class="text-red-500">*</span>
                            </label>
                            <div class="relative">
                                    <textarea name="content" rows="6" required
                                              class="w-full px-4 py-3 border-2 border-gray-200 rounded-xl focus:ring-2 focus:ring-teal-500 focus:border-teal-500 resize-none transition-all duration-300 bg-gray-50/50 hover:bg-white"
                                              placeholder="Nhập nội dung bài viết..."><c:out
                                            value="${editNews.content}"/></textarea>
                                <div class="absolute bottom-3 right-3 text-xs text-gray-400 bg-white/80 px-2 py-1 rounded-md">
                                    <svg class="w-3 h-3 inline mr-1" fill="none" stroke="currentColor"
                                         viewBox="0 0 24 24">
                                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                              d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z"/>
                                    </svg>
                                    Nội dung
                                </div>
                            </div>
                        </div>

                        <!-- Action Buttons -->
                        <div class="pt-6 border-t border-gray-200/60">
                            <div class="grid grid-cols-2 gap-3">
                                <button type="submit" name="action" value="create"
                                        class="group flex items-center justify-center px-4 py-3 bg-gradient-to-r from-blue-600 to-blue-700 text-white rounded-xl hover:from-blue-700 hover:to-blue-800 focus:ring-4 focus:ring-blue-200 transition-all duration-300 font-semibold shadow-lg hover:shadow-xl transform hover:-translate-y-0.5">
                                    <svg class="w-5 h-5 mr-2 group-hover:rotate-90 transition-transform duration-300"
                                         fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                              d="M12 6v6m0 0v6m0-6h6m-6 0H6"/>
                                    </svg>
                                    Tạo mới
                                </button>

                                <button type="submit" name="action" value="update"
                                        class="group flex items-center justify-center px-4 py-3 bg-gradient-to-r from-emerald-600 to-green-700 text-white rounded-xl hover:from-emerald-700 hover:to-green-800 focus:ring-4 focus:ring-emerald-200 transition-all duration-300 font-semibold shadow-lg hover:shadow-xl transform hover:-translate-y-0.5">
                                    <svg class="w-5 h-5 mr-2 group-hover:scale-110 transition-transform duration-300"
                                         fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                              d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z"/>
                                    </svg>
                                    Cập nhật
                                </button>

                                <button type="submit" name="action" value="delete"
                                        class="group flex items-center justify-center px-4 py-3 bg-gradient-to-r from-red-600 to-red-700 text-white rounded-xl hover:from-red-700 hover:to-red-800 focus:ring-4 focus:ring-red-200 transition-all duration-300 font-semibold shadow-lg hover:shadow-xl transform hover:-translate-y-0.5">
                                    <svg class="w-5 h-5 mr-2 group-hover:scale-110 transition-transform duration-300"
                                         fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                              d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"/>
                                    </svg>
                                    Xóa
                                </button>

                                <a href="${pageContext.request.contextPath}/reporter/news-management?action=reset"
                                        class="group flex items-center justify-center px-4 py-3 bg-gradient-to-r from-gray-600 to-gray-700 text-white rounded-xl hover:from-gray-700 hover:to-gray-800 focus:ring-4 focus:ring-gray-200 transition-all duration-300 font-semibold shadow-lg hover:shadow-xl transform hover:-translate-y-0.5">
                                    <svg class="w-5 h-5 mr-2 group-hover:rotate-180 transition-transform duration-500"
                                         fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                              d="M4 4v5h.582m15.356 2A8.001 8.001 0 004.582 9m0 0H9m11 11v-5h-.581m0 0a8.003 8.003 0 01-15.357-2m15.357 2H15"/>
                                    </svg>
                                    Làm mới
                                </a>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <!-- News List Section -->
        <div class="xl:col-span-2">
            <div class="bg-white/90 backdrop-blur-md rounded-2xl shadow-xl border border-white/20 overflow-hidden">
                <!-- List Header -->
                <div class="bg-gradient-to-r from-slate-50 to-gray-100 px-6 py-5 border-b border-gray-200/50">
                    <div class="flex items-center justify-between">
                        <h2 class="text-xl font-bold text-gray-900 flex items-center">
                            <div class="bg-gradient-to-br from-blue-100 to-indigo-100 p-2 rounded-lg mr-3">
                                <svg class="w-5 h-5 text-blue-600" fill="none" stroke="currentColor"
                                     viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                          d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"/>
                                </svg>
                            </div>
                            Danh Sách Tin Tức
                        </h2>
                        <div class="flex items-center space-x-3">
                                <span class="bg-gradient-to-r from-blue-500 to-indigo-600 text-white px-4 py-2 rounded-full text-sm font-bold shadow-lg">
                                    ${fn:length(newsList)} bài viết
                                </span>
                        </div>
                    </div>
                </div>

                <!-- List Content -->
                <div class="p-6">
                    <c:if test="${empty newsList}">
                        <div class="text-center py-20">
                            <div class="bg-gradient-to-br from-gray-100 to-gray-200 rounded-full w-24 h-24 flex items-center justify-center mx-auto mb-6 shadow-inner">
                                <svg xmlns="http://www.w3.org/2000/svg" class="h-12 w-12 text-gray-400" fill="none"
                                     viewBox="0 0 24 24" stroke="currentColor">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                          d="M19 20H5a2 2 0 01-2-2V6a2 2 0 012-2h10a2 2 0 012 2v1m2 13a2 2 0 01-2-2V7m2 13a2 2 0 002-2V9a2 2 0 00-2-2h-2m-4-3H9M7 16h6M7 8h6v4H7V8z"/>
                                </svg>
                            </div>
                            <h3 class="text-xl font-bold text-gray-700 mb-3">Chưa có tin tức nào</h3>
                            <p class="text-gray-500 mb-6 max-w-sm mx-auto">Hãy tạo bài viết đầu tiên của bạn để bắt đầu
                                chia sẻ những câu chuyện thú vị!</p>
                            <div class="inline-flex items-center px-6 py-3 bg-gradient-to-r from-blue-600 to-indigo-600 text-white rounded-xl font-semibold shadow-lg hover:shadow-xl transform hover:-translate-y-1 transition-all duration-300 cursor-pointer">
                                <svg class="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                          d="M12 6v6m0 0v6m0-6h6m-6 0H6"/>
                                </svg>
                                Tạo bài viết đầu tiên
                            </div>
                        </div>
                    </c:if>

                    <c:if test="${not empty newsList}">
                        <div class="space-y-6">
                            <c:forEach var="news" items="${newsList}" varStatus="status">
                                <article
                                        class="group bg-gradient-to-br from-white to-gray-50 border-2 border-gray-100 rounded-2xl p-6 hover:border-blue-200 hover:shadow-xl transition-all duration-300 relative overflow-hidden">
                                    <!-- Background decoration -->
                                    <div class="absolute top-0 right-0 w-20 h-20 bg-gradient-to-br from-blue-100/20 to-indigo-100/20 rounded-bl-full transform translate-x-10 -translate-y-10 group-hover:scale-150 transition-transform duration-500"></div>

                                    <div class="flex gap-6 relative z-10">
                                        <!-- Image -->
                                        <div class="flex-shrink-0">
                                            <div class="w-28 h-28 bg-gradient-to-br from-gray-100 to-gray-200 rounded-2xl overflow-hidden border-2 border-gray-200 group-hover:border-blue-300 transition-all duration-300 shadow-md">
                                                <img src="${pageContext.request.contextPath}/images/${not empty news.image ? news.image : 'default.png'}"
                                                     alt="${news.title}"
                                                     class="w-full h-full object-cover group-hover:scale-110 transition-transform duration-500"/>
                                            </div>
                                        </div>

                                        <!-- Content -->
                                        <div class="flex-1 min-w-0">
                                            <div class="flex items-start justify-between mb-3">
                                                <h3 class="text-lg font-bold text-gray-900 group-hover:text-blue-700 transition-colors duration-300 line-clamp-2 leading-tight">
                                                        ${news.title}
                                                </h3>
                                                <span class="flex-shrink-0 bg-gradient-to-r from-gray-100 to-gray-200 text-gray-600 text-sm px-3 py-1 rounded-full font-semibold ml-4">
                                                        #${status.count}
                                                    </span>
                                            </div>

                                            <p class="text-gray-600 text-sm leading-relaxed mb-4 line-clamp-2">
                                                    ${fn:substring(news.content, 0, 120)}
                                                    <c:if test="${fn:length(news.content) > 120}">...</c:if>
                                            </p>

                                            <!-- Metadata -->
                                            <div class="flex flex-wrap items-center gap-4 text-xs mb-4">
                                                <div class="flex items-center bg-blue-50 text-blue-700 px-3 py-2 rounded-lg font-medium">
                                                    <svg class="w-4 h-4 mr-2" fill="none" stroke="currentColor"
                                                         viewBox="0 0 24 24">
                                                        <path stroke-linecap="round" stroke-linejoin="round"
                                                              stroke-width="2"
                                                              d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z"/>
                                                    </svg>
                                                    <fmt:formatDate value="${news.postDate}" pattern="dd/MM/yyyy"/>
                                                </div>

                                                <div class="flex items-center bg-emerald-50 text-emerald-700 px-3 py-2 rounded-lg font-medium">
                                                    <svg class="w-4 h-4 mr-2" fill="none" stroke="currentColor"
                                                         viewBox="0 0 24 24">
                                                        <path stroke-linecap="round" stroke-linejoin="round"
                                                              stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"/>
                                                        <path stroke-linecap="round" stroke-linejoin="round"
                                                              stroke-width="2"
                                                              d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z"/>
                                                    </svg>
                                                        ${news.viewCount} lượt xem
                                                </div>

                                                <div class="flex items-center bg-purple-50 text-purple-700 px-3 py-2 rounded-lg font-medium">
                                                    <svg class="w-4 h-4 mr-2" fill="none" stroke="currentColor"
                                                         viewBox="0 0 24 24">
                                                        <path stroke-linecap="round" stroke-linejoin="round"
                                                              stroke-width="2"
                                                              d="M7 7h.01M7 3h5c.512 0 1.024.195 1.414.586l7 7a2 2 0 010 2.828l-7 7a2 2 0 01-2.828 0l-7-7A1.994 1.994 0 013 12V7a4 4 0 014-4z"/>
                                                    </svg>
                                                    <c:forEach var="cat" items="${categoryList}">
                                                        <c:if test="${cat.id == news.categoryId}">${cat.name}</c:if>
                                                    </c:forEach>
                                                </div>
                                            </div>

                                            <!-- Action Button -->
                                            <div class="flex justify-end">
                                                <form action="${pageContext.request.contextPath}/reporter/news-management"
                                                      method="post" class="inline">
                                                    <input type="hidden" name="id" value="${news.id}"/>
                                                    <button type="submit" name="action" value="edit"
                                                            class="group/btn inline-flex items-center px-5 py-2.5 bg-gradient-to-r from-blue-600 to-blue-700 hover:from-blue-700 hover:to-blue-800 text-white text-sm font-semibold rounded-xl transition-all duration-300 shadow-lg hover:shadow-xl transform hover:-translate-y-1 focus:ring-4 focus:ring-blue-200">
                                                        <svg class="w-4 h-4 mr-2 group-hover/btn:rotate-12 transition-transform duration-300"
                                                             fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                                            <path stroke-linecap="round" stroke-linejoin="round"
                                                                  stroke-width="2"
                                                                  d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z"/>
                                                        </svg>
                                                        Chỉnh sửa
                                                    </button>
                                                </form>
                                            </div>
                                        </div>
                                    </div>

                                    <!-- Bottom accent line -->
                                    <div class="absolute bottom-0 left-0 right-0 h-1 bg-gradient-to-r from-blue-500 via-purple-500 to-indigo-500 transform scale-x-0 group-hover:scale-x-100 transition-transform duration-500 origin-left rounded-b-2xl"></div>
                                </article>
                            </c:forEach>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</main>

<script>
    function previewImage(event) {
        const file = event.target.files[0];
        const uploadPlaceholder = document.getElementById('uploadPlaceholder');
        const previewContainer = document.getElementById('previewContainer');
        const imagePreview = document.getElementById('imagePreview');

        if (file) {
            const reader = new FileReader();
            reader.onload = function (e) {
                imagePreview.src = e.target.result;
                if (uploadPlaceholder) {
                    uploadPlaceholder.classList.add('hidden');
                    previewContainer.classList.remove('hidden');
                }
            };
            reader.readAsDataURL(file);
        } else {
            if (uploadPlaceholder) {
                uploadPlaceholder.classList.remove('hidden');
                previewContainer.classList.add('hidden');
            }
        }
    }
</script>
</body>
</html>