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

        Intent intent = getIntent();
        level = getLevels(intent);
        LevelAdapter levelAdapter = new LevelAdapter(this,R.layout.list_item, level);

        levels.setAdapter(levelAdapter);

    }

    public void cancelActivity(View View){
        startActivity(new Intent(this,RubricsActivity.class));
    }

    public void saveRubric(View view){
        //        LevelAdapter adapter =(LevelAdapter) levels.getAdapter();
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
        for (String marks:marksList){
            Rubric.rubric.addGradingLevel(new GradingLevel("Level",Integer.parseInt(marks)));
        }

        startActivity(new Intent(this,AddCloActivity.class));
    }

    public int getLevels(Intent intent){
        String levels = intent.getStringExtra("levels");
        switch (levels){
            case "one":{
                return 1;
            }
            case "two":{
                return 2;
            }
            case "three":{
                return 3;
            }
            case "four":{
                return 4;
            }
            case "five":{
                return 5;
            }
        }
        return 0;
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
                view = layoutInflater.inflate(R.layout.list_item, null);
            }

            TextView level_ = (TextView) view.findViewById(R.id.level);
            EditText iconview = (EditText) view.findViewById(R.id.marks);

            level_.setText("Level " + (position+1));


            return view;
        }
    }
}
