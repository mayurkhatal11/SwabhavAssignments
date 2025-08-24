package com.bankapp.service;

import java.util.List;

import com.bankapp.model.Transaction;
import com.bankapp.model.User;

public interface ICustomerService {
	void makeTransfer(String senderAccount, String recipientAccount, double amount) throws Exception;

	void makeCredit(String accountNumber, double amount) throws Exception;

	void updateUserProfile(User user, String newPlainPassword);

	List<Transaction> getPassbook(String accountNumber);

	User getCustomerDetails(String accountNumber);
}