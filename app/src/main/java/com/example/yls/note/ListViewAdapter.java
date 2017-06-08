package com.example.yls.note;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by yls on 2017/6/8.
 */

public class ListViewAdapter extends BaseAdapter{
    private List<String> listItems;
    private List<String> listItemTimes;
    private LayoutInflater inflater;

    public ListViewAdapter(Context context, List<String> listItems, List<String> times){
        this.listItems = listItems;
        this.listItemTimes = times;
        inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public void addListItem(String item,String time){
        listItems.add(time);
        listItemTimes.add(time);
    }

    public void removeListItem(int position){
        listItems.remove(position);
        listItemTimes.remove(position);
    }
    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int position) {
        return listItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(R.layout.note_list_item,null);
        }

        TextView text = (TextView)convertView.findViewById(R.id.listItem);
        text.setText(listItems.get(position));

        TextView time = (TextView)convertView.findViewById(R.id.listItemTime);
        String datetime = DateFormat.format("yyyy-MM-dd kk:mm:ss",
                Long.parseLong(listItemTimes.get(position))).toString();
        time.setText(datetime);

        return convertView;
    }
}
