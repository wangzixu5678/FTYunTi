package com.example.ftkj.yunti_test.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.ftkj.yunti_test.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FTKJ on 2017/7/7.
 */

public class AutoCompeleteAdapter extends BaseAdapter implements Filterable {
    private Context mContext;
    private List<String> mOriginalValues;//所有的Item
    private int maxMatch=5;//最多显示多少个选项,负数表示全部
    private List<String> mObjects;//过滤后的item
    private final Object mLock = new Object();
    private ArrayFilter mFilter;
    public AutoCompeleteAdapter(Context context, List<String> list){
        mContext = context;
        //获取shareperence数据
        mOriginalValues = list;

    }
    @Override
    public int getCount() {

        return mObjects.size();
    }

    @Override
    public String getItem(int position) {
        return mObjects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_history,parent,false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.mText.setText(mObjects.get(position));
        return convertView;
    }

    static class ViewHolder{
        private TextView mText;

        public ViewHolder(View view){
            mText = ((TextView) view.findViewById(R.id.history_text));
        }
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ArrayFilter();
        }
        return mFilter;

    }


    private class ArrayFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            // TODO Auto-generated method stub
            FilterResults results = new FilterResults();
            if (prefix == null || prefix.length() == 0) {
                synchronized (mLock) {

                    ArrayList<String> list = new ArrayList<String>(mOriginalValues);
                    results.values = list;
                    results.count = list.size();
                    return results;
                }
            } else {
                String prefixString = prefix.toString().toLowerCase();

                final int count = mOriginalValues.size();

                final ArrayList<String> newValues = new ArrayList<String>(count);

                for (int i = 0; i < count; i++) {
                    final String value = mOriginalValues.get(i);
                    final String valueText = value.toLowerCase();

                    if (valueText.startsWith(prefixString)) { //源码 ,匹配开头
                        newValues.add(value);
                    }

                    if(maxMatch>0){//有数量限制
                        if(newValues.size()>maxMatch-1){//不要太多
                            break;
                        }
                    }
                }

                results.values = newValues;
                results.count = newValues.size();
            }

            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mObjects = (List<String>) results.values;
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }

    }
    }

