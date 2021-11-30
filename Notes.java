import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class Notes extends JFrame implements ActionListener{
	
	Color sColor = Color.getHSBColor(80, 40, 30); //
	
	private JButton nFrameBtn;
	static String title = "Sticky Notes";
	
	public DefaultListModel<Leafs> leafsM;
	int counter;
	
	Leafs[] objArr = new Leafs[12];
	Notes() {
		
		JFrame x = new JFrame(title);
		x.setSize(300,150);
		x.setLocation(100,250);
		x.setLayout(new BorderLayout());;
		
		nFrameBtn = new JButton("New Note");
		nFrameBtn.setBackground(sColor);
		nFrameBtn.setFont( new Font("sans" ,Font.BOLD, 24));
		nFrameBtn.addActionListener(this);
		
		
		
		
		
		 leafsM = new DefaultListModel<>();   
         //l1.addElement("Item4");
         
         JList<Leafs> list = new JList<>(leafsM);  
         list.setBounds(100,100, 75,75);  
         
         
         x.add(list);  
		
		
		x.add(nFrameBtn , BorderLayout.NORTH);
		
		x.setVisible(true);
		x.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
	}
	
	
		
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == nFrameBtn) {
			String lName = "My Note 0"+counter;
			counter++;
			
			Leafs r = new Leafs(lName);
			leafsM.addElement(r);
			leafsM.getListDataListeners();
			
		}}
	
	

}
