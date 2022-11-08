import java.io.*;
import java.util.Scanner;

/**
 * Class where more general methods and methods that apply to all students/courses are located
 * The two main linked lists of "studentList" and "courseList" are stored here
 */
public class DataBase { 
	
	public static boolean isRecovered = false; //if data is already recovered
	public static LinkedList studentList  = new LinkedList();//main list for students
	public static LinkedList courseList = new LinkedList();//main list for courses
	private static Scanner studentScanner; //to scan students.txt
	private static Scanner courseScanner; //to scan courses.txt
	private static String courseTXT[] = new String [100]; //to store mean and standard deviation of courses
	
	/**
	 * method to create a student
	 * @param name Name of student
	 * @param grade Grade of student
	 * @param studentNo Student Number of student
	 * @param isMale If the student is male
	 * @param IBStudent If the student is in IB
	 * @param courses The courses the Student is enrolled in
	 * @param counter The amount of courses the student is enrolled in
	 */
	public static void createStudent (String name, String grade, String studentNo, boolean isMale, boolean IBStudent, Course courses [], int counter){

		Student s;
		if (IBStudent) {
			s = new IBStudent (name, grade, studentNo, isMale, IBStudent, courses, counter); 
			//use polymorphism if student is in IB
		}else { 
			s = new Student (name, grade, studentNo, isMale, IBStudent, courses, counter);
		}
		studentList.insert(0, s);//add to linkedlist
		enroll(courses, s);
	}
	
	/**
	 * This method calls methods in the Course class to update 
	 * the array of Students in every course that the student is enrolled in
	 * @param courses Courses the student is enrolled in
	 * @param s The student instance
	 */
	public static void enroll(Course courses [], Student s){
		
		for (int i = 0; i < courseList.size(); i ++) {
			((Course)courseList.lookUpCourse(i)).addStudent(courses, s);
		}
	}
	
	/**
	 * Method used to create a new course
	 * @param name Name of the course
	 * @param teacher Teacher of the course
	 * @param level Grade Level of the course
	 * @param IB if The Course is an IB course
	 */
	public static void createCourse(String name, String teacher, String level, boolean IB) { 
		
		Course c;
		if (IB) {
			c = new IBCourse (name, teacher, level, IB);
			//use polymorphism if course is an IB course
		}else {
			c = new Course (name, teacher, level, IB);
		}
		courseList.insert(0, c);
	}
	

	
	/**
	 * method to delete a student
	 * @param s Student to be deleted
	 */
	public static void deleteStudent(Student s) {
		
		//deleting a student will affect the courses that the student is enrolled in
		Student tempStudent []; //temporary arrays to set after a student is deleted
		double tempMarks [];
		double tempIBMarks [];
		for (int i = 0; i < s.getNumOfCourses(); i++) {
			tempStudent = new Student [30];
			tempMarks = new double [30];
			tempIBMarks = new double [30];
			int len = ((Course)s.getCourses()[i]).getTotalEnrolledStudents();
			System.out.println(len);
			for (int q = 0; q < len; q++) {
				//find the position of the deleted student
				if (((Course)s.getCourses()[i]).getStudents()[q].getName().equals(s.getName())) { 
					int idx = q; //set index equal to the position
					for (int m = 0; m < idx; m++) { //store unchanged data into new arrays until the index is reached
						tempStudent [m] = s.getCourses()[i].getStudents()[m]; 
						tempMarks [m] = s.getCourses()[i].getMarks()[m];
						if (s.getCourses()[i].isIBCourse()) {
							tempIBMarks [m] = ((IBCourse)s.getCourses()[i]).getIBMarks()[m];
						}
					}
					for (int m = idx+1; m < len; m++) { //skip the index of the deleted student
						tempStudent [m-1] = s.getCourses()[i].getStudents()[m];
						tempMarks [m-1] = s.getCourses()[i].getMarks()[m];
						if (s.getCourses()[i].isIBCourse()) { //if student is in ib, update IB marks too
							tempIBMarks [m-1] = ((IBCourse)s.getCourses()[i]).getIBMarks()[m];
						}
					}
					break;
				}
			}
			//update all varaibles, for example, course average, course enrollement, course standard deviaiton, etc,
			s.getCourses()[i].setStudents(tempStudent);
			s.getCourses()[i].setMarks(tempMarks);
			if (s.getCourses()[i].isIBCourse()) {
				((IBCourse)s.getCourses()[i]).setIBMarks(tempIBMarks);
			}
			s.getCourses()[i].setTotalEnrolledStudents(len-1);
			s.getCourses()[i].setAverage();
			if (s.getCourses()[i].isIBCourse()) {
				((IBCourse)s.getCourses()[i]).setStandardDev();
				((IBCourse)s.getCourses()[i]).setCalcAverage();
			}
		}
		
		for (int i = 0; i < studentList.size(); i++) { //finally, delete the student from the list
			if (s.getName().equals(((Student)studentList.lookUpStudent(i)).getName())) {
				studentList.delete(i);
				break;
			}
		}
		if (s.isIBStudent()) {
			IBStudent.numIbStudent --;
		}
		Student.numStudent --;
		
	}
	
