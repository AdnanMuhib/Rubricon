package com.mad.rubricon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ThreeColumn_ListAdapter extends ArrayAdapter<CourseData> {

    private LayoutInflater mInflater;
    private ArrayList<CourseData> users;
    private int mViewResourceId;

    public ThreeColumn_ListAdapter(Context context, int textViewResourceId, ArrayList<CourseData> users) {
        super(context, textViewResourceId, users);
        this.users = users;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(mViewResourceId, null);

        CourseData user = users.get(position);

        if (user != null) {
            TextView Code = (TextView) convertView.findViewById(R.id.textCourseCode);
            TextView Name = (TextView) convertView.findViewById(R.id.textCourseName);
            TextView Section = (TextView) convertView.findViewById(R.id.textSectionName);
            if (Code != null) {
                Code.setText(user.getCode());
            }
            if (Name != null) {
                Name.setText((user.getName()));
            }
            if (Section != null) {
                Section.setText((user.getSection()));
            }
        }

        return convertView;
    }
}
