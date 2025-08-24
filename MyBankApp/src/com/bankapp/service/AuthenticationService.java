package com.bankapp.service;

import com.bankapp.dao.IUserDao;
import com.bankapp.dao.UserDao;
import com.bankapp.model.User;
import com.bankapp.util.PasswordUtil;

public class AuthenticationService implements IAuthenticationService {

	private final IUserDao userDao = new UserDao();

	@Override
	public User login(String username, String plainPassword, String role) throws Exception {
		User user = userDao.getUserByUsername(username);

		if (user == null || !user.getRole().equals(role)) {
			throw new Exception("Invalid username or role.");
		}

		if (!"active".equals(user.getStatus())) {
			throw new Exception("Your account is inactive. Please contact an administrator.");
		}

		if (!PasswordUtil.verifyPassword(plainPassword, user.getPassword())) {
			throw new Exception("Invalid password.");
		}

		return user;
	}
}