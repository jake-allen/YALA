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
	
	public void addItem(String itemName, int itemQuantity, String storeName) {
		// add item to the vector
		items.add(new Item(itemName, itemQuantity, storeName));
	}
	
	public void crossOff(Item i) {	//should there be protections?
		int index;
		if(items.contains(i)) {	//must exist within items
			index = items.indexOf(i);
			items.get(index).crossOff();
		}
	}
	public void uncrossOff(Item i) {
		int index;
		if(items.contains(i)) {	//must exist within items
			index = items.indexOf(i);
			items.get(index).uncross();
		}		
	}
	public void removeItem(Item i) {
		if(items.contains(i)) {	//must exist within items
			items.remove(i);
			//index = items.indexOf(i);
			//items.get(index).crossedOff = false;
		}
	}
}

