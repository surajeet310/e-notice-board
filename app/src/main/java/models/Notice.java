package models;

import java.io.Serializable;

/**
 * Created by Surajeet Das on 20-04-2019.
 */

@SuppressWarnings("serial")

public class Notice implements Serializable{
    private int notice_id;
    //private int sem;
    private String notice_title;
    private String notice_content;
    private Character user_id;

    public Notice(){

    }

    public Notice(int notice_id, String notice_title, String notice_content, Character user_id) {
        this.notice_id = notice_id;
        this.notice_title = notice_title;
        this.notice_content = notice_content;
        this.user_id = user_id;

    }



    public int getNotice_id() {
        return notice_id;
    }

    public void setNotice_id(int notice_id) {
        this.notice_id = notice_id;
    }

    public String getNotice_title() {
        return notice_title;
    }

    public void setNotice_title(String notice_title) {
        this.notice_title = notice_title;
    }

    public String getNotice_content() {
        return notice_content;
    }

    public void setNotice_content(String notice_content) {
        this.notice_content = notice_content;
    }

    public Character getUser_id() {
        return user_id;
    }

    public void setUser_id(Character user_id) {
        this.user_id = user_id;
    }
}
