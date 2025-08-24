package com.bankapp.service;

import com.bankapp.model.User;

public interface IAuthenticationService {
    User login(String username, String plainPassword, String role) throws Exception;
}