package constructs;

public class Item {
	String name;
	int quantity;
	String store;
	
	/**
	 * Creates an item with name itemName, quantity quantity, and store 
	 * storeName.
	 * 
	 * @param itemName the name to give the new item
	 * @param quantity the quantity to give the new item
	 * @param storeName the store to give the new item
	 */
	Item(String itemName, String quantity, String storeName){
		this.quantity = Integer.parseInt(quantity);
		this.name = itemName;
		this.store = storeName;
	}
	
	/**
	 * Creates an item with name itemName, quantity quantity, and store 
	 * storeName.
	 * 
	 * @param itemName the name to give the new item
	 * @param quantity the quantity to give the new item
	 * @param storeName the store to give the new item
	 */
	Item(String itemName, int quantity, String storeName){
		this.quantity = quantity;
		this.name = itemName;
		this.store = storeName;
	}
	
	/**
	 * 
	 * 
	 * @param quantity the quantity to set the item's quantity to
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	/**
	 * Effectively crosses off an item from the list. If the item has a
	 * negative quantity -x that means there are x items in the cart and the
	 * item is crossed off. If it has a positive quantity that means it is
	 * not crossed off and there are 0 items in the cart as it has yet to 
	 * be found and added.
	 */
	public void crossOff() {
		this.quantity *= -1;
	}
	
	/**
	 * Effectively uncrosses an item from the list. If the item has a
	 * negative quantity -x that means there are x items in the cart and the
	 * item is crossed off. If it has a positive quantity that means it is
	 * not crossed off and there are 0 items in the cart as it has yet to 
	 * be found and added.
	 */
	public void uncross() {
		this.quantity *= -1;
	}
	
	/**
	 * Gets the name of the item.
	 * 
	 * @return the name of the item
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Gets the quantity of the item.
	 * 
	 * @return the quantity of the item
	 */
	public int getQuantity() {
		return this.quantity;
	}
	
	/**
	 * Gets the name of the store the item can be found at.
	 * 
	 * @return the name of the store at which the item can be found
	 */
	public String getStore() {
		return this.store;
	}
	
	/**
	 * Returns true if the item is crossed off, otherwise it returns false. An
	 * item is considered crossed off if its quantity is negative.
	 * 
	 * @return
	 */
	public boolean isCrossedOff(){
		if(quantity < 0){
			return true;
		}
		return false;
	}
	
	/**
	 * Generates a hash code for a given Item.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((store == null) ? 0 : store.hashCode());
		return result;
	}

	/**
	 * Determines whether two items are equal.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (store == null) {
			if (other.store != null)
				return false;
		} else if (!store.equals(other.store))
			return false;
		return true;
	}
}
