package com.bankapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.bankapp.model.User;
import com.bankapp.util.DBConnection;

public class UserDao implements IUserDao {

	public User getUserByUsername(String username) {
		String query = "SELECT * FROM users WHERE email = ? OR account_number = ?";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setString(1, username);
			stmt.setString(2, username);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return extractUserFromResultSet(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void addCustomer(User user) {
		String query = "INSERT INTO users (first_name, last_name, email, password, account_number, balance, role) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setString(1, user.getFirstName());
			stmt.setString(2, user.getLastName());
			stmt.setString(3, user.getEmail());
			stmt.setString(4, user.getPassword());
			stmt.setString(5, user.getAccountNumber());
			stmt.setDouble(6, user.getBalance());
			stmt.setString(7, user.getRole());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<User> getAllCustomers() {
		List<User> customers = new ArrayList<>();
		String query = "SELECT * FROM users WHERE role = 'customer'";
		try (Connection conn = DBConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {
			while (rs.next()) {
				customers.add(extractUserFromResultSet(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customers;
	}
	
	public List<User> searchCustomerByAccountNumber(String accountNumber) {
	    List<User> customers = new ArrayList<>();
	    String query = "SELECT * FROM users WHERE account_number = ? AND role = 'customer'";
	    
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(query)) {
	        
	        stmt.setString(1, accountNumber);
	        ResultSet rs = stmt.executeQuery();
	   
	        while (rs.next()) {
	            customers.add(extractUserFromResultSet(rs));
	        }
	    } catch (SQLException e) {
	        System.err.println("Error searching for customer by account number: " + accountNumber);
	        e.printStackTrace();
	    }
	    return customers;
	}

	public void updateUser(User user) {
		String query = "UPDATE users SET first_name = ?, last_name = ?, password = ? WHERE id = ?";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setString(1, user.getFirstName());
			stmt.setString(2, user.getLastName());
			stmt.setString(3, user.getPassword());
			stmt.setInt(4, user.getId());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateUserStatus(String accountNumber, String status) {
	    String query = "UPDATE users SET status = ? WHERE account_number = ?";
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(query)) {
	        stmt.setString(1, status);
	        stmt.setString(2, accountNumber);
	        stmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	public void updateUserBalance(String accountNumber, double newBalance) {
	    String query = "UPDATE users SET balance = ? WHERE account_number = ?";
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(query)) {
	        stmt.setDouble(1, newBalance);
	        stmt.setString(2, accountNumber);
	        stmt.executeUpdate();
	    } catch (SQLException e) {
	        System.err.println("Error updating user balance for account: " + accountNumber);
	        e.printStackTrace();
	    }
	}

	public User getUserByAccountNumber(String accountNumber) {
		String query = "SELECT * FROM users WHERE account_number = ?";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setString(1, accountNumber);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return extractUserFromResultSet(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private User extractUserFromResultSet(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setFirstName(rs.getString("first_name"));
		user.setLastName(rs.getString("last_name"));
		user.setEmail(rs.getString("email"));
		user.setPassword(rs.getString("password"));
		user.setAccountNumber(rs.getString("account_number"));
		user.setBalance(rs.getDouble("balance"));
		user.setRole(rs.getString("role"));
		user.setStatus(rs.getString("status"));
		return user;
	}
}