<%@ page import="com.bankapp.model.Transaction"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../includes/header.jsp">
	<jsp:param name="title" value="View All Transactions" />
</jsp:include>
<%@ include file="../includes/navbar.jsp"%>

<div class="container mt-4">
	<h2 class="mb-3">All Bank Transactions</h2>
	<div class="table-responsive">
		<table class="table table-striped table-hover">
			<thead class="table-dark">
				<tr>
					<th>Transaction ID</th>
					<th>Account Number</th>
					<th>Type</th>
					<th>Amount</th>
					<th>Date/Time</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="txn" items="${transactions}">
					<tr>
						<td>${txn.id}</td>
						<td>${txn.accountNumber}</td>
						<td><span class="text-capitalize">${txn.type}</span></td>
						<td>Rs. <%=String.format("%.2f", ((Transaction) pageContext.getAttribute("txn")).getAmount())%></td>
						<%
						java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sdf.setTimeZone(java.util.TimeZone.getTimeZone("Asia/Kolkata")); // replace with your time zone
						String formattedDate = sdf.format(((Transaction) pageContext.getAttribute("txn")).getTransactionDate());
						%>
						<td><%=formattedDate%></td>
					</tr>
				</c:forEach>
				<c:if test="${empty transactions}">
					<tr>
						<td colspan="5" class="text-center">No transactions found.</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</div>
</div>

<%@ include file="../includes/footer.jsp"%>