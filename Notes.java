import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

@SuppressWarnings("serial")
public class Notes extends JFrame implements ActionListener{
	
	Color sColor = Color.getHSBColor(80, 40, 30); //
	
	private JButton nFrameBtn;
	
	String title = "Sticky Notes";
	Notes() {
		
		JFrame x = new JFrame(title);
		x.setSize(300,150);
		x.setLocation(100, 200);
		
		nFrameBtn = new JButton("New Note");
		nFrameBtn.setBackground(sColor);
		nFrameBtn.setFont( new Font("sans" ,Font.BOLD, 24));
		nFrameBtn.addActionListener(this);
		
		
		x.add(nFrameBtn);
		x.setVisible(true);
		x.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == nFrameBtn) {
			
			new Leafs(); } }
	
	

}
