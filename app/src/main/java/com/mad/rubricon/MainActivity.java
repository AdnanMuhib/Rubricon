package com.mad.rubricon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_home:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }

    }
}
