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

public class student_view_total_mark extends AppCompatActivity implements AdapterView.OnItemSelectedListener,AdapterView.OnItemClickListener {
    ListView l1;
    SharedPreferences sh;
    Spinner s1;
    String subjectid;

    ArrayList<String> name,ad,mark,subject,sub_id,internalone,internaltwo,assignment,total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view_total_mark);
        l1=(ListView)findViewById(R.id.listview);
        s1=(Spinner)findViewById(R.id.spinner2);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String url ="http://"+sh.getString("ip","")+":5000/select_subject";
        s1.setOnItemSelectedListener(student_view_total_mark.this);
        RequestQueue queue = Volley.newRequestQueue(student_view_total_mark.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);

                    subject= new ArrayList<>(ar.length());
                    sub_id= new ArrayList<>(ar.length());

                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        subject.add(jo.getString("subject"));
                        sub_id.add(jo.getString("allot_id"));


                    }

                    ArrayAdapter<String> ad=new ArrayAdapter<String>(student_view_total_mark.this,android.R.layout.simple_spinner_item,subject);
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
        subjectid=sub_id.get(i);
        String url1 ="http://"+sh.getString("ip", "") + ":5000/student_markkk";
        RequestQueue queue = Volley.newRequestQueue(student_view_total_mark.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url1,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);
                    internalone= new ArrayList<>();
                    internaltwo= new ArrayList<>();
                    assignment= new ArrayList<>();
                    total= new ArrayList<>();


                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        internalone.add(jo.getString("internal1"));
                        internaltwo.add(jo.getString("internal2"));
                        assignment.add(jo.getString("assignment"));
                        total.add(jo.getString("total"));


                    }

                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);

                    l1.setAdapter(new Custom4(student_view_total_mark.this,internalone,internaltwo,assignment,total));
                    l1.setOnItemClickListener(student_view_total_mark.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(student_view_total_mark.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("subid",subjectid);
                params.put("studid",sh.getString("id",""));

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
        Intent ii=new Intent(getApplicationContext(),Student_view_mark.class);
        ii.putExtra("sid",subjectid);
        startActivity(ii);
    }
}
