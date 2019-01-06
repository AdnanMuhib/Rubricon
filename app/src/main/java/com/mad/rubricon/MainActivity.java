package com.mad.rubricon;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped

                        switch (menuItem.getItemId()){
                            case R.id.nav_lab_eval:
                                btnLabEvaluationClicked();
                                break;
                            case R.id.nav_lab_report:
                                btnLabReportingClicked();
                                break;
                            case R.id.nav_lab_create:
                                btnLabCreateClicked();
                                break;
                            case R.id.nav_rubric:
                                btnRubricClicked();
                                break;
                            default:
                                break;
                        }
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void btnCourseCreationClicked(View view){

    }

    public  void btnTeacherCreationClicked(View view){

    }

    public  void btnStudentRegistrationClicked(View view){

    }

    public  void btnCloCreationClicked(View view){

    }

    public  void btnRubricsDefinitionClicked(){
            Intent intent = new Intent(this, NewRubricActivity.class);
            startActivity(intent);
    }

    public  void btnLabCreationClicked(View view){

    }

    public  void btnLabEvaluationClicked(){
        Intent intent = new Intent(this, LabEvaluationActivity.class);
        intent.putExtra("ActivityName","LabEvaluation");

        startActivity(intent);
    }

    public  void btnLabCreateClicked(){
        Intent intent = new Intent(this, LabActivity.class);
        startActivity(intent);
    }
    public  void btnRubricClicked(){
        Intent intent = new Intent(this, RubricsActivity.class);
        startActivity(intent);
    }
    public  void btnLabReportingClicked(){
        Intent intent = new Intent(this, LabEvaluationActivity.class);
        intent.putExtra("ActivityName","LabReporting");
        startActivity(intent);
    }
}
