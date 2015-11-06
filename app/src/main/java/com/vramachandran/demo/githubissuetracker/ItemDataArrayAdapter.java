package com.vramachandran.demo.githubissuetracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by vramachandran on 9/28/2015.
 */
public class ItemDataArrayAdapter extends ArrayAdapter<IssueItemData> {

    private static class ViewHolder {
        private TextView itemTitle;
        private TextView itemBody;
    }

    public ItemDataArrayAdapter(Context context, int textViewResourceId, ArrayList<IssueItemData> items) {
        super(context, textViewResourceId, items);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(R.layout.row_layout, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.itemTitle = (TextView) convertView.findViewById(R.id.issue_title);
            viewHolder.itemBody = (TextView) convertView.findViewById(R.id.issue_body);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        IssueItemData item = getItem(position);
        if (item != null) {
            viewHolder.itemTitle.setText(item.mTitle);
            viewHolder.itemBody.setText(item.mBody);
        }

        return convertView;
    }

}
