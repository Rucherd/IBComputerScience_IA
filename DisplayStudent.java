import java.awt.event.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * This class opens the "Display Student" course when a student is selected in the "Select Student" window
 * Displays information of a student, their courses, their marks in each course, 
 * and their IB marks if the student is in IB
 * The user has the option to update the student's marks if they need to be changed
 */
public class DisplayStudent extends JFrame implements ActionListener{
	

	private static final long serialVersionUID = 1L;
	
	private JFrame f;
	private JPanel p;
	private JButton back;
	private JButton save;
	private JButton delete;
	
	private Student student;
	
	private JLabel nameLabel;
	private JLabel gradeLabel;
	private JLabel studentNoLabel;
	private JLabel genderLabel;
	private JLabel isIBLabel;
	private JLabel courses [];
	private JLabel marks [];
	private JLabel IBMarks[];
	private JLabel average;
	
	private JTextField editMarks[];
	private JLabel editMarkLabel;
	
	private JLabel marksLabel;
	private JLabel infoLabel;
	private JLabel courseLabel;
	private JLabel topLabel;
	
	private String gender;
	private String isIB;
	
	
	/**
	 * Constructor
	 * @param student The Student instance 
	 */
	public DisplayStudent(Student student) {
		
		this.student = student;
		back = new JButton ("Back");
		delete = new JButton ("Delete Student");
		nameLabel = new JLabel ("Name:     "+student.getName());
		gradeLabel = new JLabel ("Grade:     "+student.getGrade());
		studentNoLabel = new JLabel ("Student Number:     "+student.getStudentNo());
		courseLabel = new JLabel ("Course:");
		if(student.isIBStudent()) {
			marksLabel = new JLabel ("Raw:          Adjusted:");
			topLabel = new JLabel("Marks:");
		}else {
			marksLabel = new JLabel ("Mark:");
		}
		infoLabel = new JLabel ("Student Information:");
		courses = new JLabel [6]; 
		marks = new JLabel [6];
		IBMarks = new JLabel [6];
		editMarks = new JTextField[6];		
		editMarkLabel = new JLabel("Update Mark (if required):");
		save = new JButton("Save");
		
		if (student.isMale()) {
			gender = "Male";
		}else {
			gender = "Female";
		}
		if (student.isIBStudent()) {
			isIB = "Yes";
			//if student is in IB, display their IB average, not raw average
			average = new JLabel ("IB AVERAGE:           "+((IBStudent)student).getIBaverage()+"%");
		}else {
			isIB = "No";
			average = new JLabel ("AVERAGE:                "+student.getAverage()+"%");
		}
		genderLabel = new JLabel ("Gender:     "+gender);
		isIBLabel = new JLabel ("IB Student?     "+isIB);
		
		int y = 90;
		for (int i = 0; i < student.getNumOfCourses(); i++) {
			JLabel courseLabel = new JLabel ((student.getCourses()[i]).getName());
			courseLabel.setBounds(350,y,100,50);
			courses[i] = courseLabel;
				
			JLabel markLabel = new JLabel (student.getMarks()[i]+"%");
			markLabel.setBounds(470,y,100,50);
			marks[i] = markLabel;
					
			JTextField tempTF = new JTextField(); 
			//text fields to update student marks if necessary
			tempTF.setBounds(650, y+13, 50, 25);
			editMarks [i]= tempTF;
				
			y += 50;
		}
		
		y = 90;
		if (student.isIBStudent()) {
			for (int i = 0; i < student.getNumOfCourses(); i++) {
				JLabel IBMarkLabel = new JLabel (((IBStudent)student).getIBMarks()[i]+"%");
				//populate an array of the Student's IB marks if the student is in IB
				IBMarkLabel.setBounds(540,y,100,50);
				IBMarks[i] = IBMarkLabel;
				y += 50;
			}
		}
		create();
	}
	
	private void create() {
		f = new JFrame ("Display Student");
		p = new JPanel();
		f.setVisible(true);
		f.setSize(800, 530);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false);
		f.setLocationRelativeTo(null);
		
		f.add(p);
		p.setLayout(null);
		
		p.add(infoLabel);
		infoLabel.setBounds(100, 50, 400, 30);
		
		p.add(courseLabel);
		courseLabel.setBounds(350, 50, 400, 30);
		
		p.add(marksLabel);
		marksLabel.setBounds(470, 50, 400, 30);
		
		p.add(nameLabel);
		nameLabel.setBounds(100, 100, 400, 30);
		
