import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
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

class DisabledNote implements ActionListener{
	
	//Attributes
	SimpleAttributeSet atrSet = new SimpleAttributeSet();
	SimpleAttributeSet DBSet = new SimpleAttributeSet();
	
	StyledDocument Doc;
	
	Font taFont = new Font("Sans", 0, 16);
	
	// Variables
	Color dColor = Color.getHSBColor(255, 239, 175); // 80, 50, 20
	Color sColor = Color.getHSBColor(80, 40, 30);
	Color tColor = Color.getHSBColor(100, 0, 100);

	// Buttons for edit panel
	JButton deleteBtn;
	JButton restoreBtn;
	
	StringBuilder DBTEXT = new StringBuilder();
	int DBTextIndex = 0;
	int LIndex = 0;


	// Frame
	JFrame disabledLeafFrame;

	// Test Area
	JTextPane  tArea;

	// Panels
	JPanel updatePanel;

	String DBText;
	
	// Testing Style doc
	StyledDocument styledDocument;

	private int currID;
	private String title;

	DisabledNote(int ID, String titleValue, String textValue) {
		currID = ID;
		DBTEXT.append(textValue);
		title = titleValue;
		
		
		disabledLeafFrame = new JFrame(titleValue);
		disabledLeafFrame.setSize(250, 250);
		disabledLeafFrame.setLayout(new BorderLayout());
		disabledLeafFrame.setBackground(dColor);
		disabledLeafFrame.setLocation(450, 50);

		tArea =new JTextPane();
		tArea.setEditorKit(new StyledEditorKit());                    
	    Doc = tArea.getStyledDocument();       
		tArea.setFont(taFont);
		tArea.setBackground(new Color (254, 217, 183));
		tArea.setBorder(new EmptyBorder(10, 10, 10, 10));
		tArea.setSize(disabledLeafFrame.getWidth(), disabledLeafFrame.getHeight());


		//update panel
		updatePanel = new JPanel();
		updatePanel.setLayout(new GridLayout(1,3));
		updatePanel.setBackground(Notes.blu);
		
		
		ImageIcon Delicon = new ImageIcon(getClass().getResource("\\img\\delIcon.png"));
		deleteBtn = new JButton(Delicon);
		deleteBtn.setBackground(Notes.blu);
		deleteBtn.setFocusable(false);
		deleteBtn.addActionListener(this);
		
		ImageIcon saveicon = new ImageIcon(getClass().getResource("\\img\\saveimg.png"));
		restoreBtn = new JButton(saveicon);
		restoreBtn.setBackground(Notes.blu);		
		restoreBtn.setFocusable(false);
		restoreBtn.addActionListener(this);
		

		updatePanel.add(restoreBtn);
		updatePanel.add(deleteBtn);
		
		//removing the decorations from the db text
		removeDecorations(DBTEXT.toString());
		
		disabledLeafFrame.add(tArea, BorderLayout.CENTER);
		disabledLeafFrame.add(updatePanel, BorderLayout.SOUTH);
		disabledLeafFrame.setResizable(true);
		disabledLeafFrame.setDefaultCloseOperation(closeOperation());
		disabledLeafFrame.setVisible(true);
		
	}
	
	 int  closeOperation(){
		 disabledLeafFrame.setVisible(false);
		 disabledLeafFrame.dispose();
		 
		 return 0;
	 }

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if( e.getSource()== restoreBtn)
		{
			new DB().updateRestore( currID,1, title, DBTEXT.toString());
			closeOperation();
			
		}else {
			new DB().updateRestore( currID,2,title, DBTEXT.toString());
			closeOperation();
		}

		
		// is restore
		//2 is delete
		
	}
	
	 void removeDecorations(String input){
		    
			System.out.println("TEXT FROM to DB -- "+input);
			
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
		
				for(int cIndex = 0; cIndex < input.length()-1; cIndex++)
				{
					String key = String.valueOf( input.charAt(cIndex))+String.valueOf( input.charAt(cIndex+1));
					char cChar = input.charAt(cIndex);
					
					if(key.equals("**"))
					{
						if(attrP)  
						{
							attrP = false;
							isB = false;
						}else {
							isB = true;
							attrP = true;
						}
						++cIndex;
					}else if(key.equals("$$"))
					{
			
						if(attrP)  
						{
							attrP = false;
							isS = false;
						}else {
							isS = true;
							attrP = true;
						}
						
						++cIndex;
						
					}else if(key.equals("__"))
					{
			
						if(attrP)  
						{
							attrP = false;
						}else {
							isI = true;
							attrP = true;
						}
						
						++cIndex;
						
					}else if(key.equals("~~"))
					{
			
						if(attrP)  
						{
							attrP = false;
							isU = false;
						}else {
							isU = true;
							attrP = true;
						}
						
						++cIndex;
						
					}
					
					
					//checking the style and appending
					if(true)
					{
							if( cChar !=  36 && cChar !=  42 && cChar !=  95 && cChar !=  126 )//(input.charAt(cIndex) >= 65 || input.charAt(cIndex) <= 90 || input.charAt(cIndex) >=97 || input.charAt(cIndex) <= 122)
							{
								
								if (isB)
									{
									append(Doc, bold, String.valueOf(input.charAt(cIndex)));
									result.append(cChar);
									}
								else if(isI)
								{
								append(Doc, italic, String.valueOf(input.charAt(cIndex)));
								result.append(cChar);
								}
								else if(isU)
								{
								append(Doc, uLine, String.valueOf(input.charAt(cIndex)));
								result.append(cChar);
								}
								else if(isS)
								{
								append(Doc, sThroug, String.valueOf(input.charAt(cIndex)));
								result.append(cChar);
								}
								else{
										result.append(cChar);
										System.out.println(input.charAt(cIndex) +" ---indexed");
										append(Doc, normal, String.valueOf(input.charAt(cIndex)));
										
									}
							}	
						}	
				}
		System.out.println("TEXT to Screen -- "+ result.toString());
		//tArea.setText(result.toString());
		tArea.setDocument(Doc);
		tArea.setEditable(false);
		tArea.repaint();
		
	}
	 
	  private void append(StyledDocument doc, AttributeSet style, String text) {
		    try {
		        
		    	Doc.insertString(doc.getLength(), text, style);
		    } catch (BadLocationException ex) {
		        ex.printStackTrace();
		    }
		  }

}
