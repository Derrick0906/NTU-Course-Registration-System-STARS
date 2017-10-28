/**
 * Represents a entity class for the admin
 * An admin can login to manage courses and students
 * @author Lim Boon Leng
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

import Interface.FileIOInterface;
import Interface.LoginInterface;

public class Admin implements Serializable, LoginInterface, FileIOInterface{
	/**
	 * The ID to serialize data
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The username of the admin.
	 */
	private String adminID;
	/**
	 * The hash of the admin's password.
	 */
	private String passwordHash;

	/**
	 * Creates a new Admin with no attribute values
	 */
	public Admin() {
	}
	
	/**
	 * Creates a new Admin with the given attribute values
	 * @param adminID This Admin's adminID
	 * @param passwordHash This Admin's passwordHash
	 */
	public Admin(String adminID, String passwordHash) {
		this.adminID = adminID;
		this.passwordHash = passwordHash;
	}
	
	/**
	* Admin login method
	* Compares adminID and passwordHash with database data
	* @param adminID This Admin's adminID
	* @param passwordHash This Admin's passwordHash
	* @return login success failure boolean.
	*/
	@SuppressWarnings("unchecked")
	public boolean login(String adminID, String passwordHash) {
		boolean success = false;
		List<Admin> adminUsersList = new ArrayList<Admin>();

		try {
			adminUsersList = readSerializedObject();
			
			for(int i = 0; i < adminUsersList.size(); i++) {
				Admin admin1 = (Admin)adminUsersList.get(i);
								
				if (admin1 != null) {
					if (admin1.getAdminID().equals(adminID)) {
						if (admin1.getPasswordHash().equals(passwordHash)) {
							success = true;
						}
					} 
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}
	
	/**
	* Gets the adminID of this Student.
	* @return this Admin's adminID.
	*/
	public String getAdminID() {
		return adminID;
	}
	
	/**
	* Changes the adminID of this admin.
	* @param adminID This Admin's new adminID.
	* Default get/setter, Not used in actual implementation of program
	*/
	public void setAdminID(String adminID) {
		this.adminID = adminID;
	}
	
	/**
	* Gets the passwordHash of this Student.
	* @return this Admin's passwordHash.
	*/
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	* Changes the passwordHash of this admin.
	* @param passwordHash This Admin's new passwordHash.
	* Default get/setter, Not used in actual implementation of program
	*/
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
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
			fis = new FileInputStream(System.getProperty("user.dir") + "\\Databases\\admins.dat");
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
			fos = new FileOutputStream(System.getProperty("user.dir") + "\\Databases\\admins.dat");
			out = new ObjectOutputStream(fos);
			out.writeObject(list);
			out.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