	/**
	 * method to delete a course
	 * @param c Course to be deleted
	 */
	public static void deleteCourse(Course c) { 
		
		//deleting a course will affect the students who are enrolled in the course
		Course tempCourse []; //temporary arrays
		String tempMarks[];
		String tempIBMarks[];
		for (int i = 0; i < c.getTotalEnrolledStudents(); i++) {
			int len = c.getStudents()[i].getNumOfCourses();
			tempCourse = new Course [len-1];
			tempMarks = new String [len-1];
			tempIBMarks = new String [len-1];
			for (int q = 0; q < len; q++) {
				if (c.getStudents()[i].getCourses()[q].getName().equals(c.getName())) {
					int idx = q; //find index of deleted course
					for (int m = 0; m < idx; m++) {
						//store the new marks of the students in new arrays
						tempCourse[m] = c.getStudents()[i].getCourses()[m]; 
						tempMarks[m] = c.getStudents()[i].getMarks()[m];
						if (c.getStudents()[i].isIBStudent()) {
							tempIBMarks[m] = ((IBStudent)c.getStudents()[i]).getIBMarks()[m]; 
							//cast to IBStudent if student is in IB
						}
					}
					for (int m = idx+1; m < len; m++) { //skip index of deleted course
						tempCourse[m-1] = c.getStudents()[i].getCourses()[m]; 
						tempMarks[m-1] = c.getStudents()[i].getMarks()[m];
						if (c.getStudents()[i].isIBStudent()) {
							tempIBMarks[m-1] = ((IBStudent)c.getStudents()[i]).getIBMarks()[m];
						}
					}
				}
			}
			//update student information for example: average, number of courses enrolled, etc.
			c.getStudents()[i].setCourses(tempCourse);
			c.getStudents()[i].setMarks(tempMarks);
			if (c.getStudents()[i].isIBStudent()) {
				((IBStudent)c.getStudents()[i]).setIBMarks(tempIBMarks);
			}
			c.getStudents()[i].setNumOfCourses(len-1);
			c.getStudents()[i].setAverage();
			if (c.getStudents()[i].isIBStudent()) {
				((IBStudent)c.getStudents()[i]).setCalcAverage();
			}
			
		}
		
		for (int i = 0; i < courseList.size(); i++) { //fianlly, delete the course
			if (c.getName().equals(((Course)courseList.lookUpCourse(i)).getName())) {
				courseList.delete(i);
				break;
			}
		}
		if (c.isIBCourse()) {
			IBCourse.numIBCourse --;
		}
		Course.numCourse --;
	}

	/**
	 * method that sorts an array of strings in alphabetical order
	 * @param arr Array to be sorted
	 */
	public static void sortArray(String arr []) { 
		for (int i = 0; i < arr.length-1; i++) {
			int min = i;
			for (int q = i+1; q < arr.length; q++) {
				if (arr[q].compareTo(arr[min]) < 0) {
					min = q;
				}
			}
			String temp = arr[i];
			arr[i] = arr[min];
			arr[min] = temp;
		}
	}
	

	/**
	 * Next 2 methods are methods that were used in testing stages of the program
	 */
	public static void displayStudentList (){ 
       for (int i = 0 ; i < studentList.size () ; i++){
          System.out.println ("Student " + (i+1) + ": " + studentList.lookUpStudent (i).toString());
       }
    
    }
	public static void displayCourseList (){
	       for (int i = 0 ; i < courseList.size () ; i++){
	          System.out.println ("Course " + (i+1) + ": " + courseList.lookUpCourse (i).toString());
	       }
	 }
	
