package com.example.exambooktest.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.exambooktest.R;
import com.example.exambooktest.utils.Msg;

import java.util.List;

public class TheListAdapter extends BaseAdapter {

    private Context mContext;
    private List<Msg> mList;

    public TheListAdapter(Context mContext, List<Msg> mList)
    {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = View.inflate(mContext, R.layout.list_news_item, null);

        ((TextView)convertView.findViewById(R.id.tv_notice)).setText(mList.get(position).getNotice());
        ((TextView)convertView.findViewById(R.id.tv_people)).setText(mList.get(position).getName());
        ((TextView)convertView.findViewById(R.id.tv_time)).setText(mList.get(position).getTime());
        ((TextView)convertView.findViewById(R.id.tv_content)).setText(mList.get(position).getMsg());
        return convertView;
    }


}
