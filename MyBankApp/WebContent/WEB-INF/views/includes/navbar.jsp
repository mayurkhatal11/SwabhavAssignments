<%@ page import="com.bankapp.model.User" %>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">LEO Bank</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <%
            // Check if the user is logged in
            if (session.getAttribute("user") != null) {
                User user = (User) session.getAttribute("user");
        %>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <% if ("admin".equals(user.getRole())) { %>
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a></li>
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/admin/addCustomer">Add Customer</a></li>
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/admin/viewCustomers">View Customers</a></li>
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/admin/viewTransactions">View Transactions</a></li>
                <% } else { %>
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/customer/dashboard">Dashboard</a></li>
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/customer/viewPassbook">Passbook</a></li>
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/customer/newTransaction">New Transaction</a></li>
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/customer/editProfile">Edit Profile</a></li>
                <% } %>
            </ul>
            <span class="navbar-text me-3">
              Welcome, <%= user.getFirstName() %>
            </span>
            <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline-light">Logout</a>
        </div>
        <% } %>
    </div>
</nav>