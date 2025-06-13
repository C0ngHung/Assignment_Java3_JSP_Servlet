<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="p-6 bg-white rounded shadow">
    <h1 class="text-2xl font-bold mb-4">Quản lý Newsletter</h1>

    <!-- Thông báo -->
    <c:if test="${not empty success}">
        <div class="bg-green-100 text-green-800 px-4 py-2 rounded mb-4">${success}</div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="bg-red-100 text-red-800 px-4 py-2 rounded mb-4">${error}</div>
    </c:if>

    <!-- Form thêm/cập nhật -->
    <form action="${pageContext.request.contextPath}/admin/newsletter" method="post" class="mb-6 space-y-4">
        <input type="hidden" name="action" value="${editNewsletter != null ? 'update' : 'create'}" />

        <!-- Email -->
        <div>
            <label class="block font-medium mb-1">Email:</label>
            <input type="email" name="email" required
                   value="${editNewsletter != null ? editNewsletter.email : ''}"
                   class="w-full px-4 py-2 border rounded"
                   ${editNewsletter != null ? "readonly" : ""} />
        </div>

        <!-- Trạng thái nếu đang sửa -->
        <c:if test="${editNewsletter != null}">
            <div>
                <label class="block font-medium mb-1">Trạng thái:</label>
                <select name="enable" class="w-full px-4 py-2 border rounded">
                    <option value="1" ${editNewsletter.enable ? "selected" : ""}>Enable</option>
                    <option value="0" ${!editNewsletter.enable ? "selected" : ""}>Disable</option>
                </select>
            </div>
        </c:if>

        <!-- Enable mặc định khi thêm -->
        <c:if test="${editNewsletter == null}">
            <input type="hidden" name="enable" value="1" />
        </c:if>

        <!-- Nút -->
        <div class="flex gap-2 flex-wrap">
            <button type="submit"
                    class="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700">
                ${editNewsletter != null ? "Cập nhật" : "Thêm"}
            </button>

            <c:if test="${editNewsletter != null}">
                <!-- Reset -->
                <a href="${pageContext.request.contextPath}/admin/newsletter?action=reset"
                   class="bg-gray-500 text-white px-4 py-2 rounded hover:bg-gray-600">
                    Reset
                </a>
            </c:if>
        </div>
    </form>

    <!-- Danh sách newsletter -->
    <table class="w-full table-auto border-collapse border">
        <thead class="bg-gray-100">
            <tr>
                <th class="border px-4 py-2 text-left">STT</th>
                <th class="border px-4 py-2 text-left">Email</th>
                <th class="border px-4 py-2 text-center">Trạng thái</th>
                <th class="border px-4 py-2 text-center">Hành động</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="newsletter" items="${newsletterList}" varStatus="loop">
                <tr>
                    <td class="border px-4 py-2">${loop.index + 1}</td>
                    <td class="border px-4 py-2">${newsletter.email}</td>
                    <td class="border px-4 py-2 text-center">
                        <span class="inline-block px-2 py-1 rounded text-sm font-medium
                            ${newsletter.enable ? 'bg-green-200 text-green-800' : 'bg-red-200 text-red-800'}">
                            ${newsletter.enable ? 'Enable' : 'Disable'}
                        </span>
                    </td>
                    <td class="border px-4 py-2 text-center space-x-2">
                        <!-- Sửa -->
                        <a href="${pageContext.request.contextPath}/admin/newsletter?editEmail=${newsletter.email}"
                           class="text-blue-600 hover:underline">Sửa</a>

                        <!-- Xoá -->
                        <form action="${pageContext.request.contextPath}/admin/newsletter"
                              method="post" style="display:inline;"
                              onsubmit="return confirm('Bạn có chắc chắn muốn xoá email này?');">
                            <input type="hidden" name="action" value="delete" />
                            <input type="hidden" name="email" value="${newsletter.email}" />
                            <button type="submit"
                                    class="text-red-600 hover:underline ml-2">Xoá</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
