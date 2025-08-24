<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="../includes/header.jsp">
    <jsp:param name="title" value="New Transaction"/>
</jsp:include>
<%@ include file="../includes/navbar.jsp" %>

<div class="container mt-4">
    <h2>New Transaction</h2>
    <hr>
    <div class="row justify-content-center">
        <div class="col-lg-8">
            
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger" role="alert">${errorMessage}</div>
            </c:if>
            <c:if test="${not empty successMessage}">
                <div class="alert alert-success" role="alert">${successMessage}</div>
            </c:if>

            <form action="${pageContext.request.contextPath}/customer?action=newTransaction" method="post" class="mt-3">
                <!-- The Dropdown Selector -->
                <div class="mb-3">
                    <label for="transactionType" class="form-label">Transaction Type:</label>
                    <select id="transactionType" name="transactionType" class="form-select">
                        <option value="credit">Credit (Self Deposit)</option>
                        <option value="transfer">Transfer</option>
                    </select>
                </div>

                <!-- Credit Fields Section -->
                <!-- This div is shown by default and will be hidden by JavaScript if 'Transfer' is selected. -->
                <div id="creditFields">
                    <div class="mb-3">
                        <label for="creditAccountNumber" class="form-label">Your Account Number:</label>
                        <input type="text" class="form-control" id="creditAccountNumber" name="creditAccountNumber" value="${sessionScope.user.accountNumber}" readonly>
                        <div class="form-text">For self-credit only.</div>
                    </div>
                     <div class="mb-3">
                        <label for="creditAmount" class="form-label">Amount to Credit:</label>
                        <div class="input-group">
                            <span class="input-group-text">Rs. </span>
                            <input type="number" step="0.01" class="form-control" id="creditAmount" name="creditAmount">
                        </div>
                    </div>
                </div>

                <!-- Transfer Fields Section -->
                <!-- This div is hidden by default and will be shown by JavaScript only if 'Transfer' is selected. -->
                <div id="transferFields">
                    <div class="mb-3">
                        <label for="fromAccount" class="form-label">From Account (Your Account):</label>
                        <input type="text" class="form-control" id="fromAccount" name="fromAccount" value="${sessionScope.user.accountNumber}" readonly>
                    </div>
                    <div class="mb-3">
                        <label for="toAccount" class="form-label">To Account (Recipient's Account Number):</label>
                        <input type="text" class="form-control" id="toAccount" name="toAccount">
                    </div>
                    <div class="mb-3">
                        <label for="transferAmount" class="form-label">Amount to Transfer:</label>
                        <div class="input-group">
                             <span class="input-group-text">Rs. </span>
                            <input type="number" step="0.01" class="form-control" id="transferAmount" name="transferAmount">
                        </div>
                    </div>
                </div>
                <button type="submit" class="btn btn-success">Submit Transaction</button>
                <a href="${pageContext.request.contextPath}/customer/dashboard" class="btn btn-secondary">Cancel</a>
            </form>
        </div>
    </div>
</div>

<%@ include file="../includes/footer.jsp" %>