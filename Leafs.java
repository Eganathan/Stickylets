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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
	
	// Font Variables
	int dFontDize = 12;
	int dStyle = 0; // 0 -> plain , 1 -> BOLD , 3 -> italic
	String fFamily = "Sans";
	Font dFont = new Font(fFamily, dStyle, dFontDize);
	Font btnFonts = new Font(fFamily, 0, dFontDize); // style of buttons

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
		tArea.setFont(dFont);
		tArea.setText(textValue);
		// tArea.setRequestFocusEnabled(rootPaneCheckingEnabled);
		tArea.setBackground(dColor);
		tArea.setBorder(new EmptyBorder(10, 10, 10, 10));
		tArea.addKeyListener(this);
		tArea.setSize(leafFrame.getWidth(), leafFrame.getHeight());

		ePanel = new JPanel();
		ePanel.setLayout(new BorderLayout());
		
		eBtnPanel = new JPanel();
		eBtnPanel.setBackground(sColor);
		eBtnPanel.setSize(leafFrame.getWidth(), 80);
		eBtnPanel.setLayout(new GridLayout(1, 5));

		italicBtn = new JButton("i");
		boldBtn = new JButton("B");
		upperCaseBtn = new JButton("U");
		underLineBtn = new JButton("V");
		strikeThroughBtn = new JButton("S");

		// sets Style and
		// adds Action Listener to all the Button instances
		defaultStyle(italicBtn);
		italicBtn.setFont(iFont);
		defaultStyle(boldBtn);
		defaultStyle(upperCaseBtn);
		defaultStyle(underLineBtn);
		underLineBtn.setFont(hBFont);
		defaultStyle(strikeThroughBtn);
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

		// Index of the edited text and its value
		// int startTextRef = tArea.getSelectionStart(); //Start of the index
		// int endTextRef = tArea.getSelectionEnd(); // end of the index
		// String cText = tArea.getSelectedText(); // current text from the entry
		// System.out.println(startTextRef +" "+endTextRef +" "+cText);

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
				System.out.println(styledDocument);

				if (italicBtnActive) {
					StyleConstants.setItalic(attr, true);
				}
			} else if (bol == 'f') {
				boldBtn.setBackground(sColor);
				boldBtn.setFont(btnFonts);
				StyleConstants.setBold(attr, false);
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
			Notes.dataBase.updateTextAndTitle(currID, tArea.getText().toString().toString(), tArea.getText().toString());
		}

		// END OF ACTION PERFORMED METH

		// END OF LEAF CLASS
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

		// textChangeListner.OnTextChanged(tArea.getText(),currID);

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		capitalizeText(); // Capitalize the First Letter using the method
		getWordCount(); // word count method
		charCountLabel.setText("Char :" + tArea.getText().length()); 
	
		if(tArea.getText().toString().length() > 3) {
			char currChar = tArea.getText().charAt(tArea.getText().length()-2);
	if( currChar == ' ' || tArea.getText().toString().length() < 12){
		if( tArea.getText().toString().length() > 1 && tArea.getText().toString().length() < 12) {
			leafFrame.setTitle(tArea.getText().toString());
		}
		
		new DB().loadDataFromDB();
		Notes.dataBase.updateTextAndTitle(currID, tArea.getText().toString().toString(), tArea.getText().toString());
	} }else {
		
		//do nothing 
	}
		
	}

}
