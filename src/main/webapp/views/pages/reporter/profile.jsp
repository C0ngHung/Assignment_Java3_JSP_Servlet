<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hồ sơ cá nhân - Hệ thống quản lý tin tức</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <script>
        tailwind.config = {
            theme: {
                extend: {
                    animation: {
                        'fade-in': 'fadeIn 0.6s ease-out',
                        'slide-up': 'slideUp 0.4s ease-out',
                        'bounce-subtle': 'bounceSubtle 0.8s ease-out',
                        'float': 'float 3s ease-in-out infinite'
                    }
                }
            }
        }
    </script>
    <style>
        @keyframes fadeIn {
            from {
                opacity: 0;
                transform: translateY(10px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        @keyframes slideUp {
            from {
                opacity: 0;
                transform: translateY(30px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        @keyframes bounceSubtle {
            0%, 20%, 50%, 80%, 100% {
                transform: translateY(0);
            }
            40% {
                transform: translateY(-4px);
            }
            60% {
                transform: translateY(-2px);
            }
        }

        @keyframes float {
            0%, 100% {
                transform: translateY(0px);
            }
            50% {
                transform: translateY(-6px);
            }
        }

        .input-focus:focus {
            transform: translateY(-1px);
            box-shadow: 0 10px 25px -5px rgba(59, 130, 246, 0.15);
        }

        .card-hover:hover {
            transform: translateY(-2px);
        }
    </style>
</head>
<body class="min-h-screen bg-gradient-to-br from-blue-50 via-indigo-50 to-purple-50">
<!-- Animated Background Elements -->
<div class="fixed inset-0 overflow-hidden pointer-events-none">
    <div class="absolute top-10 left-10 w-20 h-20 bg-blue-200 rounded-full opacity-20 animate-float"></div>
    <div class="absolute top-1/4 right-20 w-16 h-16 bg-purple-200 rounded-full opacity-20 animate-float"
         style="animation-delay: 1s;"></div>
    <div class="absolute bottom-20 left-1/4 w-12 h-12 bg-indigo-200 rounded-full opacity-20 animate-float"
         style="animation-delay: 2s;"></div>
    <div class="absolute bottom-1/3 right-10 w-14 h-14 bg-pink-200 rounded-full opacity-20 animate-float"
         style="animation-delay: 0.5s;"></div>
</div>

<!-- Header Section -->
<div class="relative">
    <div class="bg-white/70 backdrop-blur-md border-b border-gray-200/50 shadow-sm">
        <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
            <div class="flex items-center justify-between h-16">
                <nav class="flex items-center space-x-3 text-sm">
                    <a href="${pageContext.request.contextPath}/${sessionScope.currentUserRole}/index"
                       class="flex items-center text-gray-600 hover:text-blue-600 transition-all duration-200 hover:scale-105">
                        <svg class="w-4 h-4 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                  d="M3 7v10a2 2 0 002 2h14a2 2 0 002-2V9a2 2 0 00-2-2H5a2 2 0 00-2-2z"/>
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                  d="M8 5a2 2 0 012-2h4a2 2 0 012 2v2H8V5z"/>
                        </svg>
                        Trang chủ
                    </a>
                    <span class="text-gray-400">•</span>
                    <span class="text-gray-900 font-semibold">Hồ sơ cá nhân</span>
                </nav>
                <div class="flex items-center space-x-4">
                    <div class="flex items-center space-x-2">
                        <div class="w-8 h-8 bg-gradient-to-r from-blue-500 to-purple-600 rounded-full flex items-center justify-center">
                            <span class="text-white text-sm font-bold">${user.fullName.substring(0, 1).toUpperCase()}</span>
                        </div>
                        <span class="text-sm text-gray-700">Xin chào, <strong>${user.fullName}</strong></span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="relative max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
    <!-- Page Header -->
    <div class="text-center mb-10 animate-fade-in">
        <div class="inline-flex items-center justify-center w-20 h-20 bg-gradient-to-r from-blue-500 to-purple-600 rounded-full mb-4 shadow-lg">
            <svg class="w-10 h-10 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                      d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"/>
            </svg>
        </div>
        <h1 class="text-4xl font-bold bg-gradient-to-r from-gray-900 via-blue-900 to-purple-900 bg-clip-text text-transparent mb-3">
            Hồ sơ cá nhân
        </h1>
        <p class="text-gray-600 text-lg">Quản lý thông tin cá nhân và cài đặt bảo mật tài khoản</p>
    </div>

    <!-- Alert Messages -->
    <c:if test="${not empty success}">
        <div id="toast-message">
            <div class="mb-8 animate-bounce-subtle">
                <div class="bg-gradient-to-r from-emerald-50 to-green-50 border-l-4 border-emerald-400 p-6 rounded-r-xl shadow-lg">
                    <div class="flex items-center">
                        <div class="flex-shrink-0">
                            <svg class="w-6 h-6 text-emerald-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                      d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"/>
                            </svg>
                        </div>
                        <div class="ml-4">
                            <h3 class="text-sm font-semibold text-emerald-800">Thành công!</h3>
                            <p class="text-sm text-emerald-700 mt-1">${success}</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </c:if>

    <c:if test="${not empty error}">
        <div id="toast-message">
            <div class="mb-8 animate-bounce-subtle">
                <div class="bg-gradient-to-r from-red-50 to-rose-50 border-l-4 border-red-400 p-6 rounded-r-xl shadow-lg">
                    <div class="flex items-center">
                        <div class="flex-shrink-0">
                            <svg class="w-6 h-6 text-red-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                      d="M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"/>
                            </svg>
                        </div>
                        <div class="ml-4">
                            <h3 class="text-sm font-semibold text-red-800">Có lỗi xảy ra!</h3>
                            <p class="text-sm text-red-700 mt-1">${error}</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </c:if>

    <!-- Main Content Card -->
    <div class="bg-white/80 backdrop-blur-sm rounded-3xl shadow-2xl p-8 md:p-10 card-hover transition-all duration-300 animate-slide-up">
        <form action="${pageContext.request.contextPath}/reporter/profile" method="post" class="space-y-8">

            <!-- Personal Information Section -->
            <div class="space-y-6">
                <div class="flex items-center space-x-3 pb-4 border-b border-gray-200">
                    <div class="w-10 h-10 bg-gradient-to-r from-blue-500 to-indigo-600 rounded-xl flex items-center justify-center shadow-lg">
                        <svg class="w-5 h-5 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                  d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"/>
                        </svg>
                    </div>
                    <h2 class="text-2xl font-bold text-gray-900">Thông tin cá nhân</h2>
                </div>

                <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                    <!-- Họ tên -->
                    <div class="md:col-span-2">
                        <label class="flex items-center text-sm font-bold text-gray-700 mb-3">
                            <svg class="w-4 h-4 mr-2 text-blue-500" fill="none" stroke="currentColor"
                                 viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                      d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"/>
                            </svg>
                            Họ và tên <span class="text-red-500 ml-1">*</span>
                        </label>
                        <input type="text" name="fullName" value="${user.fullName}" required
                               class="w-full px-4 py-4 border-2 border-gray-200 rounded-2xl focus:border-blue-500 focus:ring-4 focus:ring-blue-200 transition-all duration-200 input-focus bg-white/50 backdrop-blur-sm text-gray-900 font-medium placeholder-gray-400"
                               placeholder="Nhập họ và tên đầy đủ">
                    </div>

                    <!-- Ngày sinh -->
                    <div>
                        <label class="flex items-center text-sm font-bold text-gray-700 mb-3">
                            <svg class="w-4 h-4 mr-2 text-green-500" fill="none" stroke="currentColor"
                                 viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                      d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z"/>
                            </svg>
                            Ngày sinh
                        </label>
                        <input type="date" name="birthdate"
                               value="<fmt:formatDate value='${user.birthdate}' pattern='yyyy-MM-dd'/>"
                               class="w-full px-4 py-4 border-2 border-gray-200 rounded-2xl focus:border-blue-500 focus:ring-4 focus:ring-blue-200 transition-all duration-200 input-focus bg-white/50 backdrop-blur-sm text-gray-900 font-medium">
                    </div>

                    <!-- Giới tính -->
                    <div>
                        <label class="flex items-center text-sm font-bold text-gray-700 mb-3">
                            <svg class="w-4 h-4 mr-2 text-purple-500" fill="none" stroke="currentColor"
                                 viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                      d="M12 4.354a4 4 0 110 5.292M15 21H3v-1a6 6 0 0112 0v1zm0 0h6v-1a6 6 0 00-9-5.197m13.5-9a2.5 2.5 0 11-5 0 2.5 2.5 0 015 0z"/>
                            </svg>
                            Giới tính
                        </label>
                        <select name="gender"
                                class="w-full px-4 py-4 border-2 border-gray-200 rounded-2xl focus:border-blue-500 focus:ring-4 focus:ring-blue-200 transition-all duration-200 input-focus bg-white/50 backdrop-blur-sm text-gray-900 font-medium">
                            <option value="Nam" <c:if test="${user.gender}">selected</c:if>>Nam</option>
                            <option value="Nữ" <c:if test="${not user.gender}">selected</c:if>>Nữ</option>
                        </select>
                    </div>

                    <!-- Số điện thoại -->
                    <div>
                        <label class="flex items-center text-sm font-bold text-gray-700 mb-3">
                            <svg class="w-4 h-4 mr-2 text-orange-500" fill="none" stroke="currentColor"
                                 viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                      d="M3 5a2 2 0 012-2h3.28a1 1 0 01.948.684l1.498 4.493a1 1 0 01-.502 1.21l-2.257 1.13a11.042 11.042 0 005.516 5.516l1.13-2.257a1 1 0 011.21-.502l4.493 1.498a1 1 0 01.684.949V19a2 2 0 01-2 2h-1C9.716 21 3 14.284 3 6V5z"/>
                            </svg>
                            Số điện thoại
                        </label>
                        <input type="tel" name="mobile" value="${user.mobile}"
                               class="w-full px-4 py-4 border-2 border-gray-200 rounded-2xl focus:border-blue-500 focus:ring-4 focus:ring-blue-200 transition-all duration-200 input-focus bg-white/50 backdrop-blur-sm text-gray-900 font-medium placeholder-gray-400"
                               placeholder="Nhập số điện thoại">
                    </div>

                    <!-- Email -->
                    <div>
                        <label class="flex items-center text-sm font-bold text-gray-700 mb-3">
                            <svg class="w-4 h-4 mr-2 text-red-500" fill="none" stroke="currentColor"
                                 viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                      d="M3 8l7.89 4.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z"/>
                            </svg>
                            Email <span class="text-red-500 ml-1">*</span>
                        </label>
                        <input type="email" name="email" value="${user.email}" required
                               class="w-full px-4 py-4 border-2 border-gray-200 rounded-2xl focus:border-blue-500 focus:ring-4 focus:ring-blue-200 transition-all duration-200 input-focus bg-white/50 backdrop-blur-sm text-gray-900 font-medium placeholder-gray-400"
                               placeholder="Nhập địa chỉ email">
                    </div>
                </div>
            </div>

            <!-- Password Section -->
            <div class="space-y-6 pt-8">
                <div class="flex items-center space-x-3 pb-4 border-b border-gray-200">
                    <div class="w-10 h-10 bg-gradient-to-r from-amber-500 to-orange-600 rounded-xl flex items-center justify-center shadow-lg">
                        <svg class="w-5 h-5 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                  d="M12 15v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2zm10-10V7a4 4 0 00-8 0v4h8z"/>
                        </svg>
                    </div>
                    <div>
                        <h2 class="text-2xl font-bold text-gray-900">Đổi mật khẩu</h2>
                        <p class="text-sm text-gray-600 mt-1">Cập nhật mật khẩu để bảo mật tài khoản của bạn</p>
                    </div>
                </div>

                <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                    <!-- Mật khẩu hiện tại -->
                    <div class="md:col-span-2">
                        <label class="flex items-center text-sm font-bold text-gray-700 mb-3">
                            <svg class="w-4 h-4 mr-2 text-gray-500" fill="none" stroke="currentColor"
                                 viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                      d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"/>
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                      d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z"/>
                            </svg>
                            Mật khẩu hiện tại
                        </label>
                        <input type="password" name="currentPassword" value="${user.password}"
                               class="w-full px-4 py-4 border-2 border-gray-200 rounded-2xl focus:border-amber-500 focus:ring-4 focus:ring-amber-200 transition-all duration-200 input-focus bg-white/50 backdrop-blur-sm text-gray-900 font-medium placeholder-gray-400"
                               placeholder="Nhập mật khẩu hiện tại">
                    </div>

                    <!-- Mật khẩu mới -->
                    <div>
                        <label class="flex items-center text-sm font-bold text-gray-700 mb-3">
                            <svg class="w-4 h-4 mr-2 text-green-500" fill="none" stroke="currentColor"
                                 viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                      d="M12 15v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2zm10-10V7a4 4 0 00-8 0v4h8z"/>
                            </svg>
                            Mật khẩu mới
                        </label>
                        <input type="password" name="newPassword"
                               class="w-full px-4 py-4 border-2 border-gray-200 rounded-2xl focus:border-amber-500 focus:ring-4 focus:ring-amber-200 transition-all duration-200 input-focus bg-white/50 backdrop-blur-sm text-gray-900 font-medium placeholder-gray-400"
                               placeholder="Nhập mật khẩu mới">
                    </div>

                    <!-- Xác nhận mật khẩu -->
                    <div>
                        <label class="flex items-center text-sm font-bold text-gray-700 mb-3">
                            <svg class="w-4 h-4 mr-2 text-blue-500" fill="none" stroke="currentColor"
                                 viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                      d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"/>
                            </svg>
                            Xác nhận mật khẩu mới
                        </label>
                        <input type="password" name="confirmPassword"
                               class="w-full px-4 py-4 border-2 border-gray-200 rounded-2xl focus:border-amber-500 focus:ring-4 focus:ring-amber-200 transition-all duration-200 input-focus bg-white/50 backdrop-blur-sm text-gray-900 font-medium placeholder-gray-400"
                               placeholder="Nhập lại mật khẩu mới">
                    </div>
                </div>
            </div>

            <!-- Submit Button -->
            <div class="pt-8 flex justify-center">
                <button type="submit"
                        class="group relative overflow-hidden bg-gradient-to-r from-blue-600 via-purple-600 to-indigo-600 text-white px-12 py-4 rounded-2xl font-bold text-lg shadow-2xl hover:shadow-3xl transform hover:scale-105 transition-all duration-300 focus:ring-4 focus:ring-blue-200">
                        <span class="relative z-10 flex items-center">
                            <svg class="w-6 h-6 mr-3 group-hover:rotate-12 transition-transform duration-300"
                                 fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                      d="M5 13l4 4L19 7"/>
                            </svg>
                            Cập nhật hồ sơ
                        </span>
                    <div class="absolute inset-0 bg-gradient-to-r from-purple-600 via-pink-600 to-red-600 opacity-0 group-hover:opacity-100 transition-opacity duration-300"></div>
                </button>
            </div>
        </form>
    </div>
</div>
</body>
<script>
    const toastMessage = document.getElementById('toast-message');
    if (toastMessage && !toastMessage.classList.contains("hidden")) {
        setTimeout(() => {
            toastMessage.classList.add('hidden');
        }, 1500);
    }
</script>
</html>