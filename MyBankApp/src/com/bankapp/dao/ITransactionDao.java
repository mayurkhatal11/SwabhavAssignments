package com.bankapp.dao;

import java.util.List;

import com.bankapp.model.Transaction;

public interface ITransactionDao {
    boolean addTransaction(Transaction transaction);
    List<Transaction> getAllTransactions();
    List<Transaction> getTransactionsByAccountNumber(String accountNumber);
}