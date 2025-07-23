package com.aurionpro.test;

import com.aurionpro.model.BankAccount;
import com.aurionpro.model.InsufficientFundsException;
import com.aurionpro.model.NegativeAmountException;

public class BankAccountTest {
	public static void main(String[] args) {
		BankAccount ba = new BankAccount(1, "Raj", 20000);

		try {
			ba.deposit(-200);
		} catch (NegativeAmountException e) {
			e.printStackTrace();
		}

//		System.out.println(ba);

		try {
			ba.withdraw(30000);
		} catch (InsufficientFundsException e) {
			e.printStackTrace();
		}

//		System.out.println(ba);

	}
}
