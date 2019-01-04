package com.mad.rubricon;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
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

        // when a week is clicked from the list
        mCourseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value = (String)parent.getItemAtPosition(position);
                Log.i("DebugLog", value);

                // code to go to next activity where Questions will be Displayed for Evaluation
                GoToQuestionsActivity(value);
            }
        });
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.single_item_list, courseWeeksList);

        mCourseListView.setAdapter(adapter);

        Toolbar toolbar = findViewById(R.id.toolbar_lab_week_list);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle("Lab Weeks");
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
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
    public void GoToQuestionsActivity(String val){
        Intent intent = new Intent(this, LabEvalSelectQuestionActivity.class);
        intent.putExtra("CourseWeek", val);
        intent.putExtra("CourseId", courseId);

        Log.i("DebugLog", "Selected Course week is:  "+ val);
        startActivity(intent);
    }


}
