package com.mad.rubricon;
import android.app.AlertDialog;
        import android.content.ContentValues;
        import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;

public class Login extends AppCompatActivity {
    DBManager db;
    EditText email,pass;
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = findViewById(R.id.toolbar_login);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle("Rubricon Login");
        //actionbar.setDisplayHomeAsUpEnabled(true);
       // actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white);

        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.pass);
        login = (Button) findViewById(R.id.btn_login);
        try {
            db = new DBManager(this);
            db.open();
            long result =  db.registerAdmin( "Adnan","a@e.com", "36304-1550897-9","10-08-1998",
                    "111");
            if(result == -1)
            {
                //showMessage("Error", "Record not added");
            }
            else
            {
                //showMessage("Success", "Record added");
               // clearText();
            }
        }catch(Exception e)
        {
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.reg:
                Intent intent = new Intent(this, StudentSignUp.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.register,menu);
        return true;
    }
    public void login(View v)
    {
        String mail = email.getText().toString();
        String password = pass.getText().toString();
        if (mail.trim().length() == 0 ||
                password.trim().length() == 0) {
            showMessage("Error", "Please enter all values");
            return;
        }
        ContentValues c = db.getLoginData(mail,password);
        // Checking if no records found
        if (c == null) {
            showMessage("Error", "No records found against Email and Password");
            clearText();
            return;
        }

        else{
            String role = c.get("Role").toString();
            if(role.equals("Student"))
            {
                String mal = email.getText().toString();
                String pas = pass.getText().toString();
                ContentValues con = db.getRegNo(mal,pas);
                String reg = con.get("RegNo").toString();
                Intent i = new Intent(this,StudentPanel.class);
                i.putExtra("RegNo",reg);
                startActivity(i);
            }
            else if(role.equals("Teacher"))
            {
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("Email",email.getText().toString());
                startActivity(intent);
                /*Intent i = new Intent(this,TeacherCourseList.class);
                i.putExtra("Email",email.getText().toString());
                startActivity(i);*/
            }
            else
            {
                Intent i = new Intent(this,Courses.class);
                startActivity(i);
            }
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
