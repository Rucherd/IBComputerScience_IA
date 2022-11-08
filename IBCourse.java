/**
 * Class that is a child class of the "Course" parent class using inheritance
 * Stores information for all IBCourse Objects
 */
public class IBCourse extends Course{
	
	public static int numIBCourse;//Total number of IB courses 
	
	private double standardDev;
	private double newMean;
	private double IBMarks[];
	private double mean;
	private boolean isAdjusted;

	/**
	 * @param name Name of IB Course
	 * @param teacher Teacher of IB Course
	 * @param gradeLevel Grade Level of IB Course
	 * @param isIBCourse If the course is an IB Course
	 */
	public IBCourse(String name, String teacher, String gradeLevel, boolean isIBCourse) {
		super(name, teacher, gradeLevel, isIBCourse);
		//calls constructor of the "Course" class
		numIBCourse ++;
		IBMarks = new double [30];
		isAdjusted = false;
	}
	/**
	 * Method that adjusts the marks of all students in a course according to the 
	 * new standard deviation and mean
	 * @param newMean New mean 
	 * @param newStdDev New Standard Deviation
	 */
	public void adjustMarks(double newMean, double newStdDev) {
		
		isAdjusted = true;
		for (int i = 0; i < super.getTotalEnrolledStudents(); i++) {
			//using the formula to change a set X of numbers of mean M1 and standard deviation S1 
			//to a set Y of numbers with mean M2 and standard deviation S2
			//Yi = M2 + (Xi - M1) * S2/S1
			double tempIBMark = Math.round((newMean + (super.getMarks()[i] - super.getAverage()) * (newStdDev/calculateRawSD()))*100.0)/100.0;
			if (tempIBMark > 100.0) {
				//if the scaled marks are greater than 100, set them to 100
				IBMarks [i] = 100.0;
			}else if (tempIBMark < 0.0){
				//if the scaled marks are less than 0, set them to 0
				IBMarks [i] = 0;
			}else {
				IBMarks [i] = tempIBMark;
			}
		}
		//update the standard deviation and mean for the course
		//note: This is the desired mean that the user has entered, it may not be the true mean
		//because students initially with a scaled mark of over 100 are rounded down to 100
		//However, the mean is kept as the user's desired mean to not create any confusion and
		//to keep things consistent
		standardDev = newStdDev;
		mean = newMean;

		//update the student's IB marks as well
		for (int i = 0; i < super.getTotalEnrolledStudents(); i++) {
			for (int q = 0; q < super.getStudents()[i].getNumOfCourses(); q ++) {
				if (super.getStudents()[i].getCourses()[q].getName().equals(super.getName())) {
					((IBStudent)super.getStudents()[i]).getIBMarks()[q] = Double.toString(IBMarks [i]);
					((IBStudent)super.getStudents()[i]).setCalcAverage();
					break;
				}

			}

		}
		
	}
	
	//setters and getter methods
	public double getStandardDev() {
		return standardDev;
	}

	public void setStandardDev() {
		standardDev = calculateSD();
	}

	public double getNewMean() {
		return newMean;
	}

	public void setNewMean(double newMean) {
		this.newMean = newMean;
	}
	public double[] getIBMarks() {
		return IBMarks;
	}
	
	//use of overloading 
	public void setIBMarks(double IBMark) { //updates the last mark
		IBMarks[super.getTotalEnrolledStudents()-1] = IBMark;
	}
	
	public void setIBMarks(double IBMark, int i) {//updates a specific mark
		IBMarks[i] = IBMark;
	}
	
	public void setIBMarks(double IBMarks []) {//updates the entire array of marks
		this.IBMarks = IBMarks;
	}
	public void setIBMarks() {
		for (int i = 0; i < super.getTotalEnrolledStudents(); i++) {
			//sets the IB marks equal to the raw mark when the course is created
			IBMarks [i] = super.getMarks()[i];
		}
	}
	
	public double getMean() {
		return mean;
	}

	public void setMean() {
		mean = super.getAverage();
	}
	
	public boolean isAdjusted() {
		return isAdjusted;
	}
	public void setAdjusted(boolean isAdjusted) {
		this.isAdjusted = isAdjusted;
	}
	
	/**
	 * Method to calculate the current standard deviation
	 * @return returns the current Standard Deviation
	 */
	public double calculateSD() {
		
		double mean = super.getAverage();
		double sum = 0;
		//applying the standard deviation formula
		for (int i = 0; i < super.getTotalEnrolledStudents(); i++) {
			sum += Math.pow((IBMarks[i]-mean), 2);
		}
		return Math.round((Math.sqrt((sum/(super.getTotalEnrolledStudents()*1.0))))*100.0)/100.0;
	}
	
	/**
	 * Calculates the standard deviation from the course's raw marks
	 * @return returns the "raw" standard deviation
	 */
	public double calculateRawSD() {
		
		double mean = super.getAverage();
		double sum = 0;
		for (int i = 0; i < super.getTotalEnrolledStudents(); i++) {
			sum += Math.pow((super.getMarks()[i]-mean), 2);
		}
		return Math.round((Math.sqrt((sum/(super.getTotalEnrolledStudents()*1.0))))*100.0)/100.0;
	}
	
	/**
	 * Calculates and sets the course's IB average
	 */
	public void setCalcAverage() {
		
		double sum = 0;
		for (int i = 0; i < super.getTotalEnrolledStudents(); i++) {
			sum += IBMarks[i];
		}
		mean = Math.round((sum/super.getTotalEnrolledStudents())*100.0)/100.0;
	}
	
	/**
	 * Finds every student in the current IB Course instance and updates their IB Marks accordingly 
	 */
	public void findAndAdjust() {
		
		for (int i = 0; i < super.getTotalEnrolledStudents(); i++) {
			for (int q = 0; q < super.getStudents()[i].getNumOfCourses(); q ++) {

				if (super.getStudents()[i].getCourses()[q].getName().equals(super.getName())) {
				//if the courses match
					if (isAdjusted) {
						//use the formula (see above)
						double temp = Math.round((mean + (Double.parseDouble(super.getStudents()[i].getMarks()[q])*1.0 - super.getAverage()) * (standardDev/calculateRawSD()))*100.0)/100.0;
						if (temp > 100) {
							((IBStudent)super.getStudents()[i]).setIBMarks("100",q);
						}else {
							((IBStudent)super.getStudents()[i]).setIBMarks(Double.toString(temp),q);
						}
						((IBStudent)super.getStudents()[i]).setCalcAverage();

					}else {
						//if IB Course's marks are unajusted, raw marks equals IB marks
						((IBStudent)super.getStudents()[i]).setIBMarks(super.getStudents()[i].getMarks()[q],q);
						((IBStudent)super.getStudents()[i]).setCalcAverage();
					}
					break;
				}
			}
		}
	}
	public void setStandardDev(double standardDev) {
		this.standardDev = standardDev;
	}
	public void setMean(double mean) {
		this.mean = mean;
	}

	
}
