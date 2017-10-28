/**
 * Represents a course.
 * @author Heng Ze Hao
 * @version 1.4
	@since 2017-04-06
 */

package Objects;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Interface.FileIOInterface;

public class Course implements Serializable, FileIOInterface{
	/**
	 * The ID to serialize data
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The ID of this course.
	 */	
	private String courseID;
	
	/**
	 * The name of this course.
	 */
	private String courseName;
	
	/**
	 * The AU credits of this course.
	 */
	private Integer AUCredits;
	
	/**
	 * The School of this course.
	 */
	private String school;
	
	/**
	 * The type of this course.
	 */
	private String courseType;
	
	/**
	 * The ID of the index groups of this course.
	 */
	private ArrayList<Integer> indexGroupList;

	/**
	 * Creates a new Course with no attribute values
	 */
	public Course() {
		
	}
	
	/**
	 * Creates a new Course with the given ID, name, number of AU Credits, and Waitlist of the index group.
	 * @param courseID This Course's ID.
	 * @param courseName This Course's name.
	 * @param AUCredits This Course's number of AU Credits.
	 * @param school This Course's school.
	 * @param courseType This Course's type.
	 * @param indexGroupList This Course's Array List of Index Groups.
	 */
	public Course(String courseID, String courseName, Integer AUCredits, String school, String courseType, ArrayList<Integer> indexGroupList) {
		this.courseID = courseID;
		this.courseName = courseName;
		this.AUCredits = AUCredits;
		this.school = school;
		this.courseType = courseType;
		this.indexGroupList = indexGroupList;
	}

	/**
	 * Gets the ID of the Course.
	 * @return this Course's ID.
	 */
	public String getCourseID(){
		return this.courseID;
	}

	/**
	 * Changes the ID of this course.
	 * @param courseID This Course's ID.
	 */
	public void setCourseID(String courseID){
		this.courseID= courseID;
	}	

	/**
	 * Gets the name of the Course.
	 * @return this Course's name.
	 */
	public String getCourseName() {
		return courseName;
	}

	/**
	 * Changes the name of this course.
	 * @param courseName This Course's name.
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	/**
	 * Gets the number of AU Credits of the Course.
	 * @return this Course's number of AU Credits.
	 */
	public int getAUCredits(){
		return this.AUCredits;
	}

	/**
	 * Changes the number of AU Credits of this course.
	 * @param AUCredits This Course's number of AU Credits.
	 */
	public void setAUCredits(int AUCredits){
		this.AUCredits= AUCredits;
	}

	/**
	 * Gets the school of the Course.
	 * @return this Course's school.
	 */
	public String getSchool(){
		return this.school;
	}

	/**
	 * Changes the school of this course.
	 * @param school This Course's school.
	 */
	public void setSchool(String school){
		this.school= school;
	}

	/**
	 * Gets the type of the Course.
	 * @return this Course's type.
	 */
	public String getCourseType(){
		return this.courseType;
	}

	/**
	 * Changes the Array List of IndexGroups' IDs of this course.
	 * @param courseType This Course's type.
	 */
	public void setCourseType(String courseType){
		this.courseType= courseType;
	}

	/**
	 * Gets the Array List of IndexGroups' IDs of this course.
	 * @return this Course's Array List of IndexGroups' IDs.
	 */
	public ArrayList<Integer> getIndexGroupList(){
		return this.indexGroupList;
	}
	
	/**
	 * Changes the Array List of IndexGroups' IDs of this course.
	 * @param indexGroupList This Course's Array List of IndexGroups' IDs.
	 */
	public void setIndexGroupList(ArrayList<Integer> indexGroupList){
		this.indexGroupList= indexGroupList;
	}
	
	/**
	 * Prints this Course's IndexGroups' IDs.
	 */
	public void printIndexList() {
		System.out.print("Groups of course: ");
		for(int i = 0; i<indexGroupList.size(); i++)
		{
			System.out.print(indexGroupList.get(i));
			if (i != indexGroupList.size() - 1) {
				System.out.print(", ");
			}
		}
	}

	/**
	 * Prints this Course's details.
	 */
	public void printCourseDetails() {
		System.out.println("ID of course: " + this.courseID);
		System.out.println("Name of course: " + this.courseName);
		System.out.println("AU Credits for course: " + this.AUCredits);
		System.out.println("Type of course: " + this.courseType);
		System.out.println("School of course: " + this.school);
		printIndexList();	
		System.out.println("\n\n");
	}

	/**
	 * Prints all Courses in the text file that stores all Courses.
	 */
	public static void printAllCourses() {
		Course course = new Course();
		System.out.println("List of courses: ");
		List<Course> courseList = course.retrieveAllCourseObjects();
		for (int i = 0; i < courseList.size(); i++) {
			courseList.get(i).printCourseDetails();
		}
	}
	
