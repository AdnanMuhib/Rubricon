package com.mad.rubricon;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CourseListCustomAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<Course> list = new ArrayList<Course>();
    private Context context;

    public CourseListCustomAdapter(ArrayList<Course> list, LabEvaluationActivity c){
        this.list = list;
        this.context = c;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.courses_list_item, null);
        }

        //Handle TextView and display string from your list
        TextView courseIdText = (TextView)view.findViewById(R.id.textViewCourseId);
        TextView courseTitleText = (TextView) view.findViewById(R.id.textViewCourseTitle);

        courseIdText.setText(list.get(position).getCourseId());
        courseTitleText.setText(list.get(position).getCourseTitle());
        courseTitleText.setTag(list.get(position).id);
//        courseTitleText.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public  void onClick(View v){
//
//                ((LabEvaluationActivity)context).onCourseClick(new Course());
//                Intent intent = new Intent(context, LabEvalSelectWeekActivity.class);
//                String crsId = v.getTag().toString();
//                intent.putExtra("crsId", crsId);
//                Log.i("DebugLog",crsId);
//                context.startActivity(intent);
//                //notifyDataSetChanged();
//            }
//        });
        return view;
    }
}
