package com.mad.Rubricon;

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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class CloCreation extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner Clono,Clopriority;
    DBManager db;
    EditText Clodescription;
    ListView CloList;
    ArrayList<String> arr;
    ArrayAdapter<String> itemsAdapter;
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
        setContentView(R.layout.activity_clo_creation);
        Clono = (Spinner) findViewById(R.id.Clono_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.CloNo,android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Clono.setAdapter(adapter);
        Clono.setOnItemSelectedListener(this);
        Clono.setFocusable(true);
        Clopriority = (Spinner) findViewById(R.id.Priorityspinner);
        ArrayAdapter<CharSequence> adapt = ArrayAdapter.createFromResource(this,R.array.Priority,android.R.layout.simple_spinner_dropdown_item);
        adapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Clopriority.setAdapter(adapt);
        Clopriority.setOnItemSelectedListener(this);
        Clodescription = (EditText) findViewById(R.id.Description);
        CloList = (ListView) findViewById(R.id.CloListView);
        try {
            db = new DBManager(this);
            db.open();
        }catch(Exception e)
        {
        }
        arr = new ArrayList<String>();
        itemsAdapter =new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arr);
        CloList.setAdapter(itemsAdapter);

        Intent CourseCode = getIntent();
        String Code = CourseCode.getStringExtra("CourseCode");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public void AddCLO(View v)
    {

        String CloNo = Clono.getSelectedItem().toString();
        int Clo = Integer.parseInt(CloNo);
        String Prioroity = Clopriority.getSelectedItem().toString();
        String description = Clodescription.getText().toString();
        Intent CourseCode = getIntent();
        String Code = CourseCode.getStringExtra("CourseCode");
        if (CloNo.trim().length() == 0 ||
                Prioroity.trim().length() == 0 ||
                description.trim().length() == 0) {
            showMessage("Error", "Please enter all values");
            return;
        }
        long result = db.addClo(Code,Clo,Prioroity,description);
        if(result == -1)
        {
            showMessage("Error", "Record not added");
        }
        else
        {
            showMessage("Success", "Record added");
            arr.add(Code + " | " + CloNo + " | " + Prioroity + " | " + description);
            itemsAdapter.notifyDataSetChanged();
            clearText();}
    }
    public void submit(View v)
    {
        Intent i = new Intent(this,Courses.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
        Clodescription.setText("");
    }
}
