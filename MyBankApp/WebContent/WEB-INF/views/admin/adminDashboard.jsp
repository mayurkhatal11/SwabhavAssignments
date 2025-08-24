<jsp:include page="../includes/header.jsp">
    <jsp:param name="title" value="Admin Dashboard"/>
</jsp:include>
<%@ include file="../includes/navbar.jsp" %>

<div class="container mt-4">
    <div class="p-5 mb-4 bg-light rounded-3">
      <div class="container-fluid py-5">
        <h1 class="display-5 fw-bold">Admin Dashboard</h1>
        <p class="col-md-8 fs-4">Use the tools below to manage customers and view bank-wide transactions.</p>
      </div>
    </div>

    <div class="row">
        <div class="col-md-4 mb-3">
            <div class="card h-100">
                <div class="card-body text-center">
                    <h5 class="card-title">Add New Customer</h5>
                    <p class="card-text">Create a new customer account.</p>
                    <a href="${pageContext.request.contextPath}/admin?action=addCustomer" class="btn btn-primary">Go to Add Customer</a>
                </div>
            </div>
        </div>
        <div class="col-md-4 mb-3">
            <div class="card h-100">
                <div class="card-body text-center">
                    <h5 class="card-title">View All Customers</h5>
                    <p class="card-text">Manage and view all registered customers.</p>
                    <a href="${pageContext.request.contextPath}/admin?action=viewCustomers" class="btn btn-primary">Go to View Customers</a>
                </div>
            </div>
        </div>
        <div class="col-md-4 mb-3">
            <div class="card h-100">
                <div class="card-body text-center">
                    <h5 class="card-title">View All Transactions</h5>
                    <p class="card-text">Review all transactions across the bank.</p>
                    <a href="${pageContext.request.contextPath}/admin?action=viewTransactions" class="btn btn-primary">Go to View Transactions</a>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="../includes/footer.jsp" %>