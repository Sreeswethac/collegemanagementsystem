package com.example.hp.college_management;



import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
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

public class student_attendance extends AppCompatActivity implements AdapterView.OnItemClickListener,AdapterView.OnItemSelectedListener {
    ListView l;
    SharedPreferences sp;
    String url="",ip="",count;
    Button b1;
    String s="";
    Spinner s1;
    String hour;
    ArrayList<String> sid,name;
    String sem[]={"1","2","3","4","5"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_attendance);
        l = findViewById(R.id.sl);
        s1=(Spinner)findViewById(R.id.spinner3);
        ArrayAdapter<String> add=new ArrayAdapter<>(student_attendance.this,android.R.layout.simple_list_item_1,sem);
        s1.setAdapter(add);

        s1.setOnItemSelectedListener(this);
        hour=s1.getSelectedItem().toString();
        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        b1 = (Button) findViewById(R.id.button3);
        l.setOnItemClickListener(this);
        String url = "http://" + sp.getString("ip", "") + ":5000/search";
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(student_attendance.this);


        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++", response);
                try {

                    JSONArray ar = new JSONArray(response);
                    sid = new ArrayList<>();
                    name = new ArrayList<>();


                    for (int i = 0; i < ar.length(); i++) {
                        JSONObject jo = ar.getJSONObject(i);
                        sid.add(jo.getString("login_id"));
                        name.add(jo.getString("name"));

                    }

                    ArrayAdapter<String> ad = new ArrayAdapter<String>(student_attendance.this, android.R.layout.simple_list_item_multiple_choice, name);
                    l.setAdapter(ad);
//                    l.setAdapter(new custom2(student_attendance.this,sid,name));


                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "exp" + e, Toast.LENGTH_LONG).show();

                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(getApplicationContext(), "Error" + error, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("sem", sp.getString("sem", ""));
                params.put("cid", sp.getString("cid", ""));


                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);


        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub





                    InsertRequest(s);
                    s="";
                }


        });

    }








    private void InsertRequest(final String s) {
        // TODO Auto-generated method stub


        RequestQueue queue = Volley.newRequestQueue(student_attendance.this);
        url = "http://" +  sp.getString("ip","")  + ":5000/attend";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++", response);
                try {
                    JSONObject json = new JSONObject(response);
                    String res = json.getString("result");

                    if (res.equalsIgnoreCase("success")) {
                        Toast.makeText(student_attendance.this, "Success", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),attendance.class));

                    } else if(res.equalsIgnoreCase("error")){
                        Toast.makeText(student_attendance.this, "Attendance already marked", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),student_attendance.class));

                    }
                    else {

                        Toast.makeText(student_attendance.this, "Invalid ", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),student_attendance.class));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(getApplicationContext(), "Error" + error, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();


                params.put("attd", s);
                params.put("sem", sp.getString("sem", ""));
                params.put("cid", sp.getString("cid", ""));
                params.put("hour",hour);


                return params;
            }
        };
        queue.add(stringRequest);

    }








    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1,
                            int arg2, long arg3) {
        // TODO Auto-generated method stub


        s=s+sid.get(arg2)+"@";
        count=count+1;


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


//
//	@Override
//	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//		// TODO Auto-generated method stub
//		plnt=pid.get(arg2);
//	}

}

