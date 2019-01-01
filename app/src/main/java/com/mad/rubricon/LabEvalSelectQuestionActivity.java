package com.mad.rubricon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class LabEvalSelectQuestionActivity extends AppCompatActivity {
    private  int courseId;
    private String weekId;

    TextView courseTitleView;
    TextView courseWeekView;
    ListView questionsListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_eval_select_question);

        courseId = getIntent().getIntExtra("CourseId", 0);
        weekId = getIntent().getStringExtra("CourseWeek");

        courseTitleView = (TextView) findViewById(R.id.textViewCourseTitle);

        // get the course title from db using id and display
        // courseTitleView.setText("Course Id: " + GetCourseTitle());
        courseTitleView.setText("Course Id: " + courseId);

        courseWeekView = (TextView) findViewById(R.id.textViewCourseWeek);
        courseWeekView.setText("Week Id: " + weekId);

        questionsListView = (ListView) findViewById(R.id.listViewCourseWeekQuestions);
        ArrayList<String>  questionsList =  GetQuestionsList();

        // when a week is clicked from the list
        questionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value = (String)parent.getItemAtPosition(position);
                Log.i("DebugLog", value);

                // code to go to next activity where Questions will be Displayed for Evaluation
                GoToEvaluationActivity(value);
            }
        });

        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, questionsList);

        questionsListView.setAdapter(adapter);
    }
    public String GetCourseTitle(){
        // Code to get the name of the course from database using Course Id i.e
        // select coursename from coursetable where courseid = ??
        return "";
    }

    public String GetCourseWeekName(){
        // Code to get the name of the course Week from database using Week Id i.e
        // select courseweekname from courseWeektable where courseWeekid = ??
        return "";
    }
    public ArrayList<String> GetQuestionsList(){

        ArrayList<String> questions = new ArrayList<String>();
        /// Code to get the Questions from the Database goes here

        // Dummy Data Goes Here
        questions.add("Question 1");
        questions.add("Question 2");
        questions.add("Question 3");
        questions.add("Question 4");
        questions.add("Question 5");

        return  questions;
    }
    public  void GoToEvaluationActivity(String val){
        Intent intent = new Intent(this, LabEvalEnterMarksActivity.class);
        intent.putExtra("CourseId", courseId);
        intent.putExtra("CourseWeek", weekId);
        intent.putExtra("QuestionId", val);

        startActivity(intent);
    }

}
