package com.example.layout;

/**
 * Assignment
 *
 * @author anime_000
 */
public class Assignment {
    /**
     * Name of assignment
     */
    private String name;
    /**
     * Type of assignment
     */
    private String type;
    /**
     * Class name
     */
    private String className;
    /**
     * Due date
     */
    private String dueDate;
    /**
     * Due time
     */
    private String dueTime;
    /**
     * Additional info
     */
    private String additionalInfo;

    /**
     * Constructor to create the assignment
     *
     * @param name           assignment name
     * @param type           type of assignment
     * @param className      name of class
     * @param dueDate        due date
     * @param dueTime        due time
     * @param additionalInfo addiitonal info
     */
    public Assignment(String name, String type, String className, String dueDate, String dueTime, String additionalInfo) {
        this.name = name;
        this.type = type;
        this.className = className;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
        this.additionalInfo = additionalInfo;
    }

    /**
     * gets the name
     *
     * @return assignment name
     */
    public String getName() {
        return name;
    }

    /**
     * gets the type
     *
     * @return type of assignmet
     */
    public String getType() {
        return type;
    }

    /**
     * gets the class name
     *
     * @return the assignment's class name
     */
    public String getClassName() {
        return className;
    }

    /**
     * gets the assignment due date
     *
     * @return due date
     */
    public String getDueDate() {
        return dueDate;
    }

    /**
     * gets the assignment due time
     *
     * @return due time
     */
    public String getDueTime() {
        return dueTime;
    }

    /**
     * gets the additional info
     *
     * @return additional info
     */
    public String getAdditionalInfo() {
        return additionalInfo;
    }
}
