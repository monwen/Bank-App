package com.revature.main;

import java.io.IOException;
import java.util.Scanner;

import com.revature.service.loginService;
import com.revature.utils.Menu;
import com.revature.utils.MockDB;

public class Main {
	public static void main(String[] args) {
		
		
		
		Scanner s = new Scanner(System.in);
		while(true) {
			System.out.println("What would you like to do today? \n");
			System.out.println("1. user login \n");
			System.out.println("2. register \n");
			System.out.println("3. exit \n");
			try {
				int option = s.nextInt();
				switch(option) {
				case 1: 
					System.out.println("you entered 1");
					Menu.loginPage(s);
					break;
	
				case 2: 
					System.out.println("you entered 2");
					Menu.registrationPage(s);
					break;

				case 3:
					System.out.println("you entered 3");
					System.exit(0);
					break;
					
				default:
					System.out.println("Please enter 1 or 2");
				
				}
			}catch(Exception e) {
				s.nextLine();
				System.out.println("please input 1 or 2");
			}
			
		}
		
		
		
	}
}
