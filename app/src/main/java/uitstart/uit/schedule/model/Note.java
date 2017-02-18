package uitstart.uit.schedule.model;

import java.io.Serializable;

/**
 * Created by Khang on 2/15/2017.
 * Class Note: Ghi chú ghi lại tên công việc, chi tiết công việc, thời gian và ngày thực hiện công việc đó
 */

public class Note implements Serializable {
    private int id;
    private String nameOfNote;
    private String detailOfNote;
    private String timeOfNote;
    private String dateOfNote;

    public Note(int id, String dateOfNote, String timeOfNote, String nameOfNote, String detailOfNote) {
        this.dateOfNote = dateOfNote;
        this.id = id;
        this.nameOfNote = nameOfNote;
        this.detailOfNote = detailOfNote;
        this.timeOfNote = timeOfNote;
    }

    public Note(String dateOfNote,String timeOfNote, String nameOfNote, String detailOfNote) {
        this.dateOfNote = dateOfNote;
        this.nameOfNote = nameOfNote;
        this.detailOfNote = detailOfNote;
        this.timeOfNote = timeOfNote;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameOfNote() {
        return nameOfNote;
    }

    public void setNameOfNote(String nameOfNote) {
        this.nameOfNote = nameOfNote;
    }

    public String getDetailOfNote() {
        return detailOfNote;
    }

    public void setDetailOfNote(String detailOfNote) {
        this.detailOfNote = detailOfNote;
    }

    public String getTimeOfNote() {
        return timeOfNote;
    }

    public void setTimeOfNote(String timeOfNote) {
        this.timeOfNote = timeOfNote;
    }

    public String getDateOfNote() {
        return dateOfNote;
    }

    public void setDateOfNote(String dateOfNote) {
        this.dateOfNote = dateOfNote;
    }

    @Override
    public String toString() {
        return id+"\n"+nameOfNote+"\n"+detailOfNote+"\n"+dateOfNote+"\n"+timeOfNote;
    }
}
