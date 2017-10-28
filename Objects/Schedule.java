/**
 * Represents a schedule in a student.
 * A schedule can be for 1 student only.
 * A student can have 1 schedule only.
 * @author Ang Poh Keong
 * @version 1.0
	@since 2017-04-06
 */

package Objects;
import java.io.Serializable;
import java.util.ArrayList;

public class Schedule implements Serializable {
	/**
	 * The ID to serialize data
	 */
    private static final long serialVersionUID = 1L;
    
    /**
     * A 3-Dimensional ArrayList containing information of individual student's schedule
     */
    private ArrayList<ArrayList<ArrayList<Lesson>>> scheduleTimetable;
 
    /**
	 * Creates a new Schedule with no attribute values
	 */
    public Schedule() {
 
    }
 
    /**
     * Retrieve student's schedule object
     * @return A 3-Dimensional ArrayList containing a student's schedule
     */
    public ArrayList<ArrayList<ArrayList<Lesson>>> getScheduleTimetable() {
        return scheduleTimetable;
    }
 
    /**
     * Set student's schedule object
     * @param scheduleTimetable A 3-Dimensional ArrayList containing a student's schedule
     */
    public void setScheduleTimetable(ArrayList<ArrayList<ArrayList<Lesson>>> scheduleTimetable) {
        this.scheduleTimetable = scheduleTimetable;
    }
 
   /**
	 * Check if the lesson under the index group clashes with your current
	 * schedule
	 * 
	 * @param indexGrpID
	 *            ID of a particular indexGroup
	 * @return Return whether if indexgroup's lesson clash
	 */
	public boolean checkScheduleClash(Integer indexGrpID) {
		IndexGroup indexGrp = new IndexGroup();
		indexGrp = indexGrp.retrieveIndexGroupObject(indexGrpID);
		boolean clashStatus = false;
		ArrayList<Lesson> lesson = indexGrp.getLessonList();

		if (scheduleTimetable != null) {
			for (int i = 0; i < lesson.size(); i++) {
				Lesson l1 = lesson.get(i);

				if (!(l1.getLessonType().equals("ONLINE MOD"))) {

					ArrayList<ArrayList<Lesson>> periodList = scheduleTimetable.get((Lesson.convertLessonDayToInt(l1.getLessonDay())) - 1);
					ArrayList<Integer> listOfPeriod = convertTimeToPeriod(l1.getStartTime(), l1.getEndTime());

					for (int j = 0; j < listOfPeriod.size(); j++) {

						ArrayList<Lesson> period = periodList.get(listOfPeriod.get(j));

						if (l1.getRemark() == null) {
							if (period.get(0) != null) {
								clashStatus = true;
								break;
							}
						}
						else if (l1.getRemark().equals("ODD")) {
							if (period.get(0) != null) {
								clashStatus = true;
								break;
							}
							else if (l1.getRemark().equals("EVEN")) {
								if (period.get(0) != null) {
									if (period.get(0).getRemark() == null) {
										clashStatus = true;
										break;
									} else if (period.get(1) != null) {
										clashStatus = true;
										break;
									}
								}
								else {
									if (period.get(1) != null) {
										clashStatus = true;
										break;
									}
								}

							}

						}
					}
				}
			}

		}
		if (scheduleTimetable == null) {
			createDefaultTSchedule();
		}
		return clashStatus;
	}

	/**
	 * Check if the lesson clashes with your schedule when changing indexGroup for the same course
	 * 
	 * @param indexGrpID
	 *            ID of a particular indexGroup
	 * @return Return whether if indexgroup's lesson clash
	 */
	public boolean checkScheduleClashSameCourse(Integer indexGrpID) {
		IndexGroup indexGrp = new IndexGroup();
		indexGrp = indexGrp.retrieveIndexGroupObject(indexGrpID);

		boolean clashStatus = false;
		ArrayList<Lesson> lesson = indexGrp.getLessonList();

		if (scheduleTimetable != null) {
			for (int i = 0; i < lesson.size(); i++) {
				Lesson l1 = lesson.get(i);
				if (!(l1.getLessonType().equals("LEC/STUDIO")) && !(l1.getLessonType().equals("ONLINE MOD"))) {
					ArrayList<ArrayList<Lesson>> periodList = scheduleTimetable.get((Lesson.convertLessonDayToInt(l1.getLessonDay())) - 1);
					ArrayList<Integer> listOfPeriod = convertTimeToPeriod(l1.getStartTime(), l1.getEndTime());
					for (int j = 0; j < listOfPeriod.size(); j++) {

						ArrayList<Lesson> period = periodList.get(listOfPeriod.get(j));

						if (l1.getRemark() == null) {
							if (period.get(0) != null) {
								clashStatus = true;
								break;
							}
						}
						else if (l1.getRemark().equals("ODD")) {
							if (period.get(0) != null) {
								clashStatus = true;
								break;
							}
							else if (l1.getRemark().equals("EVEN")) {
								if (period.get(0) != null) {
									if (period.get(0).getRemark() == null) {
										clashStatus = true;
										break;
									} else if (period.get(1) != null) {
										clashStatus = true;
										break;
									}
								}
								else {
									if (period.get(1) != null) {
										clashStatus = true;
										break;
									}
								}
							}
						}
					}
				}
			}
		}
		if (scheduleTimetable == null) {
			createDefaultTSchedule();
		}
		return clashStatus;
	}
    /**
     * Initialize a empty schedule if the scheduleTimetable ArrayList is not initialize
     */
    public void createDefaultTSchedule() {
        int day = 6;
        int period = 15;
        scheduleTimetable = new ArrayList<ArrayList<ArrayList<Lesson>>>();
 
        for (int i = 0; i < day; i++) {
            ArrayList<ArrayList<Lesson>> periodList = new ArrayList<ArrayList<Lesson>>();
 
            for (int j = 0; j < period; j++) {
                ArrayList<Lesson> periodItem = new ArrayList<Lesson>();
                Lesson tempLesson = null;
                periodItem.add(tempLesson);
                periodItem.add(tempLesson);
                periodList.add(periodItem);
            }
            scheduleTimetable.add(periodList);
        }
    }
 
