import java.awt.event.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * This class opens the "Display Course" window when a specific course is chosen in the "Select Courses" window
 * It displays the Course information, Students Enrolled, their marks,
 * and their IB marks (if the course is IB)
 * The user also has the option to adjust the marks of the Course they have selected
 * 
 */
public class DisplayCourse extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	private JFrame f;
	private JPanel p;
	private JButton back;
	private JButton adjustMarks;
	private JButton delete;
	private JButton nextPage;
	private JButton prevPage;
	
	private Course course;
	private JLabel students [];
	private JLabel marks[];
	
	private JLabel IBMarks[];
	private JLabel IBMarksLabel;
	private JLabel standardDev;
	
	private JLabel infoLabel;
	private JLabel studentsLabel;
	
	private JLabel nameLabel;
	private JLabel teacherLabel;
	private JLabel gradeLabel;
	private JLabel isIBLabel;
	private String isIB;
	private JLabel average;
	private JLabel studentsEnrolled;
	private JLabel marksLabel;
	
	private boolean next;
	private int page;
	private int readTo;
	
	
	/**
	 * Constructor 
	 * @param course Course to be displayed
	 * @param next Boolean to determine the page number of students
	 */
	public DisplayCourse(Course course, boolean next) {
		
		//boolean next tells which page to display
		this.next = next;
		this.course = course;
		students = new JLabel [30];
		marks = new JLabel [30];
		IBMarks = new JLabel [30];
		back = new JButton ("Back");
		delete = new JButton ("Delete Course");
		//the next two buttons only show up if the page is full 
		nextPage = new JButton ("Page 2");
		prevPage = new JButton ("Page 1");
		
		nameLabel = new JLabel ("Course:     "+course.getName());
		teacherLabel = new JLabel ("Teacher:     "+course.getTeacher());
		gradeLabel = new JLabel ("Grade Level:     "+course.getGradeLevel());
		infoLabel = new JLabel ("Course Information:");
		studentsLabel = new JLabel ("Students Enrolled:");

		if (course.isIBCourse()) { //add more information if the course is an IB course
			average = new JLabel("Course Mean (IB):     "+((IBCourse)course).getMean()+"%");
			marksLabel = new JLabel ("Raw mark:");
			IBMarksLabel = new JLabel ("Adjusted Mark:");
			adjustMarks = new JButton ("Adjust Marks");
			standardDev = new JLabel ("Standard Deviation:     "+((IBCourse)course).getStandardDev());
		}else {
			marksLabel = new JLabel ("Mark:");
			average = new JLabel("Course Mean:     "+course.getAverage()+"%");
		}
		studentsEnrolled = new JLabel ("Number of students: "+course.getTotalEnrolledStudents());
		
		if (course.isIBCourse()) {
			isIB = "Yes";
		}else {
			isIB = "No";
		}
		isIBLabel = new JLabel ("IB Course?     "+isIB);
		

		if(course.getTotalEnrolledStudents() <= 11) { 
			//one page can only display 11 students at one time
			page = 0;
			readTo = course.getTotalEnrolledStudents();
		}else if(course.getTotalEnrolledStudents() > 11 && !next) {
			page = 0;
			readTo = 11;
		}else {
			page = 11;
			readTo = course.getTotalEnrolledStudents();
		}
		
		int y = 90;
		
		for (int i = page; i < readTo; i++) {
			//intialize students' names and marks
			JLabel tempStudent = new JLabel (course.getStudents()[i].getName());
			tempStudent.setBounds(320,y,200,50);
			students [i] = tempStudent;
			
			JLabel tempMark = new JLabel (Double.toString(course.getMarks()[i])+"%");
			tempMark.setBounds(470, y, 100, 50);
			marks[i] = tempMark;
	
			y+= 30; //30 pixel gap between each student
		}
		
		y = 90;
		if(course.isIBCourse()) {
			for (int i = page; i < readTo; i++) {
				//initialize students' IB marks
				JLabel tempIBMark = new JLabel (Double.toString(((IBCourse)course).getIBMarks()[i])+"%");
				tempIBMark.setBounds(550, y, 100, 50);
				IBMarks[i] = tempIBMark;
				y += 30;
			}
		}
		
		create();
	}
	
	public void create() {
		f = new JFrame ("Display Course");
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
		
		p.add(nameLabel);
		nameLabel.setBounds(100, 100, 400, 30);
		
		p.add(teacherLabel);
		teacherLabel.setBounds(100, 150, 400, 30);
		
		p.add(gradeLabel);
		gradeLabel.setBounds(100, 200, 400, 30);
		
		p.add(isIBLabel);
		isIBLabel.setBounds(100, 250, 400, 30);
		
		p.add(average);
		average.setBounds(100, 300, 400, 30);
		
		p.add(studentsEnrolled);
		studentsEnrolled.setBounds(100, 350, 400, 30);
		
		p.add(studentsLabel);
		studentsLabel.setBounds(320, 50, 400, 30);
		
		p.add(marksLabel);
		marksLabel.setBounds(470, 50, 400, 30);
		
		
		p.add(back);
		back.setBounds(10,10,100,30);
		back.addActionListener(this);
		
		p.add(delete);
		delete.setBounds(100,440,150,30);
		delete.addActionListener(this);
		
		if (course.getTotalEnrolledStudents() > 11 && !next) { 
			//if course has more than 11 students and on the first page
			p.add(nextPage);
			nextPage.setBounds(630,300,100,30);
			nextPage.addActionListener(this);
		}
		if(next) { //if on the second page
			p.add(prevPage);
			prevPage.setBounds(630,300,100,30);
			prevPage.addActionListener(this);
		}
		
		for (int i = page; i < readTo; i ++) {
			p.add(students[i]);
			p.add(marks[i]);
		}
		
		if (course.isIBCourse()) {
			p.add(IBMarksLabel);
			IBMarksLabel.setBounds(550, 50, 400, 30);
			p.add(standardDev);
			standardDev.setBounds(100, 400, 400, 30);
			p.add(adjustMarks);
			adjustMarks.setBounds(630,100,130,30);
			adjustMarks.addActionListener(this);
			for (int i = page; i < readTo; i++) {
				//display the IB marks if it is an IB course
				p.add(IBMarks[i]);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == back) {
			new SelectCourse();
			f.dispose();
		}else  if(e.getSource() == adjustMarks) {
			if (course.getTotalEnrolledStudents() <= 1) {
				JOptionPane.showMessageDialog(this, "Please enroll more students to use this function","",JOptionPane.ERROR_MESSAGE);
			}else {
				new AdjustMarks(course);
				f.dispose();
			}
		}else if (e.getSource() == delete) {
			//a confirmation message so that the user does not accidentaly delete a course
			int result = JOptionPane.showConfirmDialog(f,"Are you sure you want to delete "+course.getName()+"?", "Confirmation",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
			if(result == JOptionPane.YES_OPTION){
				DataBase.deleteCourse(course);
				new HomePage();
				f.dispose();
			}
		}else if (e.getSource() == nextPage) {
			//next page/previous page buttons	
			new DisplayCourse (course, true);
			f.dispose();
		}else if(e.getSource() == prevPage) {
			new DisplayCourse(course, false);
			f.dispose();
		}
		
	}

}
