package constructs;

import storage.ListStorage;

public class User {
	String id; // unique username / display name
	String email;
	String password; // encrypted
	ListStorage lists;
	
	public String getID() {
		return this.id;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setID(String id) {
		this.id = id;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public User(String id, String email, String password) {
		this.id = id;
		this.email = email;
		this.password = password; // TODO needs encryption
		lists = new ListStorage();
	}
}
