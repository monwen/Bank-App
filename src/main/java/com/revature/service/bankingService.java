package com.revature.service;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.revature.dao.AccountDao;
import com.revature.dao.TransactionDao;
import com.revature.models.Account;
import com.revature.models.Person;
import com.revature.models.Transaction;
import com.revature.utils.AppLogger;

public class bankingService {

	public static boolean doApplyNewAccount(Person p, Scanner s) {
//		while (true) {
			System.out.println("what type of account?");
			System.out.println("1. Checking");
			System.out.println("2. Saving");
			System.out.println("3. back to menu");
			try {
				int select = s.nextInt();
				switch (select) {
				case 1:
					System.out.println("applying for checking. \n");
					Float amount = doInitialDeposit(s);
					if (amount != null) {
						Account a = new Account();
						a.setCustomer_id(p.getId());
						a.setAccountType("checking");
						a.setApprove(false);
						a.setBalance(amount);
						AccountDao aDao = new AccountDao();
						aDao.create(a);
						System.out.println("Checking Account application submitted");
						return true;
					}
					break;
				case 2:
					System.out.println("applying for saving. \n");
					Float amountForSaving = doInitialDeposit(s);
					if (amountForSaving != null) {
						Account a = new Account();
						a.setCustomer_id(p.getId());
						a.setAccountType("saving");
						a.setApprove(false);
						a.setBalance(amountForSaving);
						AccountDao aDao = new AccountDao();
						aDao.create(a);
						System.out.println("Saving Account application submitted");
						return true;
					}
					break;
				case 3:
					System.out.println("back to menu");
					return false;
				default:
					System.out.println("back to menu");
					return false;
				}
//				break;
			} catch (Exception e) {
				s.nextLine();
				System.out.println("please input 1/2.");
			}
//		}

		return false;
	}

	public static Float doInitialDeposit(Scanner s) {
		while (true) {
			System.out.println("How much would you like to deposit initially?");
			try {
				Float amount = s.nextFloat();
				if (amount < 0) {
					System.out.println("invalid deposit");
					return null;
				}
				return amount;
			} catch (Exception e) {
				System.out.println("please enter a number");
			}

		}

	}

	public static boolean doDeposit(Account a, Float amount) {
		if (amount < 0) {
			return false;
		}
		AccountDao aDao = new AccountDao();
		a.setBalance(a.getBalance() + amount);
		TransactionDao tDao = new TransactionDao();
		Transaction t = new Transaction();
		t.setFrom(a.getId());
		t.setTo(a.getId());
		t.setMethodType("deposit");
		t.setAmount(amount);
		t.setApprove(true);
		tDao.create(t);
		LoggService.getTransLog();
		aDao.update(a);
		return true;

	}

	public static boolean doWithdrawal(Account a, Float amount) {

		AccountDao aDao = new AccountDao();
		Float balance = a.getBalance();
		if (amount > balance) {
			return false;
		}
		balance -= amount;
		a.setBalance(balance);
		TransactionDao tDao = new TransactionDao();
		Transaction t = new Transaction();
		t.setFrom(a.getId());
		t.setTo(a.getId());
		t.setMethodType("withdraw");
		t.setAmount(amount);
		t.setApprove(true);
		tDao.create(t);
		LoggService.getTransLog();
		aDao.update(a);
		return true;
	}

	public static boolean doTransfer(Account a, Float amount, Account b) {
		AccountDao aDao = new AccountDao();
		Float balance = a.getBalance();
		if (amount > balance) {
			return false;
		}

		System.out.println("check b amount before: " + b);

		a.setBalance(balance - amount);
		b.setBalance(b.getBalance() + amount);
		System.out.println("check b amount after: " + b.getBalance());
		aDao.update(a);
		aDao.update(b);
		return true;

	}

