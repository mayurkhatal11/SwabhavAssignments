package com.bankapp.service;

import java.util.List;

import com.bankapp.model.Transaction;
import com.bankapp.model.User;

public interface IAdminService {
	

	User addNewCustomer(String firstName, String lastName, String email, String plainPassword, double initialBalance)
			throws Exception;
	
	List<User> searchCustomers(String accountNumber);

	void toggleUserStatus(String accountNumber, String currentStatus);

	List<Transaction> viewAllTransactions();
}