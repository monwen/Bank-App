package com.revature.dao;

import java.util.List;


public interface GenericDao<T> {
	public boolean create(T t);
	
	public T getById(Integer t);
	
	
	public List<T> getAll();
	
	public boolean update(T t);
	
	public boolean delete(T t);
}
