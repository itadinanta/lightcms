package it.itadinanta.lightcms.auth;

import java.io.Serializable;

public class UserBean implements User, Serializable {
	private static final long serialVersionUID = 1641232384204288424L;
	private int id;
	private String name;
	private String password;
	private int clearance;
	public UserBean() {
	}
	public UserBean(int id, String name, int clearance) {
		this.id = id;
		this.name = name;
		this.clearance = clearance;
	}
	public int getClearance() {
		return clearance;
	}
	public void setClearance(int clearance) {
		this.clearance = clearance;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
