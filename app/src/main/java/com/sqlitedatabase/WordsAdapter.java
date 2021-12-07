package com.sqlitedatabase;

import android.content.Context;
import android.database.SQLException;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sqlitedatabase.database.DatabaseHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class WordsAdapter extends BaseAdapter {
    private List<String> titleData = null;
    private List<String> idNo = null;
    private LayoutInflater mInflater;

    public WordsAdapter(Context context, List<String> title, List<String> idNo) {
        this.titleData = title;
        this.idNo = idNo;
        mInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return titleData.size();
    }

    @Override
    public Object getItem(int i) {
        return titleData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        // A ViewHolder keeps references to children views to avoid unnecessary calls
        // to findViewById() on each row.
        WordsAdapter.ViewHolder holder;

        // When convertView is not null, we can reuse it directly, there is no need
        // to reinflate it. We only inflate a new View when the convertView supplied
        // by ListView is null.
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.custom_layout, null);

            // Creates a ViewHolder and store references to the two children views
            // we want to bind data to.
            holder = new WordsAdapter.ViewHolder();

            holder.titleTv  = (TextView) convertView.findViewById(R.id.word_tv);
            holder.idTv  = (TextView) convertView.findViewById(R.id.id_tv);

            // Bind the data efficiently with the holder.

            convertView.setTag(holder);
        } else {
            // Get the ViewHolder back to get fast access to the TextView
            // and the ImageView.
            holder = (WordsAdapter.ViewHolder) convertView.getTag();
        }

        // If weren't re-ordering this you could rely on what you set last time
        holder.titleTv.setText(titleData.get(position));
        holder.idTv.setText(idNo.get(position));


        return convertView;
    }

    static class ViewHolder {
        TextView titleTv;
        TextView idTv;
    }

}

