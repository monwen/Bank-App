package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Account;
import com.revature.models.Person;
import com.revature.utils.JDBC;
import com.revature.utils.MockDB;

public class AccountDao implements GenericDao<Account>{
	
	private Connection conn = JDBC.getConnection();
	
	
	@Override
	public boolean create(Account a) {
		String sql = "insert into bank.Account values (default,?,?,?,? )";
		
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, a.getAccountType());
			ps.setFloat(2, a.getBalance());
			ps.setInt(3, a.getCustomer_id());
			ps.setBoolean(4, false);
			
			ps.execute();
			System.out.println("account added succesfull");
			return true;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		System.out.println("account added unsuccesfull");
		return false;
	}

	@Override
	public Account getById(Integer id) {
		String sql = "select * from bank.Account where id = ?;";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				Account a = new Account();
				a.setId(rs.getInt("id"));
				a.setAccountType(rs.getString("accounttype"));
				a.setBalance(rs.getFloat("amount"));
				a.setCustomer_id(rs.getInt("customer_id"));
				a.setApprove(rs.getBoolean("isapprove"));
				
				return a;
				
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	public Account getByCustomer_id(Integer id) {
		String sql = "select * from bank.Account where customer_id = ?;";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				Account a = new Account();
				a.setId(rs.getInt("id"));
				a.setAccountType(rs.getString("accounttype"));
				a.setBalance(rs.getFloat("amount"));
				
				return a;
				
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	public List<Account> getAllbyCustomer_id(Integer id){
		List<Account> accounts = new ArrayList<Account>();
		
		String sql = "select * from bank.Account  where customer_id = ?;";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Account a= new Account();
				a.setId(rs.getInt("id"));
				a.setAccountType(rs.getString("accounttype"));
				a.setBalance(rs.getFloat("amount"));
				a.setCustomer_id(rs.getInt("customer_id"));
				a.setApprove(rs.getBoolean("isapprove"));
				
				accounts.add(a);
			}
			return accounts;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	@Override
	public List<Account> getAll() {
		List<Account> accounts = new ArrayList<Account>();
		
		String sql = "select * from bank.Account;";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Account a= new Account();
				a.setId(rs.getInt("id"));
				a.setAccountType(rs.getString("accounttype"));
				a.setBalance(rs.getFloat("amount"));
				a.setCustomer_id(rs.getInt("customer_id"));
				a.setApprove(rs.getBoolean("isapprove"));
				
				accounts.add(a);
			}
			return accounts;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public boolean update(Account a) {
		String sql = "update bank.Account set accounttype = ?, amount = ?, customer_id = ?, isapprove = ? where id = ?;";
		System.out.println(a);
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(5, a.getId());
			ps.setString(1, a.getAccountType());
			ps.setFloat(2, a.getBalance());
			ps.setInt(3, a.getCustomer_id());
			ps.setBoolean(4, a.isApprove());
			
			
			ps.execute();
			return true;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		return false;
	}

	@Override
	public boolean delete(Account a) {
		String sql = "delete from bank.Account where id = ?;";
		
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, a.getId());
			ps.execute();
			return true;
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		return false;
	}
	
	public static void main(String[] args) {
		Account a = new Account("saving", new Float(1000.00), 1);
//		a.setId(1);
		AccountDao aDao = new AccountDao();
		
		aDao.create(a);
//		aDao.update(a);
//		aDao.delete(a);
		
	}

	
}