		p.add(gradeLabel);
		gradeLabel.setBounds(100, 160, 400, 30);
		
		p.add(studentNoLabel);
		studentNoLabel.setBounds(100, 220, 400, 30);
		
		p.add(genderLabel);
		genderLabel.setBounds(100, 280, 400, 30);
		
		p.add(isIBLabel);
		isIBLabel.setBounds(100, 340, 400, 30);
		
		p.add(editMarkLabel);
		editMarkLabel.setBounds(620, 40, 400, 50);
		
		p.add(average);
		average.setBounds(350, 410, 400, 40);
		
		p.add(back);
		back.setBounds(10,10,100,30);
		back.addActionListener(this);
		
		p.add(save);
		save.setBounds(650, 420, 100, 40);
		save.addActionListener(this);
		
		p.add(delete);
		delete.setBounds(100,400,150,30);
		delete.addActionListener(this);
		
		for (int i = 0; i < student.getNumOfCourses(); i++) {
			//add information to JPanel
			p.add(courses[i]);
			p.add(marks[i]);
			p.add(editMarks[i]);
		}
		
		if (student.isIBStudent()) {
			p.add(topLabel);
			topLabel.setBounds(500, 20, 400, 30);
			for (int i = 0; i < student.getNumOfCourses(); i++) {
				p.add(IBMarks[i]);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == back) {
			new SelectStudent();
			f.dispose();
		}else if(e.getSource()== delete) {
			//confimation message so user doesn't accidentally delete a student
			int result = JOptionPane.showConfirmDialog(f,"Are you sure you want to delete "+student.getName()+"?", "Confirmation",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
			if(result == JOptionPane.YES_OPTION){
				DataBase.deleteStudent(student);
				new HomePage();
				f.dispose();
			}
		}else if(e.getSource() == save) {
			//if the user has updated one or more marks
			
			String errorMessage = "Error:\n";
			boolean error = false;
			
			String tempMarks [] =  new String [6];
			for (int i = 0; i < student.getNumOfCourses(); i ++) {
				if (!editMarks[i].getText().trim().equals("")) {
					tempMarks [i]= editMarks[i].getText();
					try {
						Double.parseDouble(tempMarks [i]);
						if (Double.parseDouble(tempMarks [i]) < 0 || Double.parseDouble(tempMarks [i]) > 100) {
							//if user input was not a valid mark
							error = true;
							errorMessage += "All marks must be numbers between 0 and 100\n";
							break;
						}
					}catch(NumberFormatException ex) { //if user input was not a number
						error = true;
						errorMessage += "All marks must be numbers between 0 and 100\n";
						break;
					}
				}else {
					tempMarks [i] = student.getMarks()[i];
				}
			}
			
			if (error) {
				JOptionPane.showMessageDialog(this, errorMessage,"",JOptionPane.ERROR_MESSAGE);
			}else {
				//if there is no error, update all information
				student.setMarks(tempMarks);
				student.setAverage();
				for (int i = 0; i < student.getNumOfCourses(); i++) {
					student.getCourses()[i].setAverage();
					student.getCourses()[i].setMarks();
					if(student.getCourses()[i].isIBCourse()) {
						//this only updates the raw marks
						//if a raw mark was updated for a course that was already moderated (marks adjusted for that course),
						//an adjustment will automatically occur based on that courses mean and standard deviation
						((IBCourse)student.getCourses()[i]).findAndAdjust();
						//Re-Adjust the newly entered marks based on the enrolled courses' standard deviation and mean
						
						for (int q = 0; q < student.getCourses()[i].getTotalEnrolledStudents(); q++) {
							if (student.getName().equals(student.getCourses()[i].getStudents()[q].getName())) {
								if (!((IBCourse)student.getCourses()[i]).isAdjusted()) {
									//if an IB courses marks is not adjusted yet, IB marks are equal to raw marks
									double IBMark = Double.parseDouble(student.getMarks()[i]);
									((IBCourse)student.getCourses()[i]).setIBMarks(IBMark, q);
								}else {
									//if marks were adjusted set the course's updated IB marks to the student's mark in that course
									double IBMark = Double.parseDouble(((IBStudent)student).getIBMarks()[i]);
									((IBCourse)student.getCourses()[i]).setIBMarks(IBMark, q);
								}
							}
						}
					}
				}
				JOptionPane.showMessageDialog(this, "Marks Changed.","",JOptionPane.INFORMATION_MESSAGE);
				new DisplayStudent(student); //refresh the window
				f.dispose();
			}
		}
	}

}
