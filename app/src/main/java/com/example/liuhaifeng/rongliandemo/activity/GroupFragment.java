package com.example.liuhaifeng.rongliandemo.activity;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.liuhaifeng.rongliandemo.MyAPP;
import com.example.liuhaifeng.rongliandemo.R;
import com.example.liuhaifeng.rongliandemo.dao.GroupDao;
import com.example.liuhaifeng.rongliandemo.tool.GroupopenHelper;
import com.yuntongxun.ecsdk.im.ECGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaifeng on 2016/12/24.
 */

public class GroupFragment extends Fragment {
    private ListView grouplv;
    private ECGroup groupDao;
    private List<ECGroup> list;
    private groupAdapter adapter;
    private SQLiteDatabase db;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group, null);
        grouplv = (ListView) view.findViewById(R.id.grouplistview);
        list = new ArrayList<ECGroup>();
        adapter = new groupAdapter(list);
        grouplv.setAdapter(adapter);
        list.clear();
        for (int i = 0; i < getdata().size(); i++) {
            groupDao = new ECGroup();
            groupDao.setGroupId(getdata().get(i).getGroupId());
            groupDao.setName(getdata().get(i).getName());
            groupDao.setOwner(getdata().get(i).getOwner());
            groupDao.setDeclare(getdata().get(i).getDeclare());
            list.add(groupDao);
        }
        adapter.notifyDataSetChanged();
        grouplv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(getActivity(),ChattingActivity.class).putExtra("from",MyAPP.name).putExtra("to",list.get(i).getName()).putExtra("ty","2").putExtra("groupid",list.get(i).getGroupId()));
                Log.d("---------------",list.get(i).getGroupId());
            }
        });//080183134000000098a216da859204cc9015929b9cf1c059b


        return view;
    }

    class groupAdapter extends BaseAdapter {
        private List<ECGroup> list;

        public groupAdapter(List<ECGroup> list) {
            this.list = list;
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
            view = getActivity().getLayoutInflater().inflate(R.layout.group_item, null);
            TextView name = (TextView) view.findViewById(R.id.name_item_group);
            name.setText(list.get(i).getName());

            return view;
        }
    }

    public List<ECGroup> getdata() {
        List<ECGroup> lis;
        lis = new ArrayList<ECGroup>();
        db = MyAPP.groupopenHelper.getReadableDatabase();

        Cursor cursor = db.query("group_data", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                groupDao = new GroupDao();
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String declare = cursor.getString(cursor.getColumnIndex("declare"));
                String owner = cursor.getString(cursor.getColumnIndex("owner"));
                String id=cursor.getString(cursor.getColumnIndex("groupid"));
                groupDao.setName(name);
                groupDao.setDeclare(declare);
                groupDao.setOwner(owner);
                groupDao.setGroupId(id);
                lis.add(groupDao);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return lis;
    }

    @Override
    public void onResume() {
        super.onResume();
        list.clear();
        List<ECGroup> L = getdata();

        for (int i = 0; i < L.size(); i++) {
            groupDao = new GroupDao();
            groupDao.setName(L.get(i).getName());
            groupDao.setOwner(L.get(i).getOwner());
            groupDao.setGroupId(L.get(i).getGroupId());
            groupDao.setDeclare(L.get(i).getDeclare());
            list.add(groupDao);
        }
        adapter.notifyDataSetChanged();

    }
}
