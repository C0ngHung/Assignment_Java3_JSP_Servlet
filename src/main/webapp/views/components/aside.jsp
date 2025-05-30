<%@ page contentType="text/html;charset=UTF-8" language="java" %> <div class="container max-w-xs mx-auto bg-gray-100 rounded shadow h-full relative">
	<div class="bg-[#E6DD3C] p-4 bg-opacity-75">
		<a href="${pageContext.request.contextPath}/top5view" class="text-[14px]-bold text-white-600">5 Bản Tin Được Xem Nhiều Nhất</a>
	</div>
	<div class="bg-[#E63C5D] p-4 bg-opacity-75">
		<a href="${pageContext.request.contextPath}/top5lastest" class="text-[14px]-bold text-white-600">5 Bản Tin Mới Nhất</a>
	</div>
	<div class="bg-[#07A6E6] p-4 bg-opacity-75">
		<a href="${pageContext.request.contextPath}/top5seen" class="text-[14px]-bold text-white-600">5 Bản Tin Bạn Đã Xem</a>
	</div>
	<label class="absolute bottom-0 left-0 w-full bg-[#E55E5E] text-[14px]">
		<input type="text" placeholder="Newsletter" class="w-full px-3 py-2 border border-orange-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-400" />
	</label>
</div>