    /**
     * Convert a lesson's time and duration to period format in a timetable
     * @param startTime Starting time of a lesson
     * @param endTime Ending time of a lesson
     * @return ArrayList of Integer containing period of a lesson
     */
    public ArrayList<Integer> convertTimeToPeriod(String startTime, String endTime) {
        ArrayList<Integer> tempList = new ArrayList<Integer>();
        int startPeriod = 0;
        int noOfHour = (Integer.parseInt(endTime) - Integer.parseInt(startTime)) / 100;
 
        switch (startTime) {
 
        case "0830":
            startPeriod = 0;
            break;
        case "0930":
            startPeriod = 1;
            break;
        case "1030":
            startPeriod = 2;
            break;
        case "1130":
            startPeriod = 3;
            break;
        case "1230":
            startPeriod = 4;
            break;
        case "1330":
            startPeriod = 5;
            break;
        case "1430":
            startPeriod = 6;
            break;
        case "1530":
            startPeriod = 7;
            break;
        case "1630":
            startPeriod = 8;
            break;
        case "1730":
            startPeriod = 9;
            break;
        case "1830":
            startPeriod = 10;
            break;
        case "1930":
            startPeriod = 11;
            break;
        case "2030":
            startPeriod = 12;
            break;
        case "2130":
            startPeriod = 13;
            break;
        case "2230":
            startPeriod = 14;
            break;
        default:
            break;
        }
 
        for (int i = 0; i < noOfHour; i++) {
            tempList.add(startPeriod);
            startPeriod++;
        }
 
        return tempList;
    }
 
    /**
     * Add a particular index group to a student's schedule
     * @param indexGrpID ID of an indexGroup
     * @return Results of adding the schedule
     */
    public boolean AddSchedule(Integer indexGrpID) {
        boolean success = false;
         
        if (scheduleTimetable == null) {
            createDefaultTSchedule();
        }
         
        IndexGroup indexGrp = new IndexGroup();
        indexGrp = indexGrp.retrieveIndexGroupObject(indexGrpID);
        ArrayList<Lesson> lesson = indexGrp.getLessonList();
 
        for (int i = 0; i < lesson.size(); i++) {
            Lesson l1 = lesson.get(i);
 
            if (!(l1.getLessonType().equals("ONLINE MOD"))) {
                ArrayList<Integer> listOfPeriod = convertTimeToPeriod(l1.getStartTime(), l1.getEndTime());
 
                for (int j = 0; j < listOfPeriod.size(); j++) {
 
                    if (l1.getRemark() == null){
                        scheduleTimetable.get((Lesson.convertLessonDayToInt(l1.getLessonDay())) - 1).get(listOfPeriod.get(j)).set(0, l1);
                    }
                    else if (l1.getRemark().equals("ODD")){
                        scheduleTimetable.get((Lesson.convertLessonDayToInt(l1.getLessonDay())) - 1).get(listOfPeriod.get(j)).set(0, l1);
                    }
                    else if (l1.getRemark().equals("EVEN")){
                        scheduleTimetable.get((Lesson.convertLessonDayToInt(l1.getLessonDay())) - 1).get(listOfPeriod.get(j)).set(1, l1);
                    }
                }
            }
            success = true;
        }
        return success;
    }
 
    /**
     * Update schedules by removing current index group and adding new index group
     * @param currentIndexID ID of current index group to update
     * @param newIndexID ID of new index group to update
     * @return Results of updating the schedule
     */
    public boolean UpdateSchedule(Integer currentIndexID, Integer newIndexID) {
        boolean success = false;
        RemoveSchedule(currentIndexID);
        success = AddSchedule(newIndexID);
        return success;
    }
 
    /**
     * Remove a particular index group from the student's schedule
     * @param removeIndexID The ID of index group to remove
     * @return Results of removing index group from schedule
     */
    public boolean RemoveSchedule(Integer removeIndexID) {
        boolean success = false;
        IndexGroup indexToRemove = new IndexGroup();
        indexToRemove = indexToRemove.retrieveIndexGroupObject(removeIndexID);
        ArrayList<Lesson> lesson = indexToRemove.getLessonList();
 
        for (int i = 0; i < lesson.size(); i++) {
            Lesson l1 = lesson.get(i);

            if (!(l1.getLessonType().equals("ONLINE MOD"))) {
                ArrayList<Integer> listOfPeriod = convertTimeToPeriod(l1.getStartTime(), l1.getEndTime());
 
                for (int j = 0; j < listOfPeriod.size(); j++) {
                    if (l1.getRemark() == null){
                        scheduleTimetable.get((Lesson.convertLessonDayToInt(l1.getLessonDay())) - 1).get(listOfPeriod.get(j)).set(0, null);
                    }
                    else if (l1.getRemark().equals("ODD")){
                        scheduleTimetable.get((Lesson.convertLessonDayToInt(l1.getLessonDay())) - 1).get(listOfPeriod.get(j)).set(0, null);
                    }
                    else if (l1.getRemark().equals("EVEN")){
                        scheduleTimetable.get((Lesson.convertLessonDayToInt(l1.getLessonDay())) - 1).get(listOfPeriod.get(j)).set(1, null);
                    }
                }
            }
            success = true;
        }
        return success;
    }
    
    
}

