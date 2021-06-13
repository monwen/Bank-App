package com.revature.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBC{
	private static Connection conn = null;
	
	public static Connection getConnection(){
		if(conn == null) {
			try {
				Class.forName("org.postgresql.Driver");
				
				Properties props = new Properties();
				InputStream input = JDBC.class.getClassLoader().getResourceAsStream("connection.properties");
				props.load(input);
				String url = props.getProperty("url");
				String username = props.getProperty("username");
				String password = props.getProperty("password");
				conn = DriverManager.getConnection(url,username, password);
				
				System.out.println("connection successfull");
				return conn;
			}catch(SQLException|ClassNotFoundException|IOException e) {
				e.printStackTrace();
				System.out.println("connection unsuccessfull");
				return null;
			}
			
		}
		
		return conn;
	}
}
