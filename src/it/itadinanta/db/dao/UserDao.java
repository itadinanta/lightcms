package it.itadinanta.db.dao;

import it.itadinanta.lightcms.auth.UserBean;

public interface UserDao {
	public UserBean getById(int id);
	public UserBean getByName(String name);
	public UserBean authenticate(String name, String password);
}