import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.EventObject;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.CellEditorListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

public class ItemSearch extends JFrame implements ActionListener {
	
	Vector<Store> stores;
	Vector<JButton> storeButtons;
	Vector<JButton> addButtons;
	JTable table;
	Store mainStore;
	TableRowSorter<DefaultTableModel> sorter;
	JScrollPane sp;
	User user;
	JTextField brandFilter, typeFilter;
	
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
		//Table for search results? Three filters
	}
	
	void makeStoreButtons(Store s) {
		JButton temp = new JButton(s.name);
		temp.addActionListener(this);
		storeButtons.add(temp);
		this.add(temp);
	}
	
	private void makeTable() {
		String[] colNames = {"Brand", "Type", "Extra"};
		String[][] data = new String[mainStore.getProducts().size()][3];
		for(int i = 0; i < data.length; i++) {
			data[i] = mainStore.getProducts().get(i).getVector();
		}
		Action add = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				addItem(e.getModifiers());
			}
		};
		DefaultTableModel model = new DefaultTableModel(data, colNames);
		sorter = new TableRowSorter<DefaultTableModel>(model);
		addButtons = new Vector<JButton>();
		for(int i = 0; i < mainStore.getProducts().size(); i++) 
			addButtons.add(new JButton("Add Item"));		
		model.addColumn("Add Item", addButtons);
		
		table = new JTable(model);		
		table.setRowSorter(sorter);
		ButtonColumn bc = new ButtonColumn(table, add, 3);
		table.setCellSelectionEnabled(false);
		sp = new JScrollPane(table);
		add(sp);
		pack();
		addFilters();
		setVisible(true);
	}
	
	public void addItem(int row) {
		final String name = table.getModel().getValueAt(row, 0) + " "
				+ table.getModel().getValueAt(row, 1) + " "
				+ table.getModel().getValueAt(row, 2);
		JFrame findList = new JFrame();
		findList.setLayout(new BoxLayout(findList.getContentPane(), BoxLayout.Y_AXIS));
		findList.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		ListStorage userLists = user.lists;
		for(int i = 0; i < userLists.getLists().size(); i++) {
			List list = userLists.getLists().elementAt(i);
			JButton listButton = new JButton(list.getName());
			listButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String command = ((JButton) e.getSource()).getActionCommand();					
					int j = 0;
					while(!userLists.getLists().get(j).getName().equals(command))
						j++;
					userLists.addItemToList(j, name, "1", mainStore.getName());
					findList.dispose();
				}
			});
			findList.add(listButton);
		}
		findList.pack();
		findList.setVisible(true);
	}
	
	private void addFilters() {
		brandFilter = filterBoxes(0);
		typeFilter = filterBoxes(1);
		add(brandFilter);
		add(typeFilter);
	}
	
	private JTextField filterBoxes(final int c) {
		final JTextField template = new JTextField();
		template.getDocument().addDocumentListener(
				new DocumentListener() {
					public void changedUpdate(DocumentEvent e) {
						newFilter(template, c);
					}
					public void insertUpdate(DocumentEvent e) {
						newFilter(template, c);
					}
					public void removeUpdate(DocumentEvent e) {
						newFilter(template, c);
					}
				}
				);
		return template;
	}
	
	private void newFilter(JTextField template, int c) {
		RowFilter<? super DefaultTableModel, ? super Integer> rf = null;
		try {
			rf = RowFilter.regexFilter(template.getText(), c);
		}
		catch(java.util.regex.PatternSyntaxException e) {
			return;
		}
		sorter.setRowFilter(rf);
	}

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
			//System.out.println(mainStore.getName());
		}
	}
	

}

class ButtonColumn extends AbstractCellEditor
implements TableCellRenderer, TableCellEditor, ActionListener, MouseListener
{
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
	public ButtonColumn(JTable table, Action action, int column)
	{
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
	public Border getFocusBorder()
	{
		return focusBorder;
	}
	
	/**
	 *  The foreground color of the button when the cell has focus
	 *
	 *  @param focusBorder the foreground color
	 */
	public void setFocusBorder(Border focusBorder)
	{
		this.focusBorder = focusBorder;
		editButton.setBorder( focusBorder );
	}
		
	@Override
	public Component getTableCellEditorComponent(
		JTable table, Object value, boolean isSelected, int row, int column)
	{
		if (value == null)
		{
			editButton.setText( "Add Item" );
			editButton.setIcon( null );
		}
		else if (value instanceof Icon)
		{
			editButton.setText( "Add Item" );
			editButton.setIcon( (Icon)value );
		}
		else
		{
			editButton.setText( value.toString() );
			editButton.setIcon( null );
		}
	
		this.editorValue = value;
		return editButton;
	}
	
	@Override
	public Object getCellEditorValue()
	{
		return editorValue;
	}
	
	//
	//Implement TableCellRenderer interface
	//
	public Component getTableCellRendererComponent(
		JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
	{
		if (isSelected)
		{
			renderButton.setForeground(table.getSelectionForeground());
	 		renderButton.setBackground(table.getSelectionBackground());
		}
		else
		{
			renderButton.setForeground(table.getForeground());
			renderButton.setBackground(UIManager.getColor("Button.background"));
		}
	
		if (hasFocus)
		{
			renderButton.setBorder( focusBorder );
		}
		else
		{
			renderButton.setBorder( originalBorder );
		}
	
	//	renderButton.setText( (value == null) ? "" : value.toString() );
		if (value == null)
		{
			renderButton.setText( "Add Item" );
		}
		else if (value instanceof Icon)
		{
			renderButton.setText( "Add Item" );
		}
		else
		{
			renderButton.setText( value.toString() );
		}
	
		return renderButton;
	}
	
	//
	//Implement ActionListener interface
	//
	/*
	 *	The button has been pressed. Stop editing and invoke the custom Action
	 */
	public void actionPerformed(ActionEvent e)
	{
		int row = table.convertRowIndexToModel( table.getEditingRow() );
		fireEditingStopped();
	
		//  Invoke the Action
	
		ActionEvent event = new ActionEvent(
			table,
			ActionEvent.ACTION_PERFORMED,
			"", row);
		action.actionPerformed(event);
	}
	
	//
	//Implement MouseListener interface
	//
	/*
	 *  When the mouse is pressed the editor is invoked. If you then then drag
	 *  the mouse to another cell before releasing it, the editor is still
	 *  active. Make sure editing is stopped when the mouse is released.
	 */
	public void mousePressed(MouseEvent e)
	{
		if (table.isEditing()
		&&  table.getCellEditor() == this)
			isButtonColumnEditor = true;
	}
	
	public void mouseReleased(MouseEvent e)
	{
		if (isButtonColumnEditor
		&&  table.isEditing())
			table.getCellEditor().stopCellEditing();
	
		isButtonColumnEditor = false;
	}
	
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
}
