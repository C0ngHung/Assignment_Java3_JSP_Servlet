<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
    <script>
        tailwind.config = {
            theme: {
                extend: {
                    animation: {
                        'fade-in': 'fadeIn 0.6s ease-out',
                        'slide-up': 'slideUp 0.4s ease-out',
                        'bounce-gentle': 'bounceGentle 2s infinite',
                        'toast-slide-in': 'toastSlideIn 0.3s ease-out',
                        'toast-slide-out': 'toastSlideOut 0.3s ease-in',
                    }
                }
            }
        }
    </script>
    <style>
        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(20px); }
            to { opacity: 1; transform: translateY(0); }
        }
        @keyframes slideUp {
            from { opacity: 0; transform: translateY(30px); }
            to { opacity: 1; transform: translateY(0); }
        }
        @keyframes bounceGentle {
            0%, 100% { transform: translateY(0); }
            50% { transform: translateY(-5px); }
        }
        @keyframes toastSlideIn {
            from { transform: translateX(100%); opacity: 0; }
            to { transform: translateX(0); opacity: 1; }
        }
        @keyframes toastSlideOut {
            from { transform: translateX(0); opacity: 1; }
            to { transform: translateX(100%); opacity: 0; }
        }
    </style>
    <title>Quản Lý Newsletter</title>
</head>
<body class="bg-gradient-to-br from-slate-50 via-blue-50 to-indigo-50 min-h-screen">

<!-- Toast Container -->
<div id="toast-container" class="fixed top-4 right-4 z-50 space-y-2"></div>

<!-- Header Section -->
<header class="bg-white/80 backdrop-blur-md shadow-sm border-b border-gray-200/50 sticky top-0 z-20">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-6">
        <div class="flex items-center justify-between">
            <div class="flex items-center space-x-4">
                <div class="bg-gradient-to-br from-emerald-600 to-teal-700 p-3 rounded-2xl shadow-lg animate-bounce-gentle">
                    <svg xmlns="http://www.w3.org/2000/svg" class="h-8 w-8 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 8l7.89 4.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z"/>
                    </svg>
                </div>
                <div>
                    <h1 class="text-3xl font-bold bg-gradient-to-r from-gray-900 to-gray-700 bg-clip-text text-transparent">
                        Quản Lý Newsletter
                    </h1>
                    <p class="text-gray-600 mt-1">Quản lý danh sách email đăng ký nhận tin tức</p>
                </div>
            </div>
            
            <!-- Stats -->
            <div class="hidden md:flex items-center space-x-6">
                <div class="text-center bg-gradient-to-br from-emerald-50 to-teal-50 px-4 py-3 rounded-xl shadow-sm">
                    <div class="text-2xl font-bold text-emerald-600">
                        <c:set var="enabledCount" value="0"/>
                        <c:forEach var="newsletter" items="${newsletterList}">
                            <c:if test="${newsletter.enable}">
                                <c:set var="enabledCount" value="${enabledCount + 1}"/>
                            </c:if>
                        </c:forEach>
                        ${enabledCount}
                    </div>
                    <div class="text-xs text-gray-500 font-medium">Email hoạt động</div>
                </div>
                <div class="text-center bg-gradient-to-br from-orange-50 to-red-50 px-4 py-3 rounded-xl shadow-sm">
                    <div class="text-2xl font-bold text-orange-600">${newsletterList.size()}</div>
                    <div class="text-xs text-gray-500 font-medium">Tổng email</div>
                </div>
            </div>
        </div>
    </div>
</header>

