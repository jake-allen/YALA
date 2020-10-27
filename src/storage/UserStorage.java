package storage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;

import constructs.User;

public class UserStorage {
	Vector<String> users;
	
	final String storageFile = "users.txt";
	// format
		// user-name,email,password
		// ... 
		// user-name,email,password
	
	public UserStorage(){

	}
	
	// loads users and checks if the given user exists
	public User getUser(String email, String password) {
		// load users from file
		loadUsers();
		// create a map for searching the users
		Map<String, String> map = new HashMap<String, String>();
		
		for(String user: users) {
			
			String[] split = user.split(",");
			// split[0] = user-name
			// split[1] = email
			// split[2] = password
			
			map.put(split[1] + split[2], split[0]);
		}
		// return null if password is invalid
		if(map.containsKey(email + password)) {
			String user = map.get(email + password);
			return new User(user, email, password);
		}
		else {
			// return the corresponding user if it works
			return null;
		}
	}
	
	// load users from the storage file TODO update later to database
	void loadUsers() {
		
		// open scanner
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File(storageFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		if(scanner.hasNextLine()) {
			// read the file lines
			String line;
			int i = 0;
			while(scanner.hasNextLine()) {
				line = scanner.nextLine();
				users.add(line);
				i++;
			}
		}
		scanner.close();
	}
	
	// adds user to the storage file
	public void addUser(User user) {
		String userString = "\n" + user.getID() + "," + user.getEmail() + "," + user.getPassword();
		FileWriter fw = null;
		try {
			fw = new FileWriter(storageFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		BufferedWriter bw = new BufferedWriter(fw);
		try {
			bw.write(userString);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
