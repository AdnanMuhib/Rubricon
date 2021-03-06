package  com.mad.rubricon;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class StudentPanel extends AppCompatActivity {
    TextView text;
    DBManager db;
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
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_panel);
        text = (TextView) findViewById(R.id.textView);
        Intent StudentReg = getIntent();
        String RegNo = StudentReg.getStringExtra("RegNo");
        text.setText(RegNo);
        try {
            db = new DBManager(this);
            db.open();
        }catch(Exception e)
        {
        }
        Toolbar toolbar = findViewById(R.id.toolbar_courses);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle("Student Panel");
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white);
    }
    public void registerCourse(View v)
    {
        Cursor c = db.chkCourses();
        if(c==null || c.getCount()<=0)
        {
            showMessage("Error", "No Course Registered by an Admin!");
            return;
        }
        else {
            Intent i = new Intent(this, StudentCourses.class);
            i.putExtra("RegNo", text.getText().toString());
            startActivity(i);
        }

    }
    public void viewCourses(View v)
    {
        Cursor c = db.getStudentCoursesList(text.getText().toString());
        // Checking if no records found
        if (c==null || c.getCount() <= 0) {
            showMessage("Error", "No Course Allocated!");
            return;
        }
        Intent i = new Intent(this,StudentCourseList.class);
        i.putExtra("RegNo",text.getText().toString());
        startActivity(i);
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