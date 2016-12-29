package com.example.liuhaifeng.rongliandemo.activity;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.liuhaifeng.rongliandemo.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout huihua, friend, group;
    private FrameLayout frameLayout;
    private ImageView left;
    GroupFragment groupFragment = new GroupFragment();
    FriendFragment friendFragment = new FriendFragment();
    ContactListFragment contactListFragment = new ContactListFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    public void init() {
        huihua = (LinearLayout) findViewById(R.id.huihua);
        friend = (LinearLayout) findViewById(R.id.friend);
        group = (LinearLayout) findViewById(R.id.group);
        frameLayout = (FrameLayout) findViewById(R.id.fragment);
        left = (ImageView) findViewById(R.id.left_img);
        getFragmentManager().beginTransaction().replace(R.id.fragment, contactListFragment).commit();
        huihua.setOnClickListener(this);
        friend.setOnClickListener(this);
        group.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {


        switch (view.getId()) {
            case R.id.huihua:
                getFragmentManager().beginTransaction().replace(R.id.fragment, contactListFragment).commit();
                break;
            case R.id.friend:
                getFragmentManager().beginTransaction().replace(R.id.fragment, friendFragment).commit();
                break;
            case R.id.group:
                getFragmentManager().beginTransaction().replace(R.id.fragment, groupFragment).commit();
                break;
        }
    }
}
