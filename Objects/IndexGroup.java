/**
 * Represents an index group in a course.
 * A index group can be in 1 course only.
 * A course consists of 1 or more index groups.
 * @author Heng Ze Hao
 * @version 1.4
 * @since 2017-04-06
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

public class IndexGroup implements Serializable, FileIOInterface{
	/**
	 * The ID to serialize data
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The ID of this index group.
	 */
	private Integer indexGroupID;
	
	/**
	 * The current number of students registered into this index group.
	 */
	private Integer currentNumOfStudents;
	
	/**
	 * The maximum number of students that can be 
	 * registered into this index group.
	 */
	private Integer maxNumOfStudents;
	
	/**
	 * The name of this index group.
	 */
	private String indexGroupName;
	
	/**
	 * The lessons that this index group has.
	 */
	private ArrayList<Lesson> lessonList;
	
	/**
	 * The waitlist of this index group.
	 */
	private Waitlist waitList;

	/**
	 * Creates a new IndexGroup with no attribute values
	 */
	public IndexGroup(){

	}
	
	/**
	 * Creates a new IndexGroup with the given ID, current number of students,
	 * maximum number of students, name, ArrayList of Lessons, and Waitlist of the index group.
	 * @param indexGroupID This IndexGroup's ID.
	 * @param currentNumOfStudents This IndexGroup's current number of students registered.
	 * @param maxNumOfStudents This IndexGroup's maximum number of students that can be registered.
	 * @param indexGroupName This IndexGroup's name.
	 * @param lessonList This IndexGroup's ArrayList of Lessons.
	 * @param waitlist This IndexGroup's Waitlist.
	 */
	public IndexGroup(Integer indexGroupID, Integer currentNumOfStudents, Integer maxNumOfStudents, String indexGroupName, ArrayList<Lesson> lessonList, Waitlist waitlist){
		this.indexGroupID = indexGroupID;
		this.currentNumOfStudents = currentNumOfStudents;
		this.maxNumOfStudents = maxNumOfStudents;
		this.indexGroupName = indexGroupName;
		this.lessonList = lessonList;
		this.waitList = waitlist;
	}

	/**
	 * Calculate the vacancy for this IndexGroup.
	 * @return this IndexGroup's vacancy.
	 */	public Integer calculateVacancy() {
		return maxNumOfStudents - currentNumOfStudents;
	}


	/**
	* Retrieves an IndexGroup in the text file that stores all IndexGroups.
	* @return a List of IndexGroups
	*/	@SuppressWarnings("unchecked")
	public List<IndexGroup> retrieveAllIndexGroupObjects(){
		List<IndexGroup> listOfIndexGroups = new ArrayList<IndexGroup>();

		try{
			listOfIndexGroups = readSerializedObject();

			if(listOfIndexGroups == null){
				System.out.println("Unable to read from file!");
				throw new Exception();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listOfIndexGroups;
	}

	/**
	 * Retrieves an IndexGroup in the text file that stores all IndexGroups, based on the given ID of a IndexGroup
	 * @param indexGroupID The ID of IndexGroup to retrieve.
	 * @return specified IndexGroup
	 */
	public IndexGroup retrieveIndexGroupObject(Integer indexGroupID){
		IndexGroup indexGroupToCheck = null;
		IndexGroup indexGroup = new IndexGroup();

		try{
			List<IndexGroup> listOfIndexGroups = indexGroup.retrieveAllIndexGroupObjects();

			for (int i = 0; i < listOfIndexGroups.size(); i++){
				if(listOfIndexGroups.get(i).getIndexGroupID().equals(indexGroupID))
				{
					indexGroupToCheck = listOfIndexGroups.get(i);
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return indexGroupToCheck;
	}
	
	/**
	 * Retrieves an IndexGroup in the text file that stores all IndexGroups, based on the given ID of a Waitlist
	 * @param waitListID The ID of Waitlist to retrieve.
	 * @return specified IndexGroup
	 */
	public IndexGroup retrieveIndexGroupObjectByWaitList(Integer waitListID){
		List<Waitlist> listOfWaitlists = new ArrayList<Waitlist>();
		List<IndexGroup> listOfIndexGroups = new ArrayList<IndexGroup>();
		int indexGroupNumberToFind= -1;
		IndexGroup returnedIndexGroup= null;
		IndexGroup indexGroup = new IndexGroup();
		Waitlist waitlist = new Waitlist();
		
		try{

			listOfWaitlists = waitlist.retrieveAllWaitListObjects();
			listOfIndexGroups = indexGroup.retrieveAllIndexGroupObjects();
			
			for(int i = 0; i < listOfWaitlists.size(); i++){
				if(listOfWaitlists.get(i).getWaitListID().equals(waitListID)){
					indexGroupNumberToFind = listOfWaitlists.get(i).getIndexGroupNumber();
					
					for(int j = 0; j < listOfIndexGroups.size(); j++){
						if(listOfIndexGroups.get(j).getIndexGroupID().equals(indexGroupNumberToFind)){
							returnedIndexGroup = listOfIndexGroups.get(j);					
						}
						else if(j == listOfIndexGroups.size() + 1){					
							System.out.println("Waitlist found, but no such IndexGroupID found in IndexGroupID database for the Waitlist");
						}
					}
				}
				else if(i == listOfWaitlists.size() + 1){					
					System.out.println("Waitlist not found in Waitlist database");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnedIndexGroup;
	}
	
	/**
	 * Checks if IndexGroup with the given ID exists in the text file that stores all IndexGroups.
	 * @param indexGroupID The ID of IndexGroup to check.
	 * @return boolean (true / false)
	 */
	public boolean checkIndexGroupExists(int indexGroupID) {
		boolean exists = false;
		IndexGroup indexGroupToCheck = null;
		IndexGroup indexGroup = new IndexGroup();
		
		try {
			indexGroupToCheck = indexGroup.retrieveIndexGroupObject(indexGroupID);
			if(indexGroupToCheck != null)
				exists = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return exists;
	}

	/**
	 * Checks if there are vacancies available in this IndexGroup
	 * by calculating the difference of the current number of students
	 * registered and the maximum number of students that can be registered.
	 * @return boolean(true / false)
	 */
	public boolean checkVacancies(){
		boolean vacancyExists = false;

		if(this.getMaxNumOfStudents() - this.getCurrentNumOfStudents() > 0){
			vacancyExists = true;
		}
		return vacancyExists;
	}

	/**
	 * Update vacancies based on the given operator by incrementing or
	 * decrementing the current number of students registered in this IndexGroup.
	 * @param operator The operation to update the vacancy. 
	 * + to increment vacancy, - to decrement vacancy.
	 */
	public void updateVacancies(char operator){
		try{
			if(operator == '+'){
				if(this.getMaxNumOfStudents() >= this.getCurrentNumOfStudents())
					this.setCurrentNumOfStudents(this.getCurrentNumOfStudents() - 1);
				else{
					System.out.println("No more vacancy!");
					throw new Exception();
				}
			}
			else if(operator == '-'){
				if (this.getCurrentNumOfStudents() >= 0)
					this.setCurrentNumOfStudents(this.getCurrentNumOfStudents() + 1);
				else{
					System.out.println("Current number of students is already 0! Is the student registered in this indexGroup?");
					throw new Exception();
				}
			}
			else{
				System.out.println("Operator parameter invalid!");
				throw new Exception();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Updates an IndexGroup in the text file that stores all IndexGroups.
	 * @param indexGroupToUpdate The IndexGroup object to update.
	 */
	public void updateIndexGroupObject(IndexGroup indexGroupToUpdate){
		IndexGroup indexGroup = new IndexGroup();
		
		try{
			List<IndexGroup> listOfIndexGroups = indexGroup.retrieveAllIndexGroupObjects();
			
			for (int i = 0; i < listOfIndexGroups.size(); i++){
				if(listOfIndexGroups.get(i).getIndexGroupID().equals(indexGroupToUpdate.getIndexGroupID()))
				{
					listOfIndexGroups.set(i, indexGroupToUpdate);
					break;
				}
			}
			
			writeSerializedObject(listOfIndexGroups);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Prints out this IndexGroup's details
	 * @return boolean (true / false)
	 */
	public boolean printGroupDetailsConfirmation() {

		boolean success = false;
		IndexGroup g1 = new IndexGroup();
		Lesson l1 = new Lesson();
		ArrayList<Lesson> a1 = new ArrayList<Lesson>();

		g1 = g1.retrieveIndexGroupObject(getIndexGroupID());
		a1 = g1.getLessonList();

		System.out.println("==================================================================================");
		System.out.println("|  Class Type   |  Group  |     Day     |      Time      |   Venue   |   Remark  |");
		System.out.println("==================================================================================");

		for (int i = 0; i < a1.size(); i++) {

			success = true;

			l1 = a1.get(i);
			
			String remark = "-";
			String lessonDay = "-";
			String startTime = "-";
			String endTime = "-";
			String lessonVenue = "-";

			if (l1.getRemark() != null) {
				remark = l1.getRemark();
			}
			
			if (l1.getLessonDay() != null) {
				lessonDay = l1.getLessonDay();
			}
			
			if (l1.getStartTime() != null) {
				startTime = l1.getStartTime();
			}
			
			if (l1.getEndTime() != null) {
				endTime = l1.getEndTime();
			}
			
			if (l1.getLessonVenue() != null) {
				lessonVenue = l1.getLessonVenue();
			}
			
			System.out.format("| %-14s| %-8s| %-12s| %-15s| %-10s| %-10s|\n",l1.getLessonType(),g1.getIndexGroupID(),lessonDay,startTime + " - " + endTime,lessonVenue,remark);	
		}

		System.out.println("==================================================================================");

		return success;
	}
	
	/**
	 * Gets the ID of this IndexGroup.
	 * @return this IndexGroup's indexGroupID.
	 */
	public Integer getIndexGroupID(){
		return this.indexGroupID;
	}

	/**
	 * Changes the ID of this IndexGroup.
	 * @param indexGroupID This IndexGroup's ID.
	 */
	public void setIndexGroupID(Integer indexGroupID){
		this.indexGroupID= indexGroupID;
	}	
	
	/**
	 * Gets the current number of students registered into this IndexGroup.
	 * @return this IndexGroup's currentNumOfStudents.
	 */
	public Integer getCurrentNumOfStudents(){
		return this.currentNumOfStudents;
	}

	/**
	 * Changes the current number of students registered into this IndexGroup.
	 * @param currentNumOfStudents This IndexGroup's current number of students registered.
	 */
	public void setCurrentNumOfStudents(Integer currentNumOfStudents){
		this.currentNumOfStudents= currentNumOfStudents;
	}

	/**
	 * Gets the maximum number of students registered into this IndexGroup.
	 * @return this IndexGroup's maxNumOfStudents.
	 */
	public Integer getMaxNumOfStudents(){
		return this.maxNumOfStudents;
	}

	/**
	 * Changes the maximum number of students that can be registered into this IndexGroup.
	 * @param maxNumOfStudents This IndexGroup's maximum number of students that can be registered.
	 */
	public void setMaxNumOfStudents(Integer maxNumOfStudents){
		this.maxNumOfStudents= maxNumOfStudents;
	}

	/**
	 * Gets the name of this IndexGroup.
	 * @return this IndexGroup's indexGroupName.
	 */
	public String getIndexGroupName(){
		return this.indexGroupName;
	}

	/**
	 * Changes the name of this IndexGroup.
	 * @param indexGroupName This IndexGroup's name.
	 */
	public void setIndexGroupName(String indexGroupName){
		this.indexGroupName= indexGroupName;
	}

	/**
	 * Gets the ArrayList of Lessons that this IndexGroup has.
	 * @return this IndexGroup's lessonList.
	 */
	public ArrayList<Lesson> getLessonList(){
		return this.lessonList;
	}

	/**
	 * Changes the ArrayList of Lessons that this IndexGroup has.
	 * @param lessonList This IndexGroup's ArrayList of Lessons.
	 */
	public void setLessonList(ArrayList<Lesson> lessonList){
		this.lessonList= lessonList;
	}

	/**
	 * Gets the Waitlist of this IndexGroup.
	 * @return this IndexGroup's Waitlist.
	 */
	public Waitlist getWaitList() {
		return waitList;
	}

	/**
	 * Changes the Waitlist of this IndexGroup.
	 * @param waitList This IndexGroup's Waitlist.
	 */
	public void setWaitList(Waitlist waitList) {
		this.waitList = waitList;
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
			fis = new FileInputStream(System.getProperty("user.dir") + "\\Databases\\indexGrps.dat");
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
			fos = new FileOutputStream(System.getProperty("user.dir") + "\\Databases\\indexGrps.dat");
			out = new ObjectOutputStream(fos);
			out.writeObject(list);
			out.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}