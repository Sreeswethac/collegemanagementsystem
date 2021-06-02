package com.example.hp.college_management;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.HashMap;
import java.util.Map;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Student_view_attendance extends AppCompatActivity  {


    SharedPreferences sh;
    String admsn,semester;
    ListView l1;
    String sem[]={"1","2","3","4","5","6"};
    ArrayList<String> admission_no,studid,subject,daywrkng,dayprsnt,percentage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view_attendance);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        l1=(ListView)findViewById(R.id.listview);







                String url1 ="http://"+sh.getString("ip", "") + ":5000/student_view_attendance";
                RequestQueue queue = Volley.newRequestQueue(Student_view_attendance.this);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url1,new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the response string.
                        Log.d("+++++++++++++++++",response);
                        try {

                            JSONArray ar=new JSONArray(response);
                            subject= new ArrayList<>();
                            daywrkng= new ArrayList<>();
                            dayprsnt= new ArrayList<>();
                            percentage=new ArrayList<>();


                            for(int i=0;i<ar.length();i++)
                            {
                                JSONObject jo=ar.getJSONObject(i);
                                subject.add(jo.getString("subject"));
                                daywrkng.add(jo.getString("wkngday"));
                                dayprsnt.add(jo.getString("presentday"));
                                percentage.add(jo.getString("Attendance"));


                            }

                            // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                            //lv.setAdapter(ad);

                            l1.setAdapter(new Custom4(Student_view_attendance.this,subject,daywrkng,dayprsnt,percentage));
//                            l1.setOnItemClickListener(viewuser.this);

                        } catch (Exception e) {
                            Log.d("=========", e.toString());
                        }


                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(Student_view_attendance.this, "err"+error, Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();

                        params.put("lid",sh.getString("id",""));
                        return params;
                    }
                };
                queue.add(stringRequest);

            }




}
