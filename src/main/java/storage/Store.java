package storage;

import java.util.Vector;

import constructs.Product;


public class Store {
	Vector<Product> products;
	String name;
	
	/**
	 * Constructs a store object with the given name.
	 * 
	 * @param name the name of the store to create
	 */
	public Store(String name) {
		products = new Vector<Product>();
		this.name = name;
	}
	
	/**
	 * Adds a product to the store.
	 * 
	 * @param p the product to add to the store
	 */
	public void addProduct(Product p) {
		products.add(p);
	}
	
	/**
	 * Gets the vector of Products that the store has.
	 * 
	 * @return the vector of products in the store
	 */
	public Vector<Product> getProducts(){
		return products;
	}
	
	/**
	 * Gets the name of a Store.
	 * 
	 * @return the name of the store
	 */
	public String getName() {
		return name;
	}
}