<main class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
    <div class="grid grid-cols-1 lg:grid-cols-3 gap-8">
        
        <!-- Form Section -->
        <div class="lg:col-span-1">
            <div class="bg-white/90 backdrop-blur-md rounded-2xl shadow-xl border border-white/20 overflow-hidden sticky top-24 animate-fade-in">
                <!-- Form Header -->
                <div class="bg-gradient-to-r from-emerald-600 via-emerald-700 to-teal-700 px-6 py-5">
                    <h2 class="text-xl font-bold text-white flex items-center">
                        <div class="bg-white/20 p-2 rounded-lg mr-3">
                            <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 6v6m0 0v6m0-6h6m-6 0H6"/>
                            </svg>
                        </div>
                        <c:choose>
                            <c:when test="${editNewsletter != null}">Chỉnh Sửa Email</c:when>
                            <c:otherwise>Thêm Email Mới</c:otherwise>
                        </c:choose>
                    </h2>
                </div>

                <!-- Form Content -->
                <div class="p-6">
                    <form action="${pageContext.request.contextPath}/admin/newsletter" method="post" class="space-y-6">
                        <input type="hidden" name="action" value="${editNewsletter != null ? 'update' : 'create'}" />

                        <!-- Email -->
                        <div class="space-y-2">
                            <label class="block text-sm font-semibold text-gray-700 flex items-center">
                                <svg class="w-4 h-4 mr-2 text-emerald-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 8l7.89 4.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z"/>
                                </svg>
                                Email <span class="text-red-500">*</span>
                            </label>
                            <div class="relative group">
                                <input type="email" name="email" required
                                       value="${editNewsletter != null ? editNewsletter.email : ''}"
                                       ${editNewsletter != null ? "readonly" : ""}
                                       class="w-full px-4 py-3 border-2 border-gray-200 rounded-xl focus:ring-2 focus:ring-emerald-500 focus:border-emerald-500 transition-all duration-300 bg-gray-50/50 group-hover:bg-white ${editNewsletter != null ? 'bg-gray-100 cursor-not-allowed' : ''}"
                                       placeholder="Nhập địa chỉ email...">
                                <div class="absolute inset-y-0 right-0 flex items-center pr-3 pointer-events-none">
                                    <svg class="w-5 h-5 text-gray-400 group-focus-within:text-emerald-500 transition-colors" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 12a4 4 0 10-8 0 4 4 0 008 0zm0 0v1.5a2.5 2.5 0 005 0V12a9 9 0 10-9 9m4.5-1.206a8.959 8.959 0 01-4.5 1.207"/>
                                    </svg>
                                </div>
                            </div>
                        </div>

                        <!-- Trạng thái nếu đang sửa -->
                        <c:if test="${editNewsletter != null}">
                            <div class="space-y-2">
                                <label class="block text-sm font-semibold text-gray-700 flex items-center">
                                    <svg class="w-4 h-4 mr-2 text-purple-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"/>
                                    </svg>
                                    Trạng thái
                                </label>
                                <div class="relative">
                                    <select name="enable" class="w-full px-4 py-3 border-2 border-gray-200 rounded-xl focus:ring-2 focus:ring-purple-500 focus:border-purple-500 transition-all duration-300 bg-gray-50/50 hover:bg-white appearance-none">
                                        <option value="1" ${editNewsletter.enable ? "selected" : ""}>Enable</option>
                                        <option value="0" ${!editNewsletter.enable ? "selected" : ""}>Disable</option>
                                    </select>
                                    <div class="absolute inset-y-0 right-0 flex items-center pr-3 pointer-events-none">
                                        <svg class="w-5 h-5 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 9l-7 7-7-7"/>
                                        </svg>
                                    </div>
                                </div>
                            </div>
                        </c:if>

                        <!-- Enable mặc định khi thêm -->
                        <c:if test="${editNewsletter == null}">
                            <input type="hidden" name="enable" value="1" />
                        </c:if>

                        <!-- Action Buttons -->
                        <div class="pt-6 border-t border-gray-200/60">
                            <div class="flex gap-3">
                                <button type="submit"
                                        class="flex-1 group flex items-center justify-center px-4 py-3 bg-gradient-to-r from-emerald-600 to-emerald-700 text-white rounded-xl hover:from-emerald-700 hover:to-emerald-800 focus:ring-4 focus:ring-emerald-200 transition-all duration-300 font-semibold shadow-lg hover:shadow-xl transform hover:-translate-y-0.5">
                                    <c:choose>
                                        <c:when test="${editNewsletter != null}">
                                            <svg class="w-5 h-5 mr-2 group-hover:scale-110 transition-transform duration-300" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z"/>
                                            </svg>
                                            Cập nhật
                                        </c:when>
                                        <c:otherwise>
                                            <svg class="w-5 h-5 mr-2 group-hover:rotate-90 transition-transform duration-300" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 6v6m0 0v6m0-6h6m-6 0H6"/>
                                            </svg>
                                            Thêm
                                        </c:otherwise>
                                    </c:choose>
                                </button>

                                <c:if test="${editNewsletter != null}">
                                    <a href="${pageContext.request.contextPath}/admin/newsletter?action=reset"
                                       class="group flex items-center justify-center px-4 py-3 bg-gradient-to-r from-gray-600 to-gray-700 text-white rounded-xl hover:from-gray-700 hover:to-gray-800 focus:ring-4 focus:ring-gray-200 transition-all duration-300 font-semibold shadow-lg hover:shadow-xl transform hover:-translate-y-0.5">
                                        <svg class="w-5 h-5 mr-2 group-hover:rotate-180 transition-transform duration-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 4v5h.582m15.356 2A8.001 8.001 0 004.582 9m0 0H9m11 11v-5h-.581m0 0a8.003 8.003 0 01-15.357-2m15.357 2H15"/>
                                        </svg>
                                        Reset
                                    </a>
                                </c:if>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <!-- Newsletter List Section -->
        <div class="lg:col-span-2">
            <div class="bg-white/90 backdrop-blur-md rounded-2xl shadow-xl border border-white/20 overflow-hidden animate-fade-in">
                <!-- List Header -->
                <div class="bg-gradient-to-r from-slate-50 to-gray-100 px-6 py-5 border-b border-gray-200/50">
                    <div class="flex items-center justify-between">
                        <h2 class="text-xl font-bold text-gray-900 flex items-center">
                            <div class="bg-gradient-to-br from-emerald-100 to-teal-100 p-2 rounded-lg mr-3">
                                <svg class="w-5 h-5 text-emerald-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5H7a2 2 0 00-2 2v10a2 2 0 002 2h8a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2"/>
                                </svg>
                            </div>
                            Danh Sách Email
                        </h2>
                        <div class="flex items-center space-x-3">
                            <span class="bg-gradient-to-r from-emerald-500 to-teal-600 text-white px-4 py-2 rounded-full text-sm font-bold shadow-lg">
                                ${newsletterList.size()} email
                            </span>
                        </div>
                    </div>
                </div>

                <!-- List Content -->
                <div class="p-6">
                    <c:if test="${empty newsletterList}">
                        <div class="text-center py-20">
                            <div class="bg-gradient-to-br from-gray-100 to-gray-200 rounded-full w-24 h-24 flex items-center justify-center mx-auto mb-6 shadow-inner">
                                <svg xmlns="http://www.w3.org/2000/svg" class="h-12 w-12 text-gray-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 8l7.89 4.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z"/>
                                </svg>
                            </div>
                            <h3 class="text-xl font-bold text-gray-700 mb-3">Chưa có email nào</h3>
                            <p class="text-gray-500 mb-6 max-w-sm mx-auto">Hãy thêm email đầu tiên để bắt đầu quản lý danh sách newsletter!</p>
                        </div>
                    </c:if>

                    <c:if test="${not empty newsletterList}">
                        <div class="overflow-hidden rounded-xl border border-gray-200 shadow-sm">
                            <table class="w-full">
                                <thead class="bg-gradient-to-r from-gray-50 to-gray-100">
                                    <tr>
                                        <th class="px-6 py-4 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider border-b border-gray-200">
                                            <div class="flex items-center">
                                                <svg class="w-4 h-4 mr-2 text-gray-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M7 20l4-16m2 16l4-16M6 9h14M4 15h14"/>
                                                </svg>
                                                STT
                                            </div>
                                        </th>
                                        <th class="px-6 py-4 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider border-b border-gray-200">
                                            <div class="flex items-center">
                                                <svg class="w-4 h-4 mr-2 text-gray-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 8l7.89 4.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z"/>
                                                </svg>
                                                Email
                                            </div>
                                        </th>
                                        <th class="px-6 py-4 text-center text-xs font-semibold text-gray-600 uppercase tracking-wider border-b border-gray-200">
                                            <div class="flex items-center justify-center">
                                                <svg class="w-4 h-4 mr-2 text-gray-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"/>
                                                </svg>
                                                Trạng thái
                                            </div>
                                        </th>
                                        <th class="px-6 py-4 text-center text-xs font-semibold text-gray-600 uppercase tracking-wider border-b border-gray-200">
                                            <div class="flex items-center justify-center">
                                                <svg class="w-4 h-4 mr-2 text-gray-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 6V4m0 2a2 2 0 100 4m0-4a2 2 0 110 4m-6 8a2 2 0 100-4m0 4a2 2 0 100 4m0-4v2m0-6V4m6 6v10m6-2a2 2 0 100-4m0 4a2 2 0 100 4m0-4v2m0-6V4"/>
                                                </svg>
                                                Hành động
                                            </div>
                                        </th>
                                    </tr>
                                </thead>
                                <tbody class="bg-white divide-y divide-gray-200">
                                    <c:forEach var="newsletter" items="${newsletterList}" varStatus="loop">
                                        <tr class="hover:bg-gradient-to-r hover:from-emerald-50 hover:to-teal-50 transition-all duration-200 group">
                                            <td class="px-6 py-4 whitespace-nowrap">
                                                <div class="flex items-center">
                                                    <span class="inline-flex items-center justify-center w-8 h-8 bg-gradient-to-br from-emerald-100 to-teal-100 text-emerald-700 rounded-full text-sm font-semibold group-hover:scale-110 transition-transform duration-200">
                                                        ${loop.index + 1}
                                                    </span>
                                                </div>
                                            </td>
                                            <td class="px-6 py-4 whitespace-nowrap">
                                                <div class="flex items-center">
                                                    <div class="flex-shrink-0 h-10 w-10">
                                                        <div class="h-10 w-10 rounded-full bg-gradient-to-br from-blue-100 to-indigo-100 flex items-center justify-center">
                                                            <svg class="w-5 h-5 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 12a4 4 0 10-8 0 4 4 0 008 0zm0 0v1.5a2.5 2.5 0 005 0V12a9 9 0 10-9 9m4.5-1.206a8.959 8.959 0 01-4.5 1.207"/>
                                                            </svg>
                                                        </div>
                                                    </div>
                                                    <div class="ml-4">
                                                        <div class="text-sm font-medium text-gray-900 group-hover:text-emerald-700 transition-colors duration-200">
                                                            ${newsletter.email}
                                                        </div>
                                                    </div>
                                                </div>
                                            </td>
                                            <td class="px-6 py-4 whitespace-nowrap text-center">
                                                <span class="inline-flex items-center px-3 py-1 rounded-full text-xs font-medium
                                                    ${newsletter.enable ? 'bg-gradient-to-r from-green-100 to-emerald-100 text-green-800 border border-green-200' : 'bg-gradient-to-r from-red-100 to-rose-100 text-red-800 border border-red-200'}">
                                                    <svg class="w-3 h-3 mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                                        <c:choose>
                                                            <c:when test="${newsletter.enable}">
                                                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"/>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z"/>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </svg>
                                                    ${newsletter.enable ? 'Enable' : 'Disable'}
                                                </span>
                                            </td>
                                            <td class="px-6 py-4 whitespace-nowrap text-center">
                                                <div class="flex items-center justify-center space-x-3">
                                                    <a href="${pageContext.request.contextPath}/admin/newsletter?editEmail=${newsletter.email}"
                                                       class="inline-flex items-center px-3 py-2 bg-gradient-to-r from-blue-500 to-blue-600 text-white text-sm font-medium rounded-lg hover:from-blue-600 hover:to-blue-700 focus:ring-4 focus:ring-blue-200 transition-all duration-200 shadow-sm hover:shadow-md transform hover:-translate-y-0.5">
                                                        <svg class="w-4 h-4 mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z"/>
                                                        </svg>
                                                        Sửa
                                                    </a>
                                                    <form action="${pageContext.request.contextPath}/admin/newsletter"
                                                          method="post" style="display:inline;"
                                                          onsubmit="return confirm('Bạn có chắc chắn muốn xoá email này?');">
                                                        <input type="hidden" name="action" value="delete" />
                                                        <input type="hidden" name="email" value="${newsletter.email}" />
                                                        <button type="submit"
                                                                class="inline-flex items-center px-3 py-2 bg-gradient-to-r from-red-500 to-red-600 text-white text-sm font-medium rounded-lg hover:from-red-600 hover:to-red-700 focus:ring-4 focus:ring-red-200 transition-all duration-200 shadow-sm hover:shadow-md transform hover:-translate-y-0.5">
                                                            <svg class="w-4 h-4 mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"/>
                                                            </svg>
                                                            Xoá
                                                        </button>
                                                    </form>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</main>

