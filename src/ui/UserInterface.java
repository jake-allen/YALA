package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Vector;

import constructs.*;
import storage.*;

public class UserInterface{	
	static boolean loggedIn = false;
	public static User user;
	static UserStorage userStorage;
	static StoreStorage storeStorage;		
	static JFrame frame;
	static JFrame popUpAddFrame;
	static JFrame itemListManager;	
	
	static JPanel cards;
	static CardLayout cl;

	// card that shows the loggedInMenuBar and lists
	final static String LOGGEDIN = "Logged in User Card"; 
	// card that shows newUserMenuBar to select create account or log in
	final static String NEWUSER = "New User Card"; 
	// card for creating a new account 
	final static String CREATEACCOUNT = "Create Account Card"; 
	// card for logging in to an existing account
	final static String LOGIN = "Log in User Card"; 

//====================SYSTEM OPERATIONS (for JUnit)================================
	/**
	 * Logs user in
	 * 
	 * @param email email of user
	 * @param password password of user's login
	 */
	
	
	public static void login(String email, String password) {
		if (userStorage == null) 
			userStorage = new UserStorage();
		if(userStorage.getUser(email, password) != null) {
			user = userStorage.getUser(email, password);
			loggedIn = true;
		}
	}
	
	
	/**
	 * Gets lists in user's list storage.
	 * 
	 * @return lists in user's list storage
	 */
	public static Vector<List> getLists(){
		return user.getListStorage().getLists();
	}
	
	/**
	 * Gets user's list storage
	 * 
	 * @return user's list storage
	 */
	public static ListStorage getListStorage() {
		return user.getListStorage();
	}
	
	/**
	 * Gets the storage holding all the stores & their inventories.
	 * 
	 * @return storage holding all the stores.
	 */
	public static StoreStorage getStoreStorage() {
		return storeStorage;
	}
	
	/**
	 * Gets the user's list with the specified name.
	 * 
	 * @param listName name of list
	 * @return list with given name
	 */
	public static List getList(String listName) {
		return user.getListStorage().getList(listName);
	}
	
	/**
	 * Reads out the changes in user's list storage (e.g. adding a list)
	 *	to the the file storing the user's lists
	 */
	public static void restoreLists() {
		user.getListStorage().restoreLists();
	}
	
	/**
	 * Gets the current, logged in user.
	 * 
	 * @return the present user
	 */
	public static User getUser() {
		return user;
	}	
	
	/**
	 * Adds list with given name to user's list storage
	 * 
	 * @param newList name of new list
	 * @return if adding the list was successful
	 */
	public static boolean addList(String newList) {
		ListStorage storage = user.getListStorage();
		if (!storage.hasList(newList)) {
			storage.addList(newList);
			return true;
		}
		else {
			System.out.println("ERROR: Cannot have two lists of same name");
			return false;
		}		
	}
	
	/**
	 * Copies list with given name in user's list storage to a new list (name
	 *	given)
	 * 
	 * @param oldList name of list to be copied
	 * @param newList name of new list
	 * @return if copying list was successful
	 */
	public static boolean copyList(String oldList, String newList) {
		ListStorage storage = user.getListStorage();
		if (!storage.hasList(newList)){	
			if(storage.hasList(oldList)) {
				storage.copyList(oldList, newList);
				return true;
			}
			else {
				System.out.println("ERROR: list "+oldList+" does not exist");
				return false;
			}
		} else {
			System.out.println("ERROR: Cannot have two lists of same name");
			return false;
		}
	}
	
	/**
	 * Delete's list of given name from user's list storage
	 *
	 * @param name of list to be deleted
	 */
	public static void deleteList(String listName) {
		ListStorage storage = user.getListStorage();
		if(storage.hasList(listName)) 
			storage.deleteList(listName);
		else 
			System.out.println("ERROR: list does not exist");
	}
	
	/**
	 * Crosses item from user's given list
	 * 
	 * @param list the list in which the item to be crossed is in
	 * @param row the index having the item to be crossed
	 * @return if cross operation was successful
	 */
	public static boolean crossItem(List list, int row) {
		if (row >= 0) {
			list.getItems().get(row).crossOff();
			return true;
		} else {
			System.out.println("ERROR: Line not selected");
			return false;
		}		
	}
	
	/**
	 * Unrosses item from user's given list
	 * 
	 * @param list the list in which the item to be uncrossed is in
	 * @param row the index having the item to be uncrossed
	 * @return if uncross operation was successful
	 */
	public static boolean uncrossItem(List list, int row) {
		return crossItem(list,row);
	}
	
