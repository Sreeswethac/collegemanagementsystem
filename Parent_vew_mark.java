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

public class Parent_vew_mark extends AppCompatActivity implements AdapterView.OnItemSelectedListener,AdapterView.OnItemClickListener  {
    Spinner s1,s2;
    Button b1;
    ListView l1;
    SharedPreferences sh;
    String subjectid,semester,studentid;
    ArrayList<String>subject,subid,student,admsnno,totalmark,loginid,internalone,internaltwo,assignment,total;
    String sem[]={"1","2","3","4","5","6"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_vew_mark);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        s1=(Spinner)findViewById(R.id.spinner5);
        s2=(Spinner)findViewById(R.id.spinner6);
        b1=(Button)findViewById(R.id.button6);
        l1=(ListView)findViewById(R.id.listvew);

        ArrayAdapter<String> add=new ArrayAdapter<>(Parent_vew_mark.this,android.R.layout.simple_list_item_1,sem);
        s2.setAdapter(add);

        s2.setOnItemSelectedListener(this);
        semester=s2.getSelectedItem().toString();



        String url ="http://"+sh.getString("ip","")+":5000/select_subjectt";
        s1.setOnItemSelectedListener(Parent_vew_mark.this);
        RequestQueue queue = Volley.newRequestQueue(Parent_vew_mark.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);

                    subject= new ArrayList<>(ar.length());
                    subid= new ArrayList<>(ar.length());

                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        subject.add(jo.getString("subject"));
                        subid.add(jo.getString("allot_id"));



                    }

                    ArrayAdapter<String> ad=new ArrayAdapter<String>(Parent_vew_mark.this,android.R.layout.simple_spinner_item,subject);
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
                String url1 ="http://"+sh.getString("ip", "") + ":5000/parent_view_mark";
                RequestQueue queue = Volley.newRequestQueue(Parent_vew_mark.this);

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

                            l1.setAdapter(new Custom4(Parent_vew_mark.this,internalone,internaltwo,assignment,total));
                            l1.setOnItemClickListener(Parent_vew_mark.this);

                        } catch (Exception e) {
                            Log.d("=========", e.toString());
                        }


                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(Parent_vew_mark.this, "err"+error, Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("subid",subjectid);
                        params.put("sem",semester);
                        params.put("lid",sh.getString("id",""));
                        return params;
                    }
                };
                queue.add(stringRequest);

            }
        });







    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


        if(adapterView==s1)
        {
            subjectid=subid.get(i);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Intent in = new Intent(getApplicationContext(), Parent_view_detailed_mark.class);
//        in.putExtra("sid",studentid);


        startActivity(in);




    }
}
