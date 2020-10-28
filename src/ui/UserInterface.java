package ui;


import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.util.Vector;

import constructs.*;
import storage.*;

public class UserInterface{
	
	static boolean loggedIn = false;
	static User user;
	Vector<Store> stores;
	static UserStorage userStorage;
	
	static JFrame frame;
	static JFrame popUpAddFrame;
	
	static JPanel cards;
	final static String LOGGEDIN = "Logged in User Card"; // card that shows the loggedInMenuBar, (TODO add the list/search display)
	final static String NEWUSER = "New User Card"; // card that shows newUserMenuBar to select create account or log in
	final static String CREATEACCOUNT = "Create Account Card"; // card for creating account TODO validate input
	final static String LOGIN = "Log in User Card"; // card for logging in TODO validate user-name and password and find user
	
	public static JPanel loginCard() {
		// create text fields
		JTextField idField = new JTextField(20);
		JPasswordField passwordField = new JPasswordField(20);
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
				// validate and see if the login is correct TODO
				if(userStorage.getUser(email, password) != null) {
					user = userStorage.getUser(email, password);
					loggedIn = true;
					// refresh frame to show the user-name of the logged in user
					frame.dispose();
					createAndShowGUI();
					// switch to the logged in card 
					CardLayout cl = (CardLayout)(cards.getLayout());
					cl.show(cards, LOGGEDIN);
				}
				else {
					System.out.println("ERROR: invalid login"); // TODO ACCOUNT
					// switch back to the new user card 
					CardLayout cl = (CardLayout)(cards.getLayout());
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
				CardLayout cl = (CardLayout)(cards.getLayout());
				cl.show(cards, NEWUSER);
			}
		});
		// add buttons to the panel
		loginPanel.add(submitButton);
		loginPanel.add(cancelButton);
		return loginPanel;
	}
	
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
		JMenuItem usernameMenuItem = new JMenuItem("Change Username");
		JMenuItem passwordMenuItem = new JMenuItem("Change Password");
		JMenuItem logoutMenuItem = new JMenuItem("Logout");
		// create account menu listener
		logoutMenuItem.addActionListener(new ActionListener() {
			// logout selected
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(e.getActionCommand());
				// logout
				loggedIn = false;
				// switch to a not logged in card
				CardLayout cl = (CardLayout)(cards.getLayout());
				cl.show(cards, NEWUSER);	
			}
		});
		// add account menu items to the account menu
		accountMenu.add(usernameMenuItem);
		accountMenu.addSeparator();
		accountMenu.add(passwordMenuItem);
		accountMenu.addSeparator();
		accountMenu.add(logoutMenuItem);
		// create help menu items
		JMenu createListMenuItem = new JMenu("Creating a list");
		createListMenuItem.add(new JMenuItem("Submenu Item 1"));
		createListMenuItem.add(new JMenuItem("Item 2"));
		JMenu searchMenuItem = new JMenu("Searching for an item");
		searchMenuItem.add(new JMenuItem("Submenu Item 1"));
		searchMenuItem.add(new JMenuItem("Submenu Item 2"));
		JMenu editListMenuItem = new JMenu("Editing a list");
		editListMenuItem.add(new JMenuItem("Submenu Item 1"));
		editListMenuItem.add(new JMenuItem("Submenu Item 2"));
		JMenu accountHelpMenuItem = new JMenu("Account help");
		accountHelpMenuItem.add(new JMenuItem("Submenu Item 1"));
		accountHelpMenuItem.add(new JMenuItem("Submenu Item 2"));
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
			// selected login TODO
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(e.getActionCommand());
				CardLayout cl = (CardLayout)(cards.getLayout());
				cl.show(cards, LOGIN);	
			}
		});
		createAccountMenuItem.addActionListener(new ActionListener() {
			// selected create account 
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(e.getActionCommand());
				CardLayout cl = (CardLayout)(cards.getLayout());
				cl.show(cards, CREATEACCOUNT);
			}
		});
		// add the menu items to the menu
		accountMenu.add(loginMenuItem);
		accountMenu.addSeparator();
		accountMenu.add(createAccountMenuItem);
		return menuBar;
	}
	
	public static JPanel createAccountCard() {
		// create the text fields
		JTextField usernameField = new  JTextField(20);
		JTextField emailField = new JTextField(20);
		JPasswordField passwordField = new JPasswordField(20);
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
				
				// validate input TODO
				// password must be unique TODO
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
					CardLayout cl = (CardLayout)(cards.getLayout());
					cl.show(cards, LOGGEDIN);
					
					// print for testing 
					System.out.println("user: " + user.getID());
					System.out.println("email: " + user.getEmail());
				}
				else {
					System.out.println("ERROR: user already exists");
					// switch back to the new user card 
					CardLayout cl = (CardLayout)(cards.getLayout());
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
				CardLayout cl = (CardLayout)(cards.getLayout());
				cl.show(cards, NEWUSER);
			}
		});
		// add buttons to the panel
		textPane.add(submitButton);
		textPane.add(cancelButton);
		// return the result
		return textPane;
	}
	
	// TODO tutorial said to add this, might not be doing anything
	public void itemStateChanged(ItemEvent evt) {
	    CardLayout cl = (CardLayout)(cards.getLayout());
	    cl.show(cards, (String)evt.getItem());
	}
	
	public static JPanel loggedInCard() {
		JPanel panel = new JPanel();		
		
		if(loggedIn) {			
			Vector<List> lists = user.getListStorage().getLists();
			
			// print lists and items for debugging
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
				List list = lists.elementAt(i);
				JButton listButton = new JButton(list.getName());
				listButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// list clicked
						String command = ((JButton) e.getSource()).getActionCommand();
						System.out.println(command + " list clicked");
					}
				});
				System.out.println("ADDING BUTTON");
				panel.add(listButton);
			}
			
		}
		else {
			
		}

		JPanel listPanel = new JPanel();
		JButton addListButton = new JButton("Add a List");
		addListButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// add list clicked
				System.out.println("list clicked");
				popUpAddFrame = new JFrame("Add List");
				popUpAddFrame.add(addListTextField());
				popUpAddFrame.pack();
				popUpAddFrame.setVisible(true);
			}
		});
		listPanel.add(addListButton);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(loggedInMenuBar(), BorderLayout.PAGE_START);
		mainPanel.add(panel, BorderLayout.LINE_END);
		mainPanel.add(listPanel, BorderLayout.CENTER);
		return mainPanel;
	}
	
	public static JPanel addListTextField() {
		JPanel mainPanel = new JPanel();
		
		JLabel nameLabel = new JLabel("List name: ");
		JFormattedTextField nameField = new JFormattedTextField();
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
				System.out.println("Submit add list pressed " + nameField.getText());
				// add list
				ListStorage storage = user.getListStorage();
				storage.addList(nameField.getText());
				// close add list frame
				popUpAddFrame.dispose();
				// refresh logged-in frame and components
				frame.dispose();
				createAndShowGUI();
				// switch to the logged in card 
				CardLayout cl = (CardLayout)(cards.getLayout());
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
	}
	
	public static void addComponents(Container pane) {
		// create card for new user
		JPanel newUserCard = new JPanel();
		newUserCard.setLayout(new BorderLayout());
		newUserCard.add(newUserMenuBar(), BorderLayout.NORTH);
		
		// create card for creating an account
		JPanel createAccountCard = createAccountCard();  // TODO fix layout, make standardized
		
		// create card for logging in 
		JPanel loginCard = loginCard(); // TODO fix layout, make standardized
		
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
	
	private static void createAndShowGUI() {
		userStorage = new UserStorage();
		// set up window
		frame = new JFrame("YALA - Yet Another List App");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// set up pane; add components and menu bar
		addComponents(frame.getContentPane());
		// finish set up and display
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		// set the application to the current system's look and feel
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