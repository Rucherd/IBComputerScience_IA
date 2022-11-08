/**
 * Child class of "Student" class
 * Stores information of all IB Student Objects
 */
public class IBStudent extends Student{
	
	private String IBMarks[];
	public static int numIbStudent = 0;
	private double IBaverage;

	/**
	 * @param name Name of IB Student
	 * @param grade Grade of IB Student
	 * @param studentNo Student Number of IB Student
	 * @param isMale If the IB Student is male
	 * @param IBStudent If the student is in IB
	 * @param courses Courses the IB Student is enrolled in
	 * @param numOfCourses Number of courses the IB Student is enrolled in
	 */
	public IBStudent(String name, String grade, String studentNo, boolean isMale, boolean IBStudent, Course courses[], int numOfCourses) {
		
		super(name, grade, studentNo, isMale, IBStudent, courses, numOfCourses);
		//calling constructor of "Student" class
		numIbStudent ++;

		IBMarks = new String [6];
		setIBMarks();
		setIBAverage();
	}

	
	/**
	 * Calculates and sets the IB mean of the IB student
	 */
	public void setCalcAverage() {
		
		double sum = 0;

		for (int i = 0; i < super.getNumOfCourses(); i++) {
			sum += Double.parseDouble(IBMarks[i]);
		}
		IBaverage = Math.round((sum/(super.getNumOfCourses()*1.0))*100.0)/100.0; 
		//to 2 decimal places
	}
	
	//setter and getter methods
	public String[] getIBMarks() {
		return IBMarks;
	}
	
	//overloading of the "setIBMarks" method
	public void setIBMarks(String IBMarks[]) {//updates all IB Marks
		for (int i = 0; i < IBMarks.length; i++) {
			this.IBMarks [i] = IBMarks [i];
		}
	}
	
	public void setIBMarks(String mark, int i) {//updates a specific IB mark
		IBMarks[i] = mark;
	}

	public void setIBMarks() { //sets the IB marks equal to raw marks 
		for (int i = 0; i < super.getNumOfCourses(); i++) {
				IBMarks[i] = super.getMarks()[i];
		}
	}

	public double getIBaverage() {
		return IBaverage;
	}

	public void setIBAverage(){
		IBaverage = super.getAverage();
	}
}
