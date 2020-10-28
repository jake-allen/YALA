package constructs;

public class Item {
	String name;
	int quantity;
	String store;
	
	Item(String itemName, String quantity, String storeName){
		this.quantity = Integer.parseInt(quantity);
		this.name = itemName;
		this.store = storeName;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getQuantity() {
		return this.quantity;
	}
	
	public String getStore() {
		return this.store;
	}
}
