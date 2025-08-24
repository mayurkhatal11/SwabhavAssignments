package com.bankapp.controller;

import java.io.IOException;

import com.bankapp.model.User;
import com.bankapp.service.CustomerService;
import com.bankapp.service.ICustomerService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/customer/*")
public class CustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final ICustomerService customerService = new CustomerService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("user") == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}

		User customer = (User) session.getAttribute("user");

		// Refresh customer data in the session to ensure it's always up-to-date
		customer = customerService.getCustomerDetails(customer.getAccountNumber());
		session.setAttribute("user", customer);

		String action = getAction(request);

		switch (action) {
		case "viewPassbook":
			request.setAttribute("transactions", customerService.getPassbook(customer.getAccountNumber()));
			request.getRequestDispatcher("/WEB-INF/views/customer/viewPassbook.jsp").forward(request, response);
			break;
		case "newTransaction":
			request.getRequestDispatcher("/WEB-INF/views/customer/newTransaction.jsp").forward(request, response);
			break;
		case "editProfile":
			request.getRequestDispatcher("/WEB-INF/views/customer/editProfile.jsp").forward(request, response);
			break;
		case "dashboard":
		default:
			request.getRequestDispatcher("/WEB-INF/views/customer/customerDashboard.jsp").forward(request, response);
			break;
		}
	}

	/**
	 * Handles POST requests from customer forms (transactions, profile updates).
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("user") == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}

		User customer = (User) session.getAttribute("user");
		String action = getAction(request);

		if ("editProfile".equals(action)) {

			String newPlainPassword = request.getParameter("password");
			customer.setFirstName(request.getParameter("firstName"));
			customer.setLastName(request.getParameter("lastName"));

			customerService.updateUserProfile(customer, newPlainPassword);

			session.setAttribute("flashMessage", "Profile updated successfully!");
			session.setAttribute("flashMessageType", "success");

			response.sendRedirect(request.getContextPath() + "/customer/dashboard");

		} else if ("newTransaction".equals(action)) {
			String transactionType = request.getParameter("transactionType");

			try {
				// Determine which amount field to parse based on the transaction type
				String amountString = "credit".equals(transactionType) ? request.getParameter("creditAmount")
						: request.getParameter("transferAmount");

				if (amountString.trim().isEmpty() || amountString == null) {
					throw new Exception("Amount cannot be empty.");
				}

				double amount = Double.parseDouble(amountString);

				if ("credit".equals(transactionType)) {
					// Call the service to perform the credit
					customerService.makeCredit(customer.getAccountNumber(), amount);
					session.setAttribute("flashMessage", "Amount credited successfully!");
					session.setAttribute("flashMessageType", "success");
				} else if ("transfer".equals(transactionType)) {
					String recipientAccount = request.getParameter("toAccount");
					// Call the service to perform the transfer
					customerService.makeTransfer(customer.getAccountNumber(), recipientAccount, amount);
					session.setAttribute("flashMessage", "Transfer successful!");
					session.setAttribute("flashMessageType", "success");
				}
			} catch (NumberFormatException e) {
				session.setAttribute("flashMessage", "Invalid amount entered. Please use numbers only.");
				session.setAttribute("flashMessageType", "danger");
			} catch (Exception e) {
				// Catches all business rule exceptions from the service layer
				// (e.g., "Insufficient Balance", "Amount must be positive")
				session.setAttribute("flashMessage", e.getMessage());
				session.setAttribute("flashMessageType", "danger");
			}

			response.sendRedirect(request.getContextPath() + "/customer/viewPassbook");
		}
	}

	/**
	 * Helper method to get the action from the URL path (e.g., /dashboard) or a
	 * parameter.
	 */
	private String getAction(HttpServletRequest request) {
		String pathInfo = request.getPathInfo();
		if (pathInfo != null && !"/".equals(pathInfo)) {
			// Return the action from the path, removing the leading slash (e.g.,
			// "dashboard")
			return pathInfo.substring(1);
		}
		// Fallback for parameter-based actions, with "dashboard" as the default
		String actionParam = request.getParameter("action");
		return (actionParam == null) ? "dashboard" : actionParam;
	}
}