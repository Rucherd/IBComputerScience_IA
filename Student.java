/**
 * This class stores information for all "Student" Objects
 *
 */
public class Student {
	
	public static int numStudent;
	private String name;
	private String grade;
	private String studentNo;
	private boolean isMale;
	private String marks [];
	private Course courses [];
	private String gender;
	private boolean IBStudent;
	private double average;
	private int numOfCourses;

	/**
	 * constructor for Student objects
	 * @param name Name of student
	 * @param grade Grade of student
	 * @param studentNo Student number of student
	 * @param isMale If the student is male
	 * @param IBStudent If the student is in IB
	 * @param courses The courses the student is enrolled in
	 * @param numOfCourses The number of courses the student is enrolled in
	 */
	public Student (String name, String grade, String studentNo, boolean isMale, boolean IBStudent, Course courses[], int numOfCourses) {
		
		this.name = name;
		this.grade = grade;
		this.studentNo = studentNo;
		this.isMale = isMale;
		this.IBStudent = IBStudent;
		this.courses = courses;
		this.numOfCourses = numOfCourses;
		marks = new String [6];
		if (isMale) {
			gender = "male";
		}else {
			gender = "female";
		}
		numStudent ++;
	}
	
	//setter and getter methods
	
	public boolean isIBStudent() {
		return IBStudent;
	}

	public double getAverage() {
		return average;
	}

	public void setAverage() {
		 average = Double.parseDouble(calculateAverage());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getStudentNo() {
		return studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	public boolean isMale() {
		return isMale;
	}

	public void setMale(boolean isMale) {
		this.isMale = isMale;
	}

	public String[] getMarks() {
		return marks;
	}

	public void setMarks(String marks[]) {
		this.marks = marks;
	}

	public Course[] getCourses() {
		return courses;
	}

	public void setCourses(Course[] courses) {
		this.courses = courses;
	}
	
	public int getNumOfCourses() {
		return numOfCourses;
	}

	public void setNumOfCourses(int numOfCourses) {
		this.numOfCourses = numOfCourses;
	}
	
	/**
	 * calculates the student average 
	 * @return returns the student's average of all their courses
	 */
	public String calculateAverage() {
		
		double sum = 0;
		double average;

		for (int i = 0; i < numOfCourses; i++) {
			sum += Double.parseDouble(marks[i]);
		}
		
		average = Math.round(((double)sum/(double)numOfCourses)*100)/100.0;//round to 2 decimal places
		return Double.toString(average);
		
	}
	
	/**
	 * ToString method
	 */
	public String toString() {
		
		String c = "";
		for (int i = 0 ; i < numOfCourses; i ++) {
			c += courses[i].getName()+": "+marks[i]+"%\n";
		}
		return name + " is in grade "+ grade+", has a student number of "+ studentNo +" and is "+ gender+". IB Student?: "+IBStudent+ "\nEnrolled in these Courses:\n"+c;
	}
	

}
