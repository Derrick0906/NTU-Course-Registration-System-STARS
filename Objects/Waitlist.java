/**Represents the list of students that are waiting for a vacancy in an index group
 * @author Ang Poh Keong
 * @version 1.0
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
import java.util.Queue;

import Interface.FileIOInterface;

public class Waitlist implements Serializable, FileIOInterface {
	
	/**
	* The ID to serialize data
	*/
	private static final long serialVersionUID = 1L;
	
	/**
    * Integer containing ID of waitlist
    */
	private Integer waitListID;
	 
	/**
    * Integer containing ID of the index group
    */
	private Integer indexGroupNumber;
	
	/**
    * Queue for a index group waitlist
    */
	private Queue<String> studentQueueList;
	
    /**
	* Creates a new Waitlist with no attribute values
	*/
	public Waitlist() {
	}
	
	/**
    * Constructor for initializing waitList,indexGroupNumber and studentQueueList
    * @param waitListID ID of waitlist
    * @param indexGroupNumber ID of index group
    * @param studentQueueList Queue for index group waitlist
    */
	public Waitlist(Integer waitListID, Integer indexGroupNumber, Queue<String> studentQueueList) {
		this.waitListID = waitListID;
		this.indexGroupNumber = indexGroupNumber;
		this.studentQueueList = studentQueueList;
	}
	
    /**
     * Check if waitlist exists
     * @param waitListId ID of waitlist
     * @return Return results for checking if waitlist exists
     */
	public boolean checkWaitListExists(int waitListId) {
	 	boolean exists = false;
		List<Waitlist> list = new ArrayList<Waitlist>();
		
        try {
			list = retrieveAllWaitListObjects();
            
			for (int i = 0; i < list.size(); i++) {
				Waitlist retrievedList = list.get(i);
				
				if (retrievedList.getWaitListID().equals(waitListId)) {
					exists = true;
					break;
				}
			}
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        return exists;
    }
	
	 /**
     * Retrieve waitlist object based on index group ID
     * @param indexGroupID ID of index group
     * @return Return waitlist object
     */
	@SuppressWarnings("unchecked")
	public Waitlist retrieveWaitListObjectByIndexGroup(Integer indexGroupID) {

		List<Waitlist> list = new ArrayList<Waitlist>();
		IndexGroup g1 = new IndexGroup();
		Waitlist tempList = null;

		if (g1.checkIndexGroupExists(indexGroupID) == true) {
			list = readSerializedObject();

			for (int i = 0; i < list.size(); i++) {
				Waitlist retrievedList = list.get(i);
				if (retrievedList.getIndexGroupNumber().equals(indexGroupID)) {
					tempList = retrievedList;
					break;
				}
			}
		} else {
			System.out.println("The index group does not exist.\n");
		}
		return tempList;
	}
	
	 /**
     * Retrieve waitlist object using waitlist ID
     * @param waitListId ID of waitlist
     * @return Waitlist object
     */	
	@SuppressWarnings("unchecked")
	public Waitlist retrieveWaitListObject(Integer waitListId) {
		List<Waitlist> list = new ArrayList<Waitlist>();
		Waitlist tempList = null;

		if (checkWaitListExists(waitListId) == true) {
			list = readSerializedObject();

			for (int i = 0; i < list.size(); i++) {
				Waitlist retrievedList = list.get(i);
				if (retrievedList.getWaitListID().equals(waitListId)) {
					tempList = retrievedList;
					break;
				}
			}
		} else {
			System.out.println("IndexGrop Does Not Exist!!");
		}
		return tempList;
	}
	
    /**
     * Retrieve all waitlist objects
     * @return Return List containing all waitlist objects
     */
	@SuppressWarnings("unchecked")
	public List<Waitlist> retrieveAllWaitListObjects() {
		List<Waitlist> listOfWaitlists = new ArrayList<Waitlist>();

		try{
			listOfWaitlists = readSerializedObject();

			if(listOfWaitlists == null){
				System.out.println("Unable to read from file!");
				throw new Exception();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return listOfWaitlists;
	}
	
	 /**
     * Enqueue a student to a waitlist
     * @param studentid ID of student
     * @return Return waitlist ID
     */
	public Integer enqueueToWaitList(String studentid) {

		List<Waitlist> list = new ArrayList<Waitlist>();
		list = retrieveAllWaitListObjects();

		try {
			for (int i = 0; i < list.size(); i++) {
				Waitlist w1 = list.get(i);

				if (indexGroupNumber.equals(w1.getIndexGroupNumber())) {
					studentQueueList.add(studentid);
					w1.setStudentQueueList(studentQueueList);
					list.set(i, w1);
					writeSerializedObject(list);
				}
			}
		} catch (Exception e) {
			System.out.println("Exception >> " + e.getMessage());
		}
		return waitListID;
	}
	
	 /**
     * Dequeue student from a waitlist
     * @param studentid ID of student
     * @return Return waitlist ID
     */
	public Integer dequeueFromWaitList(String studentid) {

		List<Waitlist> list = new ArrayList<Waitlist>();
		list = retrieveAllWaitListObjects();

		try {
			for (int i = 0; i < list.size(); i++) {
				Waitlist w1 = list.get(i);

				if (indexGroupNumber.equals(w1.getIndexGroupNumber())) {
					studentQueueList.remove(studentid);
					w1.setStudentQueueList(studentQueueList);
					list.set(i, w1);
					writeSerializedObject(list);
				}
			}
		} catch (Exception e) {
			System.out.println("Exception >> " + e.getMessage());
		}
		return waitListID;
	}
	
    /**
     * Update waitlist object to the database
     * @param updatedWaitList Waitlist object
     * @return Results of updating waitlist 
     */
	public boolean updateWaitListObject(Waitlist updatedWaitList) {

		boolean success = false;

		List<Waitlist> list = new ArrayList<Waitlist>();
		list = retrieveAllWaitListObjects();

		try {
			for (int i = 0; i < list.size(); i++) {
				Waitlist retrievedList = list.get(i);

				if (retrievedList.getIndexGroupNumber().equals(updatedWaitList.getIndexGroupNumber())) {
					list.set(i, updatedWaitList);
					success = true;
					writeSerializedObject(list);
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("Exception >> " + e.getMessage());
		}
		return success;
	}
	
    /**
     * Get waitlist ID
     * @return return watlist ID
     */
    public Integer getWaitListID() {
        return waitListID;
    }
 
    /**
     * Set waitlist ID
     * @param waitListID ID of waitlist
     */
    public void setWaitListID(Integer waitListID) {
        this.waitListID = waitListID;
    }
 
    /**
     * Get index group ID
     * @return index group ID
     */
    public Integer getIndexGroupNumber() {
        return indexGroupNumber;
    }
 
    /**
     * Set index group ID
     * @param indexGroupNumber ID of index group
     */
    public void setIndexGroupNumber(Integer indexGroupNumber) {
        this.indexGroupNumber = indexGroupNumber;
    }
 
    /**
     * Get queue for waitlist
     * @return return queue for waitlist
     */
    public Queue<String> getStudentQueueList() {
        return studentQueueList;
    }
 
    /**
     * Set queue for waitlist
     * @param studQueueList Queue for waitlist
     */
    public void setStudentQueueList(Queue<String> studQueueList) {
        this.studentQueueList = studQueueList;
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
			fis = new FileInputStream(System.getProperty("user.dir") + "\\Databases\\waitlists.dat");
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
			fos = new FileOutputStream(System.getProperty("user.dir") + "\\Databases\\waitlists.dat");
			out = new ObjectOutputStream(fos);
			out.writeObject(list);
			out.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}

