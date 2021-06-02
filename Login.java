package com.example.hp.college_management;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class Login extends AppCompatActivity {
    EditText e1,e2;
    Button b1,b2;
    SharedPreferences sh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        e1=(EditText)findViewById(R.id.editText2);
        e2=(EditText)findViewById(R.id.editText3);
        b1=(Button)findViewById(R.id.button);
        b2=(Button)findViewById(R.id.button3);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username=e1.getText().toString();
                final String password=e2.getText().toString();
                if(username.equalsIgnoreCase(""))
                {
                    e1.setError("Enter username");
                }

                else if(password.equalsIgnoreCase(""))
                {
                    e2.setError("Enter password");
                }
                else {
                    RequestQueue queue = Volley.newRequestQueue(Login.this);
                    String url = "http://" + sh.getString("ip", "") + ":5000/login";

                    // Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the response string.
                            Log.d("+++++++++++++++++", response);
                            try {
                                JSONObject json = new JSONObject(response);
                                String res = json.getString("task");


                                String type = json.getString("type");
//                            pDialog.hide();
                                if (res.equalsIgnoreCase("invalid")) {
                                    Toast.makeText(getApplicationContext(), res, Toast.LENGTH_LONG).show();
                                } else if (type.equals("student")) {


                                    Toast.makeText(getApplicationContext(), type, Toast.LENGTH_LONG).show();

//                                String sem = json.getString("sem");
//                                String crse = json.getString("crse");
                                    SharedPreferences.Editor ed = sh.edit();
                                    ed.putString("id", res);
//                                ed.putString("sem", sem);
//                                ed.putString("crse", crse);
                                    ed.commit();


                                    startActivity(new Intent(getApplicationContext(), Student_home.class));
                                } else if (type.equals("parent")) {
                                    Intent i = new Intent(getApplicationContext(), LocationService.class);
                                    startService(i);
                                    SharedPreferences.Editor ed = sh.edit();
                                    ed.putString("id", res);
                                    ed.commit();
                                    startActivity(new Intent(getApplicationContext(), Parent_home.class));
                                }

                                else if (type.equals("staff")) {
                                    Intent i = new Intent(getApplicationContext(), LocationService.class);
                                    startService(i);
                                    SharedPreferences.Editor ed = sh.edit();
                                    ed.putString("id", res);
                                    ed.commit();
                                    startActivity(new Intent(getApplicationContext(), attendance.class));
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
                            params.put("uname", username);
                            params.put("pass", password);

                            return params;
                        }
                    };
                    // Add the request to the RequestQueue.
                    queue.add(stringRequest);
                }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),Parent_Registration.class);
                startActivity(i);

            }
        });

    }


    public void onBackPressed() {
        // TODO Auto-generated method stub
        AlertDialog.Builder ald=new AlertDialog.Builder(Login.this);
        ald.setTitle("Do you want to Exit")
                .setPositiveButton(" YES ", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent in=new Intent(Intent.ACTION_MAIN);
                        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        in.addCategory(Intent.CATEGORY_HOME);
                        startActivity(in);
                    }
                })
                .setNegativeButton(" NO ", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog al=ald.create();
        al.show();}
}
