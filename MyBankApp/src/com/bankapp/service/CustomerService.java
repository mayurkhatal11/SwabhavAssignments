package com.bankapp.service;

import java.util.List;

import com.bankapp.dao.ITransactionDao;
import com.bankapp.dao.IUserDao;
import com.bankapp.dao.TransactionDao;
import com.bankapp.dao.UserDao;
import com.bankapp.model.Transaction;
import com.bankapp.model.User;
import com.bankapp.util.PasswordUtil;

public class CustomerService implements ICustomerService {

	private final IUserDao userDao = new UserDao();
	private final ITransactionDao transactionDao = new TransactionDao();

	@Override
	public void makeCredit(String accountNumber, double amount) throws Exception {
		if (amount <= 0) {
			throw new Exception("Credit amount must be a positive number and above 0.");
		}
		User user = userDao.getUserByAccountNumber(accountNumber);
		userDao.updateUserBalance(accountNumber, user.getBalance() + amount);
		Transaction transaction = new Transaction();
		transaction.setAccountNumber(accountNumber);
		transaction.setType("credit");
		transaction.setAmount(amount);
		transaction.setDescription("Credited by self");
		transactionDao.addTransaction(transaction);
	}

	@Override
	public void makeTransfer(String senderAccountNumber, String recipientAccountNumber, double amount)
			throws Exception {

		if (amount <= 0) {
			throw new Exception("Transfer amount must be a positive number and above 0.");
		}
		if (senderAccountNumber.equals(recipientAccountNumber)) {
			throw new Exception("Cannot transfer funds to your own account.");
		}

		User sender = userDao.getUserByAccountNumber(senderAccountNumber);

		User recipient = userDao.getUserByAccountNumber(recipientAccountNumber);

		if (recipient == null) {
			throw new Exception("The recipient account number does not exist.");
		}

		if (sender.getBalance() < amount) {
			throw new Exception("Insufficient balance for this transfer. Your available balance is Rs. "
					+ String.format("%.2f", sender.getBalance()));
		}

		// Debit from sender
		double senderNewBalance = sender.getBalance() - amount;
		userDao.updateUserBalance(sender.getAccountNumber(), senderNewBalance);

		// Record debit for sender
		Transaction senderTxn = new Transaction();
		senderTxn.setAccountNumber(sender.getAccountNumber());
		senderTxn.setType("transfer");
		senderTxn.setAmount(amount * -1);
		senderTxn.setDescription("Transferred to account: " + recipientAccountNumber);
		if (!transactionDao.addTransaction(senderTxn)) {
			userDao.updateUserBalance(sender.getAccountNumber(), sender.getBalance());
			throw new Exception(
					"A system error occurred while recording the transaction. The transfer has been cancelled.");
		}

		// Credit to recipient
		double recipientNewBalance = recipient.getBalance() + amount;
		userDao.updateUserBalance(recipient.getAccountNumber(), recipientNewBalance);

		// Record credit for recipient
		Transaction recipientTxn = new Transaction();
		recipientTxn.setAccountNumber(recipient.getAccountNumber());
		recipientTxn.setType("credit");
		recipientTxn.setAmount(amount);
		recipientTxn.setDescription("Credited from account: " + senderAccountNumber);
		if (!transactionDao.addTransaction(recipientTxn)) {
			userDao.updateUserBalance(sender.getAccountNumber(), sender.getBalance()); // Revert sender
			userDao.updateUserBalance(recipient.getAccountNumber(), recipient.getBalance()); // Revert recipient
			throw new Exception("A critical system error occurred. The transfer has been cancelled.");
		}
	}

	@Override
	public void updateUserProfile(User user, String newPlainPassword) {
		if (newPlainPassword != null && !newPlainPassword.trim().isEmpty()) {
			user.setPassword(PasswordUtil.hashPassword(newPlainPassword));
		}
		userDao.updateUser(user);
	}

	@Override
	public List<Transaction> getPassbook(String accountNumber) {
		return transactionDao.getTransactionsByAccountNumber(accountNumber);
	}

	@Override
	public User getCustomerDetails(String accountNumber) {
		return userDao.getUserByAccountNumber(accountNumber);
	}
}
