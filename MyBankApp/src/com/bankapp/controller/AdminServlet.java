package com.bankapp.controller;

import java.io.IOException;
import java.util.List;

import com.bankapp.model.User;
import com.bankapp.service.AdminService;
import com.bankapp.service.IAdminService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/admin/*")
public class AdminServlet extends HttpServlet {
	private final IAdminService adminService = new AdminService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = getAction(request);

		switch (action) {
		case "addCustomer":
			request.getRequestDispatcher("/WEB-INF/views/admin/addCustomer.jsp").forward(request, response);
			break;
		case "viewCustomers":
			String searchQuery = request.getParameter("searchAccountNumber");
			List<User> customers;

			if (searchQuery != null && !searchQuery.trim().isEmpty()) {
				customers = adminService.searchCustomers(searchQuery);
				request.setAttribute("searchQuery", searchQuery);
			} else {
				customers = adminService.searchCustomers(null);
			}
			request.setAttribute("customers", customers);
			request.getRequestDispatcher("/WEB-INF/views/admin/viewCustomers.jsp").forward(request, response);
			break;
		case "viewTransactions":
			request.setAttribute("transactions", adminService.viewAllTransactions());
			request.getRequestDispatcher("/WEB-INF/views/admin/viewTransactions.jsp").forward(request, response);
			break;
		case "toggleStatus":
			adminService.toggleUserStatus(request.getParameter("accountNumber"), request.getParameter("status"));
			response.sendRedirect(request.getContextPath() + "/admin/viewCustomers");
			break;
		case "dashboard":
		default:
			request.getRequestDispatcher("/WEB-INF/views/admin/adminDashboard.jsp").forward(request, response);
			break;
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = getAction(request);
		if ("addCustomer".equals(action)) {
			try {
				User newUser = adminService.addNewCustomer(request.getParameter("firstName"),
						request.getParameter("lastName"), request.getParameter("email"),
						request.getParameter("password"), Double.parseDouble(request.getParameter("initialBalance")));
				HttpSession session = request.getSession();
				response.sendRedirect(request.getContextPath() + "/admin/viewCustomers");
			} catch (Exception e) {
				request.setAttribute("errorMessage", e.getMessage());
				request.getRequestDispatcher("/WEB-INF/views/admin/addCustomer.jsp").forward(request, response);
			}
		}
	}

	private String getAction(HttpServletRequest request) {
		String pathInfo = request.getPathInfo();
		if (pathInfo != null && !"/".equals(pathInfo)) {
			return pathInfo.substring(1);
		}
		String actionParam = request.getParameter("action");
		return (actionParam == null) ? "dashboard" : actionParam;
	}
}