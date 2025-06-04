<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 flex items-center justify-center min-h-screen">
<div class="bg-white p-8 rounded shadow-md w-full max-w-sm">
    <h1 class="text-2xl font-semibold text-center mb-6">Đăng nhập</h1>

    <!-- Form login -->
    <form action="${pageContext.request.contextPath}/login" method="post" class="space-y-4">
        <div>
            <label class="block text-sm font-medium mb-1">Tên đặng nhập</label>
            <input type="text" name="username" value="${username}"
                   class="w-full px-3 py-2 border rounded focus:outline-none focus:ring focus:border-blue-300" required>
        </div>

        <div>
            <label class="block text-sm font-medium mb-1">Mật khẩu</label>
            <input name="password" type="password"
                   class="w-full px-3 py-2 border rounded focus:outline-none focus:ring focus:border-blue-300" required>
        </div>

        <div class="flex justify-between">
            <div class="flex items-center">
                <input name="remember" type="checkbox" class="mr-2">
                <label>Ghi nhớ đăng nhập</label>
            </div>
            <a href="#" class="text-blue-600 hover:underline">Quên mật khẩu?</a>
        </div>

        <div class="flex space-x-2">
            <button type="submit" name="action" value="login"
                    class="flex-1 bg-blue-600 text-white py-2 rounded hover:bg-blue-700 transition">
                Login
            </button>
            <button type="submit" name="action" value="guest"
                    class="w-1/3 bg-gray-300 text-black py-2 rounded hover:bg-gray-400 transition">
                Guest
            </button>
        </div>
    </form>

    <!-- Thông báo -->
    <c:if test="${not empty message}">
        <div class="mt-4 text-center text-red-600 font-medium">
                ${message}
        </div>
    </c:if>
</div>
</body>
</html>
