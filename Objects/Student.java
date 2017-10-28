package Objects;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import Control.NotificationController;
import Interface.FileIOInterface;
import Interface.LoginInterface;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * This is a Student class which manages Student object.
 * 
 * @author Derrick
 * @version 1.2
 * @since 2017-04-12
 */
public class Student implements Serializable, LoginInterface, FileIOInterface {
	/**
	 * The version ID of Serializable.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The identification number of this Student.
	 */
	private String studentID;

	/**
	 * The hashed password of this Student's account.
	 */
	private String passwordHash;

	/**
	 * The name of this Student.
	 */
	private String name;

	/**
	 * The matriculation number of this Student.
	 */
	private String matricNum;

	/**
	 * The nationality of this Student.
	 */
	private String nationality;

	/**
	 * The gender of this Student.
	 */
	private char gender;

	/**
	 * Contains list of department of schools this Student is enrolled in.
	 */
	private ArrayList<String> school;

	/**
	 * Contains list of courses this Student is registered in.
	 */
	private ArrayList<String> courseList;

	/**
	 * Contains list of indexes of courses this Student is registered in.
	 */
	private ArrayList<Integer> indexGroupList;

	/**
	 * Contains list of courses this Student is exempted in.
	 */
	private ArrayList<String> exemptionCoursesList;

	/**
	 * Contains list of courses this Student has already completed.
	 */
	private ArrayList<String> completedCoursesList;

	/**
	 * Contains list of wait list ID this Student is registered in.
	 */
	private ArrayList<Integer> waitListIDList;

	/**
	 * This Student's schedule.
	 */
	private Schedule schedule;

	/**
	 * Start time where this Student can access the system.
	 */
	private Calendar startTime;

	/**
	 * End time where this Student can access the system.
	 */
	private Calendar endTime;

	/**
	 * Email address of this Student.
	 */
	private String email;

	/**
	 * Mobile number of this Student.
	 */
	private String mobileNum;

	/**
	 * Mode of notification for this Student.
	 */
	private String notifMode;

	/**
	 * Default Constructor
	 */
	public Student() {

	}

	/**
	 * Creates a new Student given the student ID.
	 * 
	 * @param studentID
	 *            This Student's identification number..
	 */
	public Student(String studentID) {
		this.studentID = studentID;
	}

	/**
	 * Creates a new Student given all information of the new student. Used when
	 * administrator adds a new student into the system.
	 * 
	 * @param studentID
	 *            This Student's ID.
	 * @param passwordHash
	 *            This Student's hashed password.
	 * @param name
	 *            This Student's name.
	 * @param matricNum
	 *            This Student's matriculation number.
	 * @param nationality
	 *            This Student's nationality.
	 * @param gender
	 *            This Student's gender.
	 * @param school
	 *            This Student's school(s) enrolled in.
	 * @param courseList
	 *            This Student's list of courses.
	 * @param indexGroupList
	 *            This Student's list of index groups,
	 * @param exemptionCoursesList
	 *            This Student's list of exempted courses.
	 * @param completedCoursesList
	 *            This Student's list of completed courses.
	 * @param waitListIDList
	 *            This Student's list of wait list ID.
	 * @param schedule
	 *            This Student's schedule.
	 * @param startTime
	 *            This Student's start of access time.
	 * @param endTime
	 *            This Student's end of access time.
	 * @param email
	 *            This Student's email address.
	 * @param mobileNum
	 *            This Student's mobile number.
	 * @param notifMode
	 *            This Student's mode of notification.
	 */
	public Student(String studentID, String passwordHash, String name, String matricNum, String nationality,
			char gender, ArrayList<String> school, ArrayList<String> courseList, ArrayList<Integer> indexGroupList,
			ArrayList<String> exemptionCoursesList, ArrayList<String> completedCoursesList,
			ArrayList<Integer> waitListIDList, Schedule schedule, Calendar startTime, Calendar endTime, String email,
			String mobileNum, String notifMode) {
		this.studentID = studentID;
		this.passwordHash = passwordHash;
		this.name = name;
		this.matricNum = matricNum;
		this.nationality = nationality;
		this.gender = gender;
		this.school = school;
		this.courseList = courseList;
		this.indexGroupList = indexGroupList;
		this.exemptionCoursesList = exemptionCoursesList;
		this.completedCoursesList = completedCoursesList;
		this.waitListIDList = waitListIDList;
		this.schedule = schedule;
		this.startTime = startTime;
		this.endTime = endTime;
		this.email = email;
		this.mobileNum = mobileNum;
		this.notifMode = notifMode;
	}

