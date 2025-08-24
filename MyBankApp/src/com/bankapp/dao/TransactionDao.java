package com.bankapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.bankapp.model.Transaction;
import com.bankapp.util.DBConnection;

public class TransactionDao implements ITransactionDao {

	@Override
	public boolean addTransaction(Transaction transaction) {
		String query = "INSERT INTO transactions (account_number, type, amount, description) VALUES (?, ?, ?, ?)";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setString(1, transaction.getAccountNumber());
			stmt.setString(2, transaction.getType());
			stmt.setDouble(3, transaction.getAmount());
			stmt.setString(4, transaction.getDescription());

			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			System.err.println(
					"CRITICAL ERROR: Failed to add transaction for account: " + transaction.getAccountNumber());
			e.printStackTrace();
			return false;
		}
	}

	public List<Transaction> getAllTransactions() {
		List<Transaction> transactions = new ArrayList<>();
		String query = "SELECT * FROM transactions ORDER BY id DESC";
		try (Connection conn = DBConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {
			while (rs.next()) {
				transactions.add(extractTransactionFromResultSet(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return transactions;
	}

	public List<Transaction> getTransactionsByAccountNumber(String accountNumber) {
		List<Transaction> transactions = new ArrayList<>();
		String query = "SELECT * FROM transactions WHERE account_number = ? ORDER BY transaction_date DESC";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setString(1, accountNumber);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				transactions.add(extractTransactionFromResultSet(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return transactions;
	}

	private Transaction extractTransactionFromResultSet(ResultSet rs) throws SQLException {
		Transaction transaction = new Transaction();
		transaction.setId(rs.getInt("id"));
		transaction.setAccountNumber(rs.getString("account_number"));
		transaction.setType(rs.getString("type"));
		transaction.setAmount(rs.getDouble("amount"));
		transaction.setDescription(rs.getString("description"));
		transaction.setTransactionDate(rs.getTimestamp("transaction_date"));
		return transaction;
	}
}
