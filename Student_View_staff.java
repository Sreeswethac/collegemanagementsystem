package com.example.hp.college_management;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

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
import java.util.HashMap;
import java.util.Map;

public class Student_View_staff extends AppCompatActivity implements AdapterView.OnItemSelectedListener,AdapterView.OnItemClickListener {
    Spinner s1;
    ListView l1;
    SharedPreferences sh;
    String dpidd;
    ArrayList<String> department,dep_id,name,place,phone,qualificaton,experience,joiningdate,dob,district,state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__view_staff);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        s1=(Spinner)findViewById(R.id.spinner);
        l1=(ListView)findViewById(R.id.listview);
        String url ="http://"+sh.getString("ip","")+":5000/select_deppartment";
        s1.setOnItemSelectedListener(Student_View_staff.this);
        RequestQueue queue = Volley.newRequestQueue(Student_View_staff.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);

                    department= new ArrayList<>(ar.length());
                    dep_id= new ArrayList<>(ar.length());

                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        department.add(jo.getString("Dept_name"));
                        dep_id.add(jo.getString("Dept_id"));


                    }

                    ArrayAdapter<String> ad=new ArrayAdapter<String>(Student_View_staff.this,android.R.layout.simple_spinner_item,department);
                    s1.setAdapter(ad);

                    // l1.setAdapter(new custom2(Monitoring_signal.this,notification,date));

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);






    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        dpidd=dep_id.get(i);




        String url ="http://"+sh.getString("ip", "") + ":5000/student_view_staff";
        RequestQueue queue = Volley.newRequestQueue(Student_View_staff.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);
                    name= new ArrayList<>();
                    place= new ArrayList<>();
                    phone= new ArrayList<>();
                    qualificaton= new ArrayList<>();
                    experience= new ArrayList<>();
                    joiningdate= new ArrayList<>();
                    dob= new ArrayList<>();
                    district= new ArrayList<>();
                    state= new ArrayList<>();


                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        name.add(jo.getString("Name"));
                        place.add(jo.getString("place"));
                        phone.add(jo.getString("phone"));
                        qualificaton.add(jo.getString("qualification"));
                        experience.add(jo.getString("experience"));
                        joiningdate.add(jo.getString("joiningdate"));
                        dob.add(jo.getString("Dob"));
                        district.add(jo.getString("District"));
                        state.add(jo.getString("State"));



                    }

                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);

                    l1.setAdapter(new Custom3(Student_View_staff.this,name,place,phone));
                    l1.setOnItemClickListener(Student_View_staff.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Student_View_staff.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("depid",dpidd);

                return params;
            }
        };
        queue.add(stringRequest);






    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


        Intent in = new Intent(getApplicationContext(), More_staff_details.class);

        in.putExtra("qualificaton", qualificaton.get(i));
        in.putExtra("experience", experience.get(i));
        in.putExtra("joiningdate", joiningdate.get(i));
        in.putExtra("dob", dob.get(i));
        in.putExtra("district", district.get(i));
        in.putExtra("state", state.get(i));
        startActivity(in);
    }
}
