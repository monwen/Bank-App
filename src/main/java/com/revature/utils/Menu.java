package com.revature.utils;

import java.util.List;
import java.util.Scanner;

import com.revature.dao.AccountDao;
import com.revature.models.Account;
import com.revature.models.Person;
import com.revature.service.bankingService;
import com.revature.service.loginService;

public class Menu {
	public static void registrationPage(Scanner s) {

		while (true) {
			System.out.println("first name:");

			s.nextLine();
			String firstname = s.nextLine();

			System.out.println("last name:");

			String lastname = s.nextLine();

			System.out.println("user's name:");

			String username = s.nextLine();

			String pass;

			String passConfirm;

			while (true) {
				System.out.println("password:");

				pass = s.nextLine();

				System.out.println("confirm password: ");

				passConfirm = s.nextLine();
				if (pass.equals(passConfirm)) {
					break;
				} else {
					System.out.println("password mismatch, please re-enter password");
				}
			}

			Person p = new Person(firstname, lastname, username, pass, "customer");

			System.out.println("you have enter:" + "first name: " + firstname + "\n last name: " + lastname
					+ "\n user name: " + username + "\n are they correct? Y/N");
			String correct = s.next();
			correct = correct.toLowerCase();

			if (correct.equals("y")) {
				loginService.userRegister(firstname, lastname, username, pass);
				System.out.println("registration successful");
				return;
			}
		}

	}

	public static void loginPage(Scanner s) {

		while (true) {
			System.out.println("username:");

			s.nextLine();
			String username = s.nextLine();

			System.out.println("password:");

			String pass = s.nextLine();

			Person p = loginService.userLogin(username, pass);
			if (p != null) {
				System.out.println("login successful");

				if (p.getPersonType().equals("customer")) {

					List<Account> aList = checkAccount(p);
					if (aList.size() > 0) {
						accountMenu(s, aList);
						return;
					}

					return;
				} else if (p.getPersonType().equals("employee")) {
					employeeMenu(s);
					return;
				}

//				System.out.println("waiting for approve");
//				s.nextLine();
				return;
			} else {
				System.out.println("login unsuccessful");
				return;
			}

		}
	}

