<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h1 class="text-3xl font-bold text-gray-800 mb-6 border-b border-gray-200 pb-4">Quản lý Newsletter</h1>

<!-- Thông báo -->
<c:if test="${not empty success}">
    <div id="toast-success" class="bg-green-50 border border-green-200 text-green-800 px-6 py-4 rounded-lg mb-6 flex items-center shadow-sm">
        <svg class="w-5 h-5 mr-3 flex-shrink-0" fill="currentColor" viewBox="0 0 20 20">
            <path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clip-rule="evenodd"/>
        </svg>
        ${success}
    </div>
</c:if>
<c:if test="${not empty error}">
    <div id="toast-error" class="bg-red-50 border border-red-200 text-red-800 px-6 py-4 rounded-lg mb-6 flex items-center shadow-sm">
        <svg class="w-5 h-5 mr-3 flex-shrink-0" fill="currentColor" viewBox="0 0 20 20">
            <path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zM8.707 7.293a1 1 0 00-1.414 1.414L8.586 10l-1.293 1.293a1 1 0 101.414 1.414L10 11.414l1.293 1.293a1 1 0 001.414-1.414L11.414 10l1.293-1.293a1 1 0 00-1.414-1.414L10 8.586 8.707 7.293z" clip-rule="evenodd"/>
        </svg>
        ${error}
    </div>
</c:if>

<!-- Form thêm/cập nhật -->
<div class="bg-white border border-gray-200 rounded-xl shadow-sm mb-8 overflow-hidden">
    <div class="bg-gradient-to-r from-blue-50 to-indigo-50 px-6 py-4 border-b border-gray-200">
        <h2 class="text-lg font-semibold text-gray-800 flex items-center">
            <svg class="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 6v6m0 0v6m0-6h6m-6 0H6"/>
            </svg>
            ${editNewsletter != null ? 'Cập nhật Newsletter' : 'Thêm Newsletter Mới'}
        </h2>
    </div>

    <form action="${pageContext.request.contextPath}/admin/newsletter" method="post" class="p-6 space-y-6">
        <input type="hidden" name="action" value="${editNewsletter != null ? 'update' : 'create'}"/>

        <!-- Email -->
        <div class="space-y-2">
            <label class="block text-sm font-semibold text-gray-700 mb-2">
                Email Address
                <span class="text-red-500">*</span>
            </label>
            <div class="relative">
                <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                    <svg class="h-5 w-5 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 8l7.89 4.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z"/>
                    </svg>
                </div>
                <input type="email" name="email" required
                       value="${editNewsletter != null ? editNewsletter.email : ''}"
                       placeholder="Nhập địa chỉ email"
                       class="w-full pl-10 pr-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition-colors duration-200 ${editNewsletter != null ? 'bg-gray-50 cursor-not-allowed' : 'bg-white'}"
                ${editNewsletter != null ? "readonly" : ""} />
            </div>
        </div>

        <!-- Trạng thái nếu đang sửa -->
        <c:if test="${editNewsletter != null}">
            <div class="space-y-2">
                <label class="block text-sm font-semibold text-gray-700 mb-2">Trạng thái</label>
                <div class="relative">
                    <select name="enable" class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition-colors duration-200 bg-white appearance-none">
                        <option value="1" ${editNewsletter.enable ? "selected" : ""}>🟢 Enable - Đang hoạt động</option>
                        <option value="0" ${!editNewsletter.enable ? "selected" : ""}>🔴 Disable - Tạm dừng</option>
                    </select>
                    <div class="absolute inset-y-0 right-0 flex items-center px-2 pointer-events-none">
                        <svg class="w-5 h-5 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 9l-7 7-7-7"/>
                        </svg>
                    </div>
                </div>
            </div>
        </c:if>

        <!-- Enable mặc định khi thêm -->
        <c:if test="${editNewsletter == null}">
            <input type="hidden" name="enable" value="1"/>
        </c:if>

        <!-- Nút -->
        <div class="flex gap-3 pt-4 border-t border-gray-100">
            <button type="submit"
                    class="inline-flex items-center px-6 py-3 border border-transparent text-base font-medium rounded-lg text-white bg-gradient-to-r from-blue-600 to-blue-700 hover:from-blue-700 hover:to-blue-800 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 transition-all duration-200 transform hover:scale-[1.02] shadow-lg hover:shadow-xl">
                <svg class="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"/>
                </svg>
                ${editNewsletter != null ? "Cập nhật Newsletter" : "Thêm Newsletter"}
            </button>

            <c:if test="${editNewsletter != null}">
                <!-- Reset -->
                <a href="${pageContext.request.contextPath}/admin/newsletter?action=reset"
                   class="inline-flex items-center px-6 py-3 border border-gray-300 text-base font-medium rounded-lg text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-gray-500 transition-all duration-200 transform hover:scale-[1.02] shadow-sm hover:shadow-md">
                    <svg class="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"/>
                    </svg>
                    Hủy bỏ
                </a>
            </c:if>
        </div>
    </form>
