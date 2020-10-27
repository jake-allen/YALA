package storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import constructs.List;

public class ListStorage {
	Vector<List> lists;
	String filename;
	
	// format
		// listname, items
		// item-name, item-quantity, item-store-name
		
	
	public ListStorage(String email) {
		filename = email + ".txt";
		// create empty vector
		lists = new Vector<List>();
		// load lists (if any) from the user's file
		this.loadLists();
	}
	
	public void loadLists() {
		// load users into the vector
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File(filename));
			// read user information from the scanner
			if(scanner.hasNextLine()) {
				// add list
				String line = scanner.nextLine();
				String[] split = line.split(",");
				String listName = split[0];
				int itemsInList = Integer.parseInt(split[1]);
				List newList = new List(listName);
				// add items to list
				for(int i = 0; i < itemsInList; i++) {
					line = scanner.nextLine();
					String[] splitLine = line.split(",");
					newList.addItem(splitLine[0], splitLine[1], splitLine[2]);
				}
				// add list to the storage
				lists.add(newList);	
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			// no lists
			lists = new Vector<List>();
			// create file
			File file = new File(filename);
			try {
				if(file.createNewFile()) {
					System.out.println("File created for user lists");
				}
				else {
					System.out.println("Error creating file for user lists");
				}
			} catch (IOException e1) {
				System.out.println("Error creating file for user lists");
			}
		}
	}
	
	void addList(int listIndex){
		// add 
	}
	
	void addItemToList(int listIndex, String itemName, String itemQuantity, String storeName) {
		
	}

	
	
	// TODO create storage in database (iteration III)
}
