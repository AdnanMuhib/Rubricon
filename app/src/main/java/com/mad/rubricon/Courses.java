package com.mad.rubricon;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class Courses extends AppCompatActivity {
    DBManager db;
    private DrawerLayout mDrawerLayout;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.signoutmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.item:
                //Intent j = new Intent(this,Stats.class);
                Intent j = new Intent(this,Login.class);
                j.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(j);
                return true;
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        try {
            db = new DBManager(this);
            db.open();
        }catch(Exception e)
        {
        }
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped

                        switch (menuItem.getItemId()){
                            case R.id.nav_create_course:
                                createCourse();
                                break;
                            case R.id.nav_modify_course:
                                modifyCourse();
                                break;
                            case R.id.nav_clo_creation:
                                cloCreation();
                                break;
                            case R.id.nav_course_assign:
                                allocateCourses();
                                break;
                            case R.id.nav_teacher_manage:
                                teacher_register();
                                break;
                            case R.id.nav_admin_manage:
                                admin_register();
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
        Toolbar toolbar = findViewById(R.id.toolbar_courses);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle("Admin Panel");
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
    }
    public void createCourse()
    {
        Intent i = new Intent(this,CourseCreation.class);
        startActivity(i);
    }
    public void modifyCourse()
    {
        Intent i = new Intent(this,CourseModification.class);
        startActivity(i);
    }
    public void cloCreation()
    {
        Intent i = new Intent(this,Clo.class);
        startActivity(i);
    }
    public void teacher_register(){
        Intent intent = new Intent(this, TeacherSignUp.class);
        startActivity(intent);
    }
    public void admin_register(){
        Intent intent = new Intent(this, AdminSignUp.class);
        startActivity(intent);
    }
    public void allocateCourses()
    {
        Cursor c = db.chkCourses();
        if(c==null || c.getCount()<=0)
        {
            showMessage("Error", "No Course Registered by an Admin!");
            return;
        }
        else {
            Intent i = new Intent(this,AllocateCourses.class);
            startActivity(i);
        }

    }
    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        final Intent j = new Intent(this, Login.class);
        //final Intent j = new Intent(this, Stats.class);
        builder.setTitle("Log Out")
                .setMessage("Are you sure you want to logout")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        j.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(j);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


}
