package it.itadinanta.lightcms.auth;


public interface AuthService {
	public UserBean authenticate(String name, String password);
	public UserBean getById(int id);
	public UserBean getByName(String name);
}