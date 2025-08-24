<%@ page import="com.bankapp.model.User"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../includes/header.jsp">
	<jsp:param name="title" value="Customer Dashboard" />
</jsp:include>
<%@ include file="../includes/navbar.jsp"%>

<div class="container mt-4">
	
	<%
	User customer = (User) session.getAttribute("user");
	%>

	<c:if test="${not empty sessionScope.flashMessage}">
		<div
			class="alert alert-${sessionScope.flashMessageType} alert-dismissible fade show"
			role="alert">
			<strong>${sessionScope.flashMessage}</strong>
			<button type="button" class="btn-close" data-bs-dismiss="alert"
				aria-label="Close"></button>
		</div>
		<c:remove var="flashMessage" scope="session" />
		<c:remove var="flashMessageType" scope="session" />
	</c:if>

	<h2 class="mb-3">
		Welcome,
		<%=customer.getFirstName()%>!
	</h2>

	<!-- Current Balance Display Card -->
	<div class="card text-center text-white bg-success mb-4 shadow">
		<div class="card-header">Current Available Balance</div>
		<div class="card-body">
			<h1 class="card-title display-4">
				Rs. <%=String.format("%.2f", customer.getBalance())%></h1>
		</div>
		<div class="card-footer text-white-50">
			Account Number:
			<%=customer.getAccountNumber()%>
		</div>
	</div>

	<div class="row">
		<div class="col-md-4 mb-3">
			<div class="card h-100">
				<div class="card-body text-center d-flex flex-column">
					<h5 class="card-title">View Passbook</h5>
					<p class="card-text">Check your detailed transaction history.</p>
					<a href="${pageContext.request.contextPath}/customer/viewPassbook"
						class="btn btn-primary mt-auto">View History</a>
				</div>
			</div>
		</div>
		<div class="col-md-4 mb-3">
			<div class="card h-100">
				<div class="card-body text-center d-flex flex-column">
					<h5 class="card-title">Start New Transaction</h5>
					<p class="card-text">Credit funds to your account or transfer
						money.</p>
					<a
						href="${pageContext.request.contextPath}/customer/newTransaction"
						class="btn btn-primary mt-auto">Start Transaction</a>
				</div>
			</div>
		</div>
		<div class="col-md-4 mb-3">
			<div class="card h-100">
				<div class="card-body text-center d-flex flex-column">
					<h5 class="card-title">Edit Profile</h5>
					<p class="card-text">Update your personal information and
						password.</p>
					<a href="${pageContext.request.contextPath}/customer/editProfile"
						class="btn btn-primary mt-auto">Edit My Profile</a>
				</div>
			</div>
		</div>
	</div>
</div>

<%@ include file="../includes/footer.jsp"%>