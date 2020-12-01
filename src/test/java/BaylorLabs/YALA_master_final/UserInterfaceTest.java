package BaylorLabs.YALA_master_final;

import static org.junit.Assert.fail;

import java.util.Vector;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import constructs.Item;
import constructs.List;
import ui.ItemSearch;
import ui.UserInterface;

@SuppressWarnings("static-access")
class UserInterfaceTest {	
	UserInterface ui;
	Vector<List> lists;
	String[] emptyArgs;
	
	/**
	 * Start UI and login before every test
	 * 
	 * @result UI is ready and user is logged in
	 */
	@BeforeEach
	void startUI() {
		ui.main(emptyArgs);
		ui.login("jake@jakeallen.com", "password");
	}
	
	/**
	 * Adds list and deletes same list
	 * 
	 * @result if adding list and deleting it was successful
	 */
	@Test
	void addAndDeleteList() {
		System.out.println("Testing Adding List");
		if(!ui.addList("TestAdd") || ui.getList("TestAdd") == null) 
			fail();

		lists = ui.getLists();
		for (List l : lists)
			System.out.println(l.getName());		
		ui.restoreLists();
		deleteList();
	}
	void deleteList() {
		System.out.println("Testing Delete List");
		ui.deleteList("TestAdd");
		if(ui.getList("TestAdd") != null || ui.getList("TestAdd") != null)
			fail();
		ui.restoreLists();
	}
	
	/**
	 * Copying list
	 * @result if list was succesfully copied
	 */
	@Test
	void copyList() {		
		ui.addList("TestList");
		System.out.println("Testing Copy List");
		if (!ui.copyList("TestList", "CopyList") || ui.getList("CopyList") == null)
			fail();
		System.out.println("Removing...");
		ui.deleteList("CopyList");
		ui.deleteList("TestList");
		ui.restoreLists();
	}

	/**
	 * Selecting store, as in ItemSearch in user's swing
	 * @result if switching to store was successful
	 */
	@Test
	void storeSelect() {
		System.out.println("Testing Store Select");
		ItemSearch is = new ItemSearch(ui.getStoreStorage().getStores(),ui.getUser());
		is.setStoreAndSwitch(0);	//Make table of first store
		if (!is.isTableVisible()) {
			fail();
		}
	}
	
	/**
	 * Test that items are successfully filtered
	 * @result if items were successfully filtered
	 */
	@Test
	void itemSearch() {
		System.out.println("Testing Item Search (Filtering)");
		ItemSearch is = new ItemSearch(ui.getStoreStorage().getStores(),ui.getUser());
		is.setStoreAndSwitch(0);	//Make table of first store
		is.setFilter("Bread","wheat");
		String name = is.getItemNameAt(0);
		if(name.compareTo("Bread Factory Bread wheat") != 0){
			System.out.println(name + " not equal to Bread Factory Bread wheat");
			fail();
		}
	}	
	
	/**
	 * Adds item
	 * @result if item was added successfully
	 */
	@Test
	void addItem() {
		System.out.println("Testing Add Item");
		ui.addList("TestList");
		if(ui.getList("TestList") == null) 
			fail();
		List list = ui.getList("TestList");
		list.addItem("Nintendo Wii U Console",3,"Amazon");
		if (!list.hasItem("Nintendo Wii U Console",3,"Amazon")) 
			fail();
		
		ui.deleteList("TestList");
		ui.restoreLists();
	}
	
	/**
	 * Deletes item
	 * @result if item was deleted successfully
	 */
	@Test
	void deleteItem() {
		System.out.println("Testing Delete Item");
		ui.addList("TestList");
		if(ui.getList("TestList") == null) 
			fail();
		List list = ui.getList("TestList");
		list.addItem("Nintendo Wii U Console",3,"Amazon");
		list.removeItem("Nintendo Wii U Console",3,"Amazon");
		if (list.hasItem("Nintendo Wii U Console",3,"Amazon")) 
			fail();
		
		ui.deleteList("TestList");
		ui.restoreLists();
	}
	
	/**
	 * Crosses off item
	 * @result if item was crossed of successfully
	 */
	@Test
	void crossItem() {
		System.out.println("Testing Cross Item");
		ui.addList("TestList");
		if(ui.getList("TestList") == null) 
			fail();
		List list = ui.getList("TestList");
		Item item = new Item("Nintendo Wii U Console",3,"Amazon");
		list.addItem(item);
		if (!list.hasItem(item)) 
			fail();
		
		list.crossOff(item);

		if (!list.hasItem(item)) 
			fail();
				
		if (list.getItemQuantity(item) >= 0) 
			fail();
		
		ui.deleteList("TestList");
		ui.restoreLists();
	}
	
	/**
	 * Uncrosses item
	 * @result if item was uncrossed successfully
	 */
	@Test
	void uncrossItem() {
		System.out.println("Testing Uncross Item");
		ui.addList("TestList");
		if(ui.getList("TestList") == null) 
			fail();
		List list = ui.getList("TestList");
		Item item = new Item("Nintendo Wii U Console",3,"Amazon");
		list.addItem(item);
		list.crossOff(item);
		list.uncrossOff(item);
		
		if (list.getItemQuantity(item) <= 0) 
			fail();
		
		ui.deleteList("TestList");
		ui.restoreLists();
	}

}