	public static void customerMenu(Scanner s, Account a, List<Account> alist) {
		while (true) {
			System.out.println("------------------------");
			System.out.printf("amount: %.2f \n", a.getBalance());
			System.out.println("------------------------");
			System.out.println("1. deposit \n");
			System.out.println("2. withdraw \n");
			System.out.println("3. transfer \n");
			System.out.println("4. balance \n");
			System.out.println("5. back \n");

			System.out.println("What would you like to do?");

			try {
				int option = s.nextInt();
				switch (option) {
				case 1:
					System.out.println("deposit selected");
					while (true) {
						System.out.println("please enter the deposit amount");
						try {
							float amount;

							while (true) {
								amount = s.nextFloat();
								System.out.println("You have entered: " + amount + ". Is this the correct amount? Y/N");
								String amountCheck = s.next();
								if (amountCheck.toLowerCase().equals("y")) {
									break;
								}
								System.out.println("Please enter the correct amount");
							}

							if (bankingService.doDeposit(a, amount)) {
								System.out.println("deposit successful");
								break;
							}
							System.out.println("deposit unsuccessful. Please try again.");
							break;

						} catch (Exception e) {
							s.nextLine();
							System.out.println("please enter a numer of amount");
						}

					}

					break;
				case 2:
					System.out.println("withdraw selected");
					while (true) {
						System.out.println("please enter the withdrawal amount");
						try {
							float amount;

							while (true) {
								amount = s.nextFloat();
								System.out.println("You have entered: " + amount + ". Is this the correct amount? Y/N");
								String amountCheck = s.next();
								if (amountCheck.toLowerCase().equals("y")) {
									break;
								}
								System.out.println("Please enter the correct amount");
							}

							if (bankingService.doWithdrawal(a, amount)) {
								System.out.println("deposit successful");
								break;
							}
							System.out.println("withdraw unsuccessful. Please try again.");
							break;

						} catch (Exception e) {
							s.nextLine();
							System.out.println("please enter a numer of amount");
						}

					}
					break;
				case 3:
					System.out.println("transfer selected");
					Integer account_id;
					float amount;
					AccountDao aDao = new AccountDao();
					Account b;
					while (true) {
						
						
						try {
							System.out.println("please enter the account id of the account that you would like to transfer to");
							while(true) {
								try {
									account_id = s.nextInt();
									List<Account> allAccounts = aDao.getAll();
									Integer check_id = account_id;
									b = allAccounts.stream().filter(account -> account.getId().equals(check_id)).findFirst().orElse(null);
									if(b == null) {
										System.out.println("account not found");
										return;
									}
									
									
									break;
								}catch(Exception e) {
									s.nextLine();
									System.out.println("Please enter a number for id");
								}
								
							}
							
							
							while (true) {
								System.out.println("please enter the transfer amount");
								amount = s.nextFloat();
								System.out.println("You have entered: " + amount + ". Is this the correct amount? Y/N");
								String amountCheck = s.next();
								if (amountCheck.toLowerCase().equals("y")) {
									break;
								}
								System.out.println("Please enter the correct amount");
							}
							
							if (bankingService.doTransfer(a, amount, b)) {
								System.out.println("transfer successful");
								Integer input_id_2 = account_id;
								Account updateAccount = alist.stream().filter(ac -> ac.getId().equals(input_id_2)).findFirst().get();
								updateAccount.setBalance(b.getBalance());

								break;
							}
							System.out.println("transfer unsuccessful. Please try again.");
							break;

						} catch (Exception e) {
							s.nextLine();
							System.out.println("please enter a numer of amount");
						}

					}
					break;
				case 4:
					System.out.println("balance selected");
					break;
				case 5:
					System.out.println("customer logout");
					return;
				}
			} catch (Exception e) {
				s.nextLine();
				System.out.println("please enter 1-5");
			}

			break;
		}
	}

	public static void employeeMenu(Scanner s) {
		while (true) {
			System.out.println("1. account approval \n");
			System.out.println("2. view an account \n");
			System.out.println("3. view transaction log \n");
			System.out.println("4. logout");

			System.out.println("What would you like to do?");

			try {
				int option = s.nextInt();
				switch (option) {
				case 1:
					System.out.println("account approval selected");
					break;
				case 2:
					System.out.println("view an account selected");
					break;
				case 3:
					System.out.println("view transaction log selected");
					break;
				case 4:
					System.out.println("employee logout");
					return;
				}
			} catch (Exception e) {
				s.nextLine();
				System.out.println("please enter 1-4");
			}

			break;
		}
	}

	public static boolean checkPending(Person p) {
		if (p.getStatus().equals("active")) {
			return true;
		}

		return false;
	}

	public static List<Account> checkAccount(Person p) {

		AccountDao aDao = new AccountDao();

		List<Account> aList = aDao.getAllbyCustomer_id(p.getId());
		return aList;
	}

	public static void accountMenu(Scanner s, List<Account> alist) {
		while (true) {

			for (int i = 0; i < alist.size(); i++) {
				System.out.println((i + 1) + ". " + alist.get(i).getAccountType() + "\n");

			}
			;
			System.out.println((alist.size() + 1) + ". logout");
			System.out.println("select an Account \n");

			try {
				int select = s.nextInt();
				if (select >= 1 && select <= alist.size()) {

					System.out.println("you selected :" + alist.get(select - 1).getAccountType());
					customerMenu(s, alist.get(select - 1), alist);

				} else if (select == alist.size() + 1) {
					System.out.println("logout");
					return;
				} else {
					System.out.println("please select from 1-" + (alist.size() + 1));
				}
			} catch (Exception e) {

				System.out.println("please input a number");
			}

		}

	}
}
