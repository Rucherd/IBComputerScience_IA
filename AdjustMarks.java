import java.awt.event.*;
import javax.swing.*;

/**
 * This class opens the "Adjust Marks" window after the "Adjust Marks" button is pressed in the "Display course" window
 * It prompts the user for a standard deviation and mean, and the class' marks are automatically
 * scaled accordingly to the new values given
 * Mean: The class average
 * Standard Deviation: the spread of marks  
 */

public class AdjustMarks extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	private JFrame f;
	private JPanel p;

	private JButton confirm;
	private JButton back;
	
	private JLabel setMean;
	private JLabel setSD;
	private JLabel infoLabel;
	
	private JLabel currentMean;
	private JLabel currentSD;
	
	private JTextField setMeanTF;
	private JTextField setSDTF;
	
	Course course;
	
	/**
	 * @param c Takes the Course instance from the "Display Course" class 
	 */
	public AdjustMarks (Course c) {
		
		course = c;
		confirm = new JButton ("Confirm");
		back = new JButton ("Back");
		setMean = new JLabel ("New Mean:");
		setSD = new JLabel ("New Standard Deviation:");
		infoLabel = new JLabel ("Adjust Marks for   "+course.getName());
		
		currentMean = new JLabel ("Current Mean:     "+((IBCourse)course).getMean()+"%");
		currentSD = new JLabel ("Current Standard Deviation:     "+((IBCourse)course).getStandardDev());
	
		
		setMeanTF = new JTextField();
		setSDTF = new JTextField();
		create();
	}
	
	private void create () {
		f = new JFrame ("Adjust marks");
		p = new JPanel ();
		f.setSize(500, 350);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		f.setResizable(false);
		f.setLocationRelativeTo(null);
		p.setLayout(null);
		
		f.add(p);
		
		p.add(infoLabel);
		infoLabel.setBounds(150, 15, 250, 30);
		
		p.add(currentMean);
		currentMean.setBounds(75, 70, 300, 30);
		
		p.add(currentSD);
		currentSD.setBounds(75, 120, 300, 30);
		
		p.add(setMean);
		setMean.setBounds(75, 170, 200, 30);
		
		p.add(setMeanTF);
		setMeanTF.setBounds(250, 170, 70, 25);
		
		p.add(setSD);
		setSD.setBounds(75, 220, 200, 30);
		
		p.add(setSDTF);
		setSDTF.setBounds(250, 220, 70, 25);
		
		p.add(confirm);
		confirm.setBounds(200,270,100,30);
		confirm.addActionListener(this);
		
		p.add(back);
		back.setBounds(10,10,100,30);
		back.addActionListener(this);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == back) {
			new DisplayCourse(course, false);
			f.dispose();
		}else if(e.getSource() == confirm) {
			//verify user input
			String errorMessage = "Errors:\n";
			boolean error = false;
			
			String tempNewMean = setMeanTF.getText();
			tempNewMean = tempNewMean.trim();
			String tempNewSD = setSDTF.getText();
			tempNewSD = tempNewSD.trim();
			
			try {
				Double.parseDouble(tempNewMean);
				if(Double.parseDouble(tempNewMean) < 0 || Double.parseDouble(tempNewMean) > 100) { //if input entered is an invalid mark
					error = true;
					errorMessage += "Mean\n";
				}
			}catch (NumberFormatException ex){//if input entered is not a number
				error = true;
				errorMessage += "Mean\n";
			}
			
			try {
				Double.parseDouble(tempNewSD);
				if(Double.parseDouble(tempNewSD) < 0 || Double.parseDouble(tempNewSD) > 100) {
					error = true;
					errorMessage += "Standard Deviation\n";
				}
			}catch (NumberFormatException ex){
				error = true;
				errorMessage += "Standard Deviation\n";
			}
			
			if (error) {
				JOptionPane.showMessageDialog(this, errorMessage,"",JOptionPane.ERROR_MESSAGE);
			}else {
				double newMean = Double.parseDouble(setMeanTF.getText());
				double newStdDev = Double.parseDouble(setSDTF.getText());
				((IBCourse)course).adjustMarks(newMean, newStdDev);
				//Scale the marks to match the desired mean and standard deviation
				new DisplayCourse(course, false);
				f.dispose();
			}
			
		}
		
	}
	

}
