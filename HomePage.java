import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;   
import java.awt.*;

/**
 * Class that opens the "Home Page" window when the program is opened
 * Contains the main buttons to the various windows, as well as the "Recover Data" button that recovers all the data 
 */
public class HomePage extends JFrame implements ActionListener{
	
	
	private static final long serialVersionUID = 1L;
	
	private JFrame f;
	private JPanel p;
	private JButton quit;
	private JButton addStudent;
	private JButton addCourse;
	private JButton selectStudent;
	private JButton selectCourse;
	private JButton studentStats;
	private JButton courseStats;
	private JButton recoverData;
	private JLabel introduction;

	public HomePage () {
		introduction = new JLabel ("Welcome to the Student Mark Manager!");
		quit = new JButton ("Quit");
		addStudent = new JButton("Add Student");
		addCourse = new JButton("Add Course");
		selectStudent = new JButton("Select Student");
		selectCourse = new JButton("Select Course");
		studentStats = new JButton("Student Stats");
		courseStats = new JButton("Course Stats");
		recoverData = new JButton("Recover Data");
		create();
	}
	
	private void create () {
		f = new JFrame ("Home Page");
		p = new JPanel ();
		f.setSize(800, 530);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		f.setResizable(false);
		f.setLocationRelativeTo(null);
		p.setLayout(null);
		f.add(p);
		
		p.add(introduction);
		introduction.setFont(new Font("Verdana", Font.PLAIN, 15));
		introduction.setBounds(240,15,500,60);
		
		p.add(quit);
		quit.setBounds(10,10,100,30);
		quit.addActionListener(this);
		
		p.add(addStudent);
		addStudent.setBounds(110,70,250,60);
		addStudent.addActionListener(this);
		
		p.add(addCourse);
		addCourse.setBounds(410,70,250,60);
		addCourse.addActionListener(this);
		
		p.add(selectStudent);
		selectStudent.setBounds(110,170,250,60);
		selectStudent.addActionListener(this);
		
		p.add(selectCourse);
		selectCourse.setBounds(410,170,250,60);
		selectCourse.addActionListener(this);
		
		p.add(studentStats);
		studentStats.setBounds(110,270,250,60);
		studentStats.addActionListener(this);
		
		p.add(courseStats);
		courseStats.setBounds(410,270,250,60);
		courseStats.addActionListener(this);
		
		p.add(recoverData);
		recoverData.setBounds(260,370,250,60);
		recoverData.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==quit) {
			f.dispose();
			System.exit(0);
		}else if(e.getSource() == addStudent) {
			new AddStudent(); 
			f.dispose();
		}else if(e.getSource() == addCourse) {
			new AddCourse();
			f.dispose();
		}else if(e.getSource() == selectStudent) {
			new SelectStudent();
			f.dispose();
		}else if(e.getSource() == selectCourse) {
			new SelectCourse();
			f.dispose();
		}else if(e.getSource() == studentStats) {
			new StudentStats(false);
			//false means display the first page
			f.dispose();
		}else if(e.getSource() == courseStats) {
			new CourseStats();
			f.dispose();
		}else if (e.getSource() == recoverData) {
			if (!DataBase.isRecovered) {
				try {
					DataBase.recoverData();
					JOptionPane.showMessageDialog(this, "Data Recovered","",JOptionPane.INFORMATION_MESSAGE);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}else {
				//if the data was already recovered
				JOptionPane.showMessageDialog(this, "You have already recovered the data","",JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
}
