<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="../includes/header.jsp">
    <jsp:param name="title" value="View Customers"/>
</jsp:include>
<%@ include file="../includes/navbar.jsp" %>

<div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
       <h2>Manage Customers</h2>
       <a href="${pageContext.request.contextPath}/admin/addCustomer" class="btn btn-primary">Add New Customer</a>
    </div>
    
    <div class="card card-body mb-4">
        <form method="GET" action="${pageContext.request.contextPath}/admin/viewCustomers" class="row g-3">
            <div class="col-md-9">
                <input type="text" class="form-control" name="searchAccountNumber" 
                       placeholder="Enter Account Number to filter the list..." 
                       value="${searchQuery}"  
                >
            </div>
            <div class="col-md-3 d-grid gap-2 d-md-flex justify-content-md-end">
                <button type="submit" class="btn btn-info">Search</button>
                <a href="${pageContext.request.contextPath}/admin/viewCustomers" class="btn btn-secondary">Clear Filter</a>
            </div>
        </form>
    </div>

    <c:if test="${not empty message}">
        <div class="alert alert-success" role="alert">
            ${message}
        </div>
    </c:if>

    <div class="table-responsive">
        <table class="table table-striped table-hover">
            <thead class="table-dark">
                <tr>
                    <th>Account Number</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Status</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="customer" items="${customers}">
                    <tr>
                        <td>${customer.accountNumber}</td>
                        <td>${customer.firstName} ${customer.lastName}</td>
                        <td>${customer.email}</td>
                        <td>
                           <span class="badge bg-${customer.status == 'active' ? 'success' : 'danger'}">${customer.status}</span>
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/admin/toggleStatus?accountNumber=${customer.accountNumber}&status=${customer.status}" 
                               class="btn btn-sm btn-${customer.status == 'active' ? 'warning' : 'success'}">
                               ${customer.status == 'active' ? 'Deactivate' : 'Activate'}
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                
                <%-- This special message block handles the case where the list is empty --%>
                <c:if test="${empty customers}">
                   <tr>
                       <td colspan="5" class="text-center">
                           <c:choose>
                               <c:when test="${not empty searchQuery}">
                                   
                                   No customer found with account number: <strong>${searchQuery}</strong>
                               </c:when>
                               <c:otherwise>
                              
                                   There are no customers in the bank. Click "Add New Customer" to begin.
                               </c:otherwise>
                           </c:choose>
                       </td>
                   </tr>
                </c:if>
            </tbody>
        </table>
    </div>
</div>

<%@ include file="../includes/footer.jsp" %>