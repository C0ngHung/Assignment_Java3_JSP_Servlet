<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
	<c:set var="componentPath" value="/views/components/user" />
	<header>
		<jsp:include page="${componentPath}/header.jsp" />
	</header>
	<nav>
		<jsp:include page="${componentPath}/nav.jsp" />
	</nav>
	<div class="flex max-w-[1200px] mx-auto min-h-screen">
		<main class="w-3/4 min-h-screen">
			<jsp:include page="${componentPath}/main.jsp" />
		</main>
		<aside class="w-1/4 min-h-screen">
			<jsp:include page="${componentPath}/aside.jsp" />
		</aside>
	</div>
	<footer>
		<jsp:include page="${componentPath}/footer.jsp" />
	</footer>
</body>

</html>