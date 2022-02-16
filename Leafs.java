import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyledEditorKit;

public class Leafs implements ActionListener, KeyListener {

	// Tag index
	int tagIndexS, tagIndexE;

	// Attributes
	SimpleAttributeSet atrSet = new SimpleAttributeSet();
	SimpleAttributeSet DBSet = new SimpleAttributeSet();
	int lastAttrIndex = 0;

	StyledDocument Doc;

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

	StringBuilder DBTEXT = new StringBuilder();
	int DBTextIndex = 0;
	int LIndex = 0;

	private HashMap<Integer, Integer> boldHMap = new HashMap<>();
	private HashMap<Integer, Integer> italicHmap = new HashMap<>();
	private HashMap<Integer, Integer> underlineHMap = new HashMap<>();
	private HashMap<Integer, Integer> strikeThroughHMap = new HashMap<>();

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
	JPanel ePanel, eBtnPanel, updatePanel;

	JLabel charCountLabel, wordCountLabel;

	String DBText;
	// Bold and end
	int BAtt = 0;
	int BendAtt = 0;

	// ** WORKING VARIABLES**//
	boolean boldBtnActive = false; // 0 -> un-clicked or not clicked | 1-> Clicked and active
	boolean italicBtnActive = false; // 0 -> un-clicked or not clicked | 1-> Clicked and active
	boolean isStrikeThroughActive = false;
	boolean isUnderLineActive = false;
	// Testing Style doc
	StyledDocument styledDocument;

	// Title
	private TextChangeTemp textChangeListner;
	private int currID;
	private boolean isNew = false;

	Leafs(boolean isNew, int ID, String titleValue, String textValue, TextChangeTemp tChanged) {
		currID = ID;
		isNew = isNew;
		textChangeListner = tChanged;
		DBTEXT.append(textValue);
		//System.out.println(DBTEXT.toString() + " - appended on init");

		leafFrame = new JFrame(titleValue);
		leafFrame.setSize(250, 250);
		leafFrame.setLayout(new BorderLayout());
		leafFrame.setBackground(dColor);
		leafFrame.setLocation(450, 50);

		tArea = new JTextPane();
		tArea.setEditorKit(new StyledEditorKit());
		Doc = tArea.getStyledDocument();
		tArea.setFont(taFont);
		tArea.setBackground(dColor);
		tArea.setBorder(new EmptyBorder(10, 10, 10, 10));
		tArea.addKeyListener(this);
		tArea.setSize(leafFrame.getWidth(), leafFrame.getHeight());

		// JScrollPane areaScrollPane = new JScrollPane(tArea);
		// areaScrollPane.setVerticalScrollBarPolicy(
		// JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		// areaScrollPane.setPreferredSize(new Dimension(250, 250));

		ePanel = new JPanel();
		ePanel.setLayout(new BorderLayout());

		eBtnPanel = new JPanel();
		eBtnPanel.setBackground(sColor);
		eBtnPanel.setSize(leafFrame.getWidth(), 80);
		eBtnPanel.setLayout(new GridLayout(1, 5));

		// Strike through FONT
		Font Sfont = new Font("helvetica", Font.PLAIN, 12);
		Map attributes = Sfont.getAttributes();
		attributes.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
		Font newFont = new Font(attributes);

		// Underline FONT btn
		Font fontU = new Font("helvetica", Font.PLAIN, 12);
		Map attributesU = fontU.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		Font uFont = new Font(attributesU);

		italicBtn = new JButton("i");
		boldBtn = new JButton("B");
		strikeThroughBtn = new JButton("S");
		// upperCaseBtn = new JButton("U");
		underLineBtn = new JButton("U");

		// strikeThroughBtn.setFont(newFont);

		// sets Style and
		// adds Action Listener to all the Button instances
		defaultStyle(italicBtn);
		italicBtn.setFont(iFont);

		defaultStyle(boldBtn);

		defaultStyle(underLineBtn);
		underLineBtn.setFont(uFont);

		defaultStyle(strikeThroughBtn);
		strikeThroughBtn.setFont(newFont);

		// END of Edit Panel

		// update panel
		updatePanel = new JPanel();
		updatePanel.setLayout(new GridLayout(1, 3));
		updatePanel.setBackground(Notes.blu);
		// updatePanel.setBounds(5,5,5,5);

		// wordCountLabel
		charCountLabel = new JLabel("Char: " + tArea.getText().toString().length());
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
		// updatePanel.add(wordCountLabel);
		updatePanel.add(saveBtn);
		updatePanel.add(deleteBtn);

		ePanel.add(eBtnPanel, BorderLayout.NORTH);
		ePanel.add(updatePanel, BorderLayout.SOUTH);

		// removing the decorations from the db text
		removeDecorations(DBTEXT.toString());

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
		return wCount;
	}

