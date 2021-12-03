import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

@SuppressWarnings("serial")
public class Leafs implements ActionListener {

	// Variables
	Color dColor = Color.getHSBColor(90, 50, 20); // 80, 50, 20
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

	// Bold font for Btn
	Font bFont = new Font(fFamily, Font.BOLD, dFontDize);
	Font iFont = new Font(fFamily, Font.ITALIC, dFontDize);
	Font hBFont = new Font(fFamily, Font.CENTER_BASELINE, dFontDize);
	
	JFrame leafFrame;

	// Test Area
	JTextPane tArea;

	// Panels
	JPanel ePanel;

	String firstWord = "";

	// ** WORKING VARIABLES**
	boolean boldBtnActive = false; // 0 -> un-clicked or not clicked | 1-> Clicked and active
	boolean italicBtnActive = false; // 0 -> un-clicked or not clicked | 1-> Clicked and active
	public boolean visi = true;

	Leafs(String fname) {

		leafFrame = new JFrame(fname);
		leafFrame.setSize(250, 250);
		leafFrame.setLayout(new BorderLayout());
		leafFrame.setBackground(dColor);
		leafFrame.setLocation(450,50);

		tArea = new JTextPane();
		tArea.setFont(dFont);
		// tArea.setRequestFocusEnabled(rootPaneCheckingEnabled);
		tArea.setBackground(dColor);
		tArea.setBorder(new EmptyBorder(10, 10, 10, 10));
		tArea.setSize(leafFrame.getWidth(), leafFrame.getHeight());

		ePanel = new JPanel();
		ePanel.setBackground(sColor);
		ePanel.setSize(leafFrame.getWidth(), 80);
		ePanel.setLayout(new GridLayout(1, 5));

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

		leafFrame.add(tArea, BorderLayout.CENTER);
		leafFrame.add(ePanel, BorderLayout.SOUTH);
		leafFrame.setResizable(true);
		leafFrame.setVisible(visi);
		leafFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		
		//firstWord = tArea.getText().substring(0,10);
	}

	// paints the Buttons to match the same
	void defaultStyle(JButton obj) {
		obj.setFont(btnFonts);
		obj.setBackground(sColor);
		obj.setBorderPainted(false);
		obj.setFocusable(false);
		obj.addActionListener(this);
		obj.setBorder(new EmptyBorder(5, 5, 5, 5));
		ePanel.add(obj);
		italicBtn.setFont(iFont);
		ePanel.repaint();
	}

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

			String data = tArea.getText();
			firstWord = data.substring(0,5);
			
			String s= tArea.getText();
			if(s.length()>0)
			{
			FileDialog fd = new FileDialog(leafFrame,"Save File As",FileDialog.SAVE);
			fd.setFile(firstWord+".txt");
			fd.setDirectory("c:\\windows\\temp");
			fd.setVisible(true);
			String path=fd.getDirectory()+fd.getFile();

			FileOutputStream fos;
			try {
				fos = new FileOutputStream(path);
				System.out.println(s);
				byte[] b = s.getBytes();
				fos.write(b);
				fos.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			

		}}

		// END OF ACTION PERFORMED METH
	}

	// END OF LEAF CLASS
}
