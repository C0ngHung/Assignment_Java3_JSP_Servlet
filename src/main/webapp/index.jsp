<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

<head>
	<script src="https://cdn.tailwindcss.com"></script>
	<title>Trang Đọc Giả</title>
	<style>
	.relative>a::after {
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
		<jsp:include page="/views/components/header.jsp" />
	</header>
	<nav>
		<jsp:include page="/views/components/nav.jsp" />
	</nav>
	<div class="flex max-w-[1200px] mx-auto h-[400px]">
		<main class="w-3/4 h-full">
		</main>
		<aside class="w-1/4 h-full">
			<jsp:include page="/views/components/aside.jsp" />
		</aside>
	</div>
	<footer>
		<jsp:include page="/views/components/footer.jsp" />
	</footer>
</body>

</html>