	// close operation
	int closeOperation() {
		leafFrame.setVisible(false);
		leafFrame.dispose();
		Notes.reload();
		
		return 1;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == boldBtn) {

			// BOLD BTN ACTION
			if (!boldBtnActive) {

				// if false
				boldBtnActive = true;
				StyleConstants.setBold(atrSet, true);
				tArea.setCharacterAttributes(atrSet, true);
				atrSet.removeAttributes(atrSet);

				DBTEXT.append("**");
			} else if (boldBtnActive) {
				// if true
				// editText('b', 'f');
				boldBtnActive = false;
				DBTEXT.append("**");
				StyleConstants.setBold(atrSet, false);
				tArea.setCharacterAttributes(atrSet, false);
				System.out.println("BOLD" + DBTEXT);

			} // END OF BOLD BTN ACTION

		} else if (e.getSource() == italicBtn) {

			// ITALIC BTN ACTION
			if (!italicBtnActive) { // IF False
				italicBtnActive = true;
				italicBtn.setBackground(sColor);
				StyleConstants.setItalic(atrSet, true);
				tArea.setCharacterAttributes(atrSet, true);

				DBTEXT.append("__");

			} else if (italicBtnActive) {
				// TRUE
				italicBtnActive = false;
				DBTEXT.append("__");

				System.out.println("ITALIC" + DBTEXT);

				StyleConstants.setItalic(atrSet, false);
				tArea.setCharacterAttributes(atrSet, false);
				atrSet.removeAttributes(atrSet);
				// END OF BOLD ITALIC ACTION
			}
		} else if (e.getSource() == strikeThroughBtn) {

			// Strikethrough BTN ACTION
			if (!isStrikeThroughActive) {
				// false
				tagIndexS = tArea.getCaretPosition();
				italicBtn.setBackground(sColor);
				StyleConstants.setStrikeThrough(atrSet, true);
				tArea.setCharacterAttributes(atrSet, true);

				DBTEXT.append("$$");
				isStrikeThroughActive = true;
			} else if (isStrikeThroughActive) {
				// true
				isStrikeThroughActive = false;
				tagIndexE = tArea.getCaretPosition();
				DBTEXT.append("$$");
				// DBTextIndex++;
				System.out.println("Strike through - " + DBTEXT);
				StyleConstants.setStrikeThrough(atrSet, true);
				tArea.setCharacterAttributes(atrSet, true);
				// atrSet.removeAttributes(atrSet);
			} // END OF BOLD BTN ACTION

		} else if (e.getSource() == underLineBtn) {

			// Strikethrough BTN ACTION
			if (!isUnderLineActive) {
				// false
				tagIndexS = tArea.getCaretPosition();
				underLineBtn.setBackground(sColor);
				StyleConstants.setUnderline(atrSet, true);
				tArea.setCharacterAttributes(atrSet, true);

				DBTEXT.append("~~");
				isUnderLineActive = true;
			} else if (isUnderLineActive) {
				// true
				isUnderLineActive = false;
				DBTextIndex = tagIndexE; // so next time we from this location
				DBTEXT.append("~~");
				// DBTextIndex++;
				System.out.println("ULine through - " + DBTEXT);
				StyleConstants.setUnderline(atrSet, true);
				tArea.setCharacterAttributes(atrSet, true);
				// atrSet.removeAttributes(atrSet);
			} // END OF BOLD BTN ACTION

		} else if (e.getSource() == deleteBtn) {
			// deleteBtn
			Notes.dataBase.deleteRowWithID(currID);
			closeOperation();
		} else if (e.getSource() == saveBtn) {

			// Notes.dataBase.updateTextAndTitle(currID, tArea.getText().toString(),
			// tArea.getText().toString()); //DBText
			new DB().updateTextAndTitle(currID, genTitle(), DBTEXT.toString());
			new DB().loadDataFromDB();
			Notes.updateListModal();
		}

		// END OF ACTION PERFORMED METH

