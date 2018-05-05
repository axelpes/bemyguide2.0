package com.example.thomas.projet_signup.com.example.thomas.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thomas.projet_signup.R;

import java.util.List;

public class PlaceAdapter extends ArrayAdapter<PlaceItem> {

    public PlaceAdapter(Context context, List<PlaceItem> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.place_row_details,parent, false);
        }

        ListItemViewHolder viewHolder = (ListItemViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new ListItemViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.address = (TextView) convertView.findViewById(R.id.address);
            viewHolder.avatar = (ImageView) convertView.findViewById(R.id.place_icon);
            viewHolder.distance = (TextView) convertView.findViewById(R.id.distance);

            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List
        PlaceItem item = getItem(position);
        viewHolder.title.setText(item.getTitle());
        viewHolder.address.setText(item.getAddress());
        viewHolder.avatar.setImageResource(item.getId());
        viewHolder.distance.setText(item.getDistance());

        return convertView;
    }

    private class ListItemViewHolder{
        public TextView title;
        public TextView address;
        public ImageView avatar;
        private TextView distance;

    }
}
