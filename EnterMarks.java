import java.awt.event.*;
import javax.swing.*;

/**
 * This class opens the "Enter Marks" window when the "confirm" button in the "Add student" class is pressed
 * Prompts the user for the marks of the student in each course that the student is enrolled in
 *
 */

public class EnterMarks extends JFrame implements ActionListener{
	

	private static final long serialVersionUID = 1L;
	
	private JFrame f;
	private JPanel p;

	private JButton confirm;
	private JLabel markLabel;
	private JLabel courseLabel;
	private JLabel infoLabel;
	
	private Student s;
	private JLabel markLabels [];
	private JTextField markTF [];
	private String marks [];
	
	/**
	 * @param s Student instance that was just created
	 * @param courses Array of Courses that the student is enrolled in
	 */
	public EnterMarks (Student s, Course courses []) {
		
		confirm = new JButton("Confirm");
		markLabel = new JLabel (s.getName()+"'s "+"Mark:");
		courseLabel = new JLabel ("Course:");
		if (s.isIBStudent()) {
			//user will always enter raw marks (if IB Student)
			infoLabel = new JLabel ("Please enter "+s.getName()+"'s raw marks:");
		}else {
			infoLabel = new JLabel ("Please enter "+s.getName()+"'s marks:");
		}
		
		this.s = s;
		marks = new String [courses.length];
		markLabels = new JLabel [courses.length];
		markTF= new JTextField [courses.length];
		int y = 100;
		for (int i = 0; i < markLabels.length; i++) {
			if (courses[i] != null) {
				//initializing JLabels and JTextFields
				String label = courses[i].getName();
				JLabel tempLabel = new JLabel (label);
				tempLabel.setBounds(125, y, 100, 50);
				markLabels[i] = tempLabel;
				
				JTextField tempTF = new JTextField();
				tempTF.setBounds (245, y+10, 50, 30);
				markTF[i] = tempTF;
				
				y += 50;
			}else {
				break;
			}
		}
		create();
	}
	
	private void create () {
		f = new JFrame ("Enter Marks");
		p = new JPanel ();
		f.setSize(450, 530);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		f.setResizable(false);
		f.setLocationRelativeTo(null);
		p.setLayout(null);
		
		f.add(p);
		
		p.add(confirm);
		confirm.setBounds(165, 425, 100, 30);
		confirm.addActionListener(this);
		
		p.add(markLabel);
		markLabel.setBounds(245, 50, 250, 50);
		
		p.add(courseLabel);
		courseLabel.setBounds(125, 50, 100, 50);
		
		p.add(infoLabel);
		infoLabel.setBounds(125, 10, 350, 50);
		
		//adding Textfields and labels
		for (int i = 0; i < markLabels.length; i++) {
			if(markLabels[i] != null) {
				p.add(markLabels[i]);
				p.add(markTF[i]);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == confirm) {
			//verify user input
			String errorMessage = "Error:\n";
			boolean error = false;
			
			for (int i = 0; i < marks.length; i ++) {
				if (markTF[i] != null) {
					marks[i] = (markTF[i].getText()).trim();
				}else {
					break;
				}

			}
			
			for (int i = 0; i < marks.length; i ++) {
				if (markTF[i] != null) {
					try {
						if(Double.parseDouble(marks[i]) < 0 || Double.parseDouble(marks[i]) > 100) {
							//if the user input is not a valid mark
							error = true;
							errorMessage += "All marks must be numbers between 0 and 100\n";
							break;
						}
					}catch (NumberFormatException ex) { //if user input is not a number
						error = true;
						errorMessage += "All marks must be numbers between 0 and 100\n";
						break;
					}
				}else {
					break;
				}
			}
			
			if (error) {
				//error message
				JOptionPane.showMessageDialog(this, errorMessage,"",JOptionPane.ERROR_MESSAGE);
			}else {
				
				//set marks and IB marks
				s.setMarks(marks); 
				s.setAverage();
				if (s.isIBStudent()) {
					((IBStudent)s).setIBMarks();
					((IBStudent)s).setIBAverage();
				}
				for (int i = 0; i < s.getNumOfCourses(); i++) {
					//set information
					s.getCourses()[i].setAverage();
					s.getCourses()[i].setMarks();
					if ((s.getCourses()[i]).isIBCourse()) {
						((IBCourse)s.getCourses()[i]).findAndAdjust();
						double IBMark = Double.parseDouble(((IBStudent)s).getIBMarks()[i]);
						//update the courses IB marks
						((IBCourse)s.getCourses()[i]).setIBMarks(IBMark);
						((IBCourse)s.getCourses()[i]).setCalcAverage();
						((IBCourse)s.getCourses()[i]).setStandardDev();
					}

				}
				if (s.isIBStudent()) {
					((IBStudent)s).setCalcAverage(); //update the mean
				}
				
				JOptionPane.showMessageDialog(this, "Student Added.","",JOptionPane.INFORMATION_MESSAGE);
				new HomePage();
				f.dispose();
			}
		}
		
	}

}
