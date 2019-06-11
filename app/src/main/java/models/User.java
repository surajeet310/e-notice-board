package models;

import java.io.Serializable;

/**
 * Created by Surajeet Das on 03-05-2019.
 */

@SuppressWarnings("serial")

public class User  implements Serializable{
    private int user_id;
    private String user_first_name;
    private String user_last_name;
    private String user_email_id;
    private String user_ac_pass;
    private String user_designation;
    private int user_sem_id;


    public User(){

    }

    public User(int user_id, String user_first_name, String user_last_name, String user_email_id, String user_ac_pass, String user_designation, int user_sem_id) {
        this.user_id = user_id;
        this.user_first_name = user_first_name;
        this.user_last_name = user_last_name;
        this.user_email_id = user_email_id;
        this.user_ac_pass = user_ac_pass;
        this.user_designation = user_designation;
        this.user_sem_id = user_sem_id;
    }


    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_first_name() {
        return user_first_name;
    }

    public void setUser_first_name(String user_first_name) {
        this.user_first_name = user_first_name;
    }

    public String getUser_last_name() {
        return user_last_name;
    }

    public void setUser_last_name(String user_last_name) {
        this.user_last_name = user_last_name;
    }

    public String getUser_email_id() {
        return user_email_id;
    }

    public void setUser_email_id(String user_email_id) {
        this.user_email_id = user_email_id;
    }

    public String getUser_ac_pass() {
        return user_ac_pass;
    }

    public void setUser_ac_pass(String user_ac_pass) {
        this.user_ac_pass = user_ac_pass;
    }

    public String getUser_designation() {
        return user_designation;
    }

    public void setUser_designation(String user_designation) {
        this.user_designation = user_designation;
    }

    public int getUser_sem_id() {
        return user_sem_id;
    }

    public void setUser_sem_id(int user_sem_id) {
        this.user_sem_id = user_sem_id;
    }
}
