<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

<head>
	<script src="https://cdn.tailwindcss.com"></script>
	<title>Trang Đọc Giả</title>
	<style>
	.relative > a::after {
		content: '';
		position: absolute;
		left: 0;
		right: 0;
		bottom: -15px;
		height: 15px;
		pointer-events: auto;
	}
	</style>
</head>

<body class="bg-gray-100 text-gray-800">
	<header>
		<div class="w-full mx-auto flex justify-center bg-white shadow">
			<img src="https://caodang.fpt.edu.vn/wp-content/uploads/image3-1.png" alt="Ảnh minh họa" class="h-[250px] w-full object-cover" />
		</div>
	</header>
	<nav>
		<div class="bg-blue-800 text-white py-3">
			<div class="max-w-7xl mx-auto px-4">
				<ul class="flex space-x-12 justify-center text-[18px]">
					<!-- Trang Chủ -->
					<li class="relative group">
						<a href="${pageContext.request.contextPath}/user/index" class="relative z-10 border-b-2 border-transparent group-hover:border-yellow-400 group-hover:text-yellow-400 transition duration-300 uppercase">Trang Chủ</a>
					</li>
					<!-- Văn Hóa -->
					<li class="relative group">
						<a href="${pageContext.request.contextPath}/user/culture" class="relative z-10 border-b-2 border-transparent group-hover:border-green-400 group-hover:text-green-400 transition duration-300 uppercase">Văn Hóa</a>
					</li>
					<!-- Pháp Luật -->
					<li class="relative group">
						<a href="${pageContext.request.contextPath}/user/law" class="relative z-10 border-b-2 border-transparent group-hover:border-yellow-300 group-hover:text-yellow-300 transition duration-300 uppercase">Pháp Luật</a>
					</li>
					<!-- Thể Thao -->
					<li class="relative group">
						<a href="${pageContext.request.contextPath}/user/sport" class="relative z-10 border-b-2 border-transparent group-hover:border-orange-400 group-hover:text-orange-400 transition duration-300 uppercase">Thể Thao</a>
					</li>
				</ul>
			</div>
		</div>
	</nav>
	<div class="flex max-w-[1200px] mx-auto min-h-screen">
		<main class="w-3/4 min-h-screen">
			<div class="max-w-7xl mx-auto p-4 bg-white rounded shadow min-h-screen flex items-center justify-center">
				<!-- Nội dung chính -->
			</div>
		</main>
		<aside class="w-1/4 min-h-screen">
			<div class="max-w-xs mx-auto bg-gray-100 rounded shadow h-full relative">
				<div class="bg-yellow-200 p-4 hover:bg-yellow-300 hover:shadow-md transition duration-300 cursor-pointer">
					<a href="${pageContext.request.contextPath}/user/top5-view" class="text-[14px] text-gray-900 font-semibold hover:text-black">5 Bản Tin Được Xem Nhiều Nhất</a>
				</div>
				<div class="bg-red-200 p-4 hover:bg-red-300 hover:shadow-md transition duration-300 cursor-pointer">
					<a href="${pageContext.request.contextPath}/user/top5-lastest" class="text-[14px] text-gray-900 font-semibold hover:text-black">5 Bản Tin Mới Nhất</a>
				</div>
				<div class="bg-blue-200 p-4 hover:bg-blue-300 hover:shadow-md transition duration-300 cursor-pointer">
					<a href="${pageContext.request.contextPath}/user/top5-seen" class="text-[14px] text-gray-900 font-semibold hover:text-black">5 Bản Tin Bạn Đã Xem</a>
				</div>
				<div class="absolute bottom-0 left-0 w-full border border-[#E55E5E] text-[14px] rounded">
					<input type="text" placeholder="Newsletter" class="w-full px-3 py-2 rounded focus:outline-none focus:ring-2 focus:ring-blue-400 border border-gray-300" />
				</div>
			</div>
		</aside>
	</div>
	<footer>
		<div class="p-4 bg-gray-800 text-white text-center">
			<p class="text-sm">Chào Mừng Bạn Đến Với Môn Học Java 3 JSP/Servlet © 2025. All Rights Reserved.</p>
		</div>
	</footer>
</body>

</html>
