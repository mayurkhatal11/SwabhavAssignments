<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="../includes/header.jsp">
    <jsp:param name="title" value="Add Customer"/>
</jsp:include>
<%@ include file="../includes/navbar.jsp" %>

<div class="container mt-4">
    <h2>Add New Customer</h2>
    <hr>
    <div class="row justify-content-center">
        <div class="col-lg-8">

            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger" role="alert">
                    ${errorMessage}
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/admin/addCustomer" method="post" class="mt-3">
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label for="firstName" class="form-label">First Name:</label>
                        <input type="text" class="form-control" id="firstName" name="firstName" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="lastName" class="form-label">Last Name:</label>
                        <input type="text" class="form-control" id="lastName" name="lastName" required>
                    </div>
                </div>
                <div class="mb-3">
                    <label for="email" class="form-label">Email:</label>
                    <input type="email" class="form-control" id="email" name="email" required>
                </div>
                <div class="mb-3">
                    <label for="password" class="form-label">Password:</label>
                    <input type="password" class="form-control" id="password" name="password" required>
                </div>
                <div class="mb-3">
                    <label for="initialBalance" class="form-label">Initial Balance:</label>
                    <div class="input-group">
                      <span class="input-group-text">Rs.</span>
                      <input type="number" step="0.01" class="form-control" id="initialBalance" name="initialBalance" value="0.00" required>
                    </div>
                    <div class="form-text">Enter 0 for a zero-balance account. Cannot be negative.</div>
                </div>
                <button type="submit" class="btn btn-success">Create Customer Account</button>
                <a href="${pageContext.request.contextPath}/admin/dashboard" class="btn btn-secondary">Cancel</a>
            </form>
        </div>
    </div>
</div>

<%@ include file="../includes/footer.jsp" %>