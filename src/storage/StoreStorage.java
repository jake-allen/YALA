package storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

import constructs.Product;

public class StoreStorage {
	Vector<Store> stores;
	
	/**
	 * Loads stores in from the storage file to the vector of stores in the
	 * StoreStorage.
	 * 
	 * @param filename the file from which to load the stores from
	 */
	public StoreStorage(String filename) {
		stores = new Vector<Store>();
		Scanner scanner = null;
		try {
			String line;
			scanner = new Scanner(new File(filename));
			while(scanner.hasNextLine()) {
				line = scanner.nextLine();
				String[] split = line.split(",");
				String storeName = split[0];
				int itemsInStore = Integer.parseInt(split[1]);
				Store s = new Store(storeName);		
				for (int i = 0; i < itemsInStore; i++) {
					String[] productInfo = scanner.nextLine().split(",");
					s.addProduct(new Product(productInfo));	
				}				
				stores.add(s);
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("StoreStorage error: "+ e.getMessage());
		}
	}
	
	/**
	 * Gets the vector of stores.
	 * 
	 * @return a vector of stores contained in the StoreStorage.
	 */
	public Vector<Store> getStores(){
		return stores;
	}
}
