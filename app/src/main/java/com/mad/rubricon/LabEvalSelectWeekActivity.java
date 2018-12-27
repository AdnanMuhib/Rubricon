package com.mad.rubricon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class LabEvalSelectWeekActivity extends AppCompatActivity {
    private  int courseId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_eval_select_week);
        courseId = Integer.parseInt(getIntent().getStringExtra("crsId"));

        // find the Weekly Labs using Lab Course Id from Data Base
        ArrayList<String> courseWeeksList = GetCourseWeeks();
        courseWeeksList.add("Course Id" + courseId);
        ListView mCourseListView  = (ListView) findViewById(R.id.listViewCourseWeeks);

        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, courseWeeksList);
        mCourseListView.setAdapter(adapter);

    }

    public ArrayList<String> GetCourseWeeks(){

        // Get a List of Lab Weeks using course Id from Database
            // code goes here

        // Dummy Data for Testing
        ArrayList<String> lst = new ArrayList<String>();
        lst.add("Lab Week 1");
        lst.add("Lab Week 2");
        lst.add("Lab Week 3");
        lst.add("Lab Week 4");
        lst.add("Lab Week 5");

        return  lst;
    }


}
