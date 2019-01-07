package com.mad.rubricon;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class LabEvalSelectQuestionActivity extends AppCompatActivity {
    private  String courseId;
    private String weekId;
    private String teacherId;
    private String weekText;

    TextView courseTitleView;
    TextView courseWeekView;
    ListView questionsListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_eval_select_question);

        courseId = getIntent().getStringExtra("CourseId");
        weekText = getIntent().getStringExtra("CourseWeek");
        weekId = getIntent().getStringExtra("CourseWeekId");
        teacherId = getIntent().getStringExtra("TeacherId");
        courseTitleView = (TextView) findViewById(R.id.textViewCourseTitle);

        // get the course title from db using id and display
        // courseTitleView.setText("Course Id: " + GetCourseTitle());
        courseTitleView.setText("Course Id: " + courseId);

        courseWeekView = (TextView) findViewById(R.id.textViewCourseWeek);
        courseWeekView.setText("Week Id: " + weekText);

        questionsListView = (ListView) findViewById(R.id.listViewCourseWeekQuestions);
        final ArrayList<String>  questionsList =  GetQuestionsList();

        if(questionsList.size()>0){
            ArrayList<String> qustnsTitle = new ArrayList<>();
            for(int i=0;i<questionsList.size();i++){
                String[] split = questionsList.get(i).split(",");
                qustnsTitle.add(split[1]);
            }
            // when a week is clicked from the list
            questionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String value = (String)parent.getItemAtPosition(position);
                    Log.i("DebugLog", value);

                    // code to go to next activity where Questions will be Displayed for Evaluation
                    GoToEvaluationActivity(questionsList.get(position).split(",")[0]);
                }
            });

            ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.single_item_list, qustnsTitle);
            questionsListView.setAdapter(adapter);
        }


        Toolbar toolbar = findViewById(R.id.toolbar_lab_question_activity);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle("Lab Questions");
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

        QuestionTable table = new QuestionTable(this);
        table.open();

        ArrayList<String> list = table.getQuestions(Integer.parseInt(weekId));
        table.close();
        // Dummy Data Goes Here
        questions.add("Question 1");
        questions.add("Question 2");
        questions.add("Question 3");
        questions.add("Question 4");
        questions.add("Question 5");

        return  list;
    }
    public  void GoToEvaluationActivity(String val){
        Intent intent = new Intent(this, LabEvalEnterMarksActivity.class);
        intent.putExtra("CourseId", courseId);
        intent.putExtra("CourseWeek", weekId);
        intent.putExtra("QuestionId", val);
        intent.putExtra("TeacherId", teacherId);

        startActivity(intent);
    }

}
