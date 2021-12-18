import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileOutputStream;
import java.io.IOException;

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

	// Font Variables
	int dFontDize = 22;
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

	Leafs(int ID, String titleValue, String textValue, TextChangeTemp tChanged) {
		currID = ID;

		textChangeListner = tChanged;

		leafFrame = new JFrame("");
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
		updatePanel.setLayout(new GridLayout(1,2));
		updatePanel.setBounds(5,5,5,5);
		
		//, wordCountLabel
		charCountLabel = new JLabel("Char Count : **");
		charCountLabel.setFont(new Font("Sans", Font.ITALIC, 10));
		
		wordCountLabel = new JLabel("Word Count : **");
		wordCountLabel.setFont(new Font("Sans", Font.ITALIC, 10));
		
		updatePanel.add(charCountLabel);
		updatePanel.add(wordCountLabel);
		
		ePanel.add(eBtnPanel,BorderLayout.NORTH);
		ePanel.add(updatePanel,BorderLayout.SOUTH);
		

		leafFrame.add(tArea, BorderLayout.CENTER);
		leafFrame.add(ePanel, BorderLayout.SOUTH);
		leafFrame.setResizable(true);
		leafFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

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

			/*
			 * String data = tArea.getText(); firstWord = data.substring(0,5);
			 * 
			 * String s= tArea.getText(); if(s.length()>0) { FileDialog fd = new
			 * FileDialog(leafFrame,"Save File As",FileDialog.SAVE);
			 * fd.setFile(firstWord+".txt"); fd.setDirectory("c:\\windows\\temp");
			 * fd.setVisible(true); String path=fd.getDirectory()+fd.getFile();
			 * 
			 * FileOutputStream fos; try { fos = new FileOutputStream(path);
			 * System.out.println(s); byte[] b = s.getBytes(); fos.write(b); fos.close(); }
			 * catch (IOException e1) { // TODO Auto-generated catch block
			 * e1.printStackTrace(); }}
			 */

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
		wordCountLabel.setText("Char count : " + tArea.getText().length());
		 
		textChangeListner.OnTextChanged(tArea.getText(), currID); // updating the text to the Notes for
	}

}
