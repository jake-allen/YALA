package ui;


import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import constructs.*;
import storage.*;

public class UserInterface{
	
	static boolean loggedIn = false;
	static User user;
	Vector<Store> stores;
	static JFrame frame;
	JPanel cards;
	
	
	public static void temporaryStarterFunction() {
		// create user and log in
		String id = "Jake Allen";
		String email = "jake@jakeallen.com";
		String password = "192182310";
		user = new User(id, email, password);
		loggedIn = true;
	}
	
	
	public static JPanel createAccount() {
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
		JPanel textPane = new JPanel(new FlowLayout(FlowLayout.TRAILING));
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
				
				// create new user
				user = new User(username, email, password);
				
				// tell UserInterface that the user is now logged in
				loggedIn = true;
				// remove the content pane
				JPanel contentPane = (JPanel)frame.getContentPane();
				contentPane.removeAll();
				contentPane.revalidate();
				contentPane.repaint();
			}
		});
		// cancel create account action listener
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(e.getActionCommand());
				// remove the content pane
				JPanel contentPane = (JPanel)frame.getContentPane();
				contentPane.removeAll();
				contentPane.revalidate();
				contentPane.repaint();
			}
		});
		// add buttons to the panel
		textPane.add(submitButton);
		textPane.add(cancelButton);
		// return the result
		return textPane;
	}
	
	public static JPanel login() {
		// TODO create login stuff
		return null;
	}
	
	public static void addComponents(Container pane) {
		//temporaryStarterFunction();	
		
		// create card for logged in
		JPanel userCard = new JPanel();
		userCard.setLayout(new BorderLayout());
		userCard.add(loggedInMenuBar(), BorderLayout.NORTH);
		
		// create card for new user
		JPanel newUserCard = new JPanel();
		newUserCard.setLayout(new BorderLayout());
		newUserCard.add(newUserMenuBar(), BorderLayout.NORTH);
		
	}
	
	public static JMenuBar loggedInMenuBar() {
		// create menu bar
		JMenuBar menuBar = new JMenuBar();
		// create menus
		JMenu accountMenu = new JMenu("My Account");
		JMenu helpMenu = new JMenu("Help");
		JMenu userDisplay = new JMenu("Welcome,  " + user.getID());
		// add the menus to the menu bar
		menuBar.add(accountMenu);
		menuBar.add(helpMenu);
		menuBar.add(Box.createHorizontalGlue());
		menuBar.add(userDisplay);
		// create account menu items
		JMenuItem usernameMenuItem = new JMenuItem("Change Username");
		JMenuItem passwordMenuItem = new JMenuItem("Change Password");
		JMenuItem logoutMenuItem = new JMenuItem("Logout");
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
							
			}
		});
		createAccountMenuItem.addActionListener(new ActionListener() {
			// selected create account TODO
			@Override
			public void actionPerformed(ActionEvent e) {
							
			}
		});
		// add the menu items to the menu
		accountMenu.add(loginMenuItem);
		accountMenu.addSeparator();
		accountMenu.add(createAccountMenuItem);
		return menuBar;
	}
	
	private static void createAndShowGUI() {
		// set up window
		frame = new JFrame("YALA - Yet Another List App");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// set up pane; add components and menu bar
		addComponents(frame.getContentPane());
		// set up layout
		frame.getContentPane().setLayout(new BorderLayout());
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