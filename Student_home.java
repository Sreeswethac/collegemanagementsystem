package com.example.hp.college_management;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.GridView;
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

public class Student_home extends AppCompatActivity

        implements NavigationView.OnNavigationItemSelectedListener {
    GridView l1;
    String url="";
    SharedPreferences sh;
    public static ArrayList<String> image,caption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




        l1=(GridView) findViewById(R.id.po);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        String url = "http://" + sh.getString("ip", "") + ":5000/view_gallery";





        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                //  Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();

                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);
                    image=new ArrayList<>();
                    image=new ArrayList<>();
                    caption=new ArrayList<>();




                    for (int i=0;i<ar.length();i++){
                        JSONObject jo=ar.getJSONObject(i);

                        image.add(jo.getString("image"));
                        caption.add(jo.getString("caption"));



                    }

//                    ArrayAdapter<String> ad=new ArrayAdapter<String>(Searchnearestcharity.this,android.R.layout.simple_list_item_1,cname);
//                    l1.setAdapter(ad);

                    l1.setAdapter(new Custom_product_home(getApplicationContext(),image,caption));
//                    l1.setOnItemClickListener((AdapterView.OnItemClickListener) Student_home.this);

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();


                return params;
            }
        };

        // Add the request to the RequestQueue.
        requestQueue.add(stringRequest);



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.student_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.viewmark) {
            // Handle the camera action

            Intent i=new Intent(getApplicationContext(),student_view_total_mark
                    .class);
            startActivity(i);


        } else if (id == R.id.attedance) {

            Intent i=new Intent(getApplicationContext(),Student_view_attendance.class);
            startActivity(i);


        } else if (id == R.id.postcom) {
            Intent i=new Intent(getApplicationContext(),Send_Complant.class);
            startActivity(i);

        }


        else if (id == R.id.viewreply) {
            Intent i=new Intent(getApplicationContext(),Student_view_complaintreply.class);
            startActivity(i);

        }

        else if (id == R.id.viewstaff) {
            Intent i=new Intent(getApplicationContext(),Student_View_staff.class);
            startActivity(i);

        }

        else if (id == R.id.feedack) {
            Intent i=new Intent(getApplicationContext(),feedback_intro.class);
            startActivity(i);

        }

        else if (id == R.id.logout) {
            Intent i=new Intent(getApplicationContext(),Login.class);
            startActivity(i);

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
