package com.example.thomas.projet_signup;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thomas.projet_signup.com.example.thomas.model.PointOfInterest;

import java.util.List;

public class PlaceCategoryAdapter extends ArrayAdapter<PointOfInterest> {
    Context mContext;
    public PlaceCategoryAdapter(Context context, List<PointOfInterest> items) {
        super(context, 0, items);
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.placecat_row_details,parent, false);
        }

        ListItemViewHolder viewHolder = (ListItemViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new ListItemViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.placecat_text);
            viewHolder.checkboxstyle = (ImageView) convertView.findViewById(R.id.placecat_checkbox);

            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List
        PointOfInterest item = getItem(position);
        viewHolder.title.setText(item.getNamePoint());

        int idColor = mContext.getResources().getIdentifier("color/"+item.getColor(), null , mContext.getPackageName());
        viewHolder.title.setTextColor(getContext().getResources().getColorStateList(idColor));

        int idCheckBox = mContext.getResources().getIdentifier("drawable/"+item.getCheckboxstyle(), null , mContext.getPackageName());
        viewHolder.checkboxstyle.setImageResource(idCheckBox);

        return convertView;
    }

    private class ListItemViewHolder{
        public TextView title;
        public ImageView checkboxstyle;

    }
}