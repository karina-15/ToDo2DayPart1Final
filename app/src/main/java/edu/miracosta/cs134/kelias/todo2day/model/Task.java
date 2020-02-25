package edu.miracosta.cs134.kelias.todo2day.model;

public class Task {

    // data members
    private long mId;
    private String mDescription;
    private boolean mIsDone;

    /**
     * constructor - parameterized all data members
     * initializes ea. member variable accordingly
     * @param mId           unique (primary key) task id number
     * @param mDescription  what the task is
     * @param mIsDone       if task is done
     */
    public Task(long mId, String mDescription, boolean mIsDone) {
        this.mId = mId;
        this.mDescription = mDescription;
        this.mIsDone = mIsDone;
    }

    /**
     * constructor - parameterized description and isDone
     * initialized mId to -1 then ea. member accordingly
     * @param description   what the task is
     * @param isDone        if task is done
     */
    public Task(String description, boolean isDone){
        this(-1, description, isDone);
    }

    /**
     * constructor - parameterized description
     * initialized mId to -1 and mIsDone to false and description accordingly
     * @param description   what the task is
     */
    public Task(String description){
        this(-1, description, false);
    }

    /** Getters/Setters */
    public long getId() {
        return mId;
    }

    public void setId(long mId) {
        this.mId = mId;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public boolean isIsDone() {
        return mIsDone;
    }

    public void setIsDone(boolean mIsDone) {
        this.mIsDone = mIsDone;
    }

    @Override
    public String toString() {
        return "Task{" +
                "Id=" + mId +
                ", Description='" + mDescription + '\'' +
                ", IsDone=" + mIsDone +
                '}';
    }
}
