package constructs;

import java.util.Vector;

/*
 * The product represents a product in the store,
 * NOT an item on a list
 */

public class Product {
	String brand, type, extra;
	
	public Product(String[] split) {
		brand = split[0];
		type = split[1];
		extra = split[2];
	}
	
	public String getBrand() {
		return brand;
	}
	
	public String getType() {
		return type;
	}
	
	public String getExtra() {
		return extra;
	}	
	
	public String toString() {
		return brand + " " + type + " " + extra;
	}
	
	public String[] getVector(){
		String[] ret = {brand, type, extra};
		return ret;
	}
}