</div>

<!-- Danh sách newsletter -->
<div class="bg-white border border-gray-200 rounded-xl shadow-sm overflow-hidden">
    <div class="bg-gradient-to-r from-gray-50 to-gray-100 px-6 py-4 border-b border-gray-200">
        <h2 class="text-lg font-semibold text-gray-800 flex items-center">
            <svg class="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5H7a2 2 0 00-2 2v10a2 2 0 002 2h8a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2"/>
            </svg>
            Danh sách Newsletter
        </h2>
    </div>

    <div class="overflow-x-auto">
        <table class="w-full">
            <thead>
                <tr class="bg-gray-50 border-b border-gray-200">
                    <th class="px-6 py-4 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider w-16">STT</th>
                    <th class="px-6 py-4 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">Email Address</th>
                    <th class="px-6 py-4 text-center text-xs font-semibold text-gray-600 uppercase tracking-wider w-32">Trạng thái</th>
                    <th class="px-6 py-4 text-center text-xs font-semibold text-gray-600 uppercase tracking-wider w-40">Hành động</th>
                </tr>
            </thead>
            <tbody class="divide-y divide-gray-200">
                <c:forEach var="newsletter" items="${newsletterList}" varStatus="loop">
                    <tr class="hover:bg-gray-50 transition-colors duration-150">
                        <td class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">
                            <div class="flex items-center justify-center w-8 h-8 bg-blue-100 text-blue-800 rounded-full text-xs font-bold">
                                ${loop.index + 1}
                            </div>
                        </td>
                        <td class="px-6 py-4 whitespace-nowrap">
                            <div class="flex items-center">
                                <div class="flex-shrink-0 h-8 w-8 bg-gradient-to-br from-blue-400 to-blue-600 rounded-full flex items-center justify-center">
                                    <svg class="h-4 w-4 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 8l7.89 4.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z"/>
                                    </svg>
                                </div>
                                <div class="ml-3">
                                    <div class="text-sm font-medium text-gray-900">${newsletter.email}</div>
                                </div>
                            </div>
                        </td>
                        <td class="px-6 py-4 whitespace-nowrap text-center">
                            <span class="inline-flex items-center px-3 py-1 rounded-full text-xs font-semibold transition-all duration-200
                                ${newsletter.enable ?
                                    'bg-green-100 text-green-800 border border-green-200' :
                                    'bg-red-100 text-red-800 border border-red-200'}">
                                <span class="w-2 h-2 rounded-full mr-2 ${newsletter.enable ? 'bg-green-400' : 'bg-red-400'}"></span>
                                ${newsletter.enable ? 'Enable' : 'Disable'}
                            </span>
                        </td>
                        <td class="px-6 py-4 whitespace-nowrap text-center">
                            <div class="flex items-center justify-center gap-2">
                                <!-- Sửa -->
                                <a href="${pageContext.request.contextPath}/admin/newsletter?editEmail=${newsletter.email}"
                                   class="inline-flex items-center px-3 py-2 border border-blue-300 text-xs font-medium rounded-md text-blue-700 bg-blue-50 hover:bg-blue-100 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 transition-all duration-200 transform hover:scale-105">
                                    <svg class="w-3 h-3 mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z"/>
                                    </svg>
                                    Sửa
                                </a>

                                <!-- Xoá -->
                                <form action="${pageContext.request.contextPath}/admin/newsletter"
                                      method="post" style="display:inline;"
                                      onsubmit="return confirm('⚠️ Bạn có chắc chắn muốn xoá email này?\n\nHành động này không thể hoàn tác!');">
                                    <input type="hidden" name="action" value="delete"/>
                                    <input type="hidden" name="email" value="${newsletter.email}"/>
                                    <button type="submit"
                                            class="inline-flex items-center px-3 py-2 border border-red-300 text-xs font-medium rounded-md text-red-700 bg-red-50 hover:bg-red-100 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-red-500 transition-all duration-200 transform hover:scale-105">
                                        <svg class="w-3 h-3 mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"/>
                                        </svg>
                                        Xoá
                                    </button>
                                </form>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty newsletterList}">
                    <tr>
                        <td colspan="4" class="px-6 py-12 text-center">
                            <div class="flex flex-col items-center justify-center text-gray-500">
                                <svg class="w-12 h-12 mb-4 text-gray-300" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 8l7.89 4.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z"/>
                                </svg>
                                <p class="text-lg font-medium text-gray-400 mb-2">Chưa có newsletter nào</p>
                                <p class="text-sm text-gray-400">Thêm newsletter đầu tiên bằng form ở trên</p>
                            </div>
                        </td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>
</div>

<script>
    window.addEventListener('DOMContentLoaded', () => {
        const toastSuccess = document.getElementById('toast-success');
        const toastError = document.getElementById('toast-error');

        if (toastSuccess) {
            setTimeout(() => toastSuccess.remove(), 2000);
        }

        if (toastError) {
            setTimeout(() => toastError.remove(), 2000);
        }
    });
</script>