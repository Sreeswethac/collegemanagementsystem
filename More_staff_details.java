package com.example.hp.college_management;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class More_staff_details extends AppCompatActivity {
    TextView t1,t2,t3,t4,t5,t6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_staff_details);
//        t1=(TextView)findViewById(R.id.textView16);
        t2=(TextView)findViewById(R.id.textView18);
        t3=(TextView)findViewById(R.id.textView20);
        t4=(TextView)findViewById(R.id.textView22);
        t5=(TextView)findViewById(R.id.textView24);
        t6=(TextView)findViewById(R.id.textView26);
//        t1.setText(getIntent().getStringExtra("qualificaton"));
        t2.setText(getIntent().getStringExtra("experience"));
        t3.setText(getIntent().getStringExtra("joiningdate"));
        t4.setText(getIntent().getStringExtra("dob"));
        t5.setText(getIntent().getStringExtra("district"));
        t6.setText(getIntent().getStringExtra("state"));
        






    }
}
