package models;

/**
 * Created by Surajeet Das on 20-04-2019.
 */

public class Teacher {
    private String teacher_first_name;
    private String teacher_last_name;
    private String teacher_email_id;
    private String teacher_ac_pass;
    private int teacher_id;

    public Teacher() {

    }

    public Teacher(String teacher_first_name, String teacher_last_name, int teacher_id, String teacher_email_id, String teacher_ac_pass) {
        this.teacher_first_name = teacher_first_name;
        this.teacher_last_name = teacher_last_name;
        this.teacher_id = teacher_id;
        this.teacher_email_id = teacher_email_id;
        this.teacher_ac_pass = teacher_ac_pass;
    }

    public String getTeacher_first_name() {
        return teacher_first_name;
    }

    public void setTeacher_first_name(String teacher_first_name) {
        this.teacher_first_name = teacher_first_name;
    }

    public String getTeacher_last_name() {
        return teacher_last_name;
    }

    public void setTeacher_last_name(String teacher_last_name) {
        this.teacher_last_name = teacher_last_name;
    }

    public int getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getTeacher_email_id() {
        return teacher_email_id;
    }

    public void setTeacher_email_id(String teacher_email_id) {
        this.teacher_email_id = teacher_email_id;
    }

    public String getTeacher_ac_pass() {
        return teacher_ac_pass;
    }

    public void setTeacher_ac_pass(String teacher_ac_pass) {
        this.teacher_ac_pass = teacher_ac_pass;
    }

}