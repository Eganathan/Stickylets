import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.font.TextAttribute;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class Leafs implements ActionListener, KeyListener {

	// Variables
	Color dColor = Color.getHSBColor(255, 239, 175); // 80, 50, 20
	Color sColor = Color.getHSBColor(80, 40, 30);
	Color tColor = Color.getHSBColor(100, 0, 100);

	// Buttons for edit panel
	JButton boldBtn; // Bold button
	JButton italicBtn; // Italic button
	JButton upperCaseBtn; // Italic button
	JButton underLineBtn; // underline button
	JButton strikeThroughBtn; // strike through button
	JButton deleteBtn;
	JButton saveBtn;
	
	private HashMap<Integer,Integer> boldHMap = new HashMap<>();
	private HashMap<String,String> italicHmap = new HashMap<>();
	
	// Font Variables
	int dFontDize = 12;
	int dStyle = 0; // 0 -> plain , 1 -> BOLD , 3 -> italic
	String fFamily = "Sans";
	Font dFont = new Font(fFamily, dStyle, dFontDize);
	Font btnFonts = new Font(fFamily, 0, dFontDize); // style of buttons
	Font taFont = new Font(fFamily, 0, 16);
	// Bold font for Button
	Font bFont = new Font(fFamily, Font.BOLD, dFontDize);
	Font iFont = new Font(fFamily, Font.ITALIC, dFontDize);
	Font hBFont = new Font(fFamily, Font.CENTER_BASELINE, dFontDize);

	// Frame
	JFrame leafFrame;

	// Test Area
	JTextPane tArea;

	// Panels
	JPanel ePanel, eBtnPanel ,updatePanel;
	
	JLabel charCountLabel, wordCountLabel;

	String firstWord = "";
	

	// ** WORKING VARIABLES**//
	boolean boldBtnActive = false; // 0 -> un-clicked or not clicked | 1-> Clicked and active
	boolean italicBtnActive = false; // 0 -> un-clicked or not clicked | 1-> Clicked and active

	// Testing Style doc
	StyledDocument styledDocument;

	// Title
	private TextChangeTemp textChangeListner;
	private int currID;
	private boolean isNew = false;

	Leafs(boolean isNew,int ID, String titleValue, String textValue, TextChangeTemp tChanged) {
		currID = ID;
		isNew = isNew;
		textChangeListner = tChanged;

		leafFrame = new JFrame(titleValue);
		leafFrame.setSize(250, 250);
		leafFrame.setLayout(new BorderLayout());
		leafFrame.setBackground(dColor);
		leafFrame.setLocation(450, 50);

		tArea = new JTextPane();
		tArea.setFont(taFont);
		tArea.setText(textValue);
		tArea.setBackground(dColor);
		tArea.setBorder(new EmptyBorder(10, 10, 10, 10));
		tArea.addKeyListener(this);
		tArea.setSize(leafFrame.getWidth(), leafFrame.getHeight());

		JScrollPane areaScrollPane = new JScrollPane(tArea);
		areaScrollPane.setVerticalScrollBarPolicy(
		                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		//areaScrollPane.setPreferredSize(new Dimension(250, 250));
		
		ePanel = new JPanel();
		ePanel.setLayout(new BorderLayout());
		
		eBtnPanel = new JPanel();
		eBtnPanel.setBackground(sColor);
		eBtnPanel.setSize(leafFrame.getWidth(), 80);
		eBtnPanel.setLayout(new GridLayout(1, 5));
		
		//Strike through FONT
		Font font = new Font("helvetica", Font.PLAIN, 12);
		Map  attributes = font.getAttributes();
		attributes.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
		Font newFont = new Font(attributes);
		
		//Underline FONT btn
		Font fontU = new Font("helvetica", Font.PLAIN, 12);
		Map  attributesU = fontU.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		Font uFont = new Font(attributesU);

		italicBtn = new JButton("i");
		boldBtn = new JButton("B");
		strikeThroughBtn = new JButton("S");
		//upperCaseBtn = new JButton("U");
		underLineBtn = new JButton("U");
		
		//strikeThroughBtn.setFont(newFont);

		// sets Style and
		// adds Action Listener to all the Button instances
		defaultStyle(italicBtn);
		italicBtn.setFont(iFont);
		
		defaultStyle(boldBtn);
		
		defaultStyle(underLineBtn);
		underLineBtn.setFont(uFont);
		
		
		defaultStyle(strikeThroughBtn);
		strikeThroughBtn.setFont(newFont);
		
		//END of Edit Panel 
		
		//update panel
		updatePanel = new JPanel();
		updatePanel.setLayout(new GridLayout(1,3));
		updatePanel.setBackground(Notes.blu);
		//updatePanel.setBounds(5,5,5,5);
		
		//wordCountLabel
		charCountLabel = new JLabel("Char: " +tArea.getText().toString().length());
		charCountLabel.setFont(new Font("Sans", Font.ITALIC, 12));
		charCountLabel.setForeground(Color.white);
		
		ImageIcon Delicon = new ImageIcon(getClass().getResource("\\img\\delIcon.png"));
		deleteBtn = new JButton(Delicon);
		deleteBtn.setBackground(Notes.blu);
		deleteBtn.setFocusable(false);
		deleteBtn.addActionListener(this);
		
		ImageIcon saveicon = new ImageIcon(getClass().getResource("\\img\\saveimg.png"));
		saveBtn = new JButton(saveicon);
		saveBtn.setBackground(Notes.blu);		
		saveBtn.setFocusable(false);
		saveBtn.addActionListener(this);
		
		wordCountLabel = new JLabel("Word Count : **");
		wordCountLabel.setFont(new Font("Sans", Font.ITALIC, 15));
		
		updatePanel.add(charCountLabel);
		//updatePanel.add(wordCountLabel);
		updatePanel.add(saveBtn);
		updatePanel.add(deleteBtn);
		
		ePanel.add(eBtnPanel,BorderLayout.NORTH);
		ePanel.add(updatePanel,BorderLayout.SOUTH);
		

		leafFrame.add(tArea, BorderLayout.CENTER);
		leafFrame.add(ePanel, BorderLayout.SOUTH);
		leafFrame.setResizable(true);
		leafFrame.setDefaultCloseOperation(closeOperation());
		
	}

	// paints the Buttons to match the same
	void defaultStyle(JButton obj) {
		obj.setFont(btnFonts);
		obj.setBackground(sColor);
		obj.setBorderPainted(false);
		obj.setFocusable(false);
		obj.addActionListener(this);
		obj.setBorder(new EmptyBorder(5, 5, 5, 5));
		eBtnPanel.add(obj);
		italicBtn.setFont(iFont);
		eBtnPanel.repaint();
	}

	private void capitalizeText() {

		if (tArea.getText().length() == 1) {
			char capitalized = tArea.getText().charAt(0);
			String cap = ("" + capitalized).toUpperCase() + tArea.getText().toString().substring(1);

			tArea.setText(cap);
		}

	}
	
	
	 int getWordCount() {
		int wCount = 0;
		//Word Count
		return wCount;
	}
	 
	 //close operation 
	 int closeOperation(){
		 leafFrame.setVisible(false);
		 leafFrame.dispose();
		 
		 return 1;
	 }

	/*
	 * public void setParagraphAttributes(AttributeSet attr, boolean replace) { int
	 * p0 = getSelectionStart(); int p1 = getSelectionEnd(); StyledDocument doc =
	 * getStyledDocument(); doc.setParagraphAttributes(p0, p1 - p0, attr, replace);}
	 */

	void editText(char action, char bol) {

		SimpleAttributeSet attr = new SimpleAttributeSet();
		
		int startAtt = 0;
		int endAtt = 0;
		// START OF BOLD
		if (action == 'b') {
			// BOLD ACTION
			if (bol == 't') {
				// BOLD
				boldBtn.setBackground(tColor);
				boldBtn.setFont(bFont);
				StyleConstants.setBold(attr, true);
				tArea.setCharacterAttributes(attr, true);
				boldBtn.repaint();
				
				//int caretOffset = tArea.getCaretPosition();
				//int lineNumber = tArea.getLineOfOffset(caretOffset);
				//int startOffset = tArea.getLineStartOffset(lineNumber);
				//int endOffset = tArea.getLineEndOffset(lineNumber);
				startAtt = tArea.getCaretPosition();

				

				if (italicBtnActive) {
					StyleConstants.setItalic(attr, true);
				}
			} else if (bol == 'f') {
				boldBtn.setBackground(sColor);
				boldBtn.setFont(btnFonts);
				StyleConstants.setBold(attr, false);
				endAtt = tArea.getCaretPosition();
				boolean x = startAtt >= 0 ? addBoldIndexToMap(startAtt,endAtt):false;
			}
			tArea.setCharacterAttributes(attr, false);
			attr.removeAttributes(attr);
		} // END OF BOLD

		// START OF Italic
		if (action == 'i') {

			// Italic ACTION
			if (bol == 't') {

				// ITALIC
				italicBtn.setBackground(tColor);
				StyleConstants.setItalic(attr, true); // setBold(attr, true);
				tArea.setCharacterAttributes(attr, true);
				italicBtn.repaint();

				if (boldBtnActive) {
					StyleConstants.setBold(attr, true);
				}
			} else if (bol == 'f') {

				italicBtn.setBackground(sColor);
				StyleConstants.setItalic(attr, false);
				tArea.setCharacterAttributes(attr, false);
				attr.removeAttributes(attr);
			}
		} // END OF Italic

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == boldBtn) {
			
			//System.out.println(tArea.getCaretPosition());
			//tArea.setText("*");
			
			
			
			// BOLD BTN ACTION
			if (boldBtnActive == false) {
				editText('b', 't');
				boldBtnActive = true;

			} else {
				editText('b', 'f');
				boldBtnActive = false;
			} // END OF BOLD BTN ACTION

		} else if (e.getSource() == italicBtn) {

			// ITALIC BTN ACTION
			if (italicBtnActive == false) {
				editText('i', 't');
				italicBtnActive = true;
			} else {
				editText('i', 'f');
				italicBtnActive = false;
			} // END OF BOLD BTN ACTION

		} else if (e.getSource() == strikeThroughBtn) {
		

		} else if (e.getSource() == deleteBtn ) {
			//deleteBtn
			Notes.dataBase.deleteRowWithID(currID);
			closeOperation();
		}else if (e.getSource() == saveBtn ) {
			
			//saveBtn
			Notes.dataBase.updateTextAndTitle(currID, tArea.getText().toString(), tArea.getText().toString());
			new DB().loadDataFromDB();
			Notes.updateListModal();
		}

		// END OF ACTION PERFORMED METH

		// END OF LEAF CLASS
	}
	
	String genTitle() {
		
		return tArea.getText().toString().substring(0,tArea.getText().length()-1 <= 22 && tArea.getText().length()-1 > 1 ? tArea.getText().length()-1:21);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

		// textChangeListner.OnTextChanged(tArea.getText(),currID);

	}

	@Override
	public void keyPressed(KeyEvent e) {
		//TESTING
		//System.out.println(styledDocument.getProperty(e).toString());

	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		capitalizeText(); // Capitalize the First Letter using the method
		getWordCount(); // word count method
		charCountLabel.setText("Char :" + tArea.getText().length()); 
		
		int len = tArea.getText().toString().length();
		
		if(len >=5 ){
			
			char currChar = tArea.getText().charAt(len-1) ;
		if(currChar  == ' ' || currChar == '.')
		{
			
		//Notes.dataBase.updateTextAndTitle(currID, genTitle(), tArea.getText().toString());
		//leafFrame.setTitle(genTitle());
		//new DB().loadDataFromDB();
		//Notes.updateListModal();
	}
		if(len >= 5 && len <= 6) {
			Notes.dataBase.updateTextAndTitle(currID, genTitle(), tArea.getText().toString());
			new DB().loadDataFromDB();
			Notes.updateListModal();
		}
		
		}
		
		//System.out.println(tArea.);
	}
	
	//adding the bold index
	boolean addBoldIndexToMap(int start, int end) {
		boolean isValid = start+end >= 1 ? true:false; 
		boldHMap.put(start, end);
		return isValid;
	}
	
	//removing the bold index
	boolean removeBoldIndexToMap(int start) {
		boolean isValid = start >= 1 ? true:false; 
		boldHMap.remove(start);
		return isValid;
	}
	
	//getting the start index
	Set<Integer> getBoldStartIndexFromMap() {
		boolean isValid = boldHMap.size() >= 1 ? true:false; 
		return boldHMap.keySet();
	}
	
	//Getting the ending
		Collection<Integer> getBoldEndIndexFromMap() {
			boolean isValid = boldHMap.size() >= 1 ? true:false; 
			return boldHMap.values();
		}

}
