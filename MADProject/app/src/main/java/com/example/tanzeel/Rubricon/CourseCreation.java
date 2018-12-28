package com.example.tanzeel.Rubricon;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;

public class CourseCreation extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner Section;
    EditText Coursetitle,Coursecode,SessionId,TheoryCHr,LabCHr,knowledgeArea,Coursereq;
    RelativeLayout relTheory,relLab,sec,req;
    RadioButton r1,r2;
    RadioGroup rad;
    DBManager db;
    Button b1,b2;
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
                Intent j = new Intent(this,MainActivity.class);
                j.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(j);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_creation);
        Section = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.sections,android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Section.setAdapter(adapter);
        Section.setOnItemSelectedListener(this);
        Coursereq = (EditText) findViewById(R.id.Coursereq);
        Coursetitle = (EditText) findViewById(R.id.Coursetitle);
        Coursecode = (EditText) findViewById(R.id.Coursecode);
        SessionId = (EditText) findViewById(R.id.SessionID);
        TheoryCHr = (EditText) findViewById(R.id.TheoryCHr);
        LabCHr = (EditText) findViewById(R.id.LabCHr);
        knowledgeArea = (EditText) findViewById(R.id.KnowledgeArea);
        relTheory = (RelativeLayout) findViewById(R.id.relativeLayout4);
        relLab = (RelativeLayout) findViewById(R.id.relativeLayout5);
        sec = (RelativeLayout) findViewById(R.id.relativeLayout7);
        b1 = (Button) findViewById(R.id.btn_create);
        b2 = (Button) findViewById(R.id.btn_done);
        rad = (RadioGroup) findViewById(R.id.radiogroup);
        req =(RelativeLayout) findViewById(R.id.relativeLayout8);
        try {
            db = new DBManager(this);
            db.open();
        }catch(Exception e)
        {
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public void Course(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio:
                if (checked)
                {
                    relTheory.setVisibility(View.VISIBLE);
                    relLab.setVisibility(View.GONE);
                }
                break;

            case R.id.radio2:
                if (checked)
                {
                    relTheory.setVisibility(View.GONE);
                    relLab.setVisibility(View.VISIBLE);
                }

                break;
        }
    }
    public void createCourse(View v)
    {
        if(sec.getVisibility()== View.GONE && req.getVisibility() == View.GONE)
        {
            if(relTheory.getVisibility() == View.VISIBLE && relLab.getVisibility() == View.GONE)
            {
                int LabCr = 0;
                if (Coursecode.getText().toString().trim().length() == 0 ||
                        Coursetitle.getText().toString().trim().length() == 0 ||
                        SessionId.getText().toString().trim().length() == 0 ||
                        TheoryCHr.getText().toString().trim().length() == 0 ||
                        knowledgeArea.getText().toString().trim().length() == 0) {
                    showMessage("Error", "Please enter all values");
                    return;
                }

                int ThCredit = Integer.parseInt(TheoryCHr.getText().toString());
                long result =  db.registerCourse( Coursecode.getText().toString(),Coursetitle.getText().toString(), SessionId.getText().toString(),
                        ThCredit,LabCr,knowledgeArea.getText().toString());
                if(result == -1)
                {
                    showMessage("Error", "Course not added");
                }
                else
                {
                    showMessage("Success", "Course added successfully");
                    //clearText();
                }
            }
            else if(relTheory.getVisibility() == View.GONE && relLab.getVisibility() == View.VISIBLE)
            {
                int TheoryCr = 0;
                if (Coursecode.getText().toString().trim().length() == 0 ||
                        Coursetitle.getText().toString().trim().length() == 0 ||
                        SessionId.getText().toString().trim().length() == 0 ||
                        LabCHr.getText().toString().trim().length() == 0 ||
                        knowledgeArea.getText().toString().trim().length() == 0) {
                    showMessage("Error", "Please enter all values");
                    return;
                }
                int LabCr = Integer.parseInt(LabCHr.getText().toString());
                long result =  db.registerCourse( Coursecode.getText().toString(),Coursetitle.getText().toString(), SessionId.getText().toString(),
                        TheoryCr,LabCr,knowledgeArea.getText().toString());
                if(result == -1)
                {
                    showMessage("Error", "Course not added");
                }
                else
                {
                    showMessage("Success", "Course added successfully");
                    //clearText();
                }
            }

        }
        rad.setEnabled(false);
        Coursecode.setEnabled(false);
        Coursetitle.setEnabled(false);
        SessionId.setEnabled(false);
        LabCHr.setEnabled(false);
        TheoryCHr.setEnabled(false);
        knowledgeArea.setEnabled(false);
        b1.setVisibility(View.GONE);
        b2.setVisibility(View.VISIBLE);
        sec.setVisibility(View.VISIBLE);
        req.setVisibility(View.VISIBLE);
    }
    public void addSection(View v)
    {
        String SessionID = SessionId.getText().toString();
        String CourseCode = Coursecode.getText().toString();
        String SectionName = Section.getSelectedItem().toString();
        String SectionID = SessionID+"-"+SectionName;
        long result =  db.addCourseSection( SectionID,SessionID, CourseCode,
                SectionName);
        if(result == -1)
        {
            showMessage("Error", "Section Not added");
        }
        else
        {
            showMessage("Success", "Section added successfully");
            //clearText();
        }

    }
    public void addCourse(View v)
    {

        String CourseCode = Coursecode.getText().toString();
        String PreReq = Coursereq.getText().toString();
        if(CourseCode == PreReq)
        {
            showMessage("Error", "Course Code and Pre Req Not are not same");
            clearReqText();
        }
        long result =  db.addCourseReq( CourseCode, PreReq);
        if(result == -1)
        {
            showMessage("Error", "Pre Req Not added");
        }
        else
        {
            showMessage("Success", "Pre Req added successfully");
            clearReqText();
        }

    }
    public void submit(View v)
    {
        Intent i = new Intent(this,Courses.class);
        startActivity(i);
    }
    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    public void clearText() {
        Coursecode.setText("");
        Coursetitle.setText("");
        SessionId.setText("");
        TheoryCHr.setText("");
        knowledgeArea.setText("");
        LabCHr.setText("");
    }
    public void clearReqText() {
        Coursereq.setText("");
    }

}