	/**
	 * Retrieves all Courses in the text file that stores all Courses.
	 * @return List of Courses
	 */
	@SuppressWarnings("unchecked")
	public List<Course> retrieveAllCourseObjects(){
		List<Course> listOfCourses = new ArrayList<Course>();
		
		try{
			listOfCourses = readSerializedObject();
			
			if(listOfCourses == null){
				System.out.println("Unable to read from file!");
				throw new Exception();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return listOfCourses;
	}

	/**
	 * Retrieves a Course in the text file that stores all Courses, based on the given ID of a Course.
	 * @param courseID The ID of Course to retrieve.
	 * @return specified Course.
	 */
	public Course retrieveCourseObject(String courseID){
		Course returnedCourse = null;
		Course course = new Course();
		
		try{		
			List<Course> listOfCourses = course.retrieveAllCourseObjects();
			
			for (int i = 0; i < listOfCourses.size(); i++){
				
				if(listOfCourses.get(i).getCourseID().equals(courseID))
				{
					returnedCourse = (Course) listOfCourses.get(i);
					break;
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return returnedCourse;
	}

	/**
	 * Retrieves a Course in the text file that stores all Courses, based on the given ID of an IndexGroup.
	 * @param indexGroupID The ID of IndexGroup to retrieve.
	 * @return specified Course.
	 */
	public Course retrieveCourseObjectByIndexGroup(Integer indexGroupID){
		List<Course> listOfCourses = new ArrayList<Course>();
		List<Integer> currentListOfIndexGroupToCheck= new ArrayList<Integer>();
		Course returnedCourse = null;
		Course course = new Course();
		
		try{
			listOfCourses = course.retrieveAllCourseObjects();
			
			for(int i = 0; i < listOfCourses.size(); i++){
				
				currentListOfIndexGroupToCheck = listOfCourses.get(i).getIndexGroupList();
				
				for(int j = 0; j < currentListOfIndexGroupToCheck.size(); j++){
					
					if(currentListOfIndexGroupToCheck.get(j).equals(indexGroupID)){
						returnedCourse = listOfCourses.get(i);
						break;
					}
				}
				
				if(returnedCourse != null){
					break;
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return returnedCourse;
	}	
	
	/**
	 * Retrieves a Course in the text file that stores all Courses, based on the given ID of a Waitlist.
	 * @param waitListID The ID of Waitlist to retrieve.
	 * @return specified Course.
	 */
	public Course retrieveCourseObjectByWaitList(Integer waitListID){
		Course returnedCourse = null;
		ArrayList<Integer> indexGroupsToCheck = new ArrayList<Integer>();
		Course course = new Course();
		IndexGroup indexGroup = new IndexGroup();

		try{
			List<Course> listOfCourses = course.retrieveAllCourseObjects();
			IndexGroup indexGroupObjectOfWaitlist = indexGroup.retrieveIndexGroupObjectByWaitList(waitListID);
		 
			for(int i = 0; i < listOfCourses.size(); i++){
				indexGroupsToCheck = listOfCourses.get(i).getIndexGroupList();
				for(int j = 0; j < indexGroupsToCheck.size(); j++){
					if(indexGroupsToCheck.get(j).equals(indexGroupObjectOfWaitlist.getIndexGroupID())){
						returnedCourse = listOfCourses.get(i);
						break;
					}
				}
				if(returnedCourse != null){
					break;
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return returnedCourse;
	}
	
	/**
	 * Prints out this Course's details.
	 */
	public void printCourseDetailsConfirmation()
	{
		System.out.println(this.getCourseName());
		System.out.println(this.getCourseID());
		System.out.println(this.getCourseType());
	}
	
	/**
	 * Updates a Course in the text file that stores all Courses.
	 * @param courseToUpdate The Course object to update.
	 */
	public void updateCourseObject(Course courseToUpdate){
		Course course = new Course();
		
		try{
			List<Course> listOfCourses = course.retrieveAllCourseObjects();
			
			for (int i = 0; i < listOfCourses.size(); i++){
				if(listOfCourses.get(i).getCourseID().equals(courseToUpdate.getCourseID()))
				{
					listOfCourses.set(i, courseToUpdate);
					break;
				}
			}
			
			writeSerializedObject(listOfCourses);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	* Reads the data from the database file
	* @return list of objects in the database file.
	*/
	@SuppressWarnings("rawtypes")
	public List readSerializedObject() {
		List pDetails = null;
		FileInputStream fis = null;
		ObjectInputStream in = null;
		try {
			fis = new FileInputStream(System.getProperty("user.dir") + "\\Databases\\courses.dat");
			in = new ObjectInputStream(fis);
			pDetails = (ArrayList)in.readObject();
			in.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		return pDetails;
	}

	/**
	* Writes data to the database file
	* @param list The list of objects to write
	*/
	@SuppressWarnings("rawtypes")
	public void writeSerializedObject(List list) {
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try {
			fos = new FileOutputStream(System.getProperty("user.dir") + "\\Databases\\courses.dat");
			out = new ObjectOutputStream(fos);
			out.writeObject(list);
			out.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}