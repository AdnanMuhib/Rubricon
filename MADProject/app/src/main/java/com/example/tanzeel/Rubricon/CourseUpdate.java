package com.example.tanzeel.Rubricon;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import java.util.ArrayList;

public class CourseUpdate extends AppCompatActivity {
    DBManager db;
    ImageView img;
    Button add_sec,del_sec,add_Req,del_Req,add,del,cancelAdd,cancelDel,addBtn,delBtn,cancelAddBtn,cancelDelBtn;
    Button b1,b2;
    EditText Coursetitle,Coursecode,SessionId,TheoryCHr,LabCHr,knowledgeArea,req;
    Spinner SectionSpinner, ReqSpinner;
    RelativeLayout Section,Requisite,Theory,Lab;
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
        setContentView(R.layout.activity_course_update);
        add_sec = findViewById(R.id.btn_addSection);
        del_sec = findViewById(R.id.btn_delSection);
        add_Req = findViewById(R.id.btn_addReq);
        cancelAdd = findViewById(R.id.btn_cancelAdd);
        cancelDel = findViewById(R.id.btn_cancelDel);
        del_Req =  findViewById(R.id.btn_delReq);
        add = findViewById(R.id.btn_add);
        del = findViewById(R.id.btn_delete);
        addBtn =  findViewById(R.id.btn_addBtn);
        delBtn =  findViewById(R.id.btn_delBtn);
        img = (ImageView) findViewById(R.id.img_down);
        cancelAddBtn =  findViewById(R.id.btn_cancelAddBtn);
        cancelDelBtn =  findViewById(R.id.btn_cancelDelBtn);
        Coursetitle = (EditText) findViewById(R.id.Coursetitle);
        Coursecode = (EditText) findViewById(R.id.Coursecode);
        SessionId = (EditText) findViewById(R.id.SessionID);
        TheoryCHr = (EditText) findViewById(R.id.TheoryCHr);
        LabCHr = (EditText) findViewById(R.id.LabCHr);
        knowledgeArea = (EditText) findViewById(R.id.KnowledgeArea);
        req = (EditText) findViewById(R.id.req);
        Section = (RelativeLayout) findViewById(R.id.relativeLayout7);
        Requisite = (RelativeLayout) findViewById(R.id.relativeLayout8);
        Theory = (RelativeLayout) findViewById(R.id.relativeLayout4);
        Lab = (RelativeLayout) findViewById(R.id.relativeLayout5);
        b1 = (Button) findViewById(R.id.btn_create);
        b2 = (Button) findViewById(R.id.btn_submit);
        SectionSpinner = (Spinner) findViewById(R.id.spinner);
        ReqSpinner = (Spinner) findViewById(R.id.spinner2);
        try {
            db = new DBManager(this);
            db.open();
        }catch(Exception e)
        {
        }
        Intent CourseCode = getIntent();
        String Code = CourseCode.getStringExtra("CourseCode");
        ContentValues c = db.getCourseData(Code);
        // Checking if no records found
        if (c == null) {
            showMessage("Error", "No records found for CourseCode");
            clearText();
            return;
        }

        else{
            int theoryHrs = Integer.parseInt(c.get("TheoryCredit").toString());
            int labHrs = Integer.parseInt(c.get("LabCredit").toString());
            if((theoryHrs > 0) && (labHrs == 0))
            {
                Coursetitle.setText(c.get("CourseTitle").toString());
                Coursecode.setText(c.get("CourseCode").toString());
                Coursecode.setEnabled(false);

                TheoryCHr.setText(Integer.toString(theoryHrs));
                knowledgeArea.setText(c.get("KnowledgeArea").toString());
            }
            else if((theoryHrs == 0) && (labHrs > 0))
            {
                Theory.setVisibility(View.GONE);
                Lab.setVisibility(View.VISIBLE);
                Coursetitle.setText(c.get("CourseTitle").toString());
                Coursecode.setText(c.get("CourseCode").toString());
                Coursecode.setEnabled(false);
                LabCHr.setText(Integer.toString(labHrs));
                knowledgeArea.setText(c.get("KnowledgeArea").toString());
            }

        }

