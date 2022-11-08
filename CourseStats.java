import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This class opens the window "Course Stats" when the button "Course Stats" is clicked
 * It displays all the courses, in descending order of Course Means
 * displays course enrollment for all courses and the number of IB courses and non IB Courses
 * It should be noted that if a course is an IB course, IB averages will be used
 */
public class CourseStats extends JFrame implements ActionListener{
	

	private static final long serialVersionUID = 1L;
	
	private JFrame f;
	private JPanel p;
	private JButton back;
	
	private String course[];
	private double average[];
	
	private JLabel courseLabel [];
	private JLabel averageLabel[];
	
	private int numStudent[];
	private JLabel numStudentLabel[];
	
	private JLabel numCourses;
	private JLabel numIBCourses;
	
	private JLabel infoLabel;
	private JLabel markLabel;
	private JLabel enrollment;
	
	private JButton selectCourse;
	
	public CourseStats() {
		back = new JButton ("Back");
		selectCourse = new JButton ("Select a Course");
		
		infoLabel = new JLabel ("Course:");
		markLabel = new JLabel ("Mean:");
		enrollment = new JLabel ("Enrollment:");
		
		course = new String [DataBase.courseList.size()];
		average = new double [DataBase.courseList.size()];
		
		courseLabel = new JLabel [DataBase.courseList.size()];
		averageLabel = new JLabel [DataBase.courseList.size()];
		
		numStudent = new int [DataBase.courseList.size()];
		numStudentLabel = new JLabel [DataBase.courseList.size()];
		
		numCourses = new JLabel ("Number of Non-IB Courses: "+(Course.numCourse- IBCourse.numIBCourse));
		numIBCourses = new JLabel ("Number of IB Courses: "+IBCourse.numIBCourse);
		
		for (int i = 0; i < course.length; i ++) {
			if (((Course)DataBase.courseList.lookUpCourse(i)).isIBCourse()) {//if IB course, add an IB tag
				course[i] = "(IB) "+((Course)DataBase.courseList.lookUpCourse(i)).getName();
				average[i] = ((IBCourse)DataBase.courseList.lookUpCourse(i)).getMean();
			}else {
				course[i] = ((Course)DataBase.courseList.lookUpCourse(i)).getName();
				average[i] = ((Course)DataBase.courseList.lookUpCourse(i)).getAverage();
			}
			numStudent[i] = ((Course)DataBase.courseList.lookUpCourse(i)).getTotalEnrolledStudents();
		}
		for (int i = 0; i < average.length-1; i++) { //selection sort
			int max = i;
			for (int q = i+1; q < average.length; q++) {
				if (average[q]  > average[max]) {
					max = q;
				}
			}
			double temp = average[i]; //these are parallel arrays so all three need to be updated
			average[i] = average[max];
			average[max] = temp;
			
			String tempString = course [i];
			course[i] = course [max];
			course[max] = tempString;
			
			int temp1 = numStudent[i];
			numStudent[i] = numStudent[max];
			numStudent[max] = temp1;
		}
		
		int y = 75; //first column
		int y1 = 75; //second column
		for (int i = 0; i < average.length; i++) {
			JLabel tempCourse = new JLabel (course[i]);
			JLabel tempAverage = new JLabel (Double.toString(average[i])+"%");
			JLabel tempNum = new JLabel (Integer.toString(numStudent[i])+" Students");
			if (i <= 11) {
				if (course[i].substring(0,4).equals("(IB)")){ //take into account the space the "(IB)" tag takes up
					tempCourse.setBounds(26,y,200,50); //it takes up 24 pixels (50-26)
				}else {
					tempCourse.setBounds(50,y,200,50);
				}
				tempAverage.setBounds(170,y,200,50);
				tempNum.setBounds(230,y,200,50);
				y += 30;
			}else {
				if (course[i].substring(0,4).equals("(IB)")){ 
					tempCourse.setBounds(326,y1,200,50);
				}else {
					tempCourse.setBounds(350,y1,200,50);
				}
				tempAverage.setBounds(470,y1,200,50);
				tempNum.setBounds(530,y1,200,50);
				y1 += 30;
			}
			courseLabel [i] = tempCourse;
			averageLabel [i] = tempAverage;
			numStudentLabel [i] = tempNum;
	
		}
		
		create();
	}
	
	public void create() {
			f = new JFrame ("Course Stats");
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
			
			p.add(selectCourse);
			selectCourse.setBounds(600,20,140,30);
			selectCourse.addActionListener(this);
			
			p.add(infoLabel);
			infoLabel.setBounds(50,50,200,50);
			
			p.add(markLabel);
			markLabel.setBounds(170,50,200,50);
			
			p.add(enrollment);
			enrollment.setBounds(230, 50, 200, 50);
			
			p.add(numCourses);
			numCourses.setBounds(200, 20, 250, 30);
			
			p.add(numIBCourses);
			numIBCourses.setBounds(400,20, 250, 30);
			
			for (int i = 0; i < average.length; i++) {
				p.add(courseLabel[i]);
				p.add(averageLabel[i]);
				p.add(numStudentLabel[i]);
				//adding all labels to the panel
			}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == back) {
			new HomePage();
			f.dispose();
		}else if (e.getSource() == selectCourse) {
			new SelectCourse();
			f.dispose();
		}
		
	}

}
