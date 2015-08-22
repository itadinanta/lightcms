package it.itadinanta.db.auth;

import it.itadinanta.db.dao.UserDao;
import it.itadinanta.lightcms.auth.AuthService;
import it.itadinanta.lightcms.auth.UserBean;

public class SqlMapAuthService implements AuthService {
	private UserDao userDao;
	public UserDao getUserDao() {
		return userDao;
	}
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	public UserBean authenticate(String name, String password) {
		return userDao.authenticate(name, password);
	}
	public UserBean getById(int id) {
		return userDao.getById(id);
	}
	public UserBean getByName(String name) {
		return userDao.getByName(name);
	}
}
