/**
 * This class stores information for all "Course" objects
 *
 */
public class Course {
	
		public static int numCourse; //total amount of courses in the database
		private int totalEnrolledStudents;
		private String name;
		private String teacher;
		private String gradeLevel;
		private boolean isIBCourse;
		private Student students [];
		private double marks[];
		private double average;

		/**
		 * @param name Name of the Course
		 * @param teacher Teacher teaching the course
		 * @param gradeLevel The Grade Level of the Course
		 * @param isIBCourse if the Course is an IB Course or not
		 */
		public Course(String name, String teacher, String gradeLevel, boolean isIBCourse) {
			
			totalEnrolledStudents = 0;
			this.name = name;
			this.teacher = teacher;
			this.gradeLevel = gradeLevel;
			this.isIBCourse = isIBCourse;
			//max number of students per course is 22, but array can 
			//store 30 elements to prevent array index out of bounds error as a contingency
			students = new Student [30];
			marks = new double [30];
			numCourse++;
		}
		
		//setters and getters
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getTeacher() {
			return teacher;
		}

		public void setTeacher(String teacher) {
			this.teacher = teacher;
		}

		public String getGradeLevel() {
			return gradeLevel;
		}

		public void setGradeLevel(String gradeLevel) {
			this.gradeLevel = gradeLevel;
		}

		public boolean isIBCourse() {
			return isIBCourse;
		}

		public void setIBCourse(boolean isIBCourse) {
			this.isIBCourse = isIBCourse;
		}

		public Student[] getStudents() {
			return students;
		}

		public void setStudents(Student[] students) {
			this.students = students;
		}

		public double getAverage() {
			return average;
		}

		public void setAverage() {
			average = Double.parseDouble(calculateAverage()); 
		}

		public int getTotalEnrolledStudents() {
			return totalEnrolledStudents;
		}

		public void setTotalEnrolledStudents(int totalEnrolledStudents) {
			this.totalEnrolledStudents = totalEnrolledStudents;
		}

		public double[] getMarks() {
			return marks;
		}
		
		//overloading of the method "setMarks"
		public void setMarks(double marks []) { //this method is used when updating marks
			this.marks = marks;
		}
		
		public void setMarks() {
			//this method is used when initializing marks
			for (int i = 0; i < totalEnrolledStudents; i++) {
				for (int q = 0; q < students[i].getNumOfCourses(); q ++) {//serach all students enrolled
					if (students[i].getCourses()[q].getName().equals(name)) {
						marks[i] = Double.parseDouble(students[i].getMarks()[q]);
						break;
					}
				}
			}
		}
		/**
		 * Calculates the Course mean
		 * @return returning the Course mean
		 */
		public String calculateAverage() {
			
			double sum = 0;
			double average;

				for (int i = 0; i < totalEnrolledStudents; i++) {
				//search through the courses of each student
					for (int q = 0; q < students[i].getNumOfCourses(); q++) {
						if ((students[i].getCourses()[q]).getName().equals(name)) {
						//only add to sum if student is enrolled in current instance of course
							sum += Double.parseDouble(students[i].getMarks()[q]);
						}
					}

				}
			average = Math.round(((double)sum/(double)totalEnrolledStudents)*100)/100.0;//round to 2 decimal points
			return Double.toString(average);
			
		}
		
		public String toString () {
			String c = "";
			for (int i = 0 ; i < totalEnrolledStudents; i ++) {
				c += students[i].getName()+"\n";
			}
			return "The Course "+name +" is a grade "+gradeLevel+" course, and is taught by "+teacher+". IB Course? "+isIBCourse+"\nThe students enrolled in this course are:\n"+c;
		}
		
		/**
		 * @param courses  Takes the courses of a student instance 
		 * @param s  the student instance
		 */
		public void addStudent(Course courses [], Student s) { 
			
			for (int q = 0; q < s.getNumOfCourses(); q++) {
					if (name.equals(courses[q].getName())) { //search through the course and add the student into the course if the name matches
						students[totalEnrolledStudents] = s; //add the student in the last position
						totalEnrolledStudents++; //update the amount of students enrolled
						break;
					}
			}
		}
}
