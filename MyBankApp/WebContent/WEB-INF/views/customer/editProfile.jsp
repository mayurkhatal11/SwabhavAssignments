<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="../includes/header.jsp">
    <jsp:param name="title" value="Edit Profile"/>
</jsp:include>
<%@ include file="../includes/navbar.jsp" %>

<div class="container mt-4">
    <h2>Edit Profile</h2>
    <hr>
     <div class="row justify-content-center">
        <div class="col-lg-8">
            <c:if test="${not empty successMessage}">
                <div class="alert alert-success" role="alert">${successMessage}</div>
            </c:if>
            <form action="${pageContext.request.contextPath}/customer?action=editProfile" method="post" class="mt-3">
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label for="firstName" class="form-label">First Name:</label>
                        <input type="text" class="form-control" id="firstName" name="firstName" value="${sessionScope.user.firstName}" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="lastName" class="form-label">Last Name:</label>
                        <input type="text" class="form-control" id="lastName" name="lastName" value="${sessionScope.user.lastName}" required>
                    </div>
                </div>
                <div class="mb-3">
                    <label for="password" class="form-label">New Password:</label>
                    <input type="password" class="form-control" id="password" name="password">
                    <div class="form-text">Leave blank to keep your current password.</div>
                </div>
                <button type="submit" class="btn btn-primary">Update Profile</button>
                <a href="${pageContext.request.contextPath}/customer/dashboard" class="btn btn-secondary">Cancel</a>
            </form>
        </div>
    </div>
</div>

<%@ include file="../includes/footer.jsp" %>