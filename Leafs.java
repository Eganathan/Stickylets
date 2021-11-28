import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

@SuppressWarnings("serial")
public class Leafs implements ActionListener{

	//Variables
	Color dColor = Color.getHSBColor(80, 50, 20);
	Color sColor = Color.getHSBColor(80, 40, 30);
	
	//Buttons for edit panel
	JButton boldBtn; //Bold button  
	JButton italicBtn; // Italic button 
	JButton upperCaseBtn; // Italic button 
	JButton underLineBtn; //underline button 
	JButton strikeThroughBtn; //strike through button 
	
	//Font Variables
	int dFontDize = 22;
	int dStyle = 0;  // 0 -> plain , 1 -> BOLD , 3 -> italic 
	String fFamily = "Sans";
	Font dFont = new Font(fFamily ,dStyle, dFontDize);
	Font btnFonts = new Font(fFamily ,0, dFontDize); //style of buttons
	
	//Bold font for Btn
	Font bFont = new Font(fFamily ,Font.BOLD, dFontDize);
	Font iFont = new Font(fFamily ,Font.ITALIC, dFontDize);
	
	//Test Area
	 JTextPane tArea;
	 
	//Panels
	JPanel ePanel ;
	
	
	//** WORKING VARIABLES**
	boolean boldBtnActive = false; ; //0 -> un-clicked or not clicked | 1-> Clicked and active
	boolean italicBtnActive = false; ; //0 -> un-clicked or not clicked | 1-> Clicked and active
	
	Leafs(){ 

		JFrame leafFrame = new JFrame(title);
		leafFrame.setSize(250,250);
		leafFrame.setLayout( new BorderLayout());
		leafFrame.setBackground(dColor);
		
		
		tArea = new JTextPane();
		tArea.setFont(dFont);
		tArea.setRequestFocusEnabled(rootPaneCheckingEnabled);
		tArea.setBackground(dColor);
		tArea.setBorder(new EmptyBorder(10,10,10,10));
		tArea.setSize(leafFrame.getWidth(), leafFrame.getHeight());
		
		
		
		
		
		ePanel = new JPanel();
		ePanel.setBackground(sColor);
		ePanel.setSize(leafFrame.getWidth(),80);
		ePanel.setLayout(new GridLayout(1,5));
		
		
		italicBtn = new JButton("i");
		boldBtn = new JButton("B");
		upperCaseBtn = new JButton("U");  
		underLineBtn = new JButton("V"); 
		strikeThroughBtn = new JButton("S");  
		
		
		//sets Style and 
		//adds Action Listener to all the Button instances 
		defaultStyle(italicBtn);
		defaultStyle(boldBtn);
		defaultStyle(upperCaseBtn);
		defaultStyle(underLineBtn);
		defaultStyle(strikeThroughBtn);
		
		
		
		leafFrame.add(tArea,BorderLayout.CENTER);
		leafFrame.add(ePanel,BorderLayout.SOUTH);
		leafFrame.setResizable(true);
		leafFrame.setVisible(true);
		leafFrame.getDefaultCloseOperation(); }
		

	
	//paints the Buttons to match the same
	void defaultStyle(JButton obj){	
		obj.setFont(btnFonts);
		obj.setBackground(sColor);
		obj.setBorderPainted(false);
		obj.setFocusable(false);
		obj.addActionListener(this);
		obj.setBorder(new EmptyBorder(5,5,5,5));
		ePanel.add(obj);
		italicBtn.setFont(iFont);
		ePanel.repaint();}
	
	
	void editText(char action,char bol){	
		
		SimpleAttributeSet attr = new SimpleAttributeSet();	
		
	//Index of the edited text and its value
	//int startTextRef = tArea.getSelectionStart(); //Start of the index
	//int endTextRef = tArea.getSelectionEnd(); // end of the index
	//String cText = tArea.getSelectedText(); // current text from the entry
	//System.out.println(startTextRef +" "+endTextRef +" "+cText);
	
	//START OF BOLD
	if(action == 'b') {
		//BOLD ACTION
		if(bol == 't') {
				//BOLD
				boldBtn.setFont(bFont);
				StyleConstants.setBold(attr, true);
				tArea.setCharacterAttributes(attr, true);
			}else if(bol == 'f'){
				boldBtn.setFont(btnFonts);
				StyleConstants.setBold(attr, false);}
				tArea.setCharacterAttributes(attr, false);
				attr.removeAttributes(attr);
		}//END OF BOLD
	
		
	//START OF Italic
		if(action == 'i') {
			
			//Italic ACTION
			if(bol == 't') {
				
					//ITALIC
					StyleConstants.setItalic(attr, true); //setBold(attr, true);
					tArea.setCharacterAttributes(attr, true);
					italicBtn.repaint();
				}else if(bol == 'f'){
					
					italicBtn.setFont(btnFonts);
					StyleConstants.setItalic(attr, false);
					tArea.setCharacterAttributes(attr, false);
					attr.removeAttributes(attr);}
			}//END OF Italic

	

	
	
	
	}	

	
	public void actionPerformed(ActionEvent e)	{
		
		if(e.getSource()== boldBtn) {
			
				//BOLD BTN ACTION
				if(boldBtnActive == false) {
					editText('b','t');
					boldBtnActive = true;
				}else {
					editText('b','f');
					boldBtnActive = false; }//END OF BOLD BTN ACTION
			
		}else if(e.getSource()== italicBtn) {
			
				//ITALIC BTN ACTION
				if(boldBtnActive == false) {
					editText('i','t');
					italicBtnActive = true;
				}else {
					editText('i','f');
					italicBtnActive = false; }//END OF BOLD BTN ACTION
				
			}
		
	//END OF ACTION PERFORMED METH
	}
	
	
	
//END OF LEAF CLASS
}


