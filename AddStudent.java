import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 * This class opens the "Add Student" window when the "Add Student" button is pressed
 * The user is prompted for student information and will need to select which courses the student is enrolled in
 * After clicking, "confirm" the user will be brought to a new window where they can enter the marks for each course
 */
public class AddStudent extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	private JFrame f;
	private JPanel p;
	private JButton back;
	
	private JTextField nameTF;
	private JLabel nameLabel;
	private JTextField gradeTF;
	private JLabel gradeLabel;
	private JTextField studentNoTF;
	private JLabel studentNoLabel;
	private JTextField genderTF;
	private JLabel genderLabel;
	private JLabel isIBStudentLabel;
	private JCheckBox isIBStudent;
	private JButton confirm;
	private JLabel courseList;
	
	private String name;
	private String grade;
	private String studentNo;
	private String gender;
	private boolean IBStudent;
	
	//ArrayLists to store the checkboxes so that the user can select courses 
	private ArrayList <JCheckBox> courseEnrollment = new ArrayList <JCheckBox> ();
	private ArrayList <JLabel> courseLabels = new ArrayList <JLabel>();
	
	public AddStudent() {
		back = new JButton ("Back");
		nameTF = new JTextField();
		nameLabel = new JLabel ("Student Name:");
		gradeTF = new JTextField();
		gradeLabel = new JLabel ("Grade:");
		studentNoTF = new JTextField();
		studentNoLabel = new JLabel("Student Number:");
		genderLabel = new JLabel ("Gender:");
		genderTF = new JTextField ();
		isIBStudentLabel = new JLabel ("IB Student?");
		isIBStudent = new JCheckBox ("Yes");
		confirm = new JButton("Confirm");
		courseList = new JLabel ("Select Courses to Enroll in: (to a maximum of 6)");
		
		int y = 50; //the spacing between each checkbox
		
		for (int i = 0; i < DataBase.courseList.size();i++) {//store course names into labels
			String label = "";
			if (((Course)DataBase.courseList.lookUpCourse(i)).getTotalEnrolledStudents() >= 22) {
				continue;
			}
			if (((Course)(DataBase.courseList.lookUpCourse(i))).isIBCourse()) { //if the course is an IB course
				label = ((Course)(DataBase.courseList.lookUpCourse(i))).getName()+" (IB)";//add a tag
				JLabel tempLabel = new JLabel (label);
				tempLabel.setBounds(500, y, 150, 50);
				courseLabels.add(tempLabel);
			}else {
				label = ((Course)(DataBase.courseList.lookUpCourse(i))).getName();
				JLabel tempLabel = new JLabel (label);
				tempLabel.setBounds(500, y, 130, 50);
				courseLabels.add(tempLabel);
			}
			
			JCheckBox tempCB = new JCheckBox("");
			tempCB.setBounds (630, y+10, 30, 30); 
			courseEnrollment.add(tempCB);
			
			y += 20;
		}
		
		create();
	}
	
	private void create() {
		f = new JFrame ("Add Student");
		p = new JPanel();
		f.setVisible(true);
		f.setSize(800, 530);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false);
		f.setLocationRelativeTo(null);
		
		f.add(p);
		p.setLayout(null);
		
		p.add(back);
		back.setBounds(10,10,100,30);
		back.addActionListener(this);
		
		p.add(nameLabel);
		nameLabel.setBounds(100, 50, 150, 30);
		
		p.add(nameTF);
		nameTF.setBounds(230, 50, 150, 30);
		
		p.add(gradeLabel);
		gradeLabel.setBounds(100, 110, 150, 30);
		
		p.add(gradeTF);
		gradeTF.setBounds(230, 110, 150, 30);
		
		p.add(studentNoLabel);
		studentNoLabel.setBounds(100, 170, 150, 30);
		
		p.add(studentNoTF);
		studentNoTF.setBounds(230, 170, 150, 30);
		
		p.add(genderLabel);
		genderLabel.setBounds(100, 230, 150, 30);
		
		p.add(genderTF);
		genderTF.setBounds(230, 230, 150, 30);
		
		p.add(isIBStudentLabel);
		isIBStudentLabel.setBounds(100, 300, 150, 30);
		
		p.add(isIBStudent);
		isIBStudent.setBounds(230, 290, 50, 50);
		
		p.add(confirm);
		confirm.setBounds(200, 360, 150, 50);
		confirm.addActionListener(this);
		
		p.add(courseList);
		courseList.setBounds(470, 25, 300, 30);
		
		for (int i = 0; i < courseEnrollment.size(); i++) {
			p.add(courseLabels.get(i)); //adding checkboxes to the JPanel
			p.add(courseEnrollment.get(i));
		}
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == back) {
			new HomePage();
			f.dispose();
		}else if(e.getSource() == confirm) {
			//verify the user input
			String errorMessage = "Errors:\n";
			boolean error = false;
			Course courses [] = new Course [6];
			boolean isMale;
			name = nameTF.getText();
			grade = gradeTF.getText();
			grade = grade.replaceAll(" ","");
			studentNo = studentNoTF.getText();
			studentNo = studentNo.replaceAll(" ","");
			gender = genderTF.getText();
			gender = gender.replaceAll(" ","");
			
			try { //try catch exceptions
				Integer.parseInt(grade);
			}catch (NumberFormatException ex) {//if input is not an integer
				error = true;
				errorMessage += "Grade\n";
			}
			
			try {
				Integer.parseInt(studentNo);
			}catch (NumberFormatException ex) {
				error = true;
				errorMessage += "Student Number\n";
			}
			
			if (name.length() < 3) {
				error = true;
				errorMessage += "Name is too short\n";
			}
			if(gender.equals("")) {
				error = true;
				errorMessage += "Gender\n";
			}
			
			try {
				int counter = 0;
				for (int i = 0; i < courseEnrollment.size(); i ++) {
					if (courseEnrollment.get(i).isSelected()) {
						courses[counter] = (Course)DataBase.courseList.lookUpCourse(i); 
						counter ++;
					}
				}
				if (counter  == 0) {
					error = true;
					errorMessage +="You have not chosen any courses\n";
				}
				
			}catch(ArrayIndexOutOfBoundsException ex) { //if user selects more than 6 courses
				error = true;
				errorMessage +="You can only choose a maximum of 6 courses\n";
			}
			
			if(!isIBStudent.isSelected()) { //if user chooses IB courses for a Non IB student
				for (int i = 0; i < courseEnrollment.size(); i ++) {
					if (courseEnrollment.get(i).isSelected()) {
						if (((Course)DataBase.courseList.lookUpCourse(i)).isIBCourse()) {
							error = true;
							errorMessage += "Please only choose non-IB Courses\n";
							break;
						}
					}	
				}
			}else {
				for (int i = 0; i < courseEnrollment.size(); i ++) {//if user chooses non-IB courses for an IB student
					if (courseEnrollment.get(i).isSelected()) {
						if (!((Course)DataBase.courseList.lookUpCourse(i)).isIBCourse()) {
							error = true;
							errorMessage += "Please only choose IB Courses\n";
							break;
						}
					}	
				}
			}

			
			if (error) {
				JOptionPane.showMessageDialog(this, errorMessage,"",JOptionPane.ERROR_MESSAGE);
			}else {
				
				if (gender.equalsIgnoreCase("male") || gender.equalsIgnoreCase("m")) {
					isMale = true;
				}else {
					isMale = false;
				}
				
				int counter = 0;
				for (int i = 0; i < courseEnrollment.size(); i ++) {
					if (courseEnrollment.get(i).isSelected()) {
						courses[counter] = (Course)DataBase.courseList.lookUpCourse(i); 
						counter ++;
					}
					
				}
			
				if (isIBStudent.isSelected()) {
					IBStudent = true;
				}else {
					IBStudent = false;
				}
				DataBase.createStudent(name, grade, studentNo, isMale, IBStudent, courses, counter);//student is created
				new EnterMarks((Student)DataBase.studentList.lookUpStudent(0), courses);//next window to enter marks
				f.dispose();
			}
		}
	}
}
