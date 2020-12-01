package constructs;

import storage.ListStorage;

public class User {
	// unique user-name / display name
	String id; 			
	String email;
	String password; 
	ListStorage lists;
	
	/**
	 * Gets the user's id.
	 * 
	 * @return the id of the user
	 */
	public String getID() {
		return this.id;
	}
	
	/**
	 * Gets the user's email.
	 * 
	 * @return the email of the user
	 */
	public String getEmail() {
		return this.email;
	}
	
	/**
	 * Gets the user's password.
	 * 
	 * @return the password of the user
	 */
	public String getPassword() {
		return this.password;
	}
	
	/**
	 * Gets the user's ListStorage.
	 * 
	 * @return the ListStorage object of the user
	 */
	public ListStorage getListStorage() {
		return this.lists;
	}
	
	/**
	 * Sets the user's ID.
	 * 
	 * @param id the id to give the user
	 */
	public void setID(String id) {
		this.id = id;
	}
	
	/**
	 * Sets the user's email.
	 * 
	 * @param email the email to give the user
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Constructs a user with the given id, email, and password.
	 * 
	 * @param id the id to give the user
	 * @param email the email to give the user
	 * @param password the password to give the user
	 */
	public User(String id, String email, String password) {
		this.id = id;
		this.email = email;
		this.password = password; 
		lists = new ListStorage(this.email);
	}
}
