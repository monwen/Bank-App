package com.revature.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Person;
import com.revature.models.Transaction;
import com.revature.utils.JDBC;

public class TransactionDao implements GenericDao<Transaction> {
	
	private Connection conn = JDBC.getConnection();
	
	@Override
	public boolean create(Transaction t) {
		String sql = "insert into bank.trans values (default, ?, ?, ? , ? , ? , now());";
		
		try {
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, t.getFrom());
			ps.setInt(2, t.getTo());
			ps.setString(3, t.getMethodType());
			ps.setFloat(4, t.getAmount());
			ps.setBoolean(5, t.isApprove());
			
			ps.execute();
			return true;
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		return false;
	}

	@Override
	public Transaction getById(Integer t) {
		
		String sql = "select * from bank.trans where trans_id = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, t);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				Transaction trans = new Transaction();
				trans.setId(rs.getInt("trans_id"));
				trans.setFrom(rs.getInt("fromaccount"));
				trans.setTo(rs.getInt("toaccount"));
				trans.setAmount(rs.getFloat("amount"));
				trans.setMethodType(rs.getString("methodtype"));
				trans.setApprove(rs.getBoolean("isapprove"));
				trans.setTime(rs.getTimestamp("ts"));
				
				return trans;
				
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	public Transaction getLast() {
		String sql = "select * from bank.trans where trans_id =(select max(trans_id) from bank.trans);";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				Transaction trans = new Transaction();
				trans.setId(rs.getInt("trans_id"));
				trans.setFrom(rs.getInt("fromaccount"));
				trans.setTo(rs.getInt("toaccount"));
				trans.setAmount(rs.getFloat("amount"));
				trans.setMethodType(rs.getString("methodtype"));
				trans.setApprove(rs.getBoolean("isapprove"));
				trans.setTime(rs.getTimestamp("ts"));
				
				return trans;
				
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	public List<Transaction> getByFromAccount(Integer from) {
		String sql = "select * from bank.trans where fromaccount = ?";
		List<Transaction> transList = new ArrayList<Transaction>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, from);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Transaction trans = new Transaction();
				trans.setId(rs.getInt("trans_id"));
				trans.setFrom(rs.getInt("fromaccount"));
				trans.setTo(rs.getInt("toaccount"));
				trans.setAmount(rs.getFloat("amount"));
				trans.setMethodType(rs.getString("methodtype"));
				trans.setApprove(rs.getBoolean("isapprove"));
				trans.setTime(rs.getTimestamp("ts"));
				
				transList.add(trans);
			}
			if(transList.size()>0) {
				return transList;
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	public List<Transaction> getByToAccount(Integer to) {
		String sql = "select * from bank.trans where toaccount = ?";
		List<Transaction> transList = new ArrayList<Transaction>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, to);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Transaction trans = new Transaction();
				trans.setId(rs.getInt("trans_id"));
				trans.setFrom(rs.getInt("fromaccount"));
				trans.setTo(rs.getInt("toaccount"));
				trans.setAmount(rs.getFloat("amount"));
				trans.setMethodType(rs.getString("methodtype"));
				trans.setApprove(rs.getBoolean("isapprove"));
				trans.setTime(rs.getTimestamp("ts"));
				
				transList.add(trans);
			}
			if(transList.size()>0) {
				return transList;
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	

	@Override
	public List<Transaction> getAll() {
		
		String sql = "select * from bank.trans;";
		
		List<Transaction> transList = new ArrayList<Transaction>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Transaction trans = new Transaction();
				trans.setId(rs.getInt("trans_id"));
				trans.setFrom(rs.getInt("fromaccount"));
				trans.setTo(rs.getInt("toaccount"));
				trans.setAmount(rs.getFloat("amount"));
				trans.setMethodType(rs.getString("methodtype"));
				trans.setApprove(rs.getBoolean("isapprove"));
				trans.setTime(rs.getTimestamp("ts"));
				
				transList.add(trans);
			}
			if(transList.size()>0) {
				return transList;
			}
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		return null;
	}

	@Override
	public boolean update(Transaction t) {
		String sql = "update bank.trans set isapprove = ? where trans_id = ?";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setBoolean(1, t.isApprove());
			ps.setInt(2, t.getId());
			ps.execute();
			return true;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(Transaction t) {
		String sql = "delete from bank.trans where trans_id = ?;";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, t.getId());
			ps.execute();
			return true;
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void main(String[] args) {
		
//		Timestamp ts = new Timestamp(System.currentTimeMillis());
////		System.out.println(ts);
//		Float f = (float) 500.0;
//		Transaction t = new Transaction(3,3,"deposit", f, ts);
		TransactionDao tDao = new TransactionDao();
////		System.out.println(tDao.create(t));
//		
////		Transaction t = tDao.getById(1);
////		System.out.println(t);
//		
////		List<Transaction> t = tDao.getAll();
////		System.out.println(t);
//		t.setId(1);
////		tDao.delete(t);
//		System.out.println(tDao.getByToAccount(1));
		System.out.println(tDao.getLast());
		
	}

}
