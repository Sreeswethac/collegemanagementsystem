package com.example.hp.college_management;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Parent_view_detailed_mark extends AppCompatActivity {
    ListView l1;
    SharedPreferences sh;
    String studid;
    ArrayList<String>course,type,mark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_view_detailed_mark);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        l1=(ListView)findViewById(R.id.listview);
//       studid=getIntent().getStringExtra("sid");


        String url1 ="http://"+sh.getString("ip", "") + ":5000/parent_view_deialed_mark";
        RequestQueue queue = Volley.newRequestQueue(Parent_view_detailed_mark.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url1,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);
                    course= new ArrayList<>();
                    type= new ArrayList<>();
                    mark= new ArrayList<>();



                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        course.add(jo.getString("coursename"));
                        type.add(jo.getString("type"));
                        mark.add(jo.getString("mark"));



                    }

                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);

                    l1.setAdapter(new Custom3(Parent_view_detailed_mark.this,course,type,mark));
//                    l1.setOnItemClickListener(Parent_vew_mark.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Parent_view_detailed_mark.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("sid",sh.getString("id",""));

                return params;
            }
        };
        queue.add(stringRequest);





    }
}
