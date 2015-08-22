package it.itadinanta.db.dao;

import it.itadinanta.lightcms.auth.UserBean;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class UserDaoSqlMapClient extends SqlMapClientDaoSupport implements UserDao {
	public UserBean getById(int id) {
		return (UserBean) (getSqlMapClientTemplate().queryForObject("User.getById", new Integer(id)));
	}
	public UserBean getByName(String name) {
		return (UserBean) (getSqlMapClientTemplate().queryForObject("User.getByName", name));
	}
	public UserBean authenticate(String name, String password) {
		UserBean findMe = new UserBean();
		findMe.setName(name);
		findMe.setPassword(password);
		return (UserBean) (getSqlMapClientTemplate().queryForObject("User.authenticate", findMe));
	}
}