        ContentValues con = db.getCourseSessionID(Code);
        if (con == null) {
            showMessage("Error", "No records found for CourseCode");
            clearText();
            return;
        }
        else {
            SessionId.setText(con.get("SessionID").toString());
            SessionId.setEnabled(false);
        }
    }
    public void nextPage(View v)
    {
        String code = Coursecode.getText().toString();
        String title = Coursetitle.getText().toString();
        String session = SessionId.getText().toString();
        String Knowledge = knowledgeArea.getText().toString();
        if(Theory.getVisibility() == View.VISIBLE && Lab.getVisibility() == View.GONE)
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
            long result =  db.updateCourse( code,title, session, ThCredit,LabCr,Knowledge);
            if(result == -1)
            {
                showMessage("Error", "Course not updated");
            }
            else
            {
                showMessage("Success", "Course updated successfully");
                //clearText();
            }
        }
        else if(Theory.getVisibility() == View.GONE && Lab.getVisibility() == View.VISIBLE)
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
            long result =  db.updateCourse( code,title, session, TheoryCr,LabCr,Knowledge);
            if(result == -1)
            {
                showMessage("Error", "Course not updated");
            }
            else
            {
                showMessage("Success", "Course updated successfully");
                //clearText();
            }
        }
        Section.setVisibility(View.VISIBLE);
        Requisite.setVisibility(View.VISIBLE);
        b1.setVisibility(View.GONE);
        b2.setVisibility(View.VISIBLE);
        Coursetitle.setEnabled(false);
        LabCHr.setEnabled(false);
        TheoryCHr.setEnabled(false);
        knowledgeArea.setEnabled(false);
    }
    public void addSection(View v)
    {
        add_sec.setVisibility(View.GONE);
        del_sec.setVisibility(View.GONE);
        add.setVisibility(View.VISIBLE);
        cancelAdd.setVisibility(View.VISIBLE);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.sections,android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SectionSpinner.setAdapter(adapter);
    }
    public void addMoreSection(View v)
    {
        ContentValues c =  db.chksection(Coursecode.getText().toString(),SectionSpinner.getSelectedItem().toString());
        if (c != null) {
            showMessage("Error", "Course Already Added");
            return;
        }
        // Appending records to a string buffer
        else{
            String SessionID = SessionId.getText().toString();
            String CourseCode = Coursecode.getText().toString();
            String SectionName = SectionSpinner.getSelectedItem().toString();
            String SectionID = SessionID+"-"+SectionName;
            long result =  db.updateCourseSection( SectionID,SessionID, CourseCode,
                    SectionName);
            if(result == -1)
            {
                showMessage("Error", "Section Not added");
            }
            else
            {
                showMessage("Success", "Section added successfully");
            }
        }
    }
    public void delSection(View v)
    {
        ArrayList<String> arr = new ArrayList<String>();
        arr = db.getAllSections(Coursecode.getText().toString());
        if((arr.size()>0)) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,arr);
            SectionSpinner.setAdapter(adapter);
            add_sec.setVisibility(View.INVISIBLE);
            del_sec.setVisibility(View.INVISIBLE);
            del.setVisibility(View.VISIBLE);
            cancelDel.setVisibility(View.VISIBLE);
        }
        else {
            showMessage("Error", "No Section Exist");
        }
    }
    public void DeleteSection(View v)
    {
        long result = db.delSection(Coursecode.getText().toString(),SectionSpinner.getSelectedItem().toString());
        if (result == -1) {
            showMessage("Error", "Record not deleted");
        } else {
            showMessage("Success", "Record deleted Successfully");
        }
        SectionSpinner.setSelection(0);
    }
    public void cancelAdd(View v)
    {
        add_sec.setVisibility(View.VISIBLE);
        del_sec.setVisibility(View.VISIBLE);
        add.setVisibility(View.INVISIBLE);
        cancelAdd.setVisibility(View.INVISIBLE);
    }
    public void cancelDel(View v)
    {
        add_sec.setVisibility(View.VISIBLE);
        del_sec.setVisibility(View.VISIBLE);
        del.setVisibility(View.INVISIBLE);
        cancelDel.setVisibility(View.INVISIBLE);
    }
    public void addReq(View v)
    {
        ReqSpinner.setVisibility(View.INVISIBLE);
        req.setVisibility(View.VISIBLE);
        img.setVisibility(View.INVISIBLE);
        add_Req.setVisibility(View.INVISIBLE);
        del_Req.setVisibility(View.INVISIBLE);
        addBtn.setVisibility(View.VISIBLE);
        cancelAddBtn.setVisibility(View.VISIBLE);
    }
    public void AddMoreReq(View v)
    {
        ContentValues c =  db.chkreq(Coursecode.getText().toString(),req.getText().toString());
        if (c != null) {
            showMessage("Error", "Pre Req. Already Added");
            return;
        }
        // Appending records to a string buffer
        else{
            String CourseCode = Coursecode.getText().toString();
            String Req = req.getText().toString();
            long result =  db.updateCourseReq(CourseCode,Req);
            if(result == -1)
            {
                showMessage("Error", "Pre Req. Not added");
            }
            else
            {
                showMessage("Success", "Pre Req. added successfully");
            }
        }
    }
    public void delReq(View v)
    {
        ArrayList<String> arr = new ArrayList<String>();
        arr = db.getAllCoursesReq(Coursecode.getText().toString());
        if((arr.size()>0)) {
            req.setVisibility(View.INVISIBLE);
            img.setVisibility(View.VISIBLE);
            ReqSpinner.setVisibility(View.VISIBLE);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,arr);
            ReqSpinner.setAdapter(adapter);
            add_Req.setVisibility(View.INVISIBLE);
            del_Req.setVisibility(View.INVISIBLE);
            delBtn.setVisibility(View.VISIBLE);
            cancelDelBtn.setVisibility(View.VISIBLE);
        }
        else {
            showMessage("Error", "No Req. Exist");
        }
    }
    public void DeleteReq(View v)
    {
        long result = db.delReq(Coursecode.getText().toString(),ReqSpinner.getSelectedItem().toString());
        if (result == -1) {
            showMessage("Error", "Record not deleted");
        } else {
            showMessage("Success", "Record deleted Successfully");
        }
        ReqSpinner.setSelection(0);
    }
    public void cancelAddBtn(View v)
    {
        add_Req.setVisibility(View.VISIBLE);
        del_Req.setVisibility(View.VISIBLE);
        addBtn.setVisibility(View.INVISIBLE);
        cancelAddBtn.setVisibility(View.INVISIBLE);
    }
    public void cancelDelBtn(View v)
    {
        add_Req.setVisibility(View.VISIBLE);
        del_Req.setVisibility(View.VISIBLE);
        delBtn.setVisibility(View.INVISIBLE);
        cancelDelBtn.setVisibility(View.INVISIBLE);
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
    public void submit(View v)
    {
        Intent i = new Intent(this,Courses.class);
        startActivity(i);
    }
}
