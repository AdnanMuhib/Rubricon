package com.example.tanzeel.Rubricon;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AdminLogin extends AppCompatActivity {
    EditText email,pass;
    Button login;
    DBManager db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.pass);
        login = (Button) findViewById(R.id.btn_login);
        try {
            db = new DBManager(this);
            db.open();
        }catch(Exception e)
        {
        }
    }
    public void courses(View v)
    {
        if (email.getText().toString().trim().length() == 0 ||
                pass.getText().toString().trim().length() == 0) {
            showMessage("Error", "Please enter all values");
            return;
        }
        Boolean login = db.adminLogin(email.getText().toString(),pass.getText().toString());
        if(login==true)
        {
            showMessage("Success", "Login Successfully");
            Intent i = new Intent(this,Courses.class);
            startActivity(i);
        }
        else
        {
            showMessage("Error", "Wrong Credentials");
            clearText();
        }
    }
    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    public void clearText() {
        email.setText("");
        pass.setText("");
    }
}
