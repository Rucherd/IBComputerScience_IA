import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This class opens when the button "Search" is clicked in the "Select Student" or "Select Course" windows
 * It takes the keyword that the user entered and filter searches through all the students/courses
 * and only displays students/courses that contain the keyword
 */
public class Search extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	private boolean isStudent;
	
	private JFrame f;
	private JPanel p;
	private JButton back;
	
	private JLabel resultsLabel;
	private JLabel noResults;
	private String tempArr[];
	private String arr[];
	private ArrayList <JButton> results = new ArrayList <JButton> ();
	
	/**
	 * @param isStudent If the call to this class came from the "Select Student" class
	 * @param key The string the user entered as a keyword
	 */
	public Search (boolean isStudent, String key) {
		
		this.isStudent = isStudent;
		resultsLabel = new JLabel ("Search Results:");
		back = new JButton ("Back");
		noResults = new JLabel ("No Results Found");
		
		int counter = 0;
		if (isStudent) { 
			//finding all the students whose names contain the keyword
			tempArr = new String [DataBase.studentList.size()];
			for (int i = 0; i < tempArr.length; i++) {
				if (((Student)DataBase.studentList.lookUpStudent(i)).getName().toLowerCase().indexOf(key) != -1) {
					tempArr[counter] = ((Student)DataBase.studentList.lookUpStudent(i)).getName();
					counter++;
				}
			}
			//copy array values into a new array of the perfect length for convenient future use
			arr = new String [counter];
			for (int i = 0; i < counter; i++) {
				arr [i] = tempArr[i];
			}
			
			
		}else { //same thing above, except search through the course list instead
			tempArr = new String [DataBase.courseList.size()];
			for (int i = 0; i < tempArr.length; i++) {
				if (((Course)DataBase.courseList.lookUpCourse(i)).getName().toLowerCase().indexOf(key) != -1) {
					tempArr[counter] = ((Course)DataBase.courseList.lookUpCourse(i)).getName();
					counter++;
				}
			}
			arr = new String [counter];
			for (int i = 0; i < counter; i++) {
				arr [i] = tempArr[i];
			}
		}
		
		DataBase.sortArray(arr);//sort the array in alphabetical order
		
		int y = 50;
		int y1 = 50;
		for (int i = 0; i < arr.length; i++) { //populate the array
			if (i <= 13) {
				JButton tempButton = new JButton(arr[i]);
				tempButton.setBounds(75,y,150,25);
				tempButton.addActionListener(this);
				results.add(tempButton);
				y += 30;
			}else {
				JButton tempButton = new JButton(arr[i]);
				tempButton.setBounds(275,y1,150,25);
				tempButton.addActionListener(this);
				results.add(tempButton);
				y1 += 30;
			}
		}
		
		create();
	}
	
	public void create() {
		f = new JFrame ("Search");
		p = new JPanel();
		f.setVisible(true);
		f.setSize(500, 530);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false);
		f.setLocationRelativeTo(null);
		
		f.add(p);
		p.setLayout(null);
		
		p.add(resultsLabel);
		resultsLabel.setBounds(200,20,100,30);
		
		p.add(back);
		back.setBounds(10,10,150,30);
		back.addActionListener(this);
		
		if (results.size() == 0) {
			//no results were found
			p.add(noResults);
			noResults.setBounds(195,50,150,30);
		}else {
			for (int i = 0; i < results.size(); i++) {
				p.add(results.get(i)); 
			}
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == back && isStudent) {
			new SelectStudent();
			f.dispose();
		}else if(e.getSource() == back && !isStudent) {
			new SelectCourse();
			f.dispose();
		}else {
			for (int i = 0; i < results.size(); i++) {
				if (e.getSource() == results.get(i)) {
					if (isStudent) {
						for (int q = 0; q < DataBase.studentList.size(); q++) {
							//match the name on the button to the name of an existing student, and call the DisplayStudent class with the student instance
							if (((Student)DataBase.studentList.lookUpStudent(q)).getName().equals(results.get(i).getText())) {
								new DisplayStudent((Student)DataBase.studentList.lookUpStudent(q));
								f.dispose();
								break;
							}
						}
					}else {
						for (int q = 0; q < DataBase.courseList.size(); q++) {
							//match the name on the button to the name of an existing course, and call the DisplayCourse class with the course instance
							if (((Course)DataBase.courseList.lookUpCourse(q)).getName().equals(results.get(i).getText())) {
								new DisplayCourse((Course)DataBase.courseList.lookUpCourse(q), false);
								f.dispose();
								break;
							}
						}
					}
				}
			}
		}
	}
}
