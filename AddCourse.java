import java.awt.event.*;
import javax.swing.*;


/**
 * This class opens the "Add Course" window once the "Add Course" button is pressed.
 * The user is prompted for course information 
 */
public class AddCourse extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JFrame f;
	private JPanel p;
	private JButton back;
	private JLabel nameLabel;
	private JTextField nameTF;
	private JLabel teacherLabel;
	private JTextField teacherTF;
	private JLabel levelLabel;
	private JTextField levelTF;
	private JLabel IBCourseLabel;
	private JCheckBox isIBCourse;
	private JButton confirm;
	private String name;
	private String teacher;
	private String grade;
	private boolean isIB;

	
	public AddCourse() {
		back = new JButton("Back");
		nameLabel = new JLabel("Course Name:");
		nameTF = new JTextField();
		teacherLabel = new JLabel("Teacher:");
		teacherTF = new JTextField();
		levelLabel = new JLabel("Grade Level:");
		levelTF = new JTextField();
		IBCourseLabel = new JLabel("IB Course?");
		isIBCourse = new JCheckBox("Yes");
		confirm = new JButton("Confirm");

		create();

	}

	private void create() {
		f = new JFrame("Add Course");
		p = new JPanel();
		f.setVisible(true);
		f.setSize(800, 530);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false);
		f.setLocationRelativeTo(null);

		f.add(p);
		p.setLayout(null);

		p.add(back);
		back.setBounds(10, 10, 100, 30);
		back.addActionListener(this);

		p.add(nameLabel);
		nameLabel.setBounds(100, 100, 150, 30);

		p.add(nameTF);
		nameTF.setBounds(230, 100, 150, 30);

		p.add(teacherLabel);
		teacherLabel.setBounds(100, 160, 150, 30);

		p.add(teacherTF);
		teacherTF.setBounds(230, 160, 150, 30);

		p.add(levelLabel);
		levelLabel.setBounds(100, 220, 150, 30);

		p.add(levelTF);
		levelTF.setBounds(230, 220, 150, 30);

		p.add(IBCourseLabel);
		IBCourseLabel.setBounds(100, 280, 150, 30);

		p.add(isIBCourse);
		isIBCourse.setBounds(230, 280, 50, 50);

		p.add(confirm);
		confirm.setBounds(200, 350, 150, 50);
		confirm.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == back) {
			new HomePage();
			f.dispose();
		} else if (e.getSource() == confirm) {
			// verify user input and display errors
			String errorMessage = "Errors:\n";
			boolean error = false;

			name = nameTF.getText();
			name = name.trim();
			teacher = teacherTF.getText();
			teacher = teacher.trim();
			grade = levelTF.getText();
			grade = grade.replaceAll(" ", "");

			if (name.equals("")) {
				error = true;
				errorMessage += "Name\n";
			}

			if (teacher.equals("")) {
				error = true;
				errorMessage += "Teacher\n";
			}

			if (grade.equals("")) {
				error = true;
				errorMessage += "Grade\n";
			}

			if (error) {
				JOptionPane.showMessageDialog(this, errorMessage, "", JOptionPane.ERROR_MESSAGE);
			} else {
				if (isIBCourse.isSelected()) {
					isIB = true;
				} else {
					isIB = false;
				}
				DataBase.createCourse(name, teacher, grade, isIB);

				JOptionPane.showMessageDialog(this, "Course Added.", "", JOptionPane.INFORMATION_MESSAGE);
				new HomePage();
				f.dispose();
			}
		}

	}
}
