import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.HashMap;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
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

	Notes() {

		JFrame x = new JFrame(title);
		x.setSize(300, 600);
		x.setLocation(50, 60);
		x.setLayout(new BorderLayout());
		;

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

		// Adding the list
		x.add(list, BorderLayout.CENTER);

		x.add(nFrameBtn, BorderLayout.NORTH);

		x.setVisible(true);
		x.setDefaultCloseOperation(EXIT_ON_CLOSE);
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

		// Title Generation for new note
		if (counter <= 9) // checking if the counter is <= 9 then the counter will be 09 , 08, 01 ..etc
		{
			newFileName = "My Note 0" + counter;
		} else // if higher than 9 then the counter will be 10,11,12...etc
		{
			newFileName = "My Note " + counter;
		}

		// creating anonymous new note here with the parameters
		Leafs newNote = new Leafs(idName, newFileName, defaultText, this);
		newNote.leafFrame.setVisible(true);

		titleHMap.put(idName, newFileName); // updating the titleHMap with ID and title
		textHMap.put(idName, defaultText); // updating the textHMap with ID and default text
		listModal.addElement(newFileName);
		counter++; // incrementing after the object is created successfully
	}

	private void openNote(int ID, String title, String text) {

		// creating anonymous new note here with the parameters
		Leafs openNote = new Leafs(ID, title, text, this);
		openNote.leafFrame.setVisible(true);
	}

	public void updateListModal() {
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

}
