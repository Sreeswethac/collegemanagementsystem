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
import android.widget.Button;
import android.widget.EditText;
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

public class Parent_Registration extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner s1,s2;
    EditText e1,e2,e3,e4,e5,e6;
    Button b1;
    SharedPreferences sh;
    int position;
    ArrayList<String> admission_no,studid;
    String studentid,dist;
    String district[]={"Kasaragod","Kannur","Wayanad","Kozhikode","Malappuram","Palakkad","Thrissur","Eranakulam","Idukki","Kottayam","Alappuzha","Pathanamthitta","Kollam","Thiruvananthapuram"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent__registration);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        e1=(EditText)findViewById(R.id.editText5);
        e2=(EditText)findViewById(R.id.editText8);
        e3=(EditText)findViewById(R.id.editText7);
        e4=(EditText)findViewById(R.id.editText9);
        e5=(EditText)findViewById(R.id.editText10);
        e6=(EditText)findViewById(R.id.editText11);
        b1=(Button)findViewById(R.id.button7);
        s1=(Spinner)findViewById(R.id.spinner7);
        s2=(Spinner)findViewById(R.id.spinner9);
        ArrayAdapter<String> ad=new ArrayAdapter<String>(Parent_Registration.this,android.R.layout.simple_list_item_1,district);
        s2.setAdapter(ad);

        s2.setOnItemSelectedListener(this);
        dist=s2.getSelectedItem().toString();

        String url ="http://"+sh.getString("ip","")+":5000/select_admssion_number";
        s1.setOnItemSelectedListener(Parent_Registration.this);
        RequestQueue queue = Volley.newRequestQueue(Parent_Registration.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);

                    admission_no= new ArrayList<>(ar.length());
                    studid= new ArrayList<>(ar.length());

                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        studid.add(jo.getString("login_id"));
                        admission_no.add(jo.getString("admno"));



                    }

                    ArrayAdapter<String> ad=new ArrayAdapter<String>(Parent_Registration.this,android.R.layout.simple_spinner_item,admission_no);
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




        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name=e1.getText().toString();
                final String pincode=e2.getText().toString();
                final String place=e3.getText().toString();
                final String contact=e4.getText().toString();
                final String username=e5.getText().toString();
                final String password=e6.getText().toString();


                if(name.equalsIgnoreCase(""))
                {
                    e1.setError("Enter name");
                }
                else if(!name.matches("^[a-zA-Z]*$"))
                {
                    e1.setError("characters allowed");
                }


                else if(pincode.equalsIgnoreCase(""))
                {
                    e2.setError("Enter pincode");
                }
                else if(pincode.length()!=6)
                {
                    e2.setError("Invalid pincode");
                }
                else if(place.equalsIgnoreCase(""))
                {
                    e3.setError("Enter place");
                }
                else if(!place.matches("^[a-zA-Z]*$"))
                {
                    e3.setError("characters allowed");
                }
                else if(contact.equalsIgnoreCase(""))
                {
                    e4.setError("Enter contact");
                }
                else if(contact.length()!=10)
                {
                    e4.setError("Invalid phoneno");
                }
                else if(username.equalsIgnoreCase(""))
                {
                    e5.setError("Enter username");
                }
                else if(password.equalsIgnoreCase(""))
                {
                    e6.setError("Enter password");
                }
                else {
                    RequestQueue queue = Volley.newRequestQueue(Parent_Registration.this);
                    String url1 = "http://" + sh.getString("ip", "") + ":5000/parentregistration";

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
                                    Toast.makeText(getApplicationContext(), "success fully registered", Toast.LENGTH_LONG).show();
                                    Intent ik = new Intent(getApplicationContext(), Login.class);
                                    startActivity(ik);

                                } else {
                                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
                                    Intent ik = new Intent(getApplicationContext(), Login.class);
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
                            params.put("name", name);
                            params.put("pincode", pincode);
                            params.put("place", place);
                            params.put("contact", contact);
                            params.put("username", username);
                            params.put("password", password);
                            params.put("studid", studentid);
                            params.put("district", dist);

                            return params;
                        }
                    };
                    // Add the request to the RequestQueue.
                    queue.add(stringRequest);
                }



            }
        });



    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView , View view, int position, long id) {


        if(adapterView==s1)
        {
            studentid=studid.get(position);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
