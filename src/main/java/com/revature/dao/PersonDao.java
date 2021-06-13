package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Person;
import com.revature.utils.JDBC;

public class PersonDao implements GenericDao<Person>{
	private Connection conn = JDBC.getConnection();
	
	@Override
	public boolean create(Person p) {
		
		String sql = "insert into bank.Person values (default, ?,?,?,?,?,?)";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, p.getFirstName());
			ps.setString(2, p.getLastName());
			ps.setString(3, p.getUserName());
			ps.setString(4, p.getPassWord());
			ps.setString(5, p.getPersonType());
			ps.setString(6, p.getStatus());
			
			ps.execute();
			return true;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
//	public boolean addAccount(Person p, Integer account_id) {
//		String sql = "insert into bank.Person values (default, ?,?,?,?,?,null,?)";
//	}

	@Override
	public Person getById(Integer id) {
		String sql = "select * from bank.person where id = ?;";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				Person p= new Person(rs.getString("kind"));
				p.setFirstName(rs.getString("firstname"));
				p.setLastName(rs.getString("lastname"));
				p.setUserName(rs.getString("username"));
				p.setPassWord(rs.getString("pass"));
				// need to implement account query
				p.setStatus(rs.getString("status"));
				p.setId(rs.getInt("id"));
				
				return p;
				
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	public Person getByUserName(String userName) {
			String sql = "select * from bank.person where username = ?;";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userName);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				Person p= new Person(rs.getString("kind"));
				p.setFirstName(rs.getString("firstname"));
				p.setLastName(rs.getString("lastname"));
				p.setUserName(rs.getString("username"));
				p.setPassWord(rs.getString("pass"));
				// need to implement account query
				p.setStatus(rs.getString("status"));
				p.setId(rs.getInt("id"));
				
				return p;
				
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	@Override
	public List<Person> getAll(){
		List<Person> people = new ArrayList<Person>();
		
		String sql = "select * from bank.Person;";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Person p= new Person(rs.getString("kind"));
				p.setFirstName(rs.getString("firstname"));
				p.setLastName(rs.getString("lastname"));
				p.setUserName(rs.getString("username"));
				p.setPassWord(rs.getString("pass"));
				// need to implement account query
				p.setStatus(rs.getString("status"));
				p.setId(rs.getInt("id"));
				
				people.add(p);
			}
			return people;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public boolean update(Person p) {
		
		String sql = "update bank.person set firstname = ?, lastname = ?, username = ?, pass = ?, kind = ?, status =? where id = ?;";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setInt(7, p.getId());
			ps.setString(1, p.getFirstName());
			ps.setString(2, p.getLastName());
			ps.setString(3, p.getUserName());
			ps.setString(4, p.getPassWord());
			ps.setString(5, p.getPersonType());
			ps.setString(6, p.getStatus());
			
			ps.execute();
			return true;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		return false;
	}

	@Override
	public boolean delete(Person p) {
		
		String sql = "delete from bank.person where id = ?;";
		
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, p.getId());
			ps.execute();
			return true;
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		return false;
	}
	
	public static void main(String[] args) {
		Person p = new Person("update", "my mama." ,"customer");
		p.setId(1);
		PersonDao pDao = new PersonDao();
		pDao.create(p);
		
//		System.out.println(pDao.getById(4));
//		System.out.println(pDao.getAll());
//		pDao.update(p);
//		pDao.delete(p);
		
	}
}
