package constructs;

import storage.ListStorage;

public class User {
	String id; 			// unique user-name / display name
	String email;
	String password; 
	ListStorage lists;
	
	public String getID() {
		return this.id;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public ListStorage getListStorage() {
		return this.lists;
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
		this.password = password; 
		lists = new ListStorage(this.email);
	}
}
