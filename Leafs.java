import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class Leafs extends Notes{
	
	Color dColor = Color.getHSBColor(80, 50, 20);
	Color sColor = Color.getHSBColor(80, 40, 30);
	
	
	Leafs(){ 

		JFrame leafFrame = new JFrame(title);
		leafFrame.setSize(250,250);
		leafFrame.setLayout( new BorderLayout());
		leafFrame.setBackground(dColor);
		
		JTextArea tArea = new JTextArea();
		tArea.setRequestFocusEnabled(rootPaneCheckingEnabled);
		tArea.setBackground(dColor);
		tArea.setBorder(new EmptyBorder(10,10,10,10));
		tArea.setLineWrap(true);
		tArea.setWrapStyleWord(true);
		tArea.setSize(leafFrame.getWidth(), leafFrame.getHeight());
		
		JPanel ePanel = new JPanel();
		ePanel.setBackground(sColor);
		ePanel.setSize(leafFrame.getWidth(),50);
		JButton boldBtn = new JButton("B");
		ePanel.add(boldBtn);
		
		leafFrame.add(tArea,BorderLayout.CENTER);
		leafFrame.add(ePanel,BorderLayout.SOUTH);
		leafFrame.setResizable(true);
		leafFrame.setVisible(true);
		leafFrame.getDefaultCloseOperation();
	}

}
