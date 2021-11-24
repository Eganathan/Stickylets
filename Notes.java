import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Notes extends JFrame implements ActionListener{
	
	private JButton nFrameBtn;
	
	String title = "Sticky Notes";
	Notes() {
		
		JFrame x = new JFrame(title);
		x.setSize(250,100);
		
		nFrameBtn = new JButton("New Note");
		nFrameBtn.addActionListener(this);
		
		
		x.add(nFrameBtn);
		x.setVisible(true);
		x.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == nFrameBtn) {
			
			createnewFrame();
		}
		
	}
	private void createnewFrame() {
		new Leafs();
	}
	
	

}
