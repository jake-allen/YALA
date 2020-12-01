package storage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Vector;

import constructs.Item;
import constructs.List;

public class ListStorage {
	Vector<List> lists;
	String filename;
		
	// format of the storage email.txt
		// listAmount
		// listname,itemAmount
		// item-name,item-quantity,item-store-name,itemID
	
	public Vector<List> getLists() {
		return this.lists;
	}
	
	public ListStorage(String email) {
		filename = email + ".txt";
		// create empty vector
		lists = new Vector<List>();
		// load lists (if any) from the user's file
		this.loadLists();
	}
	
	public List getList(String listName) {
		for (List l : lists) {
			if (l.getName().equals(listName)) {
				return l;	
			}
		}
		// if list wasn't found
		return null; 	
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
				int listAmount = Integer.parseInt(line);
				for(int i = 0; i < listAmount; i++) {
					line = scanner.nextLine();
					String[] split = line.split(",");
					String listName = split[0]; // split[0] = listName
					int itemsInList = Integer.parseInt(split[1]); // split[1] = listAmountOfItems
					List newList = new List(listName);
					// add items to list
					for(int j = 0; j < itemsInList; j++) {
						line = scanner.nextLine();
						String[] splitLine = line.split(",");
						// splitLine[0] = itemName, splitLine[1] = itemQuantity, splitLine[2] = itemStoreName
						newList.addItem(splitLine[0], splitLine[1], splitLine[2]);
					}
					// add list to the storage
					lists.add(newList);						
				}
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
	
	public boolean hasList(String listName) {
		// find the corresponding list
		for(int i = 0; i < lists.size(); i++) {
			List tempList = lists.elementAt(i);
			System.out.println("name looking for list: -" + tempList.getName() + "--" + listName + "--");
			if(tempList.getName().equals(listName)) {
				return true;
			}
		}
		return false;
	}
	
	public void deleteList(String listName) {
		// find the corresponding list
		for(int i = 0; i < lists.size(); i++) {
			List tempList = lists.elementAt(i);
			if(tempList.getName().equals(listName)) {
				lists.remove(i);
				break;
			}
		}
	}
	
	public void addList(String listName){
		List newList = new List(listName);
		// add list to the vector
		lists.add(newList);
	}
	
	public void copyList(String listName, String newListName) {
		// find the corresponding list
		for(int i = 0; i < lists.size(); i++) {
			List tempList = lists.elementAt(i);
			if(tempList.getName().equals(listName)) {
				List newList = new List(newListName);
				for(int j = 0; j < tempList.getItems().size(); j++) {
					Item tempItem = tempList.getItems().elementAt(j);
					newList.addItem(tempItem.getName(), tempItem.getQuantity(), tempItem.getStore());
				}
				lists.add(newList);
				return;
			}
		}
	}
	
	public void addItemtoList(int index, Item item) {
		lists.elementAt(index).addItem(item);
	}
	
	public void addItemToList(int index, String itemName, String itemQuantity, String storeName) {
		lists.elementAt(index).addItem(itemName, itemQuantity, storeName);
	}
	
	public void addItemToList(int index, String itemName, int itemQuantity, String storeName) {
		lists.elementAt(index).addItem(itemName, itemQuantity, storeName);
	}
	
	public void deleteListItem(int index, String itemName, String itemQuantity, String storeName) {
		lists.elementAt(index).removeItem(itemName, itemQuantity, storeName);
	}
	
	public void deleteListItem(int index, String itemName, int itemQuantity, String storeName) {
		lists.elementAt(index).removeItem(itemName, itemQuantity, storeName);
	}
	
	public void deleteListItem(int index, Item item) {
		lists.elementAt(index).removeItem(item);
	}
	
	// update list storage
	public void restoreLists() {
		// remove file's content
		try {
			new PrintWriter(filename).close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		// rewrite
		FileWriter fw = null;
		try {
			fw = new FileWriter(filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
		BufferedWriter bw = new BufferedWriter(fw);
		try {
			// write amountOfLists and newline
			bw.write(lists.size() + "\n");
			// write each list
			for(List list: lists) {
				Vector<Item> listItems = list.getItems();
				// write list-name and listItemAmount and newline
				bw.write(list.getName() + "," + listItems.size() + "\n");
				for(int i = 0; i < listItems.size(); i++) {
					// write items
					Item item = listItems.elementAt(i);
					bw.write(item.getName() + "," + item.getQuantity() + "," + item.getStore() + "\n");
				}
			}			
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