	/**
	 * Deletes item of given index from user's given list
	 * 
	 * @param list the list in which the item to be deleted is in
	 * @param row the index having the item to be deleted
	 * @return if deleting the item was successful
	 */
	public static boolean deleteItem(List list, int row) {
		if (row >= 0) {
			list.removeItem(list.getItems().get(row));
			return true;
		} else {
			System.out.println("ERROR: Invalid item");
			return false;
		}
		
	}
	
	//======================SWING CARDS====================================

	/**
	 * Creates a JPanel swing component with fields for the user's ID and 
	 * password, and handles the submit login and cancel login user actions.
	 * 
	 * @return login JPanel for the CardLayout
	 */
	public static JPanel loginCard() {
		// create text fields
		final JTextField idField = new JTextField(20);
		final JPasswordField passwordField = new JPasswordField(20);
		// add labels
		JLabel idLabel = new JLabel("Enter email: ");
		JLabel passwordLabel = new JLabel("Enter password: ");
		idLabel.setLabelFor(idField);
		passwordLabel.setLabelFor(passwordField);
		// create panel and add components
		JPanel loginPanel = new JPanel();
		loginPanel.add(idLabel);
		loginPanel.add(idField);
		loginPanel.add(passwordLabel);
		loginPanel.add(passwordField);
		// create buttons
		JButton submitButton = new JButton("Submit");
		JButton cancelButton = new JButton("Cancel");
		// submit login action listener
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(e.getActionCommand());
				// get user-name and password that the user entered
				String email = idField.getText();
				String password = new String(passwordField.getPassword());
				// validate to see if the login is correct 
				if(userStorage.getUser(email, password) != null) {
					user = userStorage.getUser(email, password);
					loggedIn = true;
					// refresh frame to show the user-name of the logged in user
					frame.dispose();
					createAndShowGUI();
					// switch to the logged in card 
					cl.show(cards, LOGGEDIN);
				}
				else {
					System.out.println("ERROR: invalid login"); 
					// switch back to the new user card 
					cl.show(cards, NEWUSER);
				}
			}
		});
		// cancel login action listener
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(e.getActionCommand());
				// switch back to the new user card 
				cl.show(cards, NEWUSER);
			}
		});
		// add buttons to the panel
		loginPanel.add(submitButton);
		loginPanel.add(cancelButton);
		return loginPanel;
	}

	/**
	 * Creates a JMenuBar swing component with menu items Account and Help.
	 * Account contains the logout option. Help contains integrated 
	 * documentation for helping the user navigate the application
	 * 
	 * @return menu JMenuBar for logged in users
	 */
	public static JMenuBar loggedInMenuBar() {
		// create menu bar
		JMenuBar menuBar = new JMenuBar();
		// create menus
		JMenu accountMenu = new JMenu("My Account");
		JMenu helpMenu = new JMenu("Help");
		JMenu userDisplayMenu;
		if(loggedIn) {
			userDisplayMenu = new JMenu("Welcome, " + user.getID());
		}
		else {
			userDisplayMenu = new JMenu("Welcome, null");
		}
		// add the menus to the menu bar
		menuBar.add(accountMenu);
		menuBar.add(helpMenu);
		menuBar.add(Box.createHorizontalGlue());
		menuBar.add(userDisplayMenu);
		// create account menu items
		JMenuItem logoutMenuItem = new JMenuItem("Logout");
		// create account menu listener
		logoutMenuItem.addActionListener(new ActionListener() {
			// logout selected
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(e.getActionCommand());
				// save user changes to their lists
				user.getListStorage().restoreLists();
				// logout
				loggedIn = false;
				user = null;
				// switch to a not logged in card
				cl.show(cards, NEWUSER);	
			}
		});
		// add account menu items to the account menu
		accountMenu.add(logoutMenuItem);
		// create help menu items
		JMenu createListMenuItem = new JMenu("Creating a list");
		createListMenuItem.add(new JMenuItem("To create a list, you can either duplicate a list that you currently have, or"));
		createListMenuItem.add(new JMenuItem("you can create a new and empty list. To create a new list, click 'Add a List',"));
		createListMenuItem.add(new JMenuItem("then fill in the text box with the name of your new list. Then press 'Submit', and"));
		createListMenuItem.add(new JMenuItem("you will see that your new list is created. To duplicate a list based on an old"));
		createListMenuItem.add(new JMenuItem("list, click 'Duplicate a List', then fill in the text box with the name of your"));
		createListMenuItem.add(new JMenuItem("new list, and then click on the list whose items you'd like to be copied into your"));
		createListMenuItem.add(new JMenuItem("new list. You then will see that your new list is created, and if you click on it,"));
		createListMenuItem.add(new JMenuItem("you can see that it has the same items as your old list, and you can now edit it as"));
		createListMenuItem.add(new JMenuItem("you please"));
		// search a list help
		JMenu searchMenuItem = new JMenu("Searching for an item");
		searchMenuItem.add(new JMenuItem("To search for an item, select 'Search Items' and choose the store you would like to"));
		searchMenuItem.add(new JMenuItem("search. Then click filter in the top right and use the categories to look for the"));
		searchMenuItem.add(new JMenuItem("item that you are looking for. Click on the category your item is contained within, "));
		searchMenuItem.add(new JMenuItem("and you will see the resulting items. From there you can view the items you were"));
		searchMenuItem.add(new JMenuItem("looking for, and even add them to one of your lists."));
		// edit a list help
		JMenu editListMenuItem = new JMenu("Editing a list");
		editListMenuItem.add(new JMenuItem("To edit a list, you can add an item, delete an item, or cross/uncross the item off"));
		editListMenuItem.add(new JMenuItem("of the list."));
		editListMenuItem.add(new JMenuItem(""));
		editListMenuItem.add(new JMenuItem("Adding an Item"));
		editListMenuItem.add(new JMenuItem("To add an item, click 'Search Items', select the store you'd like to"));
		editListMenuItem.add(new JMenuItem("shop from, and then find the item you like, either through scrolling or using the"));
		editListMenuItem.add(new JMenuItem("filter in the top right. There is an 'Add Item' button to the right of the items, "));
		editListMenuItem.add(new JMenuItem("select that, then choose the list you'd like to add it to, and the quantity you'd"));
		editListMenuItem.add(new JMenuItem("like to add, and then press 'Add Item.' The item you chose will now be in the list"));
		editListMenuItem.add(new JMenuItem("you chose."));
		editListMenuItem.add(new JMenuItem(""));
		editListMenuItem.add(new JMenuItem("Deleting an Item"));
		editListMenuItem.add(new JMenuItem("To delete an item, select the list you would like to delete it from, click on the"));
		editListMenuItem.add(new JMenuItem("item that you want to delete so that the item is highlighted, and then press the"));
		editListMenuItem.add(new JMenuItem("delete button in the top middle of the display. You must then click 'Yes' to delete"));
		editListMenuItem.add(new JMenuItem("the item, or 'No' to cancel the deletion."));
		editListMenuItem.add(new JMenuItem(""));
		editListMenuItem.add(new JMenuItem("Crossing Off / Uncrossing an Item"));
		editListMenuItem.add(new JMenuItem("To cross of an item, select the list you would like to delete it from, click on the"));
		editListMenuItem.add(new JMenuItem("item that you want to delete so that the item is highlighted, and then press the"));
		editListMenuItem.add(new JMenuItem("'Cross/Uncross' button in the top left of the display. Your item will now be crossed"));
		editListMenuItem.add(new JMenuItem("off, as it is in your cart. To uncross an item off, select the item so that it is"));
		editListMenuItem.add(new JMenuItem("highlighted, and click the same button. You will notice it now is removed from the cart."));
		editListMenuItem.add(new JMenuItem(""));
		editListMenuItem.add(new JMenuItem("To cancel a step at any time, press the red X in the top left of any pop-up display."));
		// account help
		JMenu accountHelpMenuItem = new JMenu("Account help");
		accountHelpMenuItem.add(new JMenuItem("To logout, press the 'Account' menu in the top left of the display, press the logout"));
		accountHelpMenuItem.add(new JMenuItem("button that is shown in the drop down menu, and you will be logged out."));
		accountHelpMenuItem.add(new JMenuItem(""));
		accountHelpMenuItem.add(new JMenuItem("To create a new account, logout and press 'Get Started' in the top left of the display."));
		accountHelpMenuItem.add(new JMenuItem("Enter your username, email, and password and then press 'Submit.' You will then be"));
		accountHelpMenuItem.add(new JMenuItem("logged in as your new account."));
		// add help menu items to the account menu
		helpMenu.add(createListMenuItem);
		helpMenu.addSeparator();
		helpMenu.add(searchMenuItem);
		helpMenu.addSeparator();
		helpMenu.add(editListMenuItem);
		helpMenu.addSeparator();
		helpMenu.add(accountHelpMenuItem);	
		return menuBar;
	}
	
	/**
	 * Creates a JMenuBar swing component with menu item Get Started which 
	 * contains menu options of login and create account. It contains the
	 * handler for the login and create account user actions.
	 * 
	 * @return menu JMenuBar for non-logged in users
	 */
	public static JMenuBar newUserMenuBar() {
		// create menu bar
		JMenuBar menuBar = new JMenuBar();
		// create account menu
		JMenu accountMenu = new JMenu("Get Started");
		// add menu to the menu bar
		menuBar.add(accountMenu);
		// create login and create account menu options
		JMenuItem loginMenuItem = new JMenuItem("Login");
		JMenuItem createAccountMenuItem = new JMenuItem("Create account");
		// add action listeners
		loginMenuItem.addActionListener(new ActionListener() {
			// selected login 
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(e.getActionCommand());
				cl.show(cards, LOGIN);	
			}
		});
		createAccountMenuItem.addActionListener(new ActionListener() {
			// selected create account 
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(e.getActionCommand());
				cl.show(cards, CREATEACCOUNT);
			}
		});
		//SAM'S DEBUG MODE - TODO REMOVE
		JMenuItem debugMenuItem = new JMenuItem("Debug");	
		debugMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(e.getActionCommand());
				user = userStorage.getUser("jake@jakeallen.com", "password");
				loggedIn = true;
				frame.dispose();
				createAndShowGUI();
				cl.show(cards, LOGGEDIN);
			}
		});
		// add the menu items to the menu
		accountMenu.add(loginMenuItem);
		accountMenu.addSeparator();
		accountMenu.add(createAccountMenuItem);
		accountMenu.addSeparator();
		accountMenu.add(debugMenuItem);
		return menuBar;
	}
	
	/**
	 * Creates a JPanel swing component with fields for a username, email,
	 * and password, and a submit and cancel button. It contains the handler 
	 * for the submit and cancel create account user actions.
	 * 
	 * @return create account JPanel for the CardLayout
	 */
	public static JPanel createAccountCard() {
		// create the text fields
		final JTextField usernameField = new  JTextField(20);
		final JTextField emailField = new JTextField(20);
		final JPasswordField passwordField = new JPasswordField(20);
		// add labels
		JLabel usernameLabel = new JLabel("Enter username: ");
		JLabel emailLabel = new JLabel("Enter email: ");
		JLabel passwordLabel = new JLabel("Enter password: ");
		usernameLabel.setLabelFor(usernameField);
		emailLabel.setLabelFor(emailField);
		passwordLabel.setLabelFor(passwordField);
		// create panel and add components
		JPanel textPane = new JPanel();
		textPane.add(usernameLabel);
		textPane.add(usernameField);
		textPane.add(emailLabel);
		textPane.add(emailField);
		textPane.add(passwordLabel);
		textPane.add(passwordField);
		// create buttons
		JButton submitButton = new JButton("Submit");
		JButton cancelButton = new JButton("Cancel");
		// submit create account action listener
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(e.getActionCommand());
				// get the password, user-name, and email entered
				String password = new String(passwordField.getPassword());
				String username = usernameField.getText();
				String email = emailField.getText();
				// validate input
				if(userStorage.getUser(email, password) == null) {
					// create new user and add it
					user = new User(username, email, password);
					userStorage.addUser(user);
					// tell UserInterface that the user is now logged in
					loggedIn = true;
					// refresh frame to show the user-name of the logged in user
					frame.dispose();
					createAndShowGUI();
					// switch to the logged in card
					cl.show(cards, LOGGEDIN);
					// print for testing 
					System.out.println("user: " + user.getID());
					System.out.println("email: " + user.getEmail());
				}
				else {
					System.out.println("ERROR: user already exists");
					System.out.println("ERROR: user email/password already exists");
					// switch back to the new user card 
					cl.show(cards, NEWUSER);
				}	
			}
		});
		// cancel create account action listener
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(e.getActionCommand());
				// switch back to the new user card 
				cl.show(cards, NEWUSER);
			}
		});
		// add buttons to the panel
		textPane.add(submitButton);
		textPane.add(cancelButton);
		// return the result
		return textPane;
	}
	
	/**
	 * Creates a JPanel swing component that displays the logged in user's
	 * lists as buttons, the logged in menu bar, and the buttons to Search 
	 * Items, Add a List, Duplicate a List, and Delete a List. It contains the 
	 * handler for the Search Items, Add a List, Delete a List, and the 
	 * Copy/Duplicate a List user actions.
	 * 
	 * @return logged in JPanel for the CardLayout
	 */
	public static JPanel loggedInCard() {
		JPanel panel = new JPanel();		
		
		if(loggedIn) {	
			Vector<List> lists = user.getListStorage().getLists();
			
			// print lists and items for debugging TODO REMOVE??
			for(int i = 0; i < lists.size(); i++) {
				List list = lists.elementAt(i);
				Vector<Item> items = list.getItems();
				System.out.println("list: " + i + ", " + list.getName());
				for(int j = 0; j < items.size(); j++) {
					Item item = items.elementAt(j);
					System.out.println(item.getName() + " " + item.getQuantity() + " " + item.getStore());
				}
			}
			for(int i = 0; i < lists.size(); i++) {
				final List list = lists.elementAt(i);
				JButton listButton = new JButton(list.getName());
				listButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// list clicked
						String command = ((JButton) e.getSource()).getActionCommand();
						System.out.println(command + " list clicked");
						itemListManager = new JFrame(command);
						itemListManager.add(itemInListManagerField(list));
						itemListManager.pack();	
						itemListManager.setVisible(true);
						
						// print lists and items for debugging TODO REMOVE???
						for(int i = 0; i < lists.size(); i++) {
							List list = lists.elementAt(i);
							Vector<Item> items = list.getItems();
							System.out.println("list: " + i + ", " + list.getName());
							for(int j = 0; j < items.size(); j++) {
								Item item = items.elementAt(j);
								System.out.println(item.getName() + " " + item.getQuantity() + " " + item.getStore());
							}
						}
						
					}
				});
				System.out.println("ADDING BUTTON");
				panel.add(listButton);	
			}	
		}

		JPanel listPanel = new JPanel();
		JButton addListButton = new JButton("Add a List");
		addListButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// add list clicked
				System.out.println("add a list clicked");
				popUpAddFrame = new JFrame("Add List");
				popUpAddFrame.add(addListTextField());
				popUpAddFrame.pack();
				popUpAddFrame.setVisible(true);
			}
		});
		JButton deleteListButton = new JButton("Delete a List");
		deleteListButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// delete list clicked
				System.out.println("add a list clicked");
				popUpAddFrame = new JFrame("Delete List");
				
				//popUpAddFrame.add(deleteListTextField());
				popUpAddFrame.add(deleteListButtonField());
				
				popUpAddFrame.pack();
				popUpAddFrame.setVisible(true);
			}
		});
		JButton copyListButton = new JButton("Duplicate a List");
		copyListButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// delete list clicked
				System.out.println("copy a list clicked");
				popUpAddFrame = new JFrame("Copy a List");
				
				//popUpAddFrame.add(copyListTextField());
				popUpAddFrame.add(copyListButtonField());
				
				popUpAddFrame.pack();
				popUpAddFrame.setVisible(true);
			}
		});
		
		JButton searchButton = new JButton("Search Items");
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ItemSearch is = new ItemSearch(storeStorage.getStores(), user);
			}
		});

		listPanel.add(searchButton);	
		listPanel.add(addListButton);
		listPanel.add(copyListButton);	
		listPanel.add(deleteListButton);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(loggedInMenuBar(), BorderLayout.PAGE_START);
		mainPanel.add(listPanel, BorderLayout.CENTER);
		mainPanel.add(panel, BorderLayout.PAGE_END);
		return mainPanel;
	}
	
	/**
	 * Creates a JPanel swing component that is a pop-up text field with a
	 * field for the user to enter the list name, and buttons to submit or 
	 * cancel. It handles the Submit create a list and the cancel creating a
	 * list user actions's
	 * 
	 * @return pop-up JPanel for adding a list
	 */
	public static JPanel addListTextField() {
		JPanel mainPanel = new JPanel();
		// create text field and label
		JLabel nameLabel = new JLabel("List name: ");
		final JFormattedTextField nameField = new JFormattedTextField();
		nameField.setColumns(20);
		// create panel for the text field and label
		JPanel labelPane = new JPanel(new GridLayout(0, 1));
		labelPane.add(nameLabel);
		JPanel fieldPane = new JPanel(new GridLayout(0, 1));
		fieldPane.add(nameField);
		// create buttons
		JPanel buttonPane = new JPanel();
		JButton submitButton = new JButton("Submit");
		JButton cancelButton = new JButton("Cancel");
		buttonPane.add(submitButton);
		buttonPane.add(cancelButton);
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Submit add list pressed " + nameField.getText());
				// add list
				ListStorage storage = user.getListStorage();

				if (!storage.hasList(nameField.getText())) {
					storage.addList(nameField.getText());
				}
				else {
					System.out.println("Cannot have two lists of same name");
				}
				// close add list frame
				popUpAddFrame.dispose();
				// refresh logged-in frame and components
				frame.dispose();
				createAndShowGUI();
				// switch to the logged in card 
				cl.show(cards, LOGGEDIN);
			}
		});
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Cancel add list pressed");
				popUpAddFrame.dispose();
			}
		});
		// add components
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(labelPane, BorderLayout.CENTER);
		mainPanel.add(fieldPane, BorderLayout.LINE_END);
		mainPanel.add(buttonPane, BorderLayout.PAGE_END);
		return mainPanel;
	}
	
	/*
	public static JPanel deleteListTextField() {
		JPanel mainPanel = new JPanel();
		JLabel nameLabel = new JLabel("List name to delete: ");
		final JFormattedTextField nameField = new JFormattedTextField();
		nameField.setColumns(20);
		JPanel labelPane = new JPanel(new GridLayout(0, 1));
		labelPane.add(nameLabel);
		JPanel fieldPane = new JPanel(new GridLayout(0, 1));
		fieldPane.add(nameField);
		JPanel buttonPane = new JPanel();
		JButton submitButton = new JButton("Submit");
		JButton cancelButton = new JButton("Cancel");
		buttonPane.add(submitButton);
		buttonPane.add(cancelButton);
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Submit delete list pressed " + nameField.getText());
				// delete list
				ListStorage storage = user.getListStorage();
				if(storage.hasList(nameField.getText())) {
					storage.deleteList(nameField.getText());
				}
				else {
					System.out.println("ERROR: list does not exist");
				}
				// close add list frame
				popUpAddFrame.dispose();
				// refresh logged-in frame and components
				frame.dispose();
				createAndShowGUI();
				// switch to the logged in card 
				cl.show(cards, LOGGEDIN);
			}
		});
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Cancel add list pressed");
				popUpAddFrame.dispose();
			}
		});
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(labelPane, BorderLayout.CENTER);
		mainPanel.add(fieldPane, BorderLayout.LINE_END);
		mainPanel.add(buttonPane, BorderLayout.PAGE_END);
		return mainPanel;
	}*/
	
	/**
	 * Creates a JPanel swing component that is a pop-up display of all of the
	 * logged-in user's lists as buttons, allowing the user to select one 
	 * to delete. It then opens a prompt asking the user if they are sure
	 * they want to delete list name list, with the button options of Yes and 
	 * No. It contains the handlers for selecting a list to delete, deleting 
	 * the list, and canceling the deletion user actions. 
	 * 
	 * @return JPanel to choose which list to delete
	 */
	public static JPanel deleteListButtonField() {
		JPanel mainPanel = new JPanel();		
		JLabel nameLabel = new JLabel("List to delete: ");
		JPanel buttonPane = new JPanel();
		ListStorage storage = user.getListStorage();
		// create a button for each list
		for (int i = 0; i < storage.getLists().size(); i++) {
			JButton listButton = new JButton(storage.getLists().get(i).getName());
			List list = storage.getLists().elementAt(i);
			listButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JFrame confirmPrompt = new JFrame("Delete "+ list.getName() + "?");
					JPanel confirm = new JPanel();
					JButton yesButton = new JButton("Yes");
					yesButton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							storage.deleteList(list.getName());
							confirmPrompt.dispose();
							popUpAddFrame.dispose();
							// refresh logged-in frame and components
							frame.dispose();
							createAndShowGUI();
							// switch to the logged in card 
							cl.show(cards, LOGGEDIN);
						}			
					});		
					JButton noButton = new JButton("No");
					noButton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							confirmPrompt.dispose();
						}			
					});	
					confirm.add(yesButton);
					confirm.add(noButton);
					confirmPrompt.add(confirm);
					confirmPrompt.setSize(500,75);
					confirmPrompt.setVisible(true);
				}			
			});
			buttonPane.add(listButton);
		}
		JButton cancelButton = new JButton("Cancel");
		buttonPane.add(cancelButton);	
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Cancel delete list pressed");
				popUpAddFrame.dispose();
			}
		});
		// add components
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(nameLabel, BorderLayout.LINE_END);
		mainPanel.add(buttonPane, BorderLayout.PAGE_END);
		return mainPanel;
	}
	
	/*
	public static JPanel copyListTextField() {
		JPanel mainPanel = new JPanel();
		JLabel nameLabel = new JLabel("List to copy: ");
		final JFormattedTextField nameField = new JFormattedTextField();
		nameField.setColumns(20);
		JLabel newNameLabel = new JLabel("Name of new list: ");
		final JFormattedTextField newNameField = new JFormattedTextField();
		newNameField.setColumns(20);
		JPanel labelPane = new JPanel(new GridLayout(0, 1));
		labelPane.add(nameLabel);
		labelPane.add(newNameLabel);
		JPanel fieldPane = new JPanel(new GridLayout(0, 1));
		fieldPane.add(nameField);
		fieldPane.add(newNameField);
		JPanel buttonPane = new JPanel();
		JButton submitButton = new JButton("Submit");
		JButton cancelButton = new JButton("Cancel");
		buttonPane.add(submitButton);
		buttonPane.add(cancelButton);
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Submit copy list pressed " + nameField.getText());
				// add list
				ListStorage storage = user.getListStorage();
				if(storage.hasList(nameField.getText())) {
					// duplicate list with new name
					storage.copyList(nameField.getText(), newNameField.getText());
				}
				else {
					System.out.println("ERROR: list does not exist");
				}
				// close add list frame
				popUpAddFrame.dispose();
				// refresh logged-in frame and components
				frame.dispose();
				createAndShowGUI();
				// switch to the logged in card 
				cl.show(cards, LOGGEDIN);
			}
		});
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Cancel copy list pressed");
				popUpAddFrame.dispose();
			}
		});
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(labelPane, BorderLayout.CENTER);
		mainPanel.add(fieldPane, BorderLayout.LINE_END);
		mainPanel.add(buttonPane, BorderLayout.PAGE_END);
		return mainPanel;	
	}
	*/
	
	/**
	 * Creates a JPanel swing component for copying a list. It allows the
	 * user to enter a name for the new list, and select the list it would like
	 * to copy. It contains the handlers for duplicating a list and canceling
	 * the duplication user actions.
	 * 
	 * @return JPanel with the lists shown and a text field for duplicating
	 * a list that already exists
	 */
	public static JPanel copyListButtonField() {
		JPanel mainPanel = new JPanel();		
		JLabel nameLabel = new JLabel("List to copy: ");		
		JLabel newNameLabel = new JLabel("Name of new list: ");
		final JFormattedTextField newNameField = new JFormattedTextField();
		newNameField.setColumns(20);
		JPanel labelPane = new JPanel(new GridLayout(0, 1));
		labelPane.add(newNameLabel);
		labelPane.add(newNameField);
		JPanel buttonPane = new JPanel();
		buttonPane.add(nameLabel);
		ListStorage storage = user.getListStorage();
		for (int i = 0; i < storage.getLists().size(); i++) {
			JButton listButton = new JButton(storage.getLists().get(i).getName());
			List list = storage.getLists().elementAt(i);
			listButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (!storage.hasList(newNameField.getText())){		
						storage.copyList(list.getName(), newNameField.getText());
						popUpAddFrame.dispose();
						// refresh logged-in frame and components
						frame.dispose();
						createAndShowGUI();
						// switch to the logged in card 
						cl.show(cards, LOGGEDIN);
					} else {
						System.out.println("Cannot have two lists of same name");
						popUpAddFrame.dispose();	
					}
				}
			});
			buttonPane.add(listButton);
		}
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Cancel copy list pressed");
				popUpAddFrame.dispose();
			}
		});
		buttonPane.add(cancelButton);	
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(buttonPane, BorderLayout.CENTER);
		mainPanel.add(labelPane, BorderLayout.PAGE_START);
		return mainPanel;
		
	}
	
	/**
	 * Creates a JPanel swing pop-up component that displays the items in a
	 * certain list and gives the user the ability to cross off, uncross, or
	 * delete items from the list. It contains the handlers for crossing off,
	 * uncrossing off, and deleting an item user actions.
	 * 
	 * @param list to display the items of
	 * @return JPanel with a list's items and options to cross off and uncross 
	 * and delete
	 */
	public static JPanel itemInListManagerField(final List list) {
		JPanel mainPanel = new JPanel(new BorderLayout());
		JMenuBar optionBar = new JMenuBar();
		String[] columnNames = {"Item", "Store", "NumLeft"};
		// get items from current list
		Vector<Item> items = list.getItems();
		Object[][] data = new Object[items.size()][];
		for (int i = 0; i < items.size(); i++) {
			String[] stuff = new String[3];
			stuff[0] = items.get(i).getName();
			stuff[1] = items.get(i).getStore();
			int quantity = items.get(i).getQuantity();
			// TODO - Consider how to implement if quantity happens to be zero
			if (quantity < 0) {
				stuff[2] = "Complete ("+Integer.toString(quantity*-1)+" in cart)";
			}
			else {
				stuff[2] = Integer.toString(quantity);
			}
			data[i] = stuff;
		}
		
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		JTable table = new JTable(model);
		// create cross and uncross button
		JButton crossButton = new JButton("Cross/Uncross");
		crossButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();				
				if (row >= 0) {	
					// do stuff - item.crossOff/uncross do the exact same thing
					list.getItems().get(row).crossOff();
					// refresh table
					itemListManager.dispose();
					itemListManager = new JFrame(list.getName());
					itemListManager.add(itemInListManagerField(list));
					itemListManager.pack();
					itemListManager.setVisible(true);					
				} else {
					System.out.println("ERROR: Line not selected");
				}				
			}			
		});		
		// create delete button
		JButton deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if (row >= 0) {
					JFrame confirmPrompt = new JFrame("Delete "+ list.getItems().get(row).getName() + "?");
					JPanel confirm = new JPanel();
					JButton yesButton = new JButton("Yes");
					yesButton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							list.removeItem(list.getItems().get(row));
							confirmPrompt.dispose();
							// refresh table
							itemListManager.dispose();
							itemListManager = new JFrame(list.getName());
							itemListManager.add(itemInListManagerField(list));
							itemListManager.pack();
							itemListManager.setVisible(true);	
						}			
					});		
					JButton noButton = new JButton("No");
					noButton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							confirmPrompt.dispose();
						}			
					});	
					confirm.add(yesButton);
					confirm.add(noButton);
					confirmPrompt.add(confirm);
					confirmPrompt.setSize(500,75);
					confirmPrompt.setVisible(true);
				} else {
					System.out.println("ERROR: Line not selected");
				}
			}			
		});			
		// add components
		optionBar.add(crossButton);
		optionBar.add(deleteButton);
		optionBar.add(new JLabel("Highlight item you wish to modify."),BorderLayout.EAST);
		mainPanel.add(optionBar, BorderLayout.PAGE_START);
		mainPanel.add(table, BorderLayout.CENTER);
		mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);
		return mainPanel;
	}
	
	/**
	 * Adds the cards for the CardLayout to the main swing container
	 * 
	 * @param pane to add the cards too
	 */
	public static void addComponents(Container pane) {
		// create card for new user
		JPanel newUserCard = new JPanel();
		newUserCard.setLayout(new BorderLayout());
		newUserCard.add(newUserMenuBar(), BorderLayout.NORTH);
		
		// create card for creating an account
		JPanel createAccountCard = createAccountCard(); 
		
		// create card for logging in 
		JPanel loginCard = loginCard(); 
		
		// create card for logged in
		JPanel userCard = loggedInCard(); 
		
		// create cards and add them
		cards = new JPanel(new CardLayout());
		cards.add(newUserCard, NEWUSER);
		cards.add(createAccountCard, CREATEACCOUNT);
		cards.add(userCard, LOGGEDIN);
		cards.add(loginCard, LOGIN);
		
		// add cards to the pane
		pane.add(cards, BorderLayout.CENTER);
	}

	/**
	 * Gets the users of the application, creates the frame with components
	 * and default behaviors, and displays the frame/GUI. Contains the handler
	 * for the user action of closing the GUI.
	 */
	private static void createAndShowGUI() {
		userStorage = new UserStorage();
		// set up window
		frame = new JFrame("YALA - Yet Another List App");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// set up pane; add components and menu bar
		addComponents(frame.getContentPane());
		cl = (CardLayout)(cards.getLayout());

		frame.addWindowListener(new WindowListener() {
			@Override
			public void windowClosing(WindowEvent e) {
				if(loggedIn) {
					user.getListStorage().restoreLists();
				}
			}
			// no need for these, but they must be implemented
			@Override
			public void windowOpened(WindowEvent e) {}
			@Override
			public void windowClosed(WindowEvent e) {}
			@Override
			public void windowIconified(WindowEvent e) {}
			@Override
			public void windowDeiconified(WindowEvent e) {}
			@Override
			public void windowActivated(WindowEvent e) {}
			@Override
			public void windowDeactivated(WindowEvent e) {}
		});
		// finish set up and display
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Loads the stores and their inventories and schedules a Swing Runnable 
	 * that displays the GUI
	 * 
	 * @param args the user gives from the command line
	 */
	public static void main(String[] args) {
		// set the application to the current system's look and feel
		storeStorage = new StoreStorage("storage/stores.txt");		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | 
				 IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		// schedule job; create and show the GUI
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}