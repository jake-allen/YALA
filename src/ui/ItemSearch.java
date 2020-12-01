package ui;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

import constructs.Product;
import constructs.User;
import storage.ListStorage;
import storage.Store;

public class ItemSearch extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = -4192343028680787613L;
	Vector<Store> stores;
	Vector<JButton> storeButtons;
	Vector<JButton> addButtons;
	JTable table;
	Store mainStore;
	TableRowSorter<DefaultTableModel> sorter;
	JScrollPane sp;
	User user;
	JTextField brandFilter, typeFilter;
	
	/**
	 * Creates the ItemSearch GUI, sets the user and vector of stores, and 
	 * displays it.
	 * 
	 * @param s the vector of stores to be displayed
	 * @param u the user that is currently searching the items
	 */
	public ItemSearch(Vector<Store> s, User u) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Item Search");
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		storeButtons = new Vector<JButton>();
		stores = s;
		stores.stream()
			.forEach(s2 -> makeStoreButtons(s2));
		pack();
		setVisible(true);
		user = u;
	}
	
	/**
	 * Adds the button to the vector of buttons that are used to display each 
	 * store name and allow the user to select a store
	 * 
	 * @param s the store to add a button of
	 */
	void makeStoreButtons(Store s) {
		JButton temp = new JButton(s.getName());
		temp.addActionListener(this);
		storeButtons.add(temp);
		this.add(temp);
	}
	
	/**
	 * Creates and displays the main search table containing the items of
	 * a certain store.
	 */
	private void makeTable() {
		String[] colNames = {"Brand", "Type", "Extra"};
		String[][] data = new String[mainStore.getProducts().size()][3];
		for(int i = 0; i < data.length; i++) {
			data[i] = mainStore.getProducts().get(i).getVector();
		}
		Action add = new AbstractAction() {
			private static final long serialVersionUID = 885677526106133799L;

			public void actionPerformed(ActionEvent e) {
				addItem(e.getModifiers());
			}
		};
		DefaultTableModel model = new DefaultTableModel(data, colNames);
		sorter = new TableRowSorter<DefaultTableModel>(model);
		addButtons = new Vector<JButton>();
		for(int i = 0; i < mainStore.getProducts().size(); i++) { 
			addButtons.add(new JButton("Add Item"));		
		}
		model.addColumn("Add Item", addButtons);
		
		table = new JTable(model);		
		table.setRowSorter(sorter);
		ButtonColumn bc = new ButtonColumn(table, add, 3);
		table.setCellSelectionEnabled(false);
		sp = new JScrollPane(table);
		add(sp);
		pack();
		filterMenu();
		setVisible(true);
	}
	
	/**
	 * Adds an item to the display of the stores items.
	 * 
	 * @param row at which to add the item to
	 */
	public void addItem(int row) {
		final String name = table.getModel().getValueAt(row, 0) + " "
				+ table.getModel().getValueAt(row, 1) + " "
				+ table.getModel().getValueAt(row, 2);
		JFrame findList = new JFrame();
		findList.setLayout(new BoxLayout(findList.getContentPane(), BoxLayout.Y_AXIS));
		findList.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		ListStorage userLists = user.getListStorage();
		DefaultListModel<String> lists = new DefaultListModel<String>();
		for(int i = 0; i < userLists.getLists().size(); i++) {
			lists.addElement(userLists.getLists().get(i).getName());
		}
		JList<String> allLists = new JList<>(lists);
		
		SpinnerModel quants = new SpinnerNumberModel(1, 1, 20, 1);
		JSpinner spinner = new JSpinner(quants);
		JButton adder = new JButton("Add Item");
		adder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(allLists.getSelectedIndex() != -1) {
					userLists.addItemToList(allLists.getSelectedIndex(), name,
							spinner.getValue().toString(), mainStore.getName());
					findList.dispose();
				}
			}
		});
		JLabel listLab = new JLabel("Select List");
		JLabel quantLab = new JLabel("How many?");
		
		findList.add(listLab);
		findList.add(allLists);
		findList.add(quantLab);
		findList.add(spinner);
		findList.add(adder);
		findList.pack();
		findList.setVisible(true);
	}
	
	/**
	 * Creates the menu that contains the filter for easily searching
	 * through the items of a store.
	 */
	public void filterMenu() {
		Vector<Product> products = mainStore.getProducts();
		JMenuBar menuBar = new JMenuBar();
		JMenu filterMenu = new JMenu("Filter");
		Vector<String> typeStr = new Vector<String>();
		Vector<JMenu> types = new Vector<JMenu>();
		for(int i = 0; i < products.size(); i++) {
			String newType = products.get(i).getType();
			if(!typeStr.contains(newType)) {
				typeStr.add(newType);
				JMenu temp = new JMenu(newType);
				JMenuItem getAll = new JMenuItem("All");
				getAll.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						sorter.setRowFilter(RowFilter.regexFilter(newType, 1));
					}
				});
				temp.add(getAll);
				types.add(temp);
				filterMenu.add(temp);
			}
			for(int j = 0; j < types.size(); j++) {
				if(types.get(j).getText().equals(newType)) {
					String newExtra = products.get(i).getExtra();
					JMenuItem getExtra = new JMenuItem(newExtra);
					getExtra.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							sorter.setRowFilter(RowFilter.regexFilter(newType, 1));
							sorter.setRowFilter(RowFilter.regexFilter(newExtra, 2));
						}
					});
					types.get(j).add(getExtra);
					break;
				}
			}
		}
		menuBar.add(filterMenu);
		setJMenuBar(menuBar);
	}
	
	/**
	 * Contains the handling for the user action of selecting a store to
	 * search the items from. Displays the table of items.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(storeButtons.contains(e.getSource())) {
			int i = 0;
			while(!stores.get(i).getName().equals(((JButton) e.getSource()).getText()))
					i++;
			mainStore = stores.get(i);
			for(int j = 0; j < storeButtons.size(); j++)
				remove(storeButtons.get(j));
			makeTable();
		}
	}
}

class ButtonColumn extends AbstractCellEditor 
implements TableCellRenderer, TableCellEditor, ActionListener, MouseListener{

	private static final long serialVersionUID = -3562325159635287876L;
	private JTable table;
	private Action action;
	private Border originalBorder;
	private Border focusBorder;
	
	private JButton renderButton;
	private JButton editButton;
	private Object editorValue;
	private boolean isButtonColumnEditor;
	
	/**
	 *  Create the ButtonColumn to be used as a renderer and editor. The
	 *  renderer and editor will automatically be installed on the TableColumn
	 *  of the specified column.
	 *
	 *  @param table the table containing the button renderer/editor
	 *  @param action the Action to be invoked when the button is invoked
	 *  @param column the column to which the button renderer/editor is added
	 */
	public ButtonColumn(JTable table, Action action, int column){
		this.table = table;
		this.action = action;
	
		renderButton = new JButton("Add Item");
		editButton = new JButton("Add Item");
		editButton.setFocusPainted( false );
		editButton.addActionListener( this );
		originalBorder = editButton.getBorder();
		setFocusBorder( new LineBorder(Color.BLUE) );
	
		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(column).setCellRenderer( this );
		columnModel.getColumn(column).setCellEditor( this );
		table.addMouseListener( this );
	}
	
	
	/**
	 *  Get foreground color of the button when the cell has focus
	 *
	 *  @return the foreground color
	 */
	public Border getFocusBorder(){
		return focusBorder;
	}
	
	/**
	 *  The foreground color of the button when the cell has focus
	 *
	 *  @param focusBorder the foreground color
	 */
	public void setFocusBorder(Border focusBorder){
		this.focusBorder = focusBorder;
		editButton.setBorder( focusBorder );
	}
	
	/**
	 * TODO
	 */
	@Override
	public Component getTableCellEditorComponent(
		JTable table, Object value, boolean isSelected, int row, int column){
		if (value == null){
			editButton.setText( "Add Item" );
			editButton.setIcon( null );
		}
		else if (value instanceof Icon){
			editButton.setText( "Add Item" );
			editButton.setIcon( (Icon)value );
		}
		else{
			editButton.setText( "Add Item" );
			editButton.setIcon( null );
		}
	
		this.editorValue = value;
		return editButton;
	}
	
	/**
	 * TODO
	 */
	@Override
	public Object getCellEditorValue(){
		return editorValue;
	}
	
	/**
	 * Implements the TableCellRenderer interface.
	 */
	public Component getTableCellRendererComponent(JTable table, Object value, 
			boolean isSelected, boolean hasFocus, int row, int column){
		if (isSelected){
			renderButton.setForeground(table.getSelectionForeground());
	 		renderButton.setBackground(table.getSelectionBackground());
		}
		else{
			renderButton.setForeground(table.getForeground());
			renderButton.setBackground(UIManager.getColor("Button.background"));
		}
	
		if (hasFocus){
			renderButton.setBorder( focusBorder );
		} 
		else{
			renderButton.setBorder( originalBorder );
		}
	
		// renderButton.setText( (value == null) ? "" : value.toString() );
		if (value == null){
			renderButton.setText( "Add Item" );
		}
		else if (value instanceof Icon){
			renderButton.setText( "Add Item" );
		}
		else{
			renderButton.setText( "Add Item" );
		}
	
		return renderButton;
	}
	
	/**
	 * Implements the ActionListener interface. When the button is pressed, it
	 * stops editing and invokes the custom action.
	 */
	public void actionPerformed(ActionEvent e){
		int row = table.convertRowIndexToModel(table.getEditingRow());
		fireEditingStopped();
		// invoke the Action
		ActionEvent event = new ActionEvent(table, ActionEvent.ACTION_PERFORMED, "", row);
		action.actionPerformed(event);
	}
	
	//
	// Implement MouseListener interface
	//
	/*
	 *  When the mouse is pressed the editor is invoked. If you then then drag
	 *  the mouse to another cell before releasing it, the editor is still
	 *  active. So this makes sure editing is stopped when the mouse is released.
	 */
	/**
	 * Implements the MouseListener interface.  When the mouse is pressed the 
	 * editor is invoked. If you then then drag the mouse to another cell 
	 * before releasing it, the editor is still active. So this makes sure 
	 * editing is stopped when the mouse is released.
	 */
	public void mousePressed(MouseEvent e){
		if (table.isEditing() &&  table.getCellEditor() == this) {
			isButtonColumnEditor = true;
		}
	}
	
	/**
	 * TODO
	 */
	public void mouseReleased(MouseEvent e){
		if (isButtonColumnEditor &&  table.isEditing()) {
			table.getCellEditor().stopCellEditing();
		}
		isButtonColumnEditor = false;
	}
	
	/**
	 * Does nothing, must be created for the class to implement MouseListener.
	 */
	public void mouseClicked(MouseEvent e) {}
	/**
	 * Does nothing, must be created for the class to implement MouseListener.
	 */
	public void mouseEntered(MouseEvent e) {}
	/**
	 * Does nothing, must be created for the class to implement MouseListener.
	 */
	public void mouseExited(MouseEvent e) {}
}
