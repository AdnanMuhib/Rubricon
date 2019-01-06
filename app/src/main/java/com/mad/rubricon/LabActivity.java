package com.mad.rubricon;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class LabActivity extends AppCompatActivity {

    Button btnAdd;
    EditText labTitle;
    EditText labMarks;
    EditText marksWeightage;
    ListView questionsList;
    LabAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab);

        labMarks = findViewById(R.id.etLabMarks);
        labTitle = findViewById(R.id.etLabTitle);
        marksWeightage = findViewById(R.id.etLMWeightage);

        questionsList = findViewById(R.id.questionsList);
        adapter = new LabAdapter(this, R.layout.question_view ,Lab.lab.questions);
        questionsList.setAdapter(adapter);

        questionsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView, View view, final int i, long l) {
                TextView clo = view.findViewById(R.id.tvCLO);
                TextView marks = view.findViewById(R.id.tvMarks);

                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.add_question_dialog, null);
                final EditText etMarks = alertLayout.findViewById(R.id.etQuestionMarks);
                final Spinner sClo = alertLayout.findViewById(R.id.sRubricClo);
                Button delete = alertLayout.findViewById(R.id.btnQCancel);
                Button update = alertLayout.findViewById(R.id.btnAddQuestion);

                etMarks.setText(marks.getText());
                delete.setText("Delete");
                update.setText("Update");

                AlertDialog.Builder alert = new AlertDialog.Builder(LabActivity.this);

                // this is set the view from XML inside AlertDialog
                alert.setView(alertLayout);
                // disallow cancel of AlertDialog on click of back button and outside touch
                alert.setCancelable(true);
                final AlertDialog dialog = alert.create();
                dialog.show();

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Lab.lab.questions.remove(i);
                        adapter.notifyDataSetChanged();
                        dialog.cancel();
                    }
                });

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String marks = etMarks.getText().toString();

                        if (marks.equals("")){
                            etMarks.setError("Marks not entered");
                        }
                        else {
                            Lab.lab.questions.get(i).setMarks(Double.parseDouble(marks));
                            adapter.notifyDataSetChanged();
                            dialog.cancel();
                        }
                    }
                });
            }
        });

        btnAdd = findViewById(R.id.btnAddCategory);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.add_question_dialog, null);
                final EditText etMarks = alertLayout.findViewById(R.id.etQuestionMarks);
                final Spinner sClo = alertLayout.findViewById(R.id.sRubricClo);
                Button cancel = alertLayout.findViewById(R.id.btnQCancel);
                Button add = alertLayout.findViewById(R.id.btnAddQuestion);

                AlertDialog.Builder alert = new AlertDialog.Builder(LabActivity.this);

                // this is set the view from XML inside AlertDialog
                alert.setView(alertLayout);
                // disallow cancel of AlertDialog on click of back button and outside touch
                alert.setCancelable(false);
                final AlertDialog dialog = alert.create();
                dialog.show();

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });

                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String marks = etMarks.getText().toString();

                        if (marks.equals("")){
                            etMarks.setError("Marks not entered");
                        }
                        else {
                            LabTable table = new LabTable(LabActivity.this);
                            table.open();
                            Question question = new Question(Double.parseDouble(marks), 1,table.getCount());
                            table.close();
                            Lab.lab.addQuestion(question);
                            adapter.notifyDataSetChanged();
                            dialog.cancel();
                        }
                    }
                });

            }
        });
    }

    public class LabAdapter extends ArrayAdapter {
        ArrayList<Question> questions;
        private Context context;
        public LabAdapter(@NonNull Context context, int resource, ArrayList<Question> questions) {
            super(context, resource ,questions);
            this.questions = questions;
            this.context = context;
        }

        @Override
        public int getCount() {
            return this.questions.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (null == view) {
                LayoutInflater layoutInflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = layoutInflater.inflate(R.layout.question_view, null);
            }
            TextView marks = view.findViewById(R.id.tvMarks);
            TextView clo = view.findViewById(R.id.tvCLO);
            TextView qNo = view.findViewById(R.id.tvNumber);

            qNo.setText("Q" + (position+1));
            marks.setText(questions.get(position).marks+"");
            clo.setText(questions.get(position).rubric_clo_id+"");

            return view;
        }
    }


    public void createLab(View view){
        String marks = labMarks.getText().toString();
        String title = labTitle.getText().toString();
        String weightage = marksWeightage.getText().toString();

        if (title.equals("")){
            labTitle.setError("Title not entered!");
            return;
        }
        else if (marks.equals("")){
            labMarks.setError("Marks not entered!");
            return;
        }
        else if(weightage.equals("")){
            marksWeightage.setError("Weightage not entered!");
            return;
        }
        else if (Lab.lab.questions.size() == 0){
            Toast.makeText(this,"No Question added!",Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            Lab lab = Lab.lab;
            lab.setLabTitle(title);
            lab.setLabMarks(Double.parseDouble(marks));
            lab.setMarksWeight(Double.parseDouble(weightage));
            lab.save(this);

        }
    }
}
