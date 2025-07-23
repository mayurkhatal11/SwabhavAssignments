package com.aurionpro.model;

public class BankAccount {
	private int accountNumber;
	private String name;
	private double balance;

	public BankAccount(int accountNumber, String name, double balance) {
		super();
		this.accountNumber = accountNumber;
		this.name = name;
		this.balance = balance;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	public double deposit(double amount) throws NegativeAmountException {
		if(amount<0) {
			throw new NegativeAmountException("Enter positive amount");
			
		} else {
			return balance +=amount;
		}
	}
	
	public double withdraw(double amount) throws InsufficientFundsException  {
		if(this.balance-amount < 0) {
			throw new InsufficientFundsException("Insufficient balance. you can't withraw this much amount.");
			
		} else {
			return balance-=amount;
		}
	}

	@Override
	public String toString() {
		return "BankAccount [accountNumber=" + accountNumber + ", name=" + name + ", balance=" + balance + "]";
	}
	
	

}
