package com.example.hp.college_management;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Parent_send_complaint extends AppCompatActivity {
    EditText e1;
    Button b;
    SharedPreferences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_send_complaint);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        e1=(EditText)findViewById(R.id.editText4);
        b=(Button)findViewById(R.id.button4);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String complaint=e1.getText().toString();
                if(complaint.equalsIgnoreCase(""))
                {
                    e1.setError("Enter Complaint");
                }
                else if(!complaint.matches("^[a-zA-Z]*$"))
                {
                    e1.setError("characters allowed");
                }
                else {
                    RequestQueue queue = Volley.newRequestQueue(Parent_send_complaint.this);
                    String url1 = "http://" + sh.getString("ip", "") + ":5000/parent_send_complaint";

                    // Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the response string.
                            Log.d("+++++++++++++++++", response);
                            try {
                                JSONObject json = new JSONObject(response);
                                String res = json.getString("task");


//                            pDialog.hide();
                                if (res.equalsIgnoreCase("success")) {
                                    Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_LONG).show();
                                    Intent ik = new Intent(getApplicationContext(), Parent_home.class);
                                    startActivity(ik);

                                } else

                                {
                                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
                                    Intent ik = new Intent(getApplicationContext(), Parent_home.class);
                                    startActivity(ik);

                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

//                        pDialog.hide();
                            Toast.makeText(getApplicationContext(), "Error" + error, Toast.LENGTH_LONG).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("complaint", complaint);
                            params.put("lid", sh.getString("id", ""));

                            return params;
                        }
                    };
                    // Add the request to the RequestQueue.
                    queue.add(stringRequest);
                }
            }
        });

    }
}
