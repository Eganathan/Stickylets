import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashMap;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

@SuppressWarnings("serial")
public class Notes extends JFrame implements ActionListener, ListSelectionListener, TextChangeTemp {

	Color sColor = Color.getHSBColor(80, 40, 30); //
	Color dColor = Color.getHSBColor(252, 195, 2); // 255,239,175
	Color eColor = Color.getHSBColor(255, 239, 175);

	private JButton nFrameBtn;
	static String title = "Sticky Notes";
	public static DefaultListModel<String> listModal;
	static int counter = 1;

	private HashMap<Integer, String> textHMap = new HashMap<Integer, String>();
	private HashMap<Integer, String> titleHMap = new HashMap<Integer, String>();

	private JList<String> list;

	// Bottem Frame
	private JPanel btmFrame; // Panel to hold extra items
	private JLabel notesCounter; // the notes count label
	private JLabel logUpdater; // log uPdater

	Notes() {

		JFrame x = new JFrame(title);
		x.setSize(300, 600);
		x.setLocation(50, 60);
		x.setLayout(new BorderLayout());

		nFrameBtn = new JButton("New Note"); // Main frame title
		nFrameBtn.setBackground(dColor); // sets the background
		nFrameBtn.setFont(new Font("sans", Font.BOLD, 25)); // setting the fonts
		nFrameBtn.addActionListener(this); // adding action listener to new note button

		// List Model
		listModal = new DefaultListModel<>();

		// J List
		list = new JList<>(listModal);
		list.setBorder(new EmptyBorder(10, 5, 10, 0));
		list.setFont(new Font("Sans", Font.ITALIC, 15));
		list.setBackground(eColor);
		list.setSelectionBackground(Color.WHITE);
		// list.setSelectionForeground(eColor);
		list.addListSelectionListener(this);

		// btm Frame
		btmFrame = new JPanel();
		btmFrame.setLayout(new BorderLayout());
		btmFrame.setBorder(new EmptyBorder(5, 5, 5, 5));

		notesCounter = new JLabel("Notes Count : **");
		notesCounter.setFont(new Font("Sans", Font.ITALIC, 12));

		logUpdater = new JLabel("Good Day ?");
		logUpdater.setFont(new Font("Sans", Font.ITALIC, 12));

		btmFrame.add(notesCounter, BorderLayout.WEST);
		btmFrame.add(logUpdater, BorderLayout.EAST);

		// Adding the list
		x.add(list, BorderLayout.CENTER);
		x.add(btmFrame, BorderLayout.SOUTH);
		x.add(nFrameBtn, BorderLayout.NORTH);

		x.setVisible(true);
		x.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		
		//sqlTrail
		try{  
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con= DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/sonoo","root","root");  
			//here sonoo is database name, root is username and password  
			Statement stmt=con.createStatement();  
			ResultSet rs=stmt.executeQuery("select * from emp");  
			while(rs.next())  
			System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));  
			con.close();  
			}catch(Exception e){ System.out.println(e);}  
			 
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

		int idName = counter;
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

		// creating anonymous new note here with the parameters
		Leafs newNote = new Leafs(idName, newFileName, defaultText, this);
		newNote.leafFrame.setVisible(true);

		titleHMap.put(idName, newFileName); // updating the titleHMap with ID and title
		textHMap.put(idName, defaultText); // updating the textHMap with ID and default text
		listModal.addElement(newFileName);
		counter++; // incrementing after the object is created successfully
		notesCounter.setText("Notes Count : " + counterLabel);
		successMgs("Created Successfully!");
	}

	private void openNote(int ID, String title, String text) {

		// creating anonymous new note here with the parameters
		Leafs openNote = new Leafs(ID, title, text, this);
		openNote.leafFrame.setVisible(true);
		successMgs("Opened Successfully!");
	}

	private void updateListModal() {
		listModal.removeAllElements();
		listModal.addAll(titleHMap.values());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == nFrameBtn) //
		{
			// calling the method to create a new note with custom parameters
			newNote();

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

			// System.out.println(titleHMap. + "selected ID ");
		} else {

		}

	}

	@Override
	public void OnTextChanged(String text, int id) {
		// TODO Auto-generated method stub
		updateText(id, text);
		// System.out.println(text + " | on the ID ->:" + id);
	}

	/*
	 * gets Invoked every time a key is pressed updates Title to the title hash map
	 */
	private void updateTitle(int ID, String text) {
		String title = text;

		if (text.length() >= 12) // in case the length is larger that 12 [MAX TITLE]
		{
			text.substring(0, 12);
		}
		titleHMap.replace(ID, title);
		// System.out.println(titleHMap.values() +"** Titles **");
		updateListModal();
	}

	/*
	 * gets Invoked every time a key is pressed updates the text from the frame
	 */
	private void updateText(int ID, String text) // updates the value to the hash map
	{
		textHMap.replace(ID, text);
		updateTitle(ID, text);
		// System.out.println(textHMap.values()+"** Text **");
	}

	private void successMgs(String msg) {

		logUpdater.setText(msg);
		btmFrame.setBackground(new Color(144, 238, 144)); //
		// btmFrame.setBackground(new Color(204,204,204));

	}

}
