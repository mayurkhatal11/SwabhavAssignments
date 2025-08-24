package com.bankapp.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

// This filter will apply to all requests within the /admin/ and /customer/ paths
@WebFilter(urlPatterns = {"/admin/*", "/customer/*"})
public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        // 1. Get the session, don't create one if it doesn't exist
        HttpSession session = httpRequest.getSession(false);
        
        // 2. Check if the user is logged in
        if (session == null || session.getAttribute("user") == null) {
            // User is not logged in. Redirect to the login page.
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
        } else {
            // User is logged in. Allow the request to proceed to the servlet.
            chain.doFilter(request, response);
        }
    }
    
    // init() and destroy() methods can be left empty for this example
}