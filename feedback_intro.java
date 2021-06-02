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



import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class feedback_intro extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner s2;
    Button b1;
    SharedPreferences sh;
    String courseid, subjectid,rid;
    Uri imageUri=null;
    //	ImageView i1;
//    JSONParser jsonParser = new JSONParser();
    public static String ur="";
    public static String url="";

    public static ArrayList<String> qstn,post;
    public static ArrayList<String> opt1;
    public static ArrayList<String> opt2;
    public static ArrayList<String> opt3;
    public static ArrayList<String> opt4;
    public static ArrayList<String> ans;
    //	    public static String sqstn="";
    private static final int CAMERA_PIC_REQUEST = 0;
    public static String encodedImage="";
    Handler hd = new Handler();
    ImageView i1;
    String imageurl="";

    ArrayList<String> course, cid, subject, sid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_intro);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        s1 = (Spinner) findViewById(R.id.spinner);
        s2 = (Spinner) findViewById(R.id.spinner6);
        b1 = (Button) findViewById(R.id.button2);
        try
        {
            if(android.os.Build.VERSION.SDK_INT >9)
            {
                StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
        }
        catch(Exception e)
        {

        }


//        String url1 = "http://" + sh.getString("ip", "") + ":5000/select_course";
//
//        RequestQueue queue = Volley.newRequestQueue(feedback_intro.this);
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                // Display the response string.
//                Log.d("+++++++++++++++++", response);
//                try {
//
//                    JSONArray ar = new JSONArray(response);
//
//                    course = new ArrayList<>(ar.length());
//                    cid = new ArrayList<>(ar.length());
//
//                    for (int i = 0; i < ar.length(); i++) {
//                        JSONObject jo = ar.getJSONObject(i);
//                        course.add(jo.getString("coursename"));
//                        cid.add(jo.getString("course_id"));
//
//
//                    }
//
//                    ArrayAdapter<String> ad = new ArrayAdapter<String>(feedback_intro.this, android.R.layout.simple_spinner_item, course);
//                    s1.setAdapter(ad);
//                    s1.setOnItemSelectedListener(feedback_intro.this);
//
//                    // l1.setAdapter(new custom2(Monitoring_signal.this,notification,date));
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
//            }
//        });
//        // Add the request to the RequestQueue.
//        queue.add(stringRequest);


        String url2 = "http://" + sh.getString("ip", "") + ":5000/select_subject";

        RequestQueue queue1 = Volley.newRequestQueue(feedback_intro.this);

        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++", response);
                try {

                    JSONArray ar = new JSONArray(response);

                    subject = new ArrayList<>(ar.length());
                    sid = new ArrayList<>(ar.length());

                    for (int i = 0; i < ar.length(); i++) {
                        JSONObject jo = ar.getJSONObject(i);
                        subject.add(jo.getString("subject"));
                        sid.add(jo.getString("allot_id"));


                    }

                    ArrayAdapter<String> ad = new ArrayAdapter<String>(feedback_intro.this, android.R.layout.simple_spinner_item, subject);
                    s2.setAdapter(ad);
                    s2.setOnItemSelectedListener(feedback_intro.this);

                    // l1.setAdapter(new custom2(Monitoring_signal.this,notification,date));

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }
        });
        // Add the request to the RequestQueue.
        queue1.add(stringRequest1);


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        subjectid=sid.get(i);
        SharedPreferences.Editor ed = sh.edit();
        ed.putString("sid", subjectid);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Insert1().execute();


            }
        });


    }



    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {


    }




    class Insert extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        protected String doInBackground(String... args)
        {



            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
//        	e.setText(values[0]);
            // TODO Auto-generated method stub, text, duration)
        }
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            // pDialog.dismiss();



        }

    }

    class Insert1 extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        protected String doInBackground(String... args)
        {

            url="http://"+sh.getString("ip","")+":5000/addques";

            RequestQueue queue = Volley.newRequestQueue(feedback_intro.this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // Display the response string.
                    Log.d("+++++++++++++++++",response);
                    try {

                        JSONArray ar=new JSONArray(response);
                        qstn=new ArrayList<String>();
//                        opt1=new ArrayList<String>();
//                        opt2=new ArrayList<String>();
//                        opt3=new ArrayList<String>();
//                        opt4=new ArrayList<String>();
//                        ans=new ArrayList<String>();

                        for(int i=0;i<ar.length();i++)
                        {
                            JSONObject cc=ar.getJSONObject(i);
                            qstn.add(cc.getString("Question"));
//                            opt1.add(cc.getString("option1"));
//                            opt2.add(cc.getString("option2"));
//                            opt3.add(cc.getString("option3"));
//                            opt4.add(cc.getString("option4"));
//                            ans.add(cc.getString("answer"));
                        }


                    } catch (Exception e) {
                        Log.d("rrrrr=========", e.toString());
                    }

                    if (qstn.size()!=0) {
                        Intent i=new Intent(getApplicationContext(),feedack_analysis.class);
                        i.putStringArrayListExtra("qstn", qstn);
//                        i.putStringArrayListExtra("opt1", opt1);
//                        i.putStringArrayListExtra("opt2", opt2);
//                        i.putStringArrayListExtra("opt3", opt3);
//                        i.putStringArrayListExtra("opt4", opt4);
//                        i.putStringArrayListExtra("ans", ans);
                        startActivity(i);


                    }

//                    Toast.makeText(getApplicationContext(), post, Toast.LENGTH_LONG).show();

                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.d("errrr=========", error.toString());
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String,String>();
                    params.put("subjectid", subjectid);
//                    params.put("cmpid", cmpid);

                    return params;
                }
            };
            queue.add(stringRequest);

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
//        	e.setText(values[0]);
            // TODO Auto-generated method stub, text, duration)
        }
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            // pDialog.dismiss();



        }
    }

}
