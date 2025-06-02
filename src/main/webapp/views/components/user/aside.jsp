<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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