	public static boolean doTransfer(Account a, Scanner s, List<Account> aList) {
		Integer b_id;
		Float amount;
		Account accountB;
		while (true) {
			System.out.println("please enter the account id you would like to transfer to");
			try {
				Integer temp_id = s.nextInt();
				accountB = aList.stream().filter(account -> account.getId().equals(temp_id)
						&& account.isApprove() == true && !account.getId().equals(a.getId())).findFirst().orElse(null);
				System.out.println(accountB);
				b_id = temp_id;
				break;
			} catch (Exception e) {
				System.out.println("plese enter a number");
			}
		}

		while (true) {
			System.out.println("please enter the amount to transfer");
			try {
				amount = s.nextFloat();
				if (a.getBalance() < amount) {
					System.out.println("invalid transaction.");
					return false;
				}
				break;
			} catch (Exception e) {
				System.out.println("plese enter a number");
			}
		}

		AccountDao aDao = new AccountDao();

		TransactionDao ts = new TransactionDao();
		if (accountB == null) {
			List<Account> extAccount = aDao.getAll();

			if (extAccount != null) {
				Integer temp_id = b_id;
				accountB = extAccount.stream().filter(account -> account.getId().equals(temp_id)
						&& account.isApprove() == true && !account.getId().equals(a.getId())).findFirst().orElse(null);
				if (accountB != null) {
					Transaction t = new Transaction();
					t.setFrom(a.getId());
					t.setTo(accountB.getId());
					t.setAmount(amount);
					t.setMethodType("transfer");
					t.setApprove(false);
					ts.create(t);
					LoggService.getTransLog();
					return true;
				}
			}
		} else if (a.getCustomer_id().equals(accountB.getCustomer_id())) {
			System.out.println("Same user transfer");
			Transaction t = new Transaction();
			t.setFrom(a.getId());
			t.setTo(accountB.getId());
			t.setAmount(amount);
			t.setMethodType("self transfer");
			t.setApprove(true);
			ts.create(t);
			LoggService.getTransLog();
			a.setBalance(a.getBalance() - amount);
			accountB.setBalance(accountB.getBalance() + amount);
			aDao.update(a);
			aDao.update(accountB);
			return true;
		}

		return false;

	}

	public static void doArrpoveTrans(Account a, Scanner s, List<Account> alist) {
		TransactionDao ts = new TransactionDao();
		AccountDao aDao = new AccountDao();
		List<Transaction> tList = ts.getByToAccount(a.getId());
		tList = tList.stream().filter(t -> t.isApprove() == false).collect(Collectors.toList());
		if (tList != null) {
			tList.forEach(t -> {
				System.out
						.println("from: " + t.getFrom() + " amount: " + t.getAmount() + " time: " + t.getTime() + "\n");
				String input;
				while (true) {
					System.out.println("approve transaction:? y/n");
					try {
						String r = s.next();
						input = r;
						break;
					} catch (Exception e) {
						s.nextLine();
						System.out.println("please enter y/n");
					}
				}

				if (input.toLowerCase().equals("y")) {

					Account newFrom = aDao.getById(t.getFrom());
					if (newFrom.getBalance() > t.getAmount()) {
						newFrom.setBalance(newFrom.getBalance() - t.getAmount());
						a.setBalance(a.getBalance() + t.getAmount());
						t.setApprove(true);
						ts.update(t);
						aDao.update(a);
						aDao.update(newFrom);
					} else {
						System.out.println("not sufficient amount");
					}

					System.out.println("transfer complete");
				}

			});
			alist = aDao.getAllbyCustomer_id(a.getCustomer_id());
		}
		System.out.println("no transaction to approve");

	}

	public static Transaction doViewTransactionById(Integer trans_id) {
		TransactionDao tDao = new TransactionDao();
		return tDao.getById(trans_id);
	}
	public static void doViewTransactionByAccId(Integer c_id){
		TransactionDao tDao = new TransactionDao();
		
		List<Transaction>  transList = tDao.getAll();
		transList = transList.stream().filter(t -> t.getFrom() == c_id).collect(Collectors.toList());
		System.out.println("------------------------------------------------------------------------------------------------------------------ \n");
		if(transList != null) {
			AccountDao aDao = new AccountDao();
			transList.forEach(t ->{
				Account a = aDao.getById(t.getFrom());
				Integer trans_id = t.getId();
				Integer account_id = t.getTo();
				String accountType = a.getAccountType();
				Float amount = t.getAmount();
				String methodType = t.getMethodType();
				Boolean approve = t.isApprove();
				Timestamp ts = t.getTime();
				System.out.print("transaction id: "
									+ trans_id + " to account: " 
									+ account_id + ", account type: " 
									+ accountType 
									+ ", method: "
									+ methodType
									+", amount: "
									+ amount
									+ ", status: "
									);
				System.out.println(t.isApprove()?"accepted": "pending \n");

			});
			System.out.println("");
			System.out.println("------------------------------------------------------------------------------------------------------------------ \n");
			return;
		}
		
		System.out.println("no transaction made by this account");
	}

	public static List<Transaction> doViewAllTransaction() {
		TransactionDao tDao = new TransactionDao();
		return tDao.getAll();
	}

	public static List<Transaction> doViewTransFromAccount(Integer from) {
		TransactionDao tDao = new TransactionDao();
		return tDao.getByFromAccount(from);
	}

	public static List<Transaction> doViewTransToAccount(Integer to) {
		TransactionDao tDao = new TransactionDao();
		return tDao.getByToAccount(to);
	}
}
