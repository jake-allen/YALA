package storage;

import java.util.Vector;

import constructs.Product;


public class Store {
	Vector<Product> products;
	String name;
	
	public Store(String name) {
		products = new Vector<Product>();
		this.name = name;
	}
	
	public void addProduct(Product p) {
		products.add(p);
	}
	
	public Vector<Product> getProducts(){
		return products;
	}
	
	public String getName() {
		return name;
	}
}
