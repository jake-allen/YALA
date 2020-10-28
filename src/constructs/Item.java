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
	
	Item(String itemName, int quantity, String storeName){
		this.quantity = quantity;
		this.name = itemName;
		this.store = storeName;
	}
	
	//Sam added this
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((store == null) ? 0 : store.hashCode());
		return result;
	}

	//also sam added this
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
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public void crossOff() {
		this.quantity *= -1;
	}
	
	public void uncross() {
		this.quantity *= -1;
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
	
	public boolean isCrossedOff(){
		if(quantity < 0){
			return true;
		}
		return false;
	}
}
