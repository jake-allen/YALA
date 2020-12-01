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
	
	/**
	 * Returns a vector containing all of the lists of the current user.
	 * 
	 * @return vector of lists
	 */
	public Vector<List> getLists() {
		return this.lists;
	}
	
	/**
	 * Establishes the filename of the list storage and loads the ListStorage 
	 * lists from the particular user's list storage file into the list vector.
	 * 
	 * @param email of the user whose lists will be loaded into the vector 
	 */
	public ListStorage(String email) {
		filename = "storage/" + email + ".txt";
		// create empty vector
		lists = new Vector<List>();
		// load lists (if any) from the user's file
		this.loadLists();
	}
	
	/**
	 * Returns a list with the list name of listName if it exists. Returns
	 * null if the list is not found.
	 * 
	 * @param listName the list within the class to be returned
	 * @return list corresponding to the listName
	 */
	public List getList(String listName) {
		for (List l : lists) {
			if (l.getName().equals(listName)) {
				return l;	
			}
		}
		// if list wasn't found
		return null; 	
	}
	
	/**
	 * Loads the list from the storage file into the vector of lists of the
	 * class.
	 */
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
	
	/**
	 * Returns true if the list is contained in the ListStorage, otherwise,
	 * it returns false.
	 * 
	 * @param listName to check against the lists in the ListStorage
	 * @return true if the list exists in the class, otherwise false
	 */
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
	
	/**
	 * Deletes the list in the ListStorage corresponding to the listName. If
	 * the lists is not contained, it modifies nothing.
	 * 
	 * @param listName the name of the list to delete 
	 */
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
	
	/**
	 * Adds an empty list with name listName to the ListStorage.
	 * 
	 * @param listName the name of the list to add
	 */
	public void addList(String listName){
		List newList = new List(listName);
		// add list to the vector
		lists.add(newList);
	}
	
	/**
	 * Copies a list within the ListStorage with a new name. If the listName 
	 * does not correspond with any existing list in the ListStorage, then no 
	 * new list is created.
	 * 
	 * @param listName of the list to be duplicated
	 * @param newListName the name to call the new list that was duplicated 
	 * from the list corresponding to listName
	 */
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
	
	/**
	 * Adds an Item item to the list at the given index in the ListStorage's
	 * vector of lists. 
	 * 
	 * @param index of the list to add the item to
	 * @param item to be added to the list
	 */
	public void addItemtoList(int index, Item item) {
		lists.elementAt(index).addItem(item);
	}
	
	/**
	 * Creates a new item with attributes itemName, itemQuantity, and 
	 * storeName and adds it to the list at the given index in the ListStorage's
	 * vector of lists.
	 * 
	 * @param index of the list to add the item to
	 * @param itemName - name of the item
	 * @param itemQuantity - quantity of the item
	 * @param storeName - store at which the item can be found
	 */
	public void addItemToList(int index, String itemName, String itemQuantity, String storeName) {
		lists.elementAt(index).addItem(itemName, itemQuantity, storeName);
	}
	
	/**
	 * Creates a new item with attributes itemName, itemQuantity, and 
	 * storeName and adds it to the list at the given index in the ListStorage's
	 * vector of lists.
	 * 
	 * @param index of the list to add the item to
	 * @param itemName - name of the item
	 * @param itemQuantity - quantity of the item
	 * @param storeName - store at which the item can be found
	 */
	public void addItemToList(int index, String itemName, int itemQuantity, String storeName) {
		lists.elementAt(index).addItem(itemName, itemQuantity, storeName);
	}
	
	/**
	 * Deletes an item from the list at the given index in the ListStorage's
	 * vector of lists. If the item does not exist in the list, then no item
	 * is deleted.
	 * 
	 * @param index of the list to delete the item from
	 * @param itemName - name of the item to delete
	 * @param itemQuantity - quantity of the item to delete
	 * @param storeName - store at which the item to delete can be found
	 */
	public void deleteListItem(int index, String itemName, String itemQuantity, String storeName) {
		lists.elementAt(index).removeItem(itemName, itemQuantity, storeName);
	}
	
	/**
	 * Deletes an item from the list at the given index in the ListStorage's
	 * vector of lists. If the item does not exist in the list, then no item
	 * is deleted.
	 * 
	 * @param index of the list to delete the item from
	 * @param itemName - name of the item to delete
	 * @param itemQuantity - quantity of the item to delete
	 * @param storeName - store at which the item to delete can be found
	 */
	public void deleteListItem(int index, String itemName, int itemQuantity, String storeName) {
		lists.elementAt(index).removeItem(itemName, itemQuantity, storeName);
	}
	
	/**
	 * Deletes an Item item from the list at the given index in the 
	 * ListStorage's vector of lists. If the item does not exist in the list,
	 * then no item is deleted
	 * 
	 * @param index of the list to delete the item from
	 * @param item to be deleted from the list
	 */
	public void deleteListItem(int index, Item item) {
		lists.elementAt(index).removeItem(item);
	}
	
	/**
	 * Removes the contents of the list storage file and rewrites all of the
	 * lists into the storage file.
	 */
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