		// END OF LEAF CLASS
	}

	String genTitle() {

		return tArea.getText().toString().substring(0,
				tArea.getText().length() - 1 <= 22 && tArea.getText().length() - 1 > 1 ? tArea.getText().length() - 1
						: 21);
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {

		 if(e.getKeyCode()==KeyEvent.VK_BACK_SPACE){
             
			 DBTEXT.deleteCharAt(DBTEXT.length()-1);
        }
		 
	}

	@Override
	public void keyReleased(KeyEvent e) {

		capitalizeText(); // Capitalize the First Letter using the method
		getWordCount(); // word count method
		charCountLabel.setText("Char :" + tArea.getText().length());

		if (tArea.getText().charAt(tArea.getText().toString().length() - 1) == ' ' && e.getKeyCode() != KeyEvent.VK_BACK_SPACE && e.getKeyCode() != 16 && e.getKeyCode() != 11  )
		{
			Notes.dataBase.updateTextAndTitle(currID, genTitle(), DBTEXT.toString());
			leafFrame.setTitle(genTitle());
			new DB().loadDataFromDB();
			Notes.updateListModal();
			//System.out.println("updating to DB inside the key event --"+DBTEXT.toString());
		}
		
		if(e.getKeyCode() != KeyEvent.VK_BACK_SPACE && e.getKeyCode() != 16 && e.getKeyCode() != 11)
		DBTEXT.append(tArea.getText().charAt(tArea.getText().length() - 1));
		System.out.println("loading the db text" + DBTEXT.toString());

	}

	// set attributes to the text from the DB
	void removeDecorations(String input) {

		System.out.println("TEXT FROM to DB -- " + input);

		MutableAttributeSet normal = new SimpleAttributeSet();
		StyleConstants.setForeground(normal, Color.black);

		MutableAttributeSet bold = new SimpleAttributeSet();
		StyleConstants.setBold(bold, true);

		MutableAttributeSet italic = new SimpleAttributeSet();
		StyleConstants.setItalic(italic, true);

		MutableAttributeSet sThroug = new SimpleAttributeSet();
		StyleConstants.setStrikeThrough(sThroug, true);

		MutableAttributeSet uLine = new SimpleAttributeSet();
		StyleConstants.setUnderline(uLine, true);

		StringBuilder result = new StringBuilder(); // string builder

		boolean attrP = false;

		boolean isB = false;
		boolean isI = false;
		boolean isU = false;
		boolean isS = false;

		for (int cIndex = 0; cIndex < input.length() - 1; cIndex++) {
			String key = String.valueOf(input.charAt(cIndex)) + String.valueOf(input.charAt(cIndex + 1));
			char cChar = input.charAt(cIndex);

			if (key.equals("**")) {
				if (attrP) {
					attrP = false;
					isB = false;
				} else {
					isB = true;
					attrP = true;
				}
				++cIndex;
			} else if (key.equals("$$")) {

				if (attrP) {
					attrP = false;
					isS = false;
				} else {
					isS = true;
					attrP = true;
				}

				++cIndex;

			} else if (key.equals("__")) {

				if (attrP) {
					attrP = false;
				} else {
					isI = true;
					attrP = true;
				}

				++cIndex;

			} else if (key.equals("~~")) {

				if (attrP) {
					attrP = false;
					isU = false;
				} else {
					isU = true;
					attrP = true;
				}

				++cIndex;

			}

			// checking the style and appending

			if (cChar != 36 && cChar != 42 && cChar != 95 && cChar != 126) {

				if (isB) {
					append(Doc, bold, String.valueOf(input.charAt(cIndex)));
					result.append(cChar);
				} else if (isI) {
					append(Doc, italic, String.valueOf(input.charAt(cIndex)));
					result.append(cChar);
				} else if (isU) {
					append(Doc, uLine, String.valueOf(input.charAt(cIndex)));
					result.append(cChar);
				} else if (isS) {
					append(Doc, sThroug, String.valueOf(input.charAt(cIndex)));
					result.append(cChar);
				} else {
					result.append(cChar);
					append(Doc, normal, String.valueOf(input.charAt(cIndex)));

				}
			}

		}
		System.out.println("TEXT to Screen -- " + result.toString());
		// tArea.setText(result.toString());
		tArea.setDocument(Doc);
		tArea.repaint();
		tagIndexS = tArea.getText().length() - 1;

	}

	private void append(StyledDocument doc, AttributeSet style, String text) {
		try {

			Doc.insertString(doc.getLength(), text, style);
		} catch (BadLocationException ex) {
			ex.printStackTrace();
		}
	}

}
