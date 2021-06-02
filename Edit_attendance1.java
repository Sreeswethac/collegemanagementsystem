package com.example.hp.college_management;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Edit_attendance1 extends AppCompatActivity {

    Spinner s1;
    String sid;
    String date;
    SharedPreferences sh;
    Button b1;
    String s[]={"1","0"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_attendance1);
        b1=(Button)findViewById(R.id.button6);
        s1=(Spinner)findViewById(R.id.spinner);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sid=getIntent().getStringExtra("sid");
        date=getIntent().getStringExtra("date");
        final ArrayAdapter<String> sem=new ArrayAdapter<String>(Edit_attendance1.this,android.R.layout.simple_list_item_1,s);
        s1.setAdapter(sem);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue queue = Volley.newRequestQueue(Edit_attendance1.this);
                String url = "http://" + sh.getString("ip", "") + ":5000/edit_attendance";

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
                                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(), attendance.class));
                            } else {

                                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();

                                startActivity(new Intent(getApplicationContext(), attendance.class));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(), "Error"+error, Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("att", s1.getSelectedItem().toString());
                        params.put("sid", sid);
                        params.put("date", date);

                        return params;
                    }
                };
                // Add the request to the RequestQueue.
                queue.add(stringRequest);


            }
        });
    }
}