package com.example.layout;

/**
 * Class
 *
 * @author cjearly
 */
public class Class {
    /**
     * Name of the class
     */
    private String className;
    /**
     * Semester hours
     */
    private int SH;
    /**
     * Days of the week that the class meets
     */
    private String daysOfTheWeek;
    /**
     * The time that the class starts each day
     */
    private String startTime;
    /**
     * The time that the class ends each day.
     */
    private String endTime;

    /**
     * Constructor to create the calss
     *
     * @param name  name of the class
     * @param hours the number of semester hours it has
     * @param days  the daysOfTheWeel that we meet
     * @param start the time that the class starts
     * @param end   the time that the class ends
     */
    public Class(String name, int hours, String days, String start, String end) {
        className = name;
        SH = hours;
        daysOfTheWeek = days;
        startTime = start;
        endTime = end;
    }

    /**
     * gets the name of the class
     *
     * @return class name
     */
    public String getClassName() {
        return className;
    }

    /**
     * gets the SH of the class
     *
     * @return SH
     */
    public int getSH() {
        return SH;
    }

    /**
     * Gets the days of the week that the class meets
     *
     * @return daysOfTheWeek
     */
    public String getDaysOfTheWeek() {
        return daysOfTheWeek;
    }

    /**
     * Gets the start time
     *
     * @return the start time
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * Gets the end time
     *
     * @return the end time
     */
    public String getEndTime() {
        return endTime;
    }


}
