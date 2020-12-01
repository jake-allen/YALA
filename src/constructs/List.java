package constructs;

import java.util.Vector;

public class List {
	String name;
	Vector<Item> items;
	
	/**
	 * Creates an empty list with name listname.
	 * 
	 * @param listname the name to give the new list
	 */
	public List(String listname){
		name = listname;
		items = new Vector<Item>();
	}
	
	/**
	 * Gets the name of the list.
	 * 
	 * @return the list's name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Gets the items of the list. If empty, it returns an empty vector.
	 * 
	 * @return the vector of the lists's items
	 */
	public Vector<Item> getItems(){
		return this.items;
	}
	
	/**
	 * Checks if an item is in a list. If it is found, it returns true, 
	 * otherwise false.
	 * 
	 * @param i the item to check if is in the list
	 * @return true if the item is found, otherwise false
	 */
	public boolean hasItem(Item i) {
		if(items.contains(i)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Adds an item to the list.
	 * 
	 * @param item the Item that is added to the list
	 */
	public void addItem(Item item) {
		items.add(item);
	}
	
	/**
	 * Creates an Item and adds it to the list.
	 * 
	 * @param itemName the name to give the item
	 * @param itemQuantity the quantity to give the item
	 * @param storeName the store at which the item can be found
	 */
	public void addItem(String itemName, String itemQuantity, String storeName) {
		items.add(new Item(itemName, itemQuantity, storeName));
	}
	
	/**
	 * Creates an Item and adds it to the list.
	 * 
	 * @param itemName the name to give the item
	 * @param itemQuantity the quantity to give the item
	 * @param storeName the store at which the item can be found
	 */
	public void addItem(String itemName, int itemQuantity, String storeName) {
		items.add(new Item(itemName, itemQuantity, storeName));
	}
	
	/**
	 * Finds the item in the list and crosses the item off if it exists. If the
	 * item does not exist in the list, nothing is modified.
	 * 
	 * @param i the item in the list to be crossed off
	 */
	public void crossOff(Item i) {	
		int index;
		if(items.contains(i)) {	
			index = items.indexOf(i);
			items.get(index).crossOff();
		}
	}
	
	/**
	 * Finds the item in the list and crosses the item off if it exists. If the 
	 * item does not exist in the list, nothing is modified.
	 * 
	 * @param i the item in the list to uncross off
	 */
	public void uncrossOff(Item i) {
		int index;
		if(items.contains(i)) {	
			index = items.indexOf(i);
			items.get(index).uncross();
		}		
	}
	
	/**
	 * Removes the Item i from a list, if it exists within the list. If the 
	 * item does not exist in the list, then the list is unchanged.
	 * 
	 * @param i the item to remove from the list 
	 */
	public void removeItem(Item i) {
		if(items.contains(i)) {	
			items.remove(i);
		}
	}
	
	/**
	 * Removes an item from the list given the items name, quantity, and store.
	 * If the item is not found within the list, then nothing occurs. If two
	 * items matching the name, quantity, and store are found, the first
	 * occurrence is deleted.
	 * 
	 * @param itemName the name of the item to remove
	 * @param itemQuantity the quantity of the item to remove
	 * @param storeName the store of the item to remove
	 */
	public void removeItem(String itemName, String itemQuantity, String storeName) {
		int quantity = Integer.parseInt(itemQuantity);
		for(int i = 0; i < items.size(); i++) {
			Item temp = items.elementAt(i);
			if(temp.getName() == itemName && temp.getQuantity() == quantity && 
			   temp.getStore() == storeName) {
				items.remove(i);
				return;
			}
		}
	}
	
	/**
	 * Removes an item from the list given the items name, quantity, and store.
	 * If the item is not found within the list, then nothing occurs. If two
	 * items matching the name, quantity, and store are found, the first
	 * occurrence is deleted.
	 * 
	 * @param itemName the name of the item to remove
	 * @param itemQuantity the quantity of the item to remove
	 * @param storeName the store of the item to remove
	 */
	public void removeItem(String itemName, int itemQuantity, String storeName) {
		for(int i = 0; i < items.size(); i++) {
			Item temp = items.elementAt(i);
			if(temp.getName() == itemName && temp.getQuantity() == itemQuantity && 
			   temp.getStore() == storeName) {
				items.remove(i);
				return;
			}
		}
	}
	
	/**
	 * Increases the quantity of the Item i in the list. If the item does not
	 * exist within the list, then the list is unchanged.
	 * 
	 * @param i the item to increase the quantity of
	 */
	public void increaseItemQuantity(Item i) {
		if(items.contains(i)) {
			int index = items.indexOf(i);
			Item temp = items.get(index);
			// if item is crossed off
			if(temp.getQuantity() < 0) {
				// uncross item
				temp.uncross();
			}
			// increase quantity
			items.get(index).setQuantity(items.get(index).getQuantity() + 1);
		}
	}
	
	/**
	 * Increases the quantity of the Item i in the list. If the item does not
	 * exist within the list, then the list is unchanged.
	 * 
	 * @param itemName the name of the item to increase the quantity of
	 * @param itemQuantity the quantity of the item to increase the quantity of
	 * @param storeName the store of the item to increase the quantity of
	 */
	public void increaseItemQuantity(String itemName, int itemQuantity, String storeName) {
		for(int i = 0; i < items.size(); i++) {
			Item temp = items.elementAt(i);
			if(temp.getName() == itemName && temp.getQuantity() == itemQuantity && 
			   temp.getStore() == storeName) {
				// if item is crossed off
				if(temp.getQuantity() < 0) {
					// uncross the item
					temp.uncross();
				}
				// increase quantity
				temp.setQuantity(temp.getQuantity() + 1);
				return;
			}
		}
	}
	
	/**
	 * Decreases the quantity of the Item i in the list. If the item does not
	 * exist within the list, then the list is unchanged.
	 * 
	 * @param i the item to decrease the quantity of
	 */
	public void decreaseItemQuantity(Item i) {
		if(items.contains(i)) {
			int index = items.indexOf(i);
			items.get(index).setQuantity(items.get(index).getQuantity() - 1);
			// if quantity is zero
			if(items.get(index).getQuantity() == 0) {
				// delete item
				this.removeItem(i);
			}
		}
	}
	
	/**
	 * Decreases the quantity of the Item i in the list. If the item does not
	 * exist within the list, then the list is unchanged.
	 * 
	 * @param itemName the name of the item to decrease the quantity of
	 * @param itemQuantity the quantity of the item to decrease the quantity of
	 * @param storeName the store of the item to decrease the quantity of
	 */
	public void decreaseItemQuantity(String itemName, int itemQuantity, String storeName) {
		for(int i = 0; i < items.size(); i++) {
			Item temp = items.elementAt(i);
			if(temp.getName() == itemName && temp.getQuantity() == itemQuantity && 
			   temp.getStore() == storeName) {
				temp.setQuantity(temp.getQuantity() - 1);
				// if quantity is now zero
				if(temp.getQuantity() == 0) {
					// remove item
					this.removeItem(itemName, itemQuantity, storeName);
				}
				return;
			}
		}
	}
	
	/**
	 * Checks if the item is contained within the list. If it is found, it
	 * returns true, otherwise it returns false.
	 * 
	 * @param itemName the name of the item to check against the list
	 * @param itemQuantity the quantity of the item to check against the list
	 * @param storeName the store of the item to check against the list
	 * @return true if the item is found, otherwise false
	 */
	public boolean hasItem(String itemName, int itemQuantity, String storeName) {
		for(int i = 0; i < items.size(); i++) {
			Item temp = items.elementAt(i);
			if(temp.getName() == itemName && temp.getQuantity() == itemQuantity && 
			   temp.getStore() == storeName) {
				return true;
			}
		}
		return false;
	}
}
