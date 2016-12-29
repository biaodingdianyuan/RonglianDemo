package com.example.liuhaifeng.rongliandemo.activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.liuhaifeng.rongliandemo.MyAPP;
import com.example.liuhaifeng.rongliandemo.R;
import com.example.liuhaifeng.rongliandemo.dao.FriendDao;
import com.example.liuhaifeng.rongliandemo.dao.UserDao;
import com.example.liuhaifeng.rongliandemo.dao.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaifeng on 2016/12/24.
 */

public class FriendFragment extends Fragment {
    private ListView friend_lv;
    private List<FriendDao> list;
    private FriendDao friendDao;
    private FriendAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view1=inflater.inflate(R.layout.fragment_friend,null);
        friend_lv= (ListView) view1.findViewById(R.id.friend_lv);
        list=new ArrayList<FriendDao>();
        adapter=new FriendAdapter(list);
        friend_lv.setAdapter(adapter);
        //测试数据
        data a=new data();
        List<UserDao> listt=a.getdata();
        for(int i=0;i<listt.size();i++){
            friendDao=new FriendDao();
            friendDao.setName(listt.get(i).getName());
            friendDao.setId(listt.get(i).getId());
            list.add(friendDao);
        }
        adapter.notifyDataSetChanged();
        final MyAPP app=new MyAPP();
        friend_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(getActivity(),ChattingActivity.class).putExtra("from",app.name).putExtra("to",list.get(i).getId()).putExtra("ty","1"));
            }
        }) ;



        return  view1;
    }

    class FriendAdapter extends BaseAdapter{
        private List<FriendDao> list;
        public FriendAdapter(List<FriendDao> list) {
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
            view=getActivity().getLayoutInflater().inflate(R.layout.friend_item,null);
            TextView friend_name= (TextView) view.findViewById(R.id.friend_name);
            ImageView friend_img= (ImageView) view.findViewById(R.id.friend_img);
            friend_name.setText(list.get(i).getName());


            return view;
        }
    }

}
