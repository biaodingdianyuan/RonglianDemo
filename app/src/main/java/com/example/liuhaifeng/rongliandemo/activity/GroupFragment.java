package com.example.liuhaifeng.rongliandemo.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.liuhaifeng.rongliandemo.R;
import com.example.liuhaifeng.rongliandemo.dao.GroupDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaifeng on 2016/12/24.
 */

public class GroupFragment extends Fragment {
    private ListView grouplv;
    private GroupDao groupDao;
    private List<GroupDao> list;
    private groupAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_group,null);
        grouplv= (ListView) view.findViewById(R.id.grouplistview);
        list=new ArrayList<GroupDao>();
        adapter=new groupAdapter(list);
        grouplv.setAdapter(adapter);
        //获取数据更新适配


        return view;
    }

    class  groupAdapter extends BaseAdapter{
        private List<GroupDao> list;

        public groupAdapter(List<GroupDao> list) {
            this.list=list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
                view =getActivity().getLayoutInflater().inflate(R.layout.group_item,null);
            TextView name= (TextView) view.findViewById(R.id.name_item_group);
            name.setText(list.get(i).getName());

            return view;
        }
    }



}
