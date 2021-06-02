package com.example.hp.college_management;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class mark_attendance extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner s1,s2;
    Button b;
    SharedPreferences sp;
    String ip,url="",crse,sm;
    ArrayList<String> course,cid;
    String s[]={"1","2","3","4","5","6"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_attendance);
        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ip = sp.getString("ip", "");


       // String url="http://"+sp.getString("ip","")+":5000/markattendance";

        s1=findViewById(R.id.csp);
        s2=findViewById(R.id.ssp);

        viewcrese();

        final ArrayAdapter<String> sem=new ArrayAdapter<String>(mark_attendance.this,android.R.layout.simple_list_item_1,s);
        s2.setAdapter(sem);
        b=findViewById(R.id.search);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                sm=s2.getSelectedItem().toString();

                SharedPreferences.Editor ed=sp.edit();
                ed.putString("cid",crse);
                ed.putString("sem",sm);
                ed.commit();
                Intent ik = new Intent(getApplicationContext(), student_attendance.class);
                startActivity(ik);

            }
        });

    }

    private void viewcrese() {


        RequestQueue queue = Volley.newRequestQueue(mark_attendance.this);
        String url="http://"+sp.getString("ip","")+":5000/managecourse";


        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++", response);
                try {

                    JSONArray ar = new JSONArray(response);
                    course = new ArrayList<>();
                    cid = new ArrayList<>();


                    for (int i = 0; i < ar.length(); i++) {
                        JSONObject jo = ar.getJSONObject(i);
                        course.add(jo.getString("subject"));
                        cid.add(jo.getString("allot_id"));

                    }

                    ArrayAdapter<String> ad=new ArrayAdapter<String>(mark_attendance.this,android.R.layout.simple_list_item_1, course);
                    s1.setAdapter(ad);
                    s1.setOnItemSelectedListener(mark_attendance.this);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(getApplicationContext(),"Error"+error,Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("lid",sp.getString("id",""));


                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {




        crse=cid.get(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
