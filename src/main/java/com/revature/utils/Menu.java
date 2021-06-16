package com.revature.utils;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.revature.dao.AccountDao;
import com.revature.dao.PersonDao;
import com.revature.models.Account;
import com.revature.models.Person;
import com.revature.models.Transaction;
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
						accountMenu(p, s, aList);
						return;
					}
					newAccountLoop(p, s);
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

	public static void newAccountLoop(Person p, Scanner s) {
		while (true) {
			System.out.println("you do not have an account yet. Would you like to open an account? y/n \n");
			try {
				String select = s.next();
				if (select.toLowerCase().equals("y")) {
					bankingService.doApplyNewAccount(p, s);
					return;
				}
				System.out.println("maybe later");
				return;
			} catch (Exception e) {
				s.nextLine();
				System.out.println("please enter y/n");
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
			System.out.println("4. approve transactions \n");
			System.out.println("5. view tansactions \n");
			System.out.println("6. back \n");

			System.out.println("What would you like to do?");

			try {
				int option = s.nextInt();
				switch (option) {
				case 1:
					System.out.println("deposit selected");
					depostitLoop(s, a);
					break;
				case 2:
					System.out.println("withdraw selected");
					withdrawLoop(s, a);
					break;
				case 3:
					System.out.println("transfer selected");
					if (bankingService.doTransfer(a, s, alist)) {
						System.out.println("transfer successful");
					} else {
						System.out.println("transfer fail");
					}
					;

					break;
				case 4:
					System.out.println("transaction approval selected");
					bankingService.doArrpoveTrans(a, s, alist);
					break;
				case 5:
					System.out.println("view transaction selected");
					bankingService.doViewTransactionByAccId(a.getId());
					break;
				case 6:
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

	public static Float enterAmount(Scanner s) {
		Float amount;
		while (true) {
			amount = s.nextFloat();
			System.out.println("You have entered: " + amount + ". Is this the correct amount? Y/N");
			String amountCheck = s.next();
			if (amountCheck.toLowerCase().equals("y")) {
				System.out.println("amount entered");
				return amount;
			}
			System.out.println("Please enter the correct amount");
		}

	}

	public static void depostitLoop(Scanner s, Account a) {

		System.out.println("please enter the deposit amount");

		float amount = enterAmount(s);

		if (bankingService.doDeposit(a, amount)) {
			System.out.println("deposit successful");
			return;
		}
		System.out.println("deposit unsuccessful. Please try again.");
		return;

	}

	public static void withdrawLoop(Scanner s, Account a) {

		System.out.println("please enter the withdrawal amount");

		float amount = enterAmount(s);

		if (bankingService.doWithdrawal(a, amount)) {
			System.out.println("withdraw successful");
			return;
		}
		System.out.println("withdraw unsuccessful. Please try again.");
		return;
	}

	public static void employeeMenu(Scanner s) {
		AccountDao aDao = new AccountDao();
		PersonDao pDao = new PersonDao();
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
					accApprovalLoop(s);
					break;
				case 2:
					System.out.println("view an account selected");
					
					while (true) {
						System.out.println("1. view by id \n");
						System.out.println("2. view by username \n");
						System.out.println("3. back \n");
						try {
							int select = s.nextInt();

							switch (select) {
							case 1:
								System.out.println("select view by id ");
								while (true) {
									try {
										System.out.println("please input the customer's id");
										int customer_id = s.nextInt();
										Person p = pDao.getById(customer_id);
										if (p == null) {
											System.out.println("invalid customer id");
											break;
										}
										List<Account> aList = aDao.getAllbyCustomer_id(customer_id);
										System.out.println("------------------------\n");
										System.out.println("first name: " + p.getFirstName() + " last name: "
												+ p.getLastName() + " username: " + p.getUserName() + " password: "
												+ p.getPassWord() + "\n");
										if (aList != null) {
											aList.forEach(account -> {
												System.out.print("account id: " + account.getId() + " type: "
														+ account.getAccountType() + " balance: "
														+ account.getBalance());
												System.out.println(" approval: " + account.isApprove() != null
														&& account.isApprove() == true ? " approved \n"
																: " pending \n");
											});
											System.out.println("------------------------\n");
										}
										break;

									} catch (Exception e) {
										s.nextLine();
										System.out.println("please input a number");
									}
								}

								break;
							case 2:
								System.out.println("select view by username");
								while (true) {
									try {
										System.out.println("please input the customer's username");
										s.nextLine();
										String uname = s.nextLine();
										Person p = pDao.getByUserName(uname);
										if (p == null) {
											System.out.println("invalid username");
											break;
										}
										List<Account> aList = aDao.getAllbyCustomer_id(p.getId());
										System.out.println("------------------------\n");
										System.out.println("first name: " + p.getFirstName() + " last name: "
												+ p.getLastName() + " username: " + p.getUserName() + " password: "
												+ p.getPassWord() + "\n");
										if (aList != null) {
											aList.forEach(account -> {
												System.out.print("account id: " + account.getId() + " type: "
														+ account.getAccountType() + " balance: "
														+ account.getBalance());
												System.out.println(" approval: " + account.isApprove() != null
														&& account.isApprove() == true ? " approved \n"
																: " pending \n");
											});
											System.out.println("------------------------\n");
										}
										break;

									} catch (Exception e) {
										s.nextLine();
										System.out.println("please input username");
									}
								}

								break;
							case 3:
								System.out.println("back to menu");
								break;

							}

						} catch (Exception e) {
							System.out.println("please select 1/2/3");
						}
						break;
					}
					break;
				case 3:
					System.out.println("view transaction log selected");
					while (true) {
						System.out.println("1. view all transaction.\n");
						System.out.println("2. back. \n");
						try {
							int select = s.nextInt();

							switch (select) {
							case 1:
								System.out.println("you select view all transaction");
								System.out.println("------------------------\n");
								List<Transaction> transList = bankingService.doViewAllTransaction();
								if (transList != null) {
									transList.forEach(trans -> {
										Account fromAccount = aDao.getById(trans.getFrom());
										Person fromUser = pDao.getById(fromAccount.getCustomer_id());
										Account toAccount = aDao.getById(trans.getTo());
										Person toUser = pDao.getById(toAccount.getCustomer_id());
										System.out.println("transaction id: " + trans.getId() + " from: "
												+ fromUser.getUserName() + " type: " + fromAccount.getAccountType()
												+ " to: " + toUser.getUserName() + " type: "
												+ toAccount.getAccountType() + " amount: " + trans.getAmount()
												+ " time: " + trans.getTime() + "\n");

									});

									System.out.println("------------------------\n");
									break;

								}
								System.out.println("cannot find any records.");
								break;

							case 2:
								System.out.println("back");
								break;
							}

						} catch (Exception e) {
							System.out.println("please input 1-3.");
						}

						break;
					}
					break;
				case 4:
					System.out.println("employee logout");
					return;
				}
			} catch (Exception e) {
				s.nextLine();
				System.out.println("please enter 1-4");
			}

		}
	}
	
	public static void accApprovalLoop(Scanner s) {
		
		AccountDao aDao = new AccountDao();
		List<Account> approveList = aDao.getAll().stream().filter(x -> !x.isApprove())
				.collect(Collectors.toList());

		System.out.println(approveList.size());
		if (approveList.size() == 0) {
			System.out.println("no account to approve.");
			System.out.println("------------------------");
			return;
		}
		while (true) {
			try {

				approveList.forEach(a -> {
					System.out.println("id: " + a.getId() + " account type: " + a.getAccountType()
							+ " amount" + a.getBalance());
					System.out.println("approve this account? y/n");
					String res = s.next();
					if (res.toLowerCase().equals("y")) {

						a.setApprove(true);
						aDao.update(a);
					}
					if (res.toLowerCase().equals("n")) {

						System.out.println("account #" + a.getId() + " is rejected.");
					}
				});

				break;
			} catch (Exception e) {
				System.out.println("please enter y/n");
			}
		}
	}
	
//	public static void viewAccountLoop(Scanner s) {
//		Person
//		
//		while (true) {
//			System.out.println("1. view by id \n");
//			System.out.println("2. view by username \n");
//			System.out.println("3. back \n");
//			try {
//				int select = s.nextInt();
//
//				switch (select) {
//				case 1:
//					System.out.println("select view by id ");
//					while (true) {
//						try {
//							System.out.println("please input the customer's id");
//							int customer_id = s.nextInt();
//							Person p = pDao.getById(customer_id);
//							if (p == null) {
//								System.out.println("invalid customer id");
//								break;
//							}
//							List<Account> aList = aDao.getAllbyCustomer_id(customer_id);
//							System.out.println("------------------------\n");
//							System.out.println("first name: " + p.getFirstName() + " last name: "
//									+ p.getLastName() + " username: " + p.getUserName() + " password: "
//									+ p.getPassWord() + "\n");
//							if (aList != null) {
//								aList.forEach(account -> {
//									System.out.print("account id: " + account.getId() + " type: "
//											+ account.getAccountType() + " balance: "
//											+ account.getBalance());
//									System.out.println(" approval: " + account.isApprove() != null
//											&& account.isApprove() == true ? " approved \n"
//													: " pending \n");
//								});
//								System.out.println("------------------------\n");
//							}
//							break;
//
//						} catch (Exception e) {
//							s.nextLine();
//							System.out.println("please input a number");
//						}
//					}
//
//					break;
//				case 2:
//					System.out.println("select view by username");
//					while (true) {
//						try {
//							System.out.println("please input the customer's username");
//							s.nextLine();
//							String uname = s.nextLine();
//							Person p = pDao.getByUserName(uname);
//							if (p == null) {
//								System.out.println("invalid username");
//								break;
//							}
//							List<Account> aList = aDao.getAllbyCustomer_id(p.getId());
//							System.out.println("------------------------\n");
//							System.out.println("first name: " + p.getFirstName() + " last name: "
//									+ p.getLastName() + " username: " + p.getUserName() + " password: "
//									+ p.getPassWord() + "\n");
//							if (aList != null) {
//								aList.forEach(account -> {
//									System.out.print("account id: " + account.getId() + " type: "
//											+ account.getAccountType() + " balance: "
//											+ account.getBalance());
//									System.out.println(" approval: " + account.isApprove() != null
//											&& account.isApprove() == true ? " approved \n"
//													: " pending \n");
//								});
//								System.out.println("------------------------\n");
//							}
//							break;
//
//						} catch (Exception e) {
//							s.nextLine();
//							System.out.println("please input username");
//						}
//					}
//
//					break;
//				case 3:
//					System.out.println("back to menu");
//					break;
//
//				}
//
//			} catch (Exception e) {
//				System.out.println("please select 1/2/3");
//			}
//			break;
//		}
//	}

	public static boolean checkPending(Person p) {
		if (p.getStatus().equals("active")) {
			return true;
		}

		return false;
	}

	public static List<Account> checkAccount(Person p) {

		AccountDao aDao = new AccountDao();

		List<Account> aList = aDao.getAllbyCustomer_id(p.getId());
//		aList = aList.stream().filter(x -> x.isApprove()).collect(Collectors.toList());
		return aList;
	}

	public static void accountMenu(Person p, Scanner s, List<Account> alist) {
		List<Account> approveAccount = alist.stream().filter(x -> x.isApprove()).collect(Collectors.toList());
		List<Account> pendingAccount = alist.stream().filter(x -> !x.isApprove()).collect(Collectors.toList());

		while (true) {
			System.out.println("============================= \n");
			if (pendingAccount != null) {
				pendingAccount.forEach(a -> System.out.println("* " + a.getAccountType() + " pending \n"));
			}

			System.out.println("============================= \n");
			System.out.println("1. apply new account \n");
			for (int i = 0; i < approveAccount.size(); i++) {
				System.out.println((i + 2) + ". " + "Account id: " + approveAccount.get(i).getId() + ", type: "
						+ approveAccount.get(i).getAccountType() + "\n");

			}
			;
			System.out.println((approveAccount.size() + 2) + ". logout \n");
			System.out.println("============================= \n");
			System.out.println("select an Account \n");

			try {
				int select = s.nextInt();
				if (select == 1) {
					System.out.println("you selected applying new account.");
					bankingService.doApplyNewAccount(p, s);
				} else if (select > 1 && select <= approveAccount.size() + 1) {

					System.out.println("you selected :" + "Account id: " + approveAccount.get(select - 2).getId() + ", "
							+ approveAccount.get(select - 2).getAccountType());
					customerMenu(s, approveAccount.get(select - 2), approveAccount);

				} else if (select == approveAccount.size() + 2) {
					System.out.println("logout");
					return;
				} else {
					System.out.println("please select from 1-" + (approveAccount.size() + 2));
				}
			} catch (Exception e) {

				System.out.println("please input a number");
			}

		}

	}
}
