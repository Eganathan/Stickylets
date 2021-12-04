import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


@SuppressWarnings("serial")
public class Notes extends JFrame implements ActionListener, ListSelectionListener {
	
	Color sColor = Color.getHSBColor(80, 40, 30); //
	
	private JButton nFrameBtn;
	static String title = "Sticky Notes";
	
	public static DefaultListModel<String> leafsM;
	static int counter;
	
	static HashMap<String,Leafs> Objmap =new HashMap<String,Leafs>();
	static private JList<String> list;
	
	Notes() {
		
		JFrame x = new JFrame(title);
		x.setSize(300,600);
		x.setLocation(50,60);
		x.setLayout(new BorderLayout());;
		
		nFrameBtn = new JButton("New Note"); // Main frame title 
		nFrameBtn.setBackground(sColor); // sets the background
		nFrameBtn.setFont( new Font("sans" ,Font.BOLD, 24)); // seting the fonts
		nFrameBtn.addActionListener(this); // ading action listener to new note button

		
		//List Model 
		 leafsM = new DefaultListModel<>();    
         //l1.addElement("Item4");
         
		 //J List 
         list = new JList<>(leafsM);
         list.setBorder(new EmptyBorder(10,10, 10, 10));
         list.addListSelectionListener(this);
         list.setBounds(100,100, 75,75);  
         
         //Ading the list
         x.add(list,BorderLayout.CENTER);  
		
		
		x.add(nFrameBtn , BorderLayout.NORTH);
		
		x.setVisible(true);
		x.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		
		while(leafsM.size() > 0) {
			upDateList();
		}
	}
	
	/*
	 * Creates a new instance of sticky note,
	 * adds them to the Hash map with the key as the file name and stores the instance there
	 * adds to the List as well
	 */
	static void newNote(){
		//Setting a file value with counter
		String lName = "My Note 0"+counter;
		//leafs Object 
		Leafs Object = new Leafs(lName);
		//adding to leafsM
		//leafsM.addElement(lName);
		//adding to HMap with key and value 
		Objmap.put(lName, Object);
		 leafsM.addElement(lName);
		//list.add(lName, new JButton(lName));
		
		;
	}
	
	
	/*static void saveNote(){
		 File notesFolder = new File(System.getProperty("user.home") + "/.sticky_notes/notes");
	        notesFolder.mkdirs();
	        File[] notesFiles = notesFolder.listFiles();
	}*/
	
	
	public void upDateList(){
		
		if(Objmap.containsKey(list.getSelectedValue())){
			
				Objmap.get(list.getSelectedValue()).leafFrame.setVisible(true);
				Objmap.get(list.getSelectedValue()).upDateTitle();
				String newKey = Objmap.get(list.getSelectedValue()).upDateTitle();
				Objmap.put(newKey, Objmap.get(list.getSelectedValue()));
				
				leafsM.remove(list.getSelectedIndex());
				leafsM.addElement(newKey);
				list.repaint();
				
				Objmap.remove(list.getSelectedValue(),Objmap.get(list.getSelectedValue()));
				System.out.println(Objmap);}
		
	}
		
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == nFrameBtn) {
			
			counter++;
			newNote();			
			System.out.println(Objmap);			
		}}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		//System.out.println(list.getSelectedIndex());
		
		if(Objmap.containsKey(list.getSelectedValue())){
			
			Objmap.get(list.getSelectedValue()).leafFrame.setVisible(true);
			Objmap.get(list.getSelectedValue()).upDateTitle();
			String newKey = Objmap.get(list.getSelectedValue()).upDateTitle();
			Objmap.put(newKey, Objmap.get(list.getSelectedValue()));
			
			leafsM.remove(list.getSelectedIndex());
			leafsM.addElement(newKey);
			list.repaint();
			
			Objmap.remove(list.getSelectedValue(),Objmap.get(list.getSelectedValue()));
			System.out.println(Objmap);
			/*
			 * Objmap.put(lName, Object);
		 		;
			 */
			
			//list.addSelectionInterval(0, 0);
			/*
			if(Objmap.get(list.getSelectedValue()).tArea.getText() == ""){
				
				
				//String wRp = Objmap.get(list.getSelectedValue()).tArea.getText().substring(0,10);
				
				//Objmap.put(wRp, Objmap.get(list.getSelectedValue()));
				 //leafsM.addElement(wRp);
				 
				 //Objmap.remove(list.getSelectedValue());
				
			}else {
				
				System.out.println(Objmap.get(list.getSelectedValue()).tArea.getText().substring(0,10));
				
			}*/
			
		}
		
	}
	

}