<script>
    // Toast Message System
    function showToast(message, type = 'success') {
        const toastContainer = document.getElementById('toast-container');
        const toastId = 'toast-' + Date.now();
        
        const toast = document.createElement('div');
        toast.id = toastId;
        toast.className = `
            flex items-center p-4 mb-4 text-sm rounded-lg shadow-lg border animate-toast-slide-in
            ${type === 'success' 
                ? 'bg-gradient-to-r from-emerald-50 to-green-50 text-emerald-800 border-emerald-200' 
                : 'bg-gradient-to-r from-red-50 to-rose-50 text-red-800 border-red-200'
            }
        `;
        
        toast.innerHTML = `
            <div class="flex items-center">
                <div class="flex-shrink-0">
                    <svg class="w-5 h-5 ${type === 'success' ? 'text-emerald-500' : 'text-red-500'}" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        ${type === 'success' 
                            ? '<path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"/>'
                            : '<path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"/>'
                        }
                    </svg>
                </div>
                <div class="ml-3 font-medium">${message}</div>
                <button onclick="removeToast('${toastId}')" class="ml-auto -mx-1.5 -my-1.5 rounded-lg p-1.5 hover:bg-white/50 inline-flex h-8 w-8 items-center justify-center">
                    <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"/>
                    </svg>
                </button>
            </div>
        `;
        
        toastContainer.appendChild(toast);
        
        // Auto remove after 5 seconds
        setTimeout(() => {
            removeToast(toastId);
        }, 5000);
    }
    
    function removeToast(toastId) {
        const toast = document.getElementById(toastId);
        if (toast) {
            toast.classList.remove('animate-toast-slide-in');
            toast.classList.add('animate-toast-slide-out');
            setTimeout(() => {
                if (toast.parentNode) {
                    toast.parentNode.removeChild(toast);
                }
            }, 300);
        }
    }
    
    // Show toast messages based on server response
    document.addEventListener('DOMContentLoaded', function() {
        // Success message
        var successMsg = '${success}';
        if (successMsg && successMsg.trim() !== '') {
            showToast(successMsg, 'success');
        }
        
        // Error message
        var errorMsg = '${error}';
        if (errorMsg && errorMsg.trim() !== '') {
            showToast(errorMsg, 'error');
        }
    });
</script>

</body>
</html> 