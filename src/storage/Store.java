import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class Store {
	Vector<Product> products;
	String name;
	
	public Store() {
		name = "Test";
		products = parseInventory();
//		products.stream()
//			.forEach(p -> System.out.println(p.toString()));
		
	}
	
	public Vector<Product> getProducts(){
		return products;
	}
	
	public String getName() {
		return name;
	}
	
	Vector<Product> parseInventory(){
		BufferedReader reader = null;
		Vector<Product> newProds = new Vector<Product>();
		try {			
			reader = new BufferedReader(new FileReader(new File("Sample Inventory")));
			String line = null;
			name = reader.readLine();
			while ((line = reader.readLine()) != null) {
				String[] split = line.split(",");
				newProds.add(new Product(split));				
			}

		} catch (IOException e) {
			System.out.println("File read error!");
		}
		return newProds;
	}
}
