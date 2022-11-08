import java.awt.event.*;
import javax.swing.*;

/**
 * This class opens the "Select Course" window when the "Select Course" button is pressed in the "Home Page" window
 * Allows the user to select a class from a JComboBox, sorted in alphabetical order
 * The user can also filter search a course using a keyword 
 */
public class SelectCourse extends JFrame implements ActionListener{ 
	

	private static final long serialVersionUID = 1L;
	
	private JFrame f;
	private JPanel p;
	private JButton back;
	private JButton confirm;
	private JComboBox<Object> allCourses;
	private JLabel selectCourses;
	private String sortedCourses [];
	
	private JLabel searchLabel;
	private JButton searchButton;
	private JTextField searchTF;
	
	public SelectCourse() {
		int l = DataBase.courseList.size();
		//populate the array with all the courses
		sortedCourses = new String[l];
		for (int i = 0; i < l; i++) {
			if (((Course)DataBase.courseList.lookUpCourse(i)).isIBCourse()) {
				sortedCourses[i] = ((Course)DataBase.courseList.lookUpCourse(i)).getName()+" (IB)";//add IB tag
			}else {
				sortedCourses[i] = ((Course)DataBase.courseList.lookUpCourse(i)).getName();
			}
		}
		
		DataBase.sortArray(sortedCourses); //sort the array
		
		allCourses = new JComboBox<Object> (sortedCourses);
		
		back = new JButton ("Back");
		confirm = new JButton ("Confirm");
		selectCourses = new JLabel ("Select a Course:");
		
		searchLabel = new JLabel ("Search for a keyword");
		searchButton = new JButton ("Search");
		searchTF = new JTextField();
		
		create();
		
	}

	private void create() {
		f = new JFrame ("Select Course");
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
		
		
		p.add(allCourses);
		allCourses.setBounds(110,130,250,30);
		allCourses.addActionListener(this);
		
		p.add(selectCourses);
		selectCourses.setBounds(110,70,250,60);
		
		p.add(confirm);
		confirm.setBounds(450, 120, 150, 50);
		confirm.addActionListener(this);
		
		p.add(searchTF);
		searchTF.setBounds(110,350,250,30);
		searchTF.addActionListener(this);
		
		p.add(searchLabel);
		searchLabel.setBounds(110,300,250,60);
		
		p.add(searchButton);
		searchButton.setBounds(450,340,150,50);
		searchButton.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()== back) {
			new HomePage();
			f.dispose();
		}else if(e.getSource() == confirm) {
			String course = String.valueOf(allCourses.getSelectedItem());
			course = course.replaceAll("IB", "");
			course = course.replaceAll("\\(\\)",""); //remove the IB tag when comparing
			course = course.trim();
			for (int i = 0; i < DataBase.courseList.size(); i++) {
		
				if (course.equalsIgnoreCase(((Course)DataBase.courseList.lookUpCourse(i)).getName())) {  
					//find which course was selected
					Course c = (Course) DataBase.courseList.lookUpCourse(i);
					new DisplayCourse(c, false);
					//display the course selected
					f.dispose();
					break;
				}
			}

		}else if(e.getSource() == searchButton) {
			String key = searchTF.getText();
			key = key.trim();
			key = key.toLowerCase();
			//make the search case insensitive
			if (key.equals("")) {
				//make sure that the input isn't empty
				JOptionPane.showMessageDialog(this, "Please Enter a Keyword","",JOptionPane.ERROR_MESSAGE);
			}else {
				new Search(false, key);
				f.dispose();
			}
		}
		
	}

}
