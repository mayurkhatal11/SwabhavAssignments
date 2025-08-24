<%@ page import="java.util.List, com.bankapp.model.Transaction, com.bankapp.model.User, java.text.SimpleDateFormat" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="../includes/header.jsp">
    <jsp:param name="title" value="Passbook"/>
</jsp:include>
<%@ include file="../includes/navbar.jsp" %>

<div class="container mt-4">
    <h2 class="mb-3">Passbook</h2>

    <!-- 
      ==========================================================================
      THE CRITICAL FIX IS HERE: This block reads and displays the message
      that was set in the session by the CustomerServlet after a transaction.
      ==========================================================================
    -->
    <c:if test="${not empty sessionScope.flashMessage}">
        <div class="alert alert-${sessionScope.flashMessageType} alert-dismissible fade show" role="alert">
            ${sessionScope.flashMessage}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
        <%-- This is crucial: Remove the attributes from the session after displaying them once --%>
        <c:remove var="flashMessage" scope="session"/>
        <c:remove var="flashMessageType" scope="session"/>
    </c:if>
    <!-- ========================================================================== -->


    <div class="table-responsive">
        <table class="table table-bordered table-hover passbook-table">
            <thead class="table-light">
                <tr>
                    <th>Date</th>
                    <th>Description</th>
                    <th class="text-end">Amount</th>
                    <th class="text-end">Available Balance</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Transaction> transactions = (List<Transaction>) request.getAttribute("transactions");
                    User customer = (User) session.getAttribute("user");
                    
                    if (transactions != null && !transactions.isEmpty()) {
                        double runningBalance = customer.getBalance();
                        
                        for (Transaction txn : transactions) {
                            String amountClass = txn.getAmount() > 0 ? "credit" : "transfer";
                            String formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(txn.getTransactionDate());
                %>
                <tr>
                    <td><%= formattedDate %></td>
                    <td><%= txn.getDescription() %></td>
                    <td class="text-end <%= amountClass %>">
                        <%= (txn.getAmount() > 0 ? "+" : "") + String.format("%.2f", txn.getAmount()) %>
                    </td>
                    <td class="text-end">
                        Rs. <%= String.format("%.2f", runningBalance) %>
                    </td>
                </tr>
                <%
                            runningBalance = runningBalance - txn.getAmount();
                        }
                    } else {
                %>
                    <tr><td colspan="4" class="text-center">No transactions have been recorded for your account.</td></tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </div>
</div>

<%@ include file="../includes/footer.jsp" %>