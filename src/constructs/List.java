package constructs;

import java.util.Vector;

public class List {
	String name;
	Vector<Item> items;
	
	public List(String listname){
		name = listname;
		items = new Vector<Item>();
	}
	
	public String getName() {
		return this.name;
	}
	
	
	public Vector<Item> getItems(){
		return this.items;
	}
	
	public boolean hasItem(Item i) {
		if(items.contains(i)) {
			return true;
		}
		return false;
	}
	
	public void addItem(Item item) {
		items.add(item);
	}
	
	public void addItem(String itemName, String itemQuantity, String storeName) {
		// add item to the vector
		items.add(new Item(itemName, itemQuantity, storeName));
	}
	
	public void addItem(String itemName, int itemQuantity, String storeName) {
		// add item to the vector
		items.add(new Item(itemName, itemQuantity, storeName));
	}
	
	public void crossOff(Item i) {	
		int index;
		// item must exist within items
		if(items.contains(i)) {	
			index = items.indexOf(i);
			items.get(index).crossOff();
		}
	}
	public void uncrossOff(Item i) {
		int index;
		// item must exist within items
		if(items.contains(i)) {	
			index = items.indexOf(i);
			items.get(index).uncross();
		}		
	}
	public void removeItem(Item i) {
		// item must exist within items
		if(items.contains(i)) {	
			items.remove(i);
		}
	}
	
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
