<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
    <script src="https://cdn.tailwindcss.com"></script>
    <title>Trang Quản Trị</title>
</head>

<body class="bg-gray-100">
    <c:set var="componentPath" value="/views/components/admin" />

    <header>
        <jsp:include page="${componentPath}/header.jsp" />
    </header>

    <nav>
        <jsp:include page="${componentPath}/nav.jsp" />
    </nav>

    <div class="flex max-w-[1200px] mx-auto min-h-screen">
        <main class="w-full min-h-screen">
			<jsp:include page="${componentPath}/main.jsp" />
		</main>
    </div>

    <footer>
        <jsp:include page="${componentPath}/footer.jsp" />
    </footer>

</body>

</html>
