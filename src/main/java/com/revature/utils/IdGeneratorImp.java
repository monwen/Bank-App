package com.revature.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;

public class IdGeneratorImp {
	static IdGenerator increament =() ->{
		
		try {
			File f = new File("./src/main/resources/idCount.txt");
			if(f.createNewFile()) {
				System.out.println("File created: " + f.getName());
				try {
					Writer w = new FileWriter("./src/main/resources/idCount.txt");
					long id = 1;
					w.write(Long.toString(id + 1));
					w.close();
					return id;
				}catch(IOException we) {
					System.out.println("write file fail");
					we.printStackTrace();
					
				}
				
				
			}else{
				System.out.println("file already exists.");
				Scanner s = new Scanner(f);
				long id = (long)Integer.parseInt(s.nextLine());
				try {
					Writer w = new FileWriter("./src/main/resources/idCount.txt");
					w.write(Long.toString(id + 1));
					w.close();
				}catch(IOException we2) {
					System.out.println("write file fail");
					we2.printStackTrace();
				}
				
				return id;
			};
			
		}catch(IOException ioe) {
			System.out.println("open file fail...");
			ioe.printStackTrace();
		}
		return 0l;
	};
	
//	public static void main(String[] args) {
//		
//		long id = IdGeneratorImp.increament.generate();
//		System.out.println(id);
//		id =  IdGeneratorImp.increament.generate();
//		System.out.println(id);
//	}
}
