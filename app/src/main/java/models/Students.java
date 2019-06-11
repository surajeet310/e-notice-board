package models;

/**
 * Created by Surajeet Das on 20-04-2019.
 */

public class Students {
    private String student_first_name;
    private String student_last_name;
    private String student_email_id;
    private String student_ac_pass;
    private int student_id;
    private int student_sem_id;

    public Students(){

    }

    public Students(String student_first_name, String student_last_name, int student_id, int student_sem_id, String student_email_id, String student_ac_pass) {
        this.student_first_name = student_first_name;
        this.student_last_name = student_last_name;
        this.student_id = student_id;
        this.student_sem_id = student_sem_id;
        this.student_email_id = student_email_id;
        this.student_ac_pass = student_ac_pass;
    }

    public String getStudent_first_name() {
        return student_first_name;
    }

    public void setStudent_first_name(String student_first_name) {
        this.student_first_name = student_first_name;
    }

    public String getStudent_last_name() {
        return student_last_name;
    }

    public void setStudent_last_name(String student_last_name) {
        this.student_last_name = student_last_name;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public int getStudent_sem_id() {
        return student_sem_id;
    }

    public void setStudent_sem_id(int student_sem_id) {
        this.student_sem_id = student_sem_id;
    }

    public String getStudent_email_id() {
        return student_email_id;
    }

    public void setStudent_email_id(String student_email_id) {
        this.student_email_id = student_email_id;
    }

    public String getStudent_ac_pass() {
        return student_ac_pass;
    }

    public void setStudent_ac_pass(String student_ac_pass) {
        this.student_ac_pass = student_ac_pass;
    }
}
