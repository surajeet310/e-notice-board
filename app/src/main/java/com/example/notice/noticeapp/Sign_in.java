package com.example.notice.noticeapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.List;

import data.SaveSharedPreferences;
import models.User;

import static conf_util.constants.USER_API_URL;

public class Sign_in extends AppCompatActivity {

    private EditText email_id_text;
    private EditText pass;
    private Button log_in_button;
    private RequestQueue queue;
    private SharedPreferences sharedPreferences;
    public static String MyPreferences ="SaveSharedPreferences";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        email_id_text = (EditText) findViewById(R.id.editText_email);
        pass = (EditText)findViewById(R.id.editText_pass);
        log_in_button = (Button) findViewById(R.id.button_log_in);



        queue = Volley.newRequestQueue(Sign_in.this);

        log_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email_text = email_id_text.getText().toString();
                final String password = pass.getText().toString();
                get_student_data(email_text);
            }
        });

    }


    public void get_student_data(String email){
         JsonObjectRequest student_data = new JsonObjectRequest(Request.Method.GET,
                 USER_API_URL + email, new Response.Listener<JSONObject>() {
             @Override
             public void onResponse(JSONObject response) {
                 try {
                     JSONArray jsonArray = response.getJSONArray("users");

                     if(jsonArray.length() > 0){

                         Intent intent = new Intent(Sign_in.this,home_activity.class);
                         Toast.makeText(Sign_in.this,"Successful Login",Toast.LENGTH_SHORT).show();

                             //Log.d("Response",jsonArray.toString());

                             JSONObject userObj = jsonArray.getJSONObject(0);
                             User user = new User();
                             user.setUser_first_name(userObj.getString("user_first_name"));
                             user.setUser_last_name(userObj.getString("user_last_name"));
                             user.setUser_id(Integer.parseInt(userObj.getString("user_id")));
                             //Log.d("id", String.valueOf(user.getUser_id()));
                             user.setUser_email_id(userObj.getString("user_email_id"));
                             user.setUser_designation(userObj.getString("user_designation"));

                             sharedPreferences = getSharedPreferences(MyPreferences,Context.MODE_PRIVATE);
                              SharedPreferences.Editor editor = sharedPreferences.edit();
                              editor.putString("username", user.getUser_email_id());
                              editor.apply();

                             intent.putExtra("userObj",user);
                             startActivity(intent);



                     }
                     else
                     {
                         Toast.makeText(Sign_in.this,"Invalid Details", Toast.LENGTH_SHORT).show();
                     }
                 } catch (JSONException e) {
                     e.printStackTrace();
                 }
             }
         }, new Response.ErrorListener() {
             @Override
             public void onErrorResponse(VolleyError error) {
                 Log.d("Error Fetching Data !", error.getMessage());
             }
         }
         );
        queue.add(student_data);
    }
}

