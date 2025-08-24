package com.bankapp.controller;

import java.io.IOException;

import com.bankapp.model.User;
import com.bankapp.service.AuthenticationService;
import com.bankapp.service.IAuthenticationService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private final IAuthenticationService authService = new AuthenticationService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String plainPassword = request.getParameter("password");
		String role = request.getParameter("role");

		try {
			User user = authService.login(username, plainPassword, role);
			HttpSession session = request.getSession();
			session.setAttribute("user", user);

			if ("admin".equals(role)) {
				response.sendRedirect(request.getContextPath() + "/admin/dashboard");
			} else {
				response.sendRedirect(request.getContextPath() + "/customer/dashboard");
			}
		} catch (Exception e) {
			request.setAttribute("errorMessage", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
		}
	}
}