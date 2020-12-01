package constructs;

/*
 * The product represents a product in the store,
 * NOT an item on a list
 */

public class Product {
	
	String brand;
	// the type of product i.e. bread/milk
	String type; 
	// an extra component describing the product i.e. wheat/white/2%/whole
	String extra;
	
	/**
	 * Creates a product from an array of strings with the brand, type, and 
	 * extra
	 * 
	 * @param split the array of strings containing the product's brand, type,
	 * and extra
	 */
	public Product(String[] split) {
		brand = split[0];
		type = split[1];
		extra = split[2];
	}
	
	/**
	 * Gets the brand of the product
	 * 
	 * @return the product's brand
	 */
	public String getBrand() {
		return brand;
	}
	
	/**
	 * Gets the type of the product
	 * 
	 * @return the product's type
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Gets the extra of the product
	 * 
	 * @return the product's extra
	 */
	public String getExtra() {
		return extra;
	}	
	
	/**
	 * Gets a string representation of the product
	 * 
	 * @return a string containing the product's brand, type, extra
	 */
	public String toString() {
		return brand + " " + type + " " + extra;
	}
	
	/**
	 * Gets the array of strings containing the product's brand, type, and
	 * extra
	 * 
	 * @return an array of strings with the product's brand, type, extra
	 */
	public String[] getVector(){
		String[] ret = {brand, type, extra};
		return ret;
	}
}
