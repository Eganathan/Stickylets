import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashMap;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

@SuppressWarnings("serial")
public class Notes extends JFrame implements ActionListener, ListSelectionListener, TextChangeTemp, KeyListener {

	Color sColor = Color.getHSBColor(80, 40, 30); // blue 
	Color dColor = Color.getHSBColor(252, 195, 2); // purple
	Color eColor = Color.getHSBColor(255, 239, 175); 
	static Color lpr = new Color (254, 217, 183);
	static Color blu = new Color(0, 129, 167);

	private JButton nFrameBtn;
	 String title = "Sticky Notes";
	public static  DefaultListModel<String> listModal;
	

	public static HashMap<Integer, String> textHMap = new HashMap<Integer, String>();
	public static HashMap<Integer, String> titleHMap = new HashMap<Integer, String>();
	public static HashMap<Integer, String> titleSHMap = new HashMap<Integer, String>();

	static JList<String> list;

	// Bottem Frame
	private JPanel btmFrame; // Panel to hold extra items
	private JLabel notesCounter; // the notes count label
	private JLabel logUpdater; // log uPdater
	static DB dataBase = new DB();
	private JTextField searchField;
	
	int counter = dataBase.getNoRows();

	Notes() {

		JFrame x = new JFrame(title);
		x.setSize(300, 600);
		x.setLocation(50, 60);
		x.setLayout(new BorderLayout());

		nFrameBtn = new JButton("New Note"); // Main frame title
		nFrameBtn.setBackground(lpr); // sets the background
		nFrameBtn.setForeground(blu);
		nFrameBtn.setFocusable(false);
		nFrameBtn.setFont(new Font("sans", Font.BOLD, 25)); // setting the fonts
		nFrameBtn.addActionListener(this); // adding action listener to new note button
		
		// List Model
		listModal = new DefaultListModel<>();

		// J List
		list = new JList<>(listModal);
		list.setBorder(new EmptyBorder(10, 10, 10, 10));
		list.setFont(new Font("Sans", Font.BOLD, 17));
		list.setBackground(blu);
		list.setFixedCellHeight(35);
		list.setForeground(Color.WHITE);
		list.setSelectionBackground(lpr);
		list.setSelectionForeground(blu);
		list.addListSelectionListener(this);
		list.setLayoutOrientation(JList.VERTICAL);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(list);
		
	      
		
		// btm Frame
		btmFrame = new JPanel();
		btmFrame.setLayout(new BorderLayout());
		btmFrame.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		searchField = new JTextField();
		searchField.setFont(new Font("Sans", Font.ITALIC, 12));
		searchField.addKeyListener(this);
		
		notesCounter = new JLabel("Notes Count : **");
		notesCounter.setFont(new Font("Sans", Font.ITALIC, 12));

		logUpdater = new JLabel("Good Day ?");
		logUpdater.setFont(new Font("Sans", Font.ITALIC, 12));

		btmFrame.add(notesCounter, BorderLayout.WEST);
		btmFrame.add(searchField, BorderLayout.NORTH);
		btmFrame.add(logUpdater, BorderLayout.EAST);

		// Adding the list


		x.add(scrollPane, BorderLayout.CENTER);
		x.add(btmFrame, BorderLayout.SOUTH);
		x.add(nFrameBtn, BorderLayout.NORTH);
		x.setVisible(true);
		x.setResizable(false);
		x.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		dataBase.loadDataFromDB();
	}

	/*
	 * Creates a new instance of sticky note, creates a unique ID, fileName ,
	 * default text adds the ID to all the Hash maps adds the temporary title to the
	 * hash map and List Modal
	 * 
	 * @param idName is a unique id with counter
	 * 
	 * @param newFileName is a temporary title
	 * 
	 * @param defaultText is the text for the Text area currently its empty
	 */
	private void newNote() {

		int id = counter;
		System.out.println(counter);
		String newFileName = "";
		String defaultText = "";
		String counterLabel = "";

		// Title Generation for new note
		if (counter <= 9) // checking if the counter is <= 9 then the counter will be 09 , 08, 01 ..etc
		{
			newFileName = "My Note 0" + counter;
			counterLabel = "0" + counter;
		} else // if higher than 9 then the counter will be 10,11,12...etc
		{
			newFileName = "My Note " + counter;
			counterLabel = "" + counter;
		}


		Leafs newNote = new Leafs(true,id, newFileName, defaultText, this);
		Notes.dataBase.insertNewNoteDB(newFileName,newFileName);
		newNote.leafFrame.setVisible(true);
		counter++;
		notesCounter.setText("Notes Count : " + counterLabel); //Note Counter Label 
		successMgs("Created Successfully!"); //btm panel color change
		
	}

	private void openNote(int ID, String title, String text) {
		
		// creating anonymous new note here with the parameters
		Leafs openNote = new Leafs(false,ID, title, text, this);
		openNote.leafFrame.setVisible(true);
		successMgs("Opened Successfully!");
	}
	
	private void searchAndUpdateList(String currString) {
		int searchCounter = 0;
		titleSHMap.clear();
		//listModal.addAll(titleHMap.values());
		
		//System.out.println("sd"+ " "+ currString);
		for(Integer title:titleHMap.keySet()) {
			//System.out.println(title+ " "+titleHMap.get(title).substring(0, currString.length()));
			
			if(titleHMap.get(title).substring(0, currString.length()).equalsIgnoreCase(currString)){
				titleSHMap.put(title,titleHMap.get(title));
				System.out.println(title+ " "+ titleHMap.get(title));
				searchCounter++;
			}
		}
		listModal.removeAllElements();
		listModal.addAll(titleSHMap.values());
		successMgs( searchCounter+" results matched");
	}

	static void updateListModal() {
		listModal.removeAllElements();
		listModal.addAll(titleHMap.values());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == nFrameBtn) //
		{
			// calling the method to create a new note with custom parameters
			newNote();

		} else if(e.getSource() == searchField) {
			System.out.println(e.getSource());
		}
	}

	@Override
	/*
	 * List selection event
	 * 
	 */
	public void valueChanged(ListSelectionEvent e) {

		String currKey = list.getSelectedValue();
		if (list.getValueIsAdjusting() && titleHMap.containsValue(currKey)) {
			
			for (int id : titleHMap.keySet()) {
				
				if (currKey == titleHMap.get(id)) {
					
					openNote(id, titleHMap.get(id), textHMap.get(id));
				}
			}
		} else {

		}

	}

	@Override
	public void OnTextChanged(String text, int id) {

	}


	 void successMgs(String msg) {

		logUpdater.setText(msg);
		btmFrame.setBackground(new Color(144, 238, 144)); //
		// btmFrame.setBackground(new Color(204,204,204));

	}
	
	//updating counter
	private void upDateCounter() {
		counter = dataBase.getNoRows();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//searchAndUpdateList(searchField.toString());	
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		if(searchField.getText().length() == 0) {
			listModal.addAll(titleHMap.values());
			updateListModal();
		} else if(searchField.getText().length() >= 1) {
			searchAndUpdateList(searchField.getText());
		}
	}



}
