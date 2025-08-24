package com.bankapp.dao;

import java.util.List;

import com.bankapp.model.User;

public interface IUserDao {
    void addCustomer(User user);
    User getUserByUsername(String username);
    List<User> getAllCustomers();
    List<User> searchCustomerByAccountNumber(String accountNumber);
    void updateUser(User user);
    void updateUserBalance(String accountNumber, double newBalance);
    void updateUserStatus(String accountNumber, String status);
    User getUserByAccountNumber(String accountNumber); // Add this if it's used publicly
}