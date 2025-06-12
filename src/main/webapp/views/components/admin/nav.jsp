<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="bg-gradient-to-r from-blue-600 to-blue-700 text-white shadow-lg border-b border-blue-800">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 h-16 flex items-center justify-between">
        <!-- MENU CHÍNH -->
        <ul class="flex items-center space-x-1 text-sm font-medium mx-auto">
            <li>
                <a href="${pageContext.request.contextPath}/${sessionScope.currentUserRole}/index"
                   class="flex items-center space-x-2 hover:bg-blue-500 hover:shadow-md px-4 py-2.5 rounded-lg transition-all duration-200 ease-in-out group">
                    <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4 group-hover:scale-110 transition-transform"
                         fill="none" viewBox="0 0 24 24" stroke="currentColor">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                              d="M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6"/>
                    </svg>
                    <span class="uppercase tracking-wide">Trang chủ</span>
                </a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/${sessionScope.currentUserRole}/news-management"
                   class="flex items-center space-x-2 hover:bg-blue-500 hover:shadow-md px-4 py-2.5 rounded-lg transition-all duration-200 ease-in-out group">
                    <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4 group-hover:scale-110 transition-transform"
                         fill="none" viewBox="0 0 24 24" stroke="currentColor">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                              d="M19 20H5a2 2 0 01-2-2V6a2 2 0 012-2h10a2 2 0 012 2v1m2 13a2 2 0 01-2-2V7m2 13a2 2 0 002-2V9.5a2.5 2.5 0 00-2.5-2.5H14"/>
                    </svg>
                    <span class="uppercase tracking-wide">Quản lý tin</span>
                </a>
            </li>
            <c:if test="${sessionScope.currentUserRole == 'admin'}">
                <li>
                    <a href="${pageContext.request.contextPath}/${sessionScope.currentUserRole}/categories"
                       class="flex items-center space-x-2 hover:bg-blue-500 hover:shadow-md px-4 py-2.5 rounded-lg transition-all duration-200 ease-in-out group">
                        <svg xmlns="http://www.w3.org/2000/svg"
                             class="h-4 w-4 group-hover:scale-110 transition-transform" fill="none" viewBox="0 0 24 24"
                             stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                  d="M7 7h.01M7 3h5c.512 0 1.024.195 1.414.586l7 7a2 2 0 010 2.828l-7 7a2 2 0 01-2.828 0l-7-7A1.994 1.994 0 013 12V7a4 4 0 014-4z"/>
                        </svg>
                        <span class="uppercase tracking-wide">Danh mục</span>
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/${sessionScope.currentUserRole}/figure"
                       class="flex items-center space-x-2 hover:bg-blue-500 hover:shadow-md px-4 py-2.5 rounded-lg transition-all duration-200 ease-in-out group">
                        <svg xmlns="http://www.w3.org/2000/svg"
                             class="h-4 w-4 group-hover:scale-110 transition-transform" fill="none" viewBox="0 0 24 24"
                             stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                  d="M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z"/>
                        </svg>
                        <span class="uppercase tracking-wide">Thống kê</span>
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/admin/newsletter"
                       class="flex items-center space-x-2 hover:bg-blue-500 hover:shadow-md px-4 py-2.5 rounded-lg transition-all duration-200 ease-in-out group">
                        <svg xmlns="http://www.w3.org/2000/svg"
                             class="h-4 w-4 group-hover:scale-110 transition-transform" fill="none" viewBox="0 0 24 24"
                             stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                  d="M3 8l7.89 5.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z"/>
                        </svg>
                        <span class="uppercase tracking-wide">Bản tin</span>
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/${sessionScope.currentUserRole}/profile"
                       class="flex items-center space-x-2 hover:bg-blue-500 hover:shadow-md px-4 py-2.5 rounded-lg transition-all duration-200 ease-in-out group">
                        <svg xmlns="http://www.w3.org/2000/svg"
                             class="h-4 w-4 group-hover:scale-110 transition-transform"
                             fill="none" viewBox="0 0 24 24" stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                  d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"/>
                        </svg>
                        <span class="uppercase tracking-wide">Quản Lý Tài Khoản</span>
                    </a>
                </li>
            </c:if>
            <c:if test="${sessionScope.currentUserRole == 'reporter'}">
                <li>
                    <a href="${pageContext.request.contextPath}/${sessionScope.currentUserRole}/profile"
                       class="flex items-center space-x-2 hover:bg-blue-500 hover:shadow-md px-4 py-2.5 rounded-lg transition-all duration-200 ease-in-out group">
                        <svg xmlns="http://www.w3.org/2000/svg"
                             class="h-4 w-4 group-hover:scale-110 transition-transform"
                             fill="none" viewBox="0 0 24 24" stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                  d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"/>
                        </svg>
                        <span class="uppercase tracking-wide">Hồ Sơ</span>
                    </a>
                </li>
            </c:if>
        </ul>
        <!-- Xin chào, người dùng BÊN PHẢI -->
        <div class="relative group flex items-center ml-6">
            <div class="flex items-center space-x-2 px-4 py-2.5 cursor-pointer hover:bg-blue-500 rounded-lg transition-all duration-200 ease-in-out">
                <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 text-blue-200" fill="none" viewBox="0 0 24 24"
                     stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                          d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"/>
                </svg>
                <p class="text-sm font-medium">
                    Xin chào, <span class="font-bold text-blue-100">${user.fullName}</span>
                </p>
                <svg xmlns="http://www.w3.org/2000/svg"
                     class="h-4 w-4 text-blue-200 group-hover:rotate-180 transition-transform duration-200" fill="none"
                     viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 9l-7 7-7-7"/>
                </svg>
            </div>
            <!-- Invisible hover area -->
            <div class="absolute top-full left-0 w-full h-4 z-10 group-hover:block hidden"></div>
            <!-- Dropdown menu -->
            <ul class="absolute right-0 top-full mt-2 w-48 bg-white text-gray-800 rounded-xl shadow-xl border border-gray-100 hidden group-hover:block z-50 overflow-hidden">
                <li class="border-b border-gray-100 last:border-b-0">
                    <a href="${pageContext.request.contextPath}/logout"
                       class="flex items-center space-x-3 px-4 py-3 hover:bg-red-50 hover:text-red-600 transition-all duration-200 ease-in-out">
                        <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24"
                             stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                  d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1"/>
                        </svg>
                        <span class="font-medium">Đăng xuất</span>
                    </a>
                </li>
            </ul>
        </div>
    </div>
</div>