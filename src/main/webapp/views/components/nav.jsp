<%@ page contentType="text/html;charset=UTF-8" language="java" %> <div class="bg-[#0D6EFD] text-white py-3">
	<div class="max-w-7xl mx-auto px-4">
		<ul class="flex space-x-12 justify-center text-[18px]">
			<!-- Trang Chủ -->
			<li class="relative group">
				<a href="${pageContext.request.contextPath}/user/index" class="relative z-10 border-b-2 border-transparent group-hover:border-[#ED9E61] group-hover:text-[#ED9E61] transition duration-300 uppercase"> Trang Chủ </a>
			</li>
			<!-- Văn Hóa -->
			<li class="relative group">
				<a href="${pageContext.request.contextPath}/user/culture" class="relative z-10 border-b-2 border-transparent group-hover:border-[#78ED61] group-hover:text-[#78ED61] transition duration-300 uppercase"> Văn Hóa </a>
			</li>
			<!-- Pháp Luật -->
			<li class="relative group">
				<a href="${pageContext.request.contextPath}/user/law" class="relative z-10 border-b-2 border-transparent group-hover:border-[#fee728] group-hover:text-[#fee728] transition duration-300 uppercase"> Pháp Luật </a>
			</li>
			<!-- Thể Thao -->
			<li class="relative group">
				<a href="${pageContext.request.contextPath}/user/sport" class="relative z-10 border-b-2 border-transparent group-hover:border-[#ff942b] group-hover:text-[#ff942b] transition duration-300 uppercase"> Thể Thao </a>
			</li>
		</ul>
	</div>
</div>