	/**
	 * Method to recover the data stored in "students.txt" and "courses.txt"
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static void recoverData() throws IOException, FileNotFoundException {
		
		isRecovered = true;
		studentScanner = new Scanner (new File("students.txt"));
		courseScanner = new Scanner (new File("courses.txt"));
		
		int m = 0;
		while(courseScanner.hasNextLine()) {
			//format of storing a course in the course file:
			//name, teacher, grade, is IB course?
			//all inputs must be separated by a comma only
			String line = courseScanner.nextLine();
			//store each line temporarily into a string
			String data [] = line.split(",");
			//store each string separated by a comma in "line" into an array
			boolean ibCourse = false;
			if (data[3].equals("IB")) { //data[3] corresponds to if the student is in IB
				ibCourse = true;
			}
			createCourse (data[0], data[1], data[2], ibCourse); //must create the courses first
			
			if (data.length == 6) {
				//if the course has a given mean and standard deviation
				courseTXT [m] = data[4] +" "+data[5]; 
			}
			m++;
			
		}
		
		while (studentScanner.hasNextLine()) {
			//format of storing a student in the student file:
			//name, grade, student number, gender, IB student?,
			//x amount of courses, x amount of marks (corresponding to each course in order) 
			//all inputs must be separated by a comma only
			boolean isMale = false;
			boolean ibStudent = false;
			String tempCourses [] = new String [6];
			String marks[] = new String [6];
			Course courses[] = new Course [6];
			
			
			String line = studentScanner.nextLine();
			String data[] = line.split(",");
			if (data[3].equalsIgnoreCase("male")) {
				isMale = true;
			}
			if (data[4].equalsIgnoreCase("IB")) {
				ibStudent = true;
			}
			
			for (int i = 0; i < (data.length-5)/2; i++) {
				//reading the names of the courses
				tempCourses[i] = data[i+5];
			}
			for (int i = 0; i < (data.length-5)/2; i++) {
				//reading the marks of each course
				marks[i] = data[i+5+(data.length-5)/2];
			}
			
			for (int i = 0; i < tempCourses.length; i++) {
				if (tempCourses[i] != null) {
					for (int q = 0; q < courseList.size(); q++) {
						if ((((Course)courseList.lookUpCourse(q)).getName()).equalsIgnoreCase(tempCourses[i])){
							courses[i] = (Course)courseList.lookUpCourse(q); //cast to course
							//creating the array of Course objects by comparing with the tempcourses array
						}
					}
				}else {
					break;
				}
			}
			
			int counter = 0; 
			for (int i = 0; i < courses.length; i++) {
				if (courses[i] != null) {
					counter++; //find the amount of courses that the student has enrolled in
				}
			}
			
			createStudent (data[0], data[1], data[2], isMale, ibStudent, courses, counter);
			//update all student information after reading all input from the text file
			((Student)studentList.lookUpStudent(0)).setMarks(marks);
			((Student)studentList.lookUpStudent(0)).setAverage();
			if(((Student)studentList.lookUpStudent(0)).isIBStudent()) {
				//at first, IB marks and raw marks are the same (because there is no adjustment yet)
				((IBStudent)studentList.lookUpStudent(0)).setIBMarks();
				((IBStudent)studentList.lookUpStudent(0)).setIBAverage();
			}
		}
		
		//update all course information after recieving student data
		for (int i = 0; i <courseList.size(); i++) {
			((Course)courseList.lookUpCourse(i)).setAverage();
			((Course)courseList.lookUpCourse(i)).setMarks();
			if (((Course)courseList.lookUpCourse(i)).isIBCourse()){
				((IBCourse)courseList.lookUpCourse(i)).setIBMarks();
				((IBCourse)courseList.lookUpCourse(i)).setStandardDev();
				((IBCourse)courseList.lookUpCourse(i)).setMean();
			}
		}
		
		//if the course text file has a given standard deviation and mean, update course data accordingly
		for (int i = 0; i < courseList.size(); i++) {
			if (courseTXT[courseList.size()-i-1] != null) {
				//use [courseList.size()-i-1] because the system read the courses in reverse order
				int idx = courseTXT[courseList.size()-i-1].indexOf(" ");
				double standardDev = Double.parseDouble(courseTXT[courseList.size()-i-1].substring(0,idx));
				double mean = Double.parseDouble(courseTXT[courseList.size()-i-1].substring(idx+1));
				((IBCourse)courseList.lookUpCourse(i)).adjustMarks(mean, standardDev);
				((IBCourse)courseList.lookUpCourse(i)).findAndAdjust();
			}
		}
	}
}