	/**
	 * Student log in interface for the system
	 * 
	 * @param studentID
	 *            This Student's identification number.
	 * @param passwordHash
	 *            This Student's hashed password.
	 * @return log in validation of this Student.
	 */
	@SuppressWarnings("unchecked")
	public boolean login(String studentID, String passwordHash) {
		boolean success = false;
		List<Student> studList = new ArrayList<Student>();

		try {
			studList = readSerializedObject();

			for (int i = 0; i < studList.size(); i++) {
				Student student1 = (Student) studList.get(i);

				if (student1.getStudentID().equals(studentID)) {
					if (student1.getPasswordHash().equals(passwordHash)) {
						success = true;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}

	/**
	 * Retrieves all Students in the system.
	 * 
	 * @return list containing all Students.
	 */
	@SuppressWarnings("unchecked")
	public List<Student> retrieveAllStudentObjects() {
		List<Student> listOfStudents = new ArrayList<Student>();

		try {
			listOfStudents = readSerializedObject();

			if (listOfStudents == null) {
				System.out.println("Unable to read from file!");
				throw new Exception();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listOfStudents;
	}

	/**
	 * Retrieves this Student given the student identification number.
	 * 
	 * @param studentID
	 *            This Student's identification number.
	 * @return this Student.
	 */
	public Student retrieveStudentObject(String studentID) {
		Student studentToRetrieve = null;
		Student student = new Student();

		try {
			List<Student> listOfStudents = student.retrieveAllStudentObjects();

			for (int i = 0; i < listOfStudents.size(); i++) {
				if (listOfStudents.get(i).getStudentID().equals(studentID)) {
					studentToRetrieve = listOfStudents.get(i);
					break;
				}
			}

			if (studentToRetrieve == null) {
				throw new Exception();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return studentToRetrieve;
	}

	/**
	 * Updates this Student's information
	 * 
	 * @param studentToUpdate
	 *            This Student
	 */
	public void updateStudentObject(Student studentToUpdate) {
		Student student = new Student();
		try {
			List<Student> listOfStudents = student.retrieveAllStudentObjects();

			for (int i = 0; i < listOfStudents.size(); i++) {
				if (listOfStudents.get(i).getStudentID().equals(studentToUpdate.getStudentID())) {
					listOfStudents.set(i, studentToUpdate);
					break;
				}
			}
			student.writeSerializedObject(listOfStudents);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Registers this Student into specified course given index group ID.
	 * 
	 * @param indexGroupID
	 *            This Student's index group ID he wants to register in.
	 * @param sc
	 *            Scanner for input.
	 * @return Success or failure to register course.
	 */
	public boolean registerCourse(Integer indexGroupID, Scanner sc) {
		boolean success = false;
		boolean validInput = false;
		int confirmation = 0;
		Student s1 = new Student();
		s1 = s1.retrieveStudentObject(this.getStudentID());
		Course c1 = new Course();
		c1 = c1.retrieveCourseObjectByIndexGroup(indexGroupID);
		Course c2 = new Course();
		IndexGroup g1 = new IndexGroup();
		g1 = g1.retrieveIndexGroupObject(indexGroupID);
		Waitlist wl1 = new Waitlist();
		wl1 = wl1.retrieveWaitListObjectByIndexGroup(indexGroupID);
		Waitlist wl2 = new Waitlist();

		try {
			if (s1 == null) {
				System.out.println("No user has been logged in.\n");
			} else {
				if (c1 != null) {
					int temp = s1.getCourseList().size();
					int temp2 = s1.getWaitListIDList().size();

					if (temp == 0) {
						if (schedule.checkScheduleClash(indexGroupID)) {
							System.out.println("Period of lesson clash with current schedule\n");
						} else {
							if (!(g1.checkVacancies())) {
								int flag = 0;
								for (int i = 0; i < temp2; i++) {
									c2 = c2.retrieveCourseObjectByWaitList(s1.getWaitListIDList().get(i));
									if (s1.getWaitListIDList().get(i).equals(wl1.getWaitListID())) {
										System.out.println("You are already in waitlist\n");
										flag = 1;
										break;
									} else if (c2.getCourseID().equals(c1.getCourseID())) {
										System.out.println(
												"You already in waitlist of another index group of the same course\n");
										flag = 1;
										break;
									}
								}
								if (flag == 0) {
									g1.printGroupDetailsConfirmation();
									System.out.print(
											"Confirm to join the waitlist of this group? 1 = Yes, Any other number = No: ");
									do {
										try {
											confirmation = sc.nextInt();
											if (confirmation >= 0) {
												validInput = true;
												sc.nextLine();
											}
										} catch (InputMismatchException e) {
											System.out.println("Enter a valid integer!");
											sc.nextLine();
										}
									} while (!validInput);
									validInput = false;

									if (confirmation == 1) {
										wl1.enqueueToWaitList(s1.getStudentID());
										s1.getWaitListIDList().add(wl1.getWaitListID());
										g1.printGroupDetailsConfirmation();
										wl1.updateWaitListObject(wl1);
										s1.updateStudentObject(s1);
										System.out.println("You have been added to the waitlist\n");
										success = true;
									} else {
										System.out.println("You are not added to the waitlist\n");
										System.out.println("Returning to main menu...\n");
									}
								}
							} else {
								System.out.println("Course: " + c1.getCourseID());
								g1.printGroupDetailsConfirmation();
								System.out.print(
										"Confirm to register course under this index group? 1 = Yes, Any other number = No: ");
								do {
									try {
										confirmation = sc.nextInt();
										if (confirmation >= 0) {
											validInput = true;
											sc.nextLine();
										}
									} catch (InputMismatchException e) {
										System.out.println("Enter a valid integer!");
										sc.nextLine();
									}
								} while (!validInput);
								validInput = false;

								if (confirmation == 1) {
									for (int i = 0; i < temp2; i++) {
										c2 = c2.retrieveCourseObjectByWaitList(s1.getWaitListIDList().get(i));
										if (c2.getCourseID().equals(c1.getCourseID())) {
											wl2 = wl2.retrieveWaitListObject(s1.getWaitListIDList().get(i));
											wl2.dequeueFromWaitList(s1.getStudentID());
											s1.getWaitListIDList().remove(i);
											wl2.updateWaitListObject(wl2);
										}
									}
									s1.getCourseList().add(c1.getCourseID());
									s1.getIndexGroupList().add(indexGroupID);
									System.out.println("Course: " + c1.getCourseID());
									g1.printGroupDetailsConfirmation();
									g1.updateVacancies('-');
									// Add schedule
									s1.getSchedule().AddSchedule(indexGroupID);
									g1.updateIndexGroupObject(g1);
									s1.updateStudentObject(s1);
									System.out.println("Course successfully registered!");
									success = true;
								} else {
									System.out.println("Course has not been registered.");
									System.out.println("Returning to main menu...");
								}
							}
						}
					} else {
						for (int j = 0; j < temp; j++) {
							if (s1.getCourseList().get(j).equals(c1.getCourseID())) {
								System.out
										.println("You are already registered in this course in another index group\n");
							} else if (j == (temp - 1)) {
								if (schedule.checkScheduleClash(indexGroupID)) {
									System.out.println("Period of lesson clash with current schedule\n");
								} else {
									if (!(g1.checkVacancies())) {
										int flag = 0;
										for (int i = 0; i < temp2; i++) {
											c2 = c2.retrieveCourseObjectByWaitList(s1.getWaitListIDList().get(i));
											if (s1.getWaitListIDList().get(i).equals(wl1.getWaitListID())) {
												System.out.println("You are already in waitlist\n");
												flag = 1;
												break;
											} else if (c2.getCourseID().equals(c1.getCourseID())) {
												System.out.println(
														"You are already in waitlist of another index group of the same course\n");
												flag = 1;
												break;
											}
										}
										if (flag == 0) {
											g1.printGroupDetailsConfirmation();
											System.out.print(
													"Confirm to join the waitlist of this group? 1 = Yes, Any other number = No: ");
											do {
												try {
													confirmation = sc.nextInt();
													if (confirmation >= 0) {
														validInput = true;
														sc.nextLine();
													}
												} catch (InputMismatchException e) {
													System.out.println("Enter a valid integer!");
													sc.nextLine();
												}
											} while (!validInput);
											validInput = false;

											if (confirmation == 1) {
												wl1.enqueueToWaitList(s1.getStudentID());
												s1.getWaitListIDList().add(wl1.getWaitListID());
												g1.printGroupDetailsConfirmation();
												System.out.println("You have been added to waitlist\n");
												wl1.updateWaitListObject(wl1);
												s1.updateStudentObject(s1);
												success = true;
											} else {
												System.out.println("You have not been added to waitlist\n");
												System.out.println("Returning to main menu...\n");
											}
										}
									} else {
										System.out.println("Course: " + c1.getCourseID());
										g1.printGroupDetailsConfirmation();
										System.out.print(
												"Confirm to register course under this index group? 1 = Yes, Any other number = No: ");
										do {
											try {
												confirmation = sc.nextInt();
												if (confirmation >= 0) {
													validInput = true;
													sc.nextLine();
												}
											} catch (InputMismatchException e) {
												System.out.println("Enter a valid integer!");
												sc.nextLine();
											}
										} while (!validInput);
										validInput = false;

										if (confirmation == 1) {
											for (int i = 0; i < temp2; i++) {
												c2 = c2.retrieveCourseObjectByWaitList(s1.getWaitListIDList().get(i));
												if (c2.getCourseID().equals(c1.getCourseID())) {
													wl2 = wl2.retrieveWaitListObject(s1.getWaitListIDList().get(i));
													wl2.dequeueFromWaitList(s1.getStudentID());
													s1.getWaitListIDList().remove(i);
													wl2.updateWaitListObject(wl2);
												}
											}
											s1.getCourseList().add(c1.getCourseID());
											s1.getIndexGroupList().add(indexGroupID);
											System.out.println("Course: " + c1.getCourseID());
											g1.printGroupDetailsConfirmation();
											g1.updateVacancies('-');
											s1.getSchedule().AddSchedule(indexGroupID);
											g1.updateIndexGroupObject(g1);
											s1.updateStudentObject(s1);
											System.out.println("Course successfully registered!");
											success = true;
										} else {
											System.out.println("Course has not been registered.");
											System.out.println("Returning to main menu...");
										}
									}
								}
							}
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
	 * Drops this Student from registered course.
	 * 
	 * @param sc
	 *            Scanner for input.
	 * @return Success or failure to drop course.
	 */
	public boolean dropCourse(Scanner sc) {
		boolean success = false;
		boolean validInput = false;
		int choice, confirmation = 0;
		Student s1 = new Student();
		s1 = s1.retrieveStudentObject(this.getStudentID());
		Student s2 = new Student();
		Course c1 = new Course();
		IndexGroup g1 = new IndexGroup();
		Waitlist wl1 = new Waitlist();

		try {
			if (s1 != null) {
				s1.printCoursesRegistered();

				if (s1.getCourseList().size() != 0 || s1.getWaitListIDList().size() != 0) {
					do {
						System.out.print("Select the Course you want to drop: ");
						choice = sc.nextInt();

						if (choice >= 1 && choice <= this.waitListIDList.size() && this.waitListIDList.size() != 0) {
							Integer tempWaitListID = this.waitListIDList.get(choice - 1);
							c1 = c1.retrieveCourseObjectByWaitList(tempWaitListID);
							g1 = g1.retrieveIndexGroupObjectByWaitList(tempWaitListID);

							System.out.println("Course: " + c1.getCourseID());
							g1.printGroupDetailsConfirmation();
							System.out.print("Confirm to remove from the waitlist? 1 = Yes, Any other number = No: ");
							do {
								try {
									confirmation = sc.nextInt();
									if (confirmation >= 0) {
										validInput = true;
										sc.nextLine();
									}
								} catch (InputMismatchException e) {
									System.out.println("Enter a valid integer!");
									sc.nextLine();
								}

							} while (!validInput);
							validInput = false;

							if (confirmation == 1) {
								wl1 = wl1.retrieveWaitListObject(tempWaitListID);
								wl1.dequeueFromWaitList(s1.getStudentID());
								s1.getWaitListIDList().remove(tempWaitListID);
								System.out.println("You have been removed from the waitlist.");
								wl1.updateWaitListObject(wl1);
								s1.updateStudentObject(s1);
								success = true;
							} else {
								System.out.println("Student was not removed from the waitlist.");
								System.out.println("Returning to main menu...");
							}
						} else if (choice > this.waitListIDList.size()
								&& choice <= (this.waitListIDList.size() + this.getIndexGroupList().size())) {
							Integer tempGroupID = this.getIndexGroupList().get(choice - this.waitListIDList.size() - 1);
							c1 = c1.retrieveCourseObjectByIndexGroup(tempGroupID);
							g1 = g1.retrieveIndexGroupObject(tempGroupID);

							System.out.println("Course: " + c1.getCourseID());
							g1.printGroupDetailsConfirmation();
							System.out.print("Confirm to drop the course? 1 = Yes, Any other number = No: ");
							do {
								try {
									confirmation = sc.nextInt();
									if (confirmation >= 0) {
										validInput = true;
										sc.nextLine();
									}
								} catch (InputMismatchException e) {
									System.out.println("Enter a valid integer!");
									sc.nextLine();
								}
							} while (!validInput);
							validInput = false;

							if (confirmation == 1) {
								s1.getCourseList().remove(c1.getCourseID());
								s1.getIndexGroupList().remove(tempGroupID);
								g1.updateVacancies('+');
								wl1 = wl1.retrieveWaitListObjectByIndexGroup(tempGroupID);
								String tempStudentID = wl1.getStudentQueueList().peek();
								if (tempStudentID != null) {
									s2 = s2.retrieveStudentObject(tempStudentID);
									wl1.dequeueFromWaitList(tempStudentID);
									s2.getCourseList().add(c1.getCourseID());
									s2.getIndexGroupList().add(tempGroupID);
									s2.getSchedule().AddSchedule(tempGroupID);

									for (int i = 0; i < s2.getWaitListIDList().size(); i++) {
										if (s2.getWaitListIDList().get(i).equals(wl1.getWaitListID())) {
											s2.getWaitListIDList().remove(i);
										}
									}
									g1.updateVacancies('-');

									wl1.updateWaitListObject(wl1);
									s2.updateStudentObject(s2);

									System.out.println("System message:");
									if (s2.getNotifMode().toLowerCase().equals("sms")) {
										System.out.println(s2.getName()
												+ " has been removed from waitlist and added to the index group "
												+ tempGroupID);
										NotificationController.sendSMS(s2.getMobileNum());
									} else if (s2.getNotifMode().toLowerCase().equals("email")) {
										System.out.println(s2.getName()
												+ " has been removed from waitlist and added to the index group "
												+ tempGroupID);
										NotificationController.sendEmail(s2.getEmail(), c1.getCourseID());
									} else if (s2.getNotifMode().toLowerCase().equals("both")) {
										System.out.println(s2.getName()
												+ " has been removed from waitlist and added to the index group "
												+ tempGroupID);
										NotificationController.sendSMS(s2.getMobileNum());
										NotificationController.sendEmail(s2.getEmail(), c1.getCourseID());
									}
								}
								s1.getSchedule().RemoveSchedule(tempGroupID);

								g1.updateIndexGroupObject(g1);
								s1.updateStudentObject(s1);
								System.out.println("Course has been dropped successfully.");
								success = true;
							} else {
								System.out.println("Course has not been dropped.");
								System.out.println("Returning to main menu...");
							}
						} else {
							System.out.print("Invalid choice.\n");
						}
					} while (choice < 0 || choice > (this.waitListIDList.size() + this.getIndexGroupList().size()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}

	/**
	 * Displays courses this Student is registered in.
	 */
	public void printCoursesRegistered() {
		Course c1 = new Course();
		Course c2 = new Course();

		for (int j = 0; j < this.waitListIDList.size(); j++) {
			c1 = c1.retrieveCourseObjectByWaitList(this.waitListIDList.get(j));
			IndexGroup ig1 = new IndexGroup();
			ig1 = ig1.retrieveIndexGroupObjectByWaitList(this.waitListIDList.get(j));
			System.out.print(j + 1 + ") " + ig1.getIndexGroupID() + " " + c1.getCourseID() + " On Waitlist" + '\n');
		}

		for (int k = 0; k < this.getIndexGroupList().size(); k++) {
			c2 = c2.retrieveCourseObjectByIndexGroup(this.getIndexGroupList().get(k));
			System.out.print((k + 1 + waitListIDList.size()) + ") " + this.getIndexGroupList().get(k) + " "
					+ c2.getCourseID() + " Registered" + '\n');
		}

		if (this.getCourseList().size() == 0 && this.getWaitListIDList().size() == 0) {
			System.out.println("You have no courses registered!\n");
		}
	}

	/**
	 * Swaps this Student's index with peer's index given both are in the same
	 * course.
	 * 
	 * @param peerUsername
	 *            Peer's username.
	 * @param peerPassword
	 *            Peer's password.
	 * @param ownIndexNumber
	 *            This Student's index number to swap.
	 * @param peerIndexNumber
	 *            Peer's index number to swap.
	 * @param sc
	 *            Scanner for input.
	 * @return Success or failure to swap index.
	 */
	public boolean swapIndex(String peerUsername, String peerPassword, Integer ownIndexNumber, Integer peerIndexNumber,
			Scanner sc) {
		boolean success = false;
		boolean validInput = false;
		int confirmation = 0;
		Student s1 = new Student();
		s1 = s1.retrieveStudentObject(this.getStudentID());
		Student s2 = new Student();
		s2 = s2.retrieveStudentObject(peerUsername);
		Course c1 = new Course();
		c1 = c1.retrieveCourseObjectByIndexGroup(ownIndexNumber);
		Course c2 = new Course();
		c2 = c2.retrieveCourseObjectByIndexGroup(peerIndexNumber);
		IndexGroup g1 = new IndexGroup();
		g1 = g1.retrieveIndexGroupObject(ownIndexNumber);
		IndexGroup g2 = new IndexGroup();
		g2 = g2.retrieveIndexGroupObject(peerIndexNumber);

		try {
			if (!(s1.checkCourseIndexRegistered(ownIndexNumber))) {
				System.out.println("You are not registered in this index group.");
			} else {
				if (!(s2.checkCourseIndexRegistered(peerIndexNumber))) {
					System.out.println("Peer is not registered in this index group.");
				} else {
					if (!(login(peerUsername, peerPassword))) {
						System.out.println("Peer has invalid Username/Password.");
					} else {
						if (!(c1.getCourseID().equals(c2.getCourseID()))) {
							System.out.println("User index group is of different course from peer index group.");
						} else {
							if (schedule.checkScheduleClashSameCourse(peerIndexNumber)) {
								System.out.println("Index group schedule clash with current schedule");
							} else if (s2.getSchedule().checkScheduleClashSameCourse(ownIndexNumber)) {
								System.out.println("Peer index group schedule clash with current schedule");
							} else {
								System.out.println(s1.getName());
								System.out.println("Course: " + c1.getCourseID());
								System.out.println("Student#1");
								System.out.println(s1.getMatricNum());
								g1.printGroupDetailsConfirmation();

								System.out.println(s2.getName());
								System.out.println("Course: " + c2.getCourseID());
								System.out.println("Student#2");
								System.out.println(s2.getMatricNum());
								g2.printGroupDetailsConfirmation();

								System.out.print("Confirm to swap index? 1 = Yes, Any other number = No: ");
								do {
									try {
										confirmation = sc.nextInt();
										if (confirmation >= 0) {
											validInput = true;
											sc.nextLine();
										}
									} catch (InputMismatchException e) {
										System.out.println("Enter a valid integer!");
										sc.nextLine();
									}
								} while (!validInput);
								validInput = false;

								if (confirmation == 1) {
									s1.getIndexGroupList().remove(ownIndexNumber);
									s2.getIndexGroupList().remove(peerIndexNumber);
									s1.getIndexGroupList().add(peerIndexNumber);
									s2.getIndexGroupList().add(ownIndexNumber);
									s1.getSchedule().UpdateSchedule(ownIndexNumber, peerIndexNumber);
									s2.getSchedule().UpdateSchedule(peerIndexNumber, ownIndexNumber);

									s1.updateStudentObject(s1);
									s2.updateStudentObject(s2);
									success = true;
									System.out.println("You have successfully swapped your index group number with "
											+ s2.getName());

								} else {
									System.out.println("Index group has not been swapped.");
									System.out.println("Returning to main menu...");
								}
							}
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
	 * Changes the index group of this Student given the same course.
	 * 
	 * @param currentChoice
	 *            Index of course which this Student is registered in.
	 * @param newChoice
	 *            Index of course this Student wants to be registered in.
	 * @param sc
	 *            Scanner for input.
	 * @return Success or failure to change index.
	 */
	public boolean changeIndex(Integer currentChoice, Integer newChoice, Scanner sc) {
		boolean success = false;
		boolean validInput = false;
		int confirmation = 0;
		Student s1 = new Student();
		s1 = s1.retrieveStudentObject(this.getStudentID());
		Student s2 = new Student();
		Course c1 = new Course();
		c1 = c1.retrieveCourseObjectByIndexGroup(currentChoice);
		Course c2 = new Course();
		c2 = c2.retrieveCourseObjectByIndexGroup(newChoice);
		IndexGroup g1 = new IndexGroup();
		g1 = g1.retrieveIndexGroupObject(currentChoice);
		IndexGroup g2 = new IndexGroup();
		g2 = g2.retrieveIndexGroupObject(newChoice);
		Waitlist wl1 = new Waitlist();
		wl1 = wl1.retrieveWaitListObjectByIndexGroup(currentChoice);

		try {
			if (!(s1.checkCourseIndexRegistered(currentChoice))) {
				System.out.println("You are not registered in this Index Group.");
			} else {
				if (c1 != null && c2 != null) {
					if (!(c1.getCourseID().equals(c2.getCourseID()))) {
						System.out.println("New index is of different course from current index.");
					} else {
						if (schedule.checkScheduleClashSameCourse(newChoice)) {
							System.out.println("Index schedule clash with current schedule.");
						} else {
							if (!(g2.checkVacancies())) {
								System.out.println("New index has no vacancies available.");
							} else {
								System.out.println(s1.getName());
								System.out.println("Course: " + c1.getCourseID());
								g1.printGroupDetailsConfirmation();
								System.out.println("Course: " + c2.getCourseID());
								g2.printGroupDetailsConfirmation();
								System.out.println("Subject type: " + c1.getCourseType());
								System.out.println("Status: Registered");

								System.out.print("Confirm to change index? 1 = Yes, Any other number = No: ");
								do {
									try {
										confirmation = sc.nextInt();
										if (confirmation >= 0) {
											validInput = true;
											sc.nextLine();
										}
									} catch (InputMismatchException e) {
										System.out.println("Enter a valid integer!");
										sc.nextLine();
									}
								} while (!validInput);
								validInput = false;

								if (confirmation == 1) {
									s1.getIndexGroupList().remove(currentChoice);
									s1.getIndexGroupList().add(newChoice);
									g1.updateVacancies('+');
									String tempStudentID = wl1.getStudentQueueList().peek();

									if (tempStudentID != null) {
										s2 = s2.retrieveStudentObject(tempStudentID);
										wl1.dequeueFromWaitList(tempStudentID);
										s2.getCourseList().add(c1.getCourseID());
										s2.getIndexGroupList().add(currentChoice);
										s2.getSchedule().AddSchedule(currentChoice);

										for (int i = 0; i < s2.getWaitListIDList().size(); i++) {
											if (s2.getWaitListIDList().get(i).equals(wl1.getWaitListID())) {
												s2.getWaitListIDList().remove(i);
											}
										}
										g1.updateVacancies('-');

										wl1.updateWaitListObject(wl1);
										s2.updateStudentObject(s2);

										System.out.println("System message:");
										if (s2.getNotifMode().equals("sms")) {
											System.out.println(s2.getName()
													+ " has been removed from waitlist and added to the index group "
													+ currentChoice);
											NotificationController.sendSMS(s2.getMobileNum());
										} else if (s2.getNotifMode().equals("email")) {
											System.out.println(s2.getName()
													+ " has been removed from waitlist and added to the index group "
													+ currentChoice);
											NotificationController.sendEmail(s2.getEmail(), c1.getCourseID());
										} else if (s2.getNotifMode().equals("both")) {
											System.out.println(s2.getName()
													+ " has been removed from waitlist and added to the index group "
													+ currentChoice);
											NotificationController.sendSMS(s2.getMobileNum());
											NotificationController.sendEmail(s2.getEmail(), c1.getCourseID());
										}
									}
									s1.getSchedule().UpdateSchedule(currentChoice, newChoice);
									g2.updateVacancies('-');

									s1.updateStudentObject(s1);
									g1.updateIndexGroupObject(g1);
									g2.updateIndexGroupObject(g2);
									success = true;
									System.out.println("You have successfully changed your index group number!");
								} else {
									System.out.println("Index group has not been changed.");
									System.out.println("Returning to main menu...");
								}
							}
						}
					}
				} else {
					System.out.println("The course or index group number does not exist!");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}

	/**
	 * Checks if course index is registered to this Student.
	 * 
	 * @param indexGroupID
	 *            Selected index group ID.
	 * @return Success or failure of course registered to Student.
	 */
	public boolean checkCourseIndexRegistered(Integer indexGroupID) {
		boolean success = false;

		if (this.getIndexGroupList().contains(indexGroupID)) {
			success = true;
		}

		return success;
	}

	/**
	 * Checks if this Student is exempted from the course.
	 * 
	 * @param indexGroupID
	 *            Selected index group ID.
	 * @return Success or failure of course exempted by this Student.
	 */
	public boolean checkCourseExempted(Integer indexGroupID) {
		boolean success = false;
		Course c1 = new Course();
		c1 = c1.retrieveCourseObjectByIndexGroup(indexGroupID);

		if (this.getExemptionCoursesList().contains(c1.getCourseID())) {
			success = true;
		}

		return success;
	}

	/**
	 * Checks if this Student has completed the course.
	 * 
	 * @param indexGroupID
	 *            Selected index group ID.
	 * @return Success or failure of course completed by this Student.
	 */
	public boolean checkCourseCompleted(Integer indexGroupID) {
		boolean success = false;
		Course c1 = new Course();
		c1 = c1.retrieveCourseObjectByIndexGroup(indexGroupID);

		if (this.getCompletedCoursesList().contains(c1.getCourseID())) {
			success = true;
		}

		return success;
	}

	/**
	 * Gets the ID of this Student.
	 * 
	 * @return Student ID of this Student.
	 */
	public String getStudentID() {
		return studentID;
	}

	/**
	 * Changes the ID of this Student.
	 * 
	 * @param studentID
	 *            This Student's new studentID.
	 */
	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}

	/**
	 * Gets the hashed password of this Student.
	 * 
	 * @return Hashed password of this Student.
	 */
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * Changes the hashed password of this Student.
	 * 
	 * @param passwordHash
	 *            This student's hashed password.
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	/**
	 * Gets the name of this Student.
	 * 
	 * @return The name of this Student.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Changes the name of this Student.
	 * 
	 * @param name
	 *            This Student's name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the matriculation number of this Student.
	 * 
	 * @return Matriculation number of this Student.
	 */
	public String getMatricNum() {
		return matricNum;
	}

	/**
	 * Changes the matriculation number of this Student.
	 * 
	 * @param matricNum
	 *            This Student's matriculation number.
	 */
	public void setMatricNum(String matricNum) {
		this.matricNum = matricNum;
	}

	/**
	 * Gets the nationality of this Student.
	 * 
	 * @return nationality of this Student.
	 */
	public String getNationality() {
		return nationality;
	}

	/**
	 * Changes the nationality of this Student.
	 * 
	 * @param nationality
	 *            This Student's nationality.
	 */
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	/**
	 * Gets the gender of this Student.
	 * 
	 * @return The gender of this Student.
	 */
	public char getGender() {
		return gender;
	}

	/**
	 * Changes the gender of this Student.
	 * 
	 * @param gender
	 *            This Student's gender.
	 */
	public void setGender(char gender) {
		this.gender = gender;
	}

	/**
	 * Gets the list of courses of this Student.
	 * 
	 * @return List of courses of this Student.
	 */
	public ArrayList<String> getCourseList() {
		return courseList;
	}

	/**
	 * Changes the name of course list of this Student.
	 * 
	 * @param courseList
	 *            This Student's course list.
	 */
	public void setCourseList(ArrayList<String> courseList) {
		this.courseList = courseList;
	}

	/**
	 * Gets the list of index groups of this Student.
	 * 
	 * @return List of index groups of this Student.
	 */
	public ArrayList<Integer> getIndexGroupList() {
		return indexGroupList;
	}

	/**
	 * Changes the name of the index group list of this Student.
	 * 
	 * @param indexGroupList
	 *            This Student's index group list.
	 */
	public void setIndexGroupList(ArrayList<Integer> indexGroupList) {
		this.indexGroupList = indexGroupList;
	}

	/**
	 * Gets the list of exempted courses of this Student.
	 * 
	 * @return List of exempted courses of this Student.
	 */
	public ArrayList<String> getExemptionCoursesList() {
		return exemptionCoursesList;
	}

	/**
	 * Changes the name of the exemption course list of this Student.
	 * 
	 * @param exemptionCoursesList
	 *            This Student's exemption course list.
	 */
	public void setExemptionCoursesList(ArrayList<String> exemptionCoursesList) {
		this.exemptionCoursesList = exemptionCoursesList;
	}

	/**
	 * Gets the list of completed courses of this Student.
	 * 
	 * @return List of completed courses of this Student.
	 */
	public ArrayList<String> getCompletedCoursesList() {
		return completedCoursesList;
	}

	/**
	 * Changes the name of the completed course list of this Student.
	 * 
	 * @param completedCoursesList
	 *            This Student's completed course list.
	 */
	public void setCompletedCoursesList(ArrayList<String> completedCoursesList) {
		this.completedCoursesList = completedCoursesList;
	}

	/**
	 * Gets the start time of access period of this Student.
	 * 
	 * @return The start time of access period of this Student.
	 */
	public Calendar getStartTime() {
		return startTime;
	}

	/**
	 * Changes the start time of access period of this Student.
	 * 
	 * @param startTime
	 *            This Student's start time of access period.
	 */
	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	}

	/**
	 * Gets the end time of access period of this Student.
	 * 
	 * @return The end time of access period of this Student.
	 */
	public Calendar getEndTime() {
		return endTime;
	}

	/**
	 * Changes the end time of access period of this Student.
	 * 
	 * @param endTime
	 *            This Student's end tiem of access period.
	 */
	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}

	/**
	 * Gets the email address of this Student.
	 * 
	 * @return The email address of this Student.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Changes the email address of this Student.
	 * 
	 * @param email
	 *            This Student's email address.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the mobile number of this Student.
	 * 
	 * @return the mobile number of this Student.
	 */
	public String getMobileNum() {
		return mobileNum;
	}

	/**
	 * Changes the mobile number of this Student.
	 * 
	 * @param mobileNum
	 *            This Student's mobile number.
	 */
	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}

	/**
	 * Gets the notification mode of this Student.
	 * 
	 * @return The notification mode of this Student.
	 */
	public String getNotifMode() {
		return notifMode;
	}

	/**
	 * Changes the notification mode of this Student.
	 * 
	 * @param notifMode
	 *            This Student's notification mode.
	 */
	public void setNotifMode(String notifMode) {
		this.notifMode = notifMode;
	}

	/**
	 * Gets the list of waitlist ID of this Student.
	 * 
	 * @return The list of waitlist ID of this Student.
	 */
	public ArrayList<Integer> getWaitListIDList() {
		return waitListIDList;
	}

	/**
	 * Changes the name of waitlist ID list of this Student.
	 * 
	 * @param waitListIDList
	 *            This Student's waitlist ID list.
	 */
	public void setWaitListIDList(ArrayList<Integer> waitListIDList) {
		this.waitListIDList = waitListIDList;
	}

	/**
	 * Gets the list of schools of this Student.
	 * 
	 * @return the list of schools of this Student.
	 */
	public ArrayList<String> getSchool() {
		return school;
	}

	/**
	 * Changes the name of school list of this Student.
	 * 
	 * @param school
	 *            This Student's school list.
	 */
	public void setSchool(ArrayList<String> school) {
		this.school = school;
	}

	/**
	 * Gets the schedule of this Student.
	 * 
	 * @return the schedule of this Student.
	 */
	public Schedule getSchedule() {
		return schedule;
	}

	/**
	 * Changes the name of schedule of this Student.
	 * 
	 * @param schedule
	 *            This student's schedule.
	 */
	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	/**
	 * Reads the data from the database file = * @return list of objects in the
	 * database file.
	 */
	@SuppressWarnings("rawtypes")
	public List readSerializedObject() {
		List pDetails = null;
		FileInputStream fis = null;
		ObjectInputStream in = null;
		try {
			fis = new FileInputStream(System.getProperty("user.dir") + "\\Databases\\students.dat");
			in = new ObjectInputStream(fis);
			pDetails = (ArrayList) in.readObject();
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
	 * 
	 * @param list
	 *            The list of objects to write
	 */
	@SuppressWarnings("rawtypes")
	public void writeSerializedObject(List list) {
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try {
			fos = new FileOutputStream(System.getProperty("user.dir") + "\\Databases\\students.dat");
			out = new ObjectOutputStream(fos);
			out.writeObject(list);
			out.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}