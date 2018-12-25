package com.mad.rubricon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnCourseCreationClicked(View view){

    }

    public  void btnTeacherCreationClicked(View view){

    }

    public  void btnStudentRegistrationClicked(View view){

    }

    public  void btnCloCreationClicked(View view){

    }

    public  void btnRubricsDefinitionClicked(View view){
            Intent intent = new Intent(this, NewRubricActivity.class);
            startActivity(intent);
    }

    public  void btnLabCreationClicked(View view){

    }

    public  void btnLabEvaluationClicked(View view){
        Intent intent = new Intent(this, LabEvaluationActivity.class);
        startActivity(intent);
    }

    public  void btnLabReportingClicked(View view){

    }
}
