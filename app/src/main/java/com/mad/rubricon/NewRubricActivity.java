package com.mad.rubricon;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NewRubricActivity extends AppCompatActivity {

    ListView levels;
    int level;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_rubric);

        levels = findViewById(R.id.levelList);
        level = Rubric.rubric.gradingLevels.size();
        LevelAdapter levelAdapter = new LevelAdapter(this,R.layout.level_item_marks, level);

        levels.setAdapter(levelAdapter);

    }

    public void cancelActivity(View View){
        startActivity(new Intent(this,RubricsActivity.class));
    }

    public void saveRubric(View view){
        List<String> marksList = new ArrayList<>();
        for (int i =0; i<level; i++){
            View item = levels.getChildAt(i);
            EditText etMarks = item.findViewById(R.id.marks);
            String marks = etMarks.getText().toString().trim();
            if(marks.equals("")) {
                etMarks.setError("Please enter marks!");
                return;
            }
            marksList.add(marks);
        }
        int i = 1;
        for (String marks:marksList){
            Rubric.rubric.addGradingLevel(new GradingLevel("Level "+i,Integer.parseInt(marks)));
            i++;
        }
        Rubric.rubric.saveRubric(this);

        startActivity(new Intent(this,AddCloActivity.class));
    }

    public class LevelAdapter extends ArrayAdapter {
        int levels;
        private Context context;
        public LevelAdapter(@NonNull Context context, int resource, int levels) {
            super(context, resource,levels);
            this.levels = levels;
            this.context = context;
        }

        @Override
        public int getCount() {
            return this.levels;
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
                view = layoutInflater.inflate(R.layout.level_item_marks, null);
            }

            TextView level_ = (TextView) view.findViewById(R.id.level);
            EditText iconview = (EditText) view.findViewById(R.id.marks);

            level_.setText("Level " + (position+1));


            return view;
        }
    }
}
