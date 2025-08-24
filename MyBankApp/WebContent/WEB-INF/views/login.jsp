<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="includes/header.jsp">
    <jsp:param name="title" value="Login"/>
</jsp:include>

<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-6 col-lg-5 login-container">
            <div class="card shadow">
                <div class="card-body p-4">
                    <h2 class="card-title text-center mb-4">LEO Bank Login</h2>
                    <form action="${pageContext.request.contextPath}/login" method="post">
                        <div class="mb-3">
                            <label for="username" class="form-label">Username (Email/Account Number):</label>
                            <input type="text" class="form-control" id="username" name="username" required>
                        </div>
                        <div class="mb-3">
                            <label for="password" class="form-label">Password:</label>
                            <input type="password" class="form-control" id="password" name="password" required>
                        </div>
                        <div class="mb-3">
                            <label for="role" class="form-label">Login as:</label>
                            <select id="role" name="role" class="form-select">
                                <option value="customer">Customer</option>
                                <option value="admin">Admin</option>
                            </select>
                        </div>
                        <div class="d-grid">
                           <button type="submit" class="btn btn-primary">Login</button>
                        </div>
                    </form>
                    <c:if test="${not empty errorMessage}">
                        <div class="alert alert-danger mt-3" role="alert">
                            ${errorMessage}
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="includes/footer.jsp"/>