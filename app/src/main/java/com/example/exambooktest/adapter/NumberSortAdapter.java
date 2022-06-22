package com.example.exambooktest.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.example.exambooktest.R;
import com.example.exambooktest.utils.Sort;

import java.util.List;

public class NumberSortAdapter extends BaseAdapter {

    private Context mContext;
    private List<Sort> SList;

    public NumberSortAdapter(Context mContext, List<Sort> SList)
    {
        this.mContext = mContext;
        this.SList = SList;

    }



    @Override
    public int getCount() {
        return SList.size();
    }

    @Override
    public Object getItem(int position) {
        return SList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = View.inflate(mContext, R.layout.list_timu_item, null);

        return convertView;
    }
}
