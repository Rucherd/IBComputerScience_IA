import java.awt.event.*;
import javax.swing.*;

/**
 * This class opens the "Select Student" window when the "Select Student" button is pressed in the "Home Page" window
 *  Allows the user to select a student from a JComboBox, sorted in alphabetical order
 *  The user can also filter search a student using a keyword
 */
public class SelectStudent extends JFrame implements ActionListener{ 

	private static final long serialVersionUID = 1L;
	
	private JFrame f;
	private JPanel p;
	private JButton back;
	private JButton confirm;
	private JComboBox<Object> allStudents;
	private JLabel selectStudent;
	private String sortedStudents [];
	
	private JLabel searchLabel;
	private JButton searchButton;
	private JTextField searchTF;
	
	public SelectStudent() {
		int l = DataBase.studentList.size();
		//populate the array with all the students
		sortedStudents = new String[l];
		for (int i = 0; i < l; i++) {
			if (((Student)DataBase.studentList.lookUpStudent(i)).isIBStudent()) {
				sortedStudents[i] = ((Student)DataBase.studentList.lookUpStudent(i)).getName()+" (IB)";//add and IB tag
			}else {
				sortedStudents[i] = ((Student)DataBase.studentList.lookUpStudent(i)).getName();
			}
		}
		
		DataBase.sortArray(sortedStudents); //sort the array
		
		allStudents = new JComboBox<Object> (sortedStudents);
		
		back = new JButton ("Back");
		confirm = new JButton ("Confirm");
		selectStudent = new JLabel ("Select a Student: ");
		
		searchLabel = new JLabel ("Search for a keyword");
		searchButton = new JButton ("Search");
		searchTF = new JTextField();
		
		create();
		
	}

	private void create() {
		f = new JFrame ("Select Student");
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
		
		p.add(allStudents);
		allStudents.setBounds(110,130,250,30);
		allStudents.addActionListener(this);
		
		p.add(selectStudent);
		selectStudent.setBounds(110,70,250,60);
		
		p.add(searchTF);
		searchTF.setBounds(110,350,250,30);
		searchTF.addActionListener(this);
		
		p.add(searchLabel);
		searchLabel.setBounds(110,300,250,60);
		
		p.add(searchButton);
		searchButton.setBounds(450,340,150,50);
		searchButton.addActionListener(this);
		
		p.add(confirm);
		confirm.setBounds(450, 120, 150, 50);
		confirm.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()== back) {
			new HomePage();
			f.dispose();
		}else if (e.getSource() == confirm) {
			String student = String.valueOf(allStudents.getSelectedItem());
			student = student.replaceAll("IB", "");
			student = student.replaceAll("\\(\\)",""); //remove the IB tag when comparing
			student = student.trim();
			
			for (int i = 0; i < DataBase.studentList.size(); i++) {
				//find which student was selected
				if (student.equalsIgnoreCase(((Student)DataBase.studentList.lookUpStudent(i)).getName())) {  
					Student s = (Student) DataBase.studentList.lookUpStudent(i);
					new DisplayStudent(s);
					//display the selcted student
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
			//make sure the input isn't empty
				JOptionPane.showMessageDialog(this, "Please Enter a Keyword","",JOptionPane.ERROR_MESSAGE);
			}else {
				new Search(true, key);
				f.dispose();
			}
		}
	}
}
