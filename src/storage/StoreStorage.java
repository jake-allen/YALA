package storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

import constructs.Product;

public class StoreStorage {
	Vector<Store> stores;
	
	// load stores in from the storage to the Vector
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
	
	public Vector<Store> getStores(){
		return stores;
	}
}
