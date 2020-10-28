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
	
	// item-name, item-quantity, item-store-name
	public void addItem(String itemName, String itemQuantity, String storeName) {
		// add item to the vector
		items.add(new Item(itemName, itemQuantity, storeName));
	}
}

