package com.bankapp.service;

import java.util.List;
import java.util.UUID;

import com.bankapp.dao.ITransactionDao;
import com.bankapp.dao.IUserDao;
import com.bankapp.dao.TransactionDao;
import com.bankapp.dao.UserDao;
import com.bankapp.model.Transaction;
import com.bankapp.model.User;
import com.bankapp.util.PasswordUtil;

public class AdminService implements IAdminService {

	private final IUserDao userDao = new UserDao();
	private final ITransactionDao transactionDao = new TransactionDao();

	

	@Override
	public User addNewCustomer(String firstName, String lastName, String email, String plainPassword,
			double initialBalance) throws Exception {
		if (initialBalance < 0) {
			throw new Exception("Initial balance cannot be negative.");
		}

		User newUser = new User();
		newUser.setFirstName(firstName);
		newUser.setLastName(lastName);
		newUser.setEmail(email);
		newUser.setPassword(PasswordUtil.hashPassword(plainPassword));
		newUser.setAccountNumber("ACC" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
		newUser.setRole("customer");
		newUser.setBalance(initialBalance);

		userDao.addCustomer(newUser);

		if (initialBalance > 0) {
			Transaction initialDeposit = new Transaction();
			initialDeposit.setAccountNumber(newUser.getAccountNumber());
			initialDeposit.setType("credit");
			initialDeposit.setAmount(initialBalance);
			initialDeposit.setDescription("Initial deposit on account creation");
			transactionDao.addTransaction(initialDeposit);
		}
		return newUser;
	}
	
	@Override
	public List<User> searchCustomers(String accountNumber) {
		if (accountNumber == null || accountNumber.trim().isEmpty()) {
			return userDao.getAllCustomers();
		}
		return userDao.searchCustomerByAccountNumber(accountNumber);
	}

	@Override
	public List<Transaction> viewAllTransactions() {
		return transactionDao.getAllTransactions();
	}
	
	@Override
	public void toggleUserStatus(String accountNumber, String currentStatus) {
		String newStatus = "active".equals(currentStatus) ? "inactive" : "active";
		userDao.updateUserStatus(accountNumber, newStatus);
	}
}