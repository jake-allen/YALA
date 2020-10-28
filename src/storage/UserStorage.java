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
	// Vector<username,email,password>
	
	Map<String, String> passMap;
	// Map<email+password, user-name> 
	
	final String storageFile = "users.txt";
	// format
		// user-name,email,password
		// ... 
		// user-name,email,password
	
	// initialize the vector, map, load existing users into them
	public UserStorage(){
		users = new Vector<String>();
		passMap = new HashMap<String, String>();
		loadUsers();
	}
	
	// loads users and checks if the given user exists
	public User getUser(String email, String password) {
		if(passMap.containsKey(email + password)) {
			String username = passMap.get(email + password);
			return new User(username, email, password);
		}
		else {
			return null;
		}
	}
	
	// load users from the storage file into the vector, map
	void loadUsers() {
		// load users into the vector
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File(storageFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// read user information from the scanner
		if(scanner.hasNextLine()) {
			// read the file lines
			String line;
			while(scanner.hasNextLine()) {
				line = scanner.nextLine();
				// add each line
				users.add(line);
			}
		}
		scanner.close();
		// load users into the map 
		for(String str: users) {
			String[] split = str.split(",");
				// split[0] = username, split[1] = email, split[2] = password
			System.out.println(str);
			passMap.put(split[1] + split[2], split[0]);
		}
	}
	
	// add user to the storage file, vector, and map
	public void addUser(User user) {
		// add user to the storage file
		String userString = "\n" + user.getID() + "," + user.getEmail() + "," + user.getPassword();
		FileWriter fw = null;
		try {
			fw = new FileWriter(storageFile, true);
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
		// add user to the vector<user-name,email,password>
		userString = user.getID() + "," + user.getEmail() + "," + user.getPassword();
		users.add(userString);
		// add user to the passMap<email+password, user-name> 
		passMap.put(user.getEmail() + user.getPassword(), user.getID());
	}
}
