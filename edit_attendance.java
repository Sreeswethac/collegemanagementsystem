package com.example.hp.college_management;



import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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

public class edit_attendance extends AppCompatActivity implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {
    Spinner s1, s2;
    Button b1, b2, b3;
    ListView l;
    SharedPreferences sp;
    String url = "", ip = "",crse,siid,dat;

    ArrayList<String> date, name, attendance,course,cid,sid;


    // String c[]={"Bsc cs","BCA","Msc cs"};
    String s[] = {"1", "2", "3", "4", "5", "6"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_attendance);
        b1 = findViewById(R.id.search);


        l = findViewById(R.id.alv);
        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        s1 = findViewById(R.id.csp);
        s2 = findViewById(R.id.ssp);
        // ArrayAdapter<String> course=new ArrayAdapter<String>(edit_attendance.this,android.R.layout.simple_list_item_1,c);
        //s1.setAdapter(course);
        ArrayAdapter<String> sem = new ArrayAdapter<String>(edit_attendance.this, android.R.layout.simple_list_item_1, s);
        s2.setAdapter(sem);

        RequestQueue queue = Volley.newRequestQueue(edit_attendance.this);
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

                    ArrayAdapter<String> ad=new ArrayAdapter<String>(edit_attendance.this,android.R.layout.simple_list_item_1, course);
                    s1.setAdapter(ad);
                  s1.setOnItemSelectedListener(edit_attendance.this);
                  l.setOnItemClickListener(edit_attendance.this);

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
//                params.put("sem", sp.getString("sem", ""));
//                params.put("cid", sp.getString("cid", ""));
                params.put("lid",sp.getString("id",""));

                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             final String   sm=s2.getSelectedItem().toString();

                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(edit_attendance.this);
                String url = "http://" + sp.getString("ip", "") + ":5000/stud";

                // Instantiate the RequestQueue.


                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the response string.
                        Log.d("+++++++++++++++++", response);
                        try {

                            JSONArray ar = new JSONArray(response);
                            date = new ArrayList<>();
                            name = new ArrayList<>();
                            attendance = new ArrayList<>();
                            sid = new ArrayList<>();
                            for (int i = 0; i < ar.length(); i++) {
                                JSONObject jo = ar.getJSONObject(i);
                                date.add(jo.getString("date"));
                                name.add(jo.getString("name"));
                                attendance.add(jo.getString("attendance"));
                                sid.add(jo.getString("student_id"));
                            }
                            // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                            //lv.setAdapter(ad);
                            l.setAdapter(new Custom3(edit_attendance.this,date, name, attendance));
                            l.setOnItemClickListener(edit_attendance.this);



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
                            params.put("sem", sm);
                            params.put("cid", crse);

                            return params;
                        }
                    };
                    // Add the request to the RequestQueue.
                queue.add(stringRequest);
            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        crse=cid.get(i);


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        siid=sid.get(i).toString();
        dat=date.get(i).toString();
        Intent in = new Intent(getApplicationContext(), Edit_attendance1.class);

        in.putExtra("sid", siid);
        in.putExtra("date", dat);


        startActivity(in);



    }
}
