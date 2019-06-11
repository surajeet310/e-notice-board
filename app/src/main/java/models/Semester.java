package models;

/**
 * Created by Surajeet Das on 20-04-2019.
 */

public class Semester {
    private int semester_id;
    private String semester_no;

    public Semester(){

    }

    public Semester(int semester_id, String semester_no) {
        this.semester_id = semester_id;
        this.semester_no = semester_no;
    }

    public int getSemester_id() {
        return semester_id;
    }

    public void setSemester_id(int semester_id) {
        this.semester_id = semester_id;
    }

    public String getSemester_no() {
        return semester_no;
    }

    public void setSemester_no(String semester_no) {
        this.semester_no = semester_no;
    }
}
