import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This class opens the "Student Stats" window when the "Student Stats" button is clicked in the "Home Page" window
 * Displays all the students in descending order of averages
 * Displays the total amount of IB students and the amount of non IB students
 * It should be noted that if a student is in IB, their IB average is used
 *
 */
public class StudentStats extends JFrame implements ActionListener{
	

	private static final long serialVersionUID = 1L;
	
	private JFrame f;
	private JPanel p;
	private JButton back;
	private JButton selectStudent;
	private JButton nextPage;
	private JButton prevPage;
	
	private JLabel student;
	private JLabel mark;
	
	private String students [];
	private double marks[];
	
	private JLabel studentLabel [];
	private JLabel markLabel[];
	
	private JLabel numIB;
	private JLabel numNonIB;
	
	boolean next;
	private int page;
	
	/**
	 * constructor
	 * @param next To indicate which page the user is on
	 */
	public StudentStats(boolean next) {
		
		this.next = next;
		nextPage = new JButton ("Next Page");
		prevPage = new JButton ("Prev Page");
		back = new JButton ("Back");
		selectStudent = new JButton ("Select a Student");
		student = new JLabel ("Student:");
		mark = new JLabel ("Average:");
		
		students = new String [DataBase.studentList.size()];
		marks = new double [DataBase.studentList.size()];
		
		studentLabel = new JLabel [DataBase.studentList.size()];
		markLabel = new JLabel [DataBase.studentList.size()];
		
		numIB = new JLabel ("Number of IB Students: "+Integer.toString(IBStudent.numIbStudent));
		numNonIB = new JLabel ("Number of Non-IB Students: "+Integer.toString(Student.numStudent - IBStudent.numIbStudent));
		
		for (int i = 0; i < marks.length; i++) {
			if(((Student)DataBase.studentList.lookUpStudent(i)).isIBStudent()) {
				students[i] = "(IB) "+((Student)DataBase.studentList.lookUpStudent(i)).getName();//add IB tag
				marks[i] = ((IBStudent)DataBase.studentList.lookUpStudent(i)).getIBaverage();
			}else {
				students[i] = ((Student)DataBase.studentList.lookUpStudent(i)).getName();
				marks[i] = ((Student)DataBase.studentList.lookUpStudent(i)).getAverage();
			}
		}
		
		for (int i = 0; i < marks.length-1; i++) { //selection sort
			int max = i;
			for (int q = i+1; q < marks.length; q++) {
				if (marks[q]  > marks[max]) {
					max = q;
				}
			}
			
			double temp = marks[i]; //sort the parallel arrays
			marks[i] = marks[max];
			marks[max] = temp;
			String tempString = students [i];
			students[i] = students [max];
			students[max] = tempString;
		}
		
		//each column
		int y = 75; 
		int y1 = 75;
		int y2 = 75;
		
		if (next) {
			page = 36;
		}else {
			page = 0;
		}
		for (int i = page; i < marks.length; i++) {
			JLabel tempStudent = new JLabel (students[i]);
			JLabel tempMark = new JLabel (Double.toString(marks[i])+"%");
			if (i <= page+11) {
				if (students[i].substring(0,4).equals("(IB)")){
					tempStudent.setBounds(26,y,200,50);
				}else {
					tempStudent.setBounds(50,y,200,50);
				}
				tempMark.setBounds(170,y,200,50);
				y += 30;
			}else if(i > page+11 && i <= page+23) {
				if (students[i].substring(0,4).equals("(IB)")){
					tempStudent.setBounds(226,y1,200,50);
				}else {
					tempStudent.setBounds(250,y1,200,50);
				}
				tempMark.setBounds(370,y1,200,50);
				y1 += 30;
			}else if(i > page+23 && i <= page+35) {
				if (students[i].substring(0,4).equals("(IB)")){
					tempStudent.setBounds(426,y2,200,50);
				}else {
					tempStudent.setBounds(450,y2,200,50);
				}
				tempMark.setBounds(570,y2,200,50);
				y2 += 30;
			}
			studentLabel [i] = tempStudent;
			markLabel[i] = tempMark;
		}
		create();
		
	}
	
	public void create() {
			f = new JFrame ("Student Statistics");
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
			
			p.add(selectStudent);
			selectStudent.setBounds(600,20,140,30);
			selectStudent.addActionListener(this);
			
			p.add(student);
			student.setBounds(50,50,100,30);
			
			p.add(mark);
			mark.setBounds(170,50,100,30);
			
			p.add(numIB);
			numIB.setBounds(400,20, 250, 30);
			
			p.add(numNonIB);
			numNonIB.setBounds(200, 20, 250, 30);
			
			//add everything to the panel
			for (int i = page; i < marks.length; i++) {
				p.add(studentLabel[i]);
				p.add(markLabel[i]);
			}
			if (DataBase.studentList.size() > 36 && !next) {
				p.add(nextPage);
				nextPage.setBounds(650, 250, 100, 30);
				nextPage.addActionListener(this);
			}
			
			if (next) {
				p.add(prevPage);
				prevPage.setBounds(650, 250, 100, 30);
				prevPage.addActionListener(this);
			}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == back) {
			new HomePage();
			f.dispose();
		}else if (e.getSource() == selectStudent){
			new SelectStudent();
			f.dispose();
		}else if(e.getSource() == nextPage) { 
			new StudentStats(true);
			f.dispose();
		}else if(e.getSource() == prevPage) {
			new StudentStats(false);
			f.dispose();
		}
		
	}

}
