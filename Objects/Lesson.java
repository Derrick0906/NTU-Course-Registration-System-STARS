/**
 * Represents a lesson in a index group of a course.
 * A lesson can be in 1 index group only.
 * A index group consists of 1 or more lesson.
 * @author Heng Ze Hao
 * @version 1.4
	@since 2017-04-06
 */

package Objects;

import java.io.Serializable;

public class Lesson implements Serializable{	
	/**
	 * The ID to serialize data
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The name of the staff that conducts this lesson
	 */
	private String staffName;
	
	/**
	 * The day of this lesson (Monday - Saturday)
	 */
	private String lessonDay;
	
	/**
	 * The starting time of this lesson
	 */
	private String startTime;
	
	/**
	 * The ending time of this lesson
	 */
	private String endTime;
	
	/**
	 * The venue of this lesson
	 */
	private String lessonVenue;
	
	/**
	 * The type of this lesson (ONLINE MOD / LAB / TUTORIAL / LECTURE)
	 */
	private String lessonType;
	
	/**
	 * The remarks for this lesson
	 */
	private String remark; //ONLY HAVE TO COMPARE STRINGS OF REMARKS TO KNOW IF LESSONS CLASHES (ODD / EVEN)

	/**
	 * Creates a new Lesson with no attribute values
	 */
	public Lesson(){
		
	}
	
	/**
	 * Creates a new Lesson with the given name of the staff, day,starting time,
	 * ending time, venue, type, and remarks for the lesson.
	 * @param staffName This Lesson's staff's name.
	 * @param lessonDay This Lesson's day.
	 * @param startTime This Lesson's starting time.
	 * @param endTime This Lesson's ending time.
	 * @param lessonVenue This Lesson's venue.
	 * @param lessonType This Lesson's type.
	 * @param remark This Lesson's remarks.
	 */
	public Lesson(String staffName, String lessonDay, String startTime, String endTime, String lessonVenue, String lessonType, String remark){
		this.staffName = staffName;
		this.lessonDay = lessonDay;
		this.startTime = startTime;
		this.endTime = endTime;
		this.lessonVenue = lessonVenue;
		this.lessonType = lessonType;
		this.remark = remark;
	}
	
	/**
	 * Prints the details of this lesson
	 */
	public void printLessonDetails() {
		System.out.println("Lesson staff: " + this.getStaffName());
		System.out.println("Lesson day: " + this.getLessonDay());
		System.out.println("Lesson start time: " + this.getStartTime());
		System.out.println("Lesson end time: " + this.getEndTime());
		System.out.println("Lesson venue: " + this.getLessonVenue());
		System.out.println("Lesson remarks: " + this.getRemark());
	}
		
	/**
	 * Converts the day of this lesson from String to integer.
	 * @param lessonDay The Lesson's day.
	 * @return the day of this lesson from String as an integer.
	 */
	public static int convertLessonDayToInt(String lessonDay){
		int day = 0;
		
		 switch (lessonDay) {
         case "Monday":
             day = 1;
             break;
         case "Tuesday":
        	 day = 2;
             break;
         case "Wednesday":
        	 day = 3;
             break;
         case "Thursday":
        	 day = 4;
             break;
         case "Friday":
        	 day = 5;
             break;
         case "Saturday":
        	 day = 6;
             break;
	     }
	     return day;
	}
	
	/**
	 * Gets the name of the staff that conducts this lesson.
	 * @return this Lesson's staffName
	 */
	public String getStaffName(){
		return this.staffName;
	}

	/**
	 * Changes the name of the staff that conducts this lesson.
	 * @param staffName This Lesson's staff's name.
	 */
	public void setStaffName(String staffName){
		this.staffName = staffName;
	}

	/**
	 * Gets the day of this lesson.
	 * @return this Lesson's lessonDay.
	 */
	public String getLessonDay(){
		return this.lessonDay;
	}

	/**
	 * Changes the day of this lesson.
	 * @param lessonDay This Lesson's day.
	 */
	public void setLessonDay(String lessonDay){
		this.lessonDay = lessonDay;
	}

	/**
	 * Gets the starting time of this lesson.
	 * @return this Lesson's startTime.
	 */
	public String getStartTime(){
		return this.startTime;
	}

	/**
	 * Changes the starting time of this lesson.
	 * @param startTime This Lesson's starting time.
	 */
	public void setStartTime(String startTime){
		this.startTime = startTime;
	}

	/**
	 * Gets the ending time of this lesson.
	 * @return this Lesson's endTime.
	 */
	public String getEndTime(){
		return this.endTime;
	}

	/**
	 * Changes the ending time of this lesson.
	 * @param endTime This Lesson's ending time.
	 */
	public void setEndDate(String endTime){
		this.endTime = endTime;
	}

	/**
	 * Gets the venue of this lesson.
	 * @return this Lesson's lessonVenue.
	 */
	public String getLessonVenue(){
		return this.lessonVenue;
	}

	/**
	 * Changes the venue of this lesson.
	 * @param lessonVenue This Lesson's venue.
	 */
	public void setLessonVenue(String lessonVenue){
		this.lessonVenue = lessonVenue;
	}

	/**
	 * Gets the type of this lesson.
	 * @return this Lesson's lessonType.
	 */
	public String getLessonType(){
		return this.lessonType;
	}
	
	/**
	 * Changes the type of this lesson.
	 * @param lessonType This Lesson's type.
	 */
	public void setLessonType(String lessonType){
		this.lessonType = lessonType;
	}

	/**
	 * Gets the remarks for this lesson.
	 * @return this Lesson's remark.
	 */
	public String getRemark() {
		return remark;
	}
	
	/**
	 * Changes the remark for this lesson.
	 * @param remark This Lesson's remarks.
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
}