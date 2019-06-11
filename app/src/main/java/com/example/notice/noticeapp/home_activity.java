package com.example.notice.noticeapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import conf_util.constants;
import data.SaveSharedPreferences;
import data.StudentNoticeRecyclerViewAdapter;
import data.TeacherNoticeRecyclerViewAdapter;
import models.Notice;
import models.User;

public class home_activity extends AppCompatActivity {

    private String user_desig;

    private RecyclerView recyclerView_student;
    private RecyclerView recyclerView_teacher;
    private StudentNoticeRecyclerViewAdapter studentNoticeRecyclerViewAdapter;
    private TeacherNoticeRecyclerViewAdapter teacherNoticeRecyclerViewAdapter;
    private List<Notice> notice_list;
    private RequestQueue queue;
    private String hello;
    private TextView helloTextview;
    private Button logout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_main);
        helloTextview=(TextView)findViewById(R.id.helloUser);

        Intent i =getIntent();

        User recieved_user = (User)i.getSerializableExtra("userObj");
        user_desig = recieved_user.getUser_designation();
        hello = recieved_user.getUser_first_name();
        helloTextview.setText(R.string.hello);
        helloTextview.setText(helloTextview.getText());
        helloTextview.append(" ");
        helloTextview.append(hello);

        //Log.d("Designation",user_desig);

        queue = Volley.newRequestQueue(home_activity.this);

        logout = (Button)findViewById(R.id.logout_btn);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedpreferences = getSharedPreferences(Sign_in.MyPreferences,Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.apply();
                Intent intent = new Intent(home_activity.this, Sign_in.class);
                startActivity(intent);
            }
        });

        SharedPreferences prefs = getSharedPreferences(Sign_in.MyPreferences, Context.MODE_PRIVATE);
        String user_email = prefs.getString("username", null);
        if(user_email != null) {


            if (user_desig.contentEquals("student")) {
                //Log.d("Designation",user_desig);
                final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView_student = (RecyclerView) findViewById(R.id.recycler_user);
                recyclerView_student.setHasFixedSize(true);
                recyclerView_student.setLayoutManager(layoutManager);
                notice_list = new ArrayList<>();
//              notice_list = getStudentNotices();
//              Log.d("Response:",notice_list.toString());


                studentNoticeRecyclerViewAdapter = new StudentNoticeRecyclerViewAdapter(this, notice_list);
                recyclerView_student.setAdapter(studentNoticeRecyclerViewAdapter);
                getStudentNotices();

            } else {
                final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView_teacher = (RecyclerView) findViewById(R.id.recycler_user);
                recyclerView_teacher.setHasFixedSize(true);
                recyclerView_teacher.setLayoutManager(layoutManager);
                notice_list = new ArrayList<>();

                // notice_list = getTeacherNotices();

                teacherNoticeRecyclerViewAdapter = new TeacherNoticeRecyclerViewAdapter(this, notice_list);
                recyclerView_teacher.setAdapter(teacherNoticeRecyclerViewAdapter);
                getTeacherNotices();

            }

        }
        else{
            Intent intent = new Intent(home_activity.this, Sign_in.class);
            startActivity(intent);
        }
    }

    public void getStudentNotices(){
        notice_list.clear();

        JsonObjectRequest noticeStudent = new JsonObjectRequest(Request.Method.GET,
                constants.STUDENT_NOTICE_API_URL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray listNotices = response.getJSONArray("studentNoticeList");
                    //Log.d("Response", listNotices.toString());
                        for (int i=0;i<listNotices.length();i++){
                            JSONObject jsonObject = listNotices.getJSONObject(i);
                            Notice noticeObjs =  new Notice();
                            noticeObjs.setNotice_title(jsonObject.getString("s_notice_title"));
                            noticeObjs.setNotice_id(Integer.parseInt(jsonObject.getString("s_notice_id")));
                            noticeObjs.setNotice_content(jsonObject.getString("s_notice_content"));
                            noticeObjs.setUser_id('s');

                            //Log.d("Response Obj",noticeObjs.getNotice_content());
                            notice_list.add(noticeObjs);
                            //Log.d("Response Object",notice_list.toString());
                    }
                    studentNoticeRecyclerViewAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error : " ,error.getMessage());
            }
        }
        );

        queue.add(noticeStudent);
        //Log.d("Response return:",notice_list.toString());
        //return notice_list;
    }

    public void getTeacherNotices(){
        notice_list.clear();

        JsonObjectRequest noticeTeacher = new JsonObjectRequest(Request.Method.GET,
                constants.TEACHER_NOTICE_API_URL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray listNotices = response.getJSONArray("teacherNoticeList");


                        for(int i=0;i<listNotices.length();i++){
                            JSONObject jsonObject = listNotices.getJSONObject(i);
                            Notice noticeObjt = new Notice();

                            noticeObjt.setUser_id('t');
                            noticeObjt.setNotice_id(Integer.parseInt(jsonObject.getString("t_notice_id")));
                            noticeObjt.setNotice_title(jsonObject.getString("t_notice_title"));
                            noticeObjt.setNotice_content(jsonObject.getString("t_notice_content"));

                            notice_list.add(noticeObjt);
                        }
                        teacherNoticeRecyclerViewAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error : " ,error.getMessage());
            }
        }

        );

        queue.add(noticeTeacher);

    }

}
