package com.example.liuhaifeng.rongliandemo.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liuhaifeng.rongliandemo.MyAPP;
import com.example.liuhaifeng.rongliandemo.R;
import com.example.liuhaifeng.rongliandemo.dao.FriendDao;
import com.example.liuhaifeng.rongliandemo.dao.friendgroupDao;
import com.example.liuhaifeng.rongliandemo.tool.FriendSQLTool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaifeng on 2017/1/18.
 */

public class Friend_groupActivity extends AppCompatActivity {
    private List<friendgroupDao> list;
    private friendgroupDao dao;
    private ListView group_lv;
    SQLiteDatabase db;
    private ImageView add;
    private Friendgroupadapter adapter;



    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_group);
        getSupportActionBar().hide();
        init();
    }

    public void init() {
        group_lv = (ListView) findViewById(R.id.friend_group_listview);
        add= (ImageView) findViewById(R.id.add_group);

        db = MyAPP.freindgrouptyopenHelper.getReadableDatabase();
        list = new ArrayList<friendgroupDao>();
        adapter = new Friendgroupadapter(list);
        group_lv.setAdapter(adapter);
        group_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(Friend_groupActivity.this,Group_add_friendActivity.class).putExtra("ty","friend_group").putExtra("id",list.get(position).getName()));
            }
        });

        getdata();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog alertDialog = null;
                android.app.AlertDialog.Builder B = null;
                Context context=Friend_groupActivity.this;
                B=new android.app.AlertDialog.Builder(context);
                final EditText et=new EditText(context);
                alertDialog=B.setTitle("请输入" )
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setView(et)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db=MyAPP.freindgrouptyopenHelper.getWritableDatabase();
                                    String s=et.getText().toString();
                                ContentValues v=new ContentValues();
                                v.put("name",s);
                                v.put("owenr",MyAPP.name);
                                db.insert("friendgroup",null,v);

                                    getdata();
                            }
                        })
                        .setNegativeButton("取消" ,  null )
                        .show();
            }
        });





    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getdata();
    }

    class Friendgroupadapter extends BaseAdapter {
        List<friendgroupDao> lis;

        public Friendgroupadapter(List<friendgroupDao> list) {
            lis = list;
        }

        @Override
        public int getCount() {
            return lis.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.friendgroupitem, null);
            TextView name = (TextView) view.findViewById(R.id.item_name);
            name.setText(lis.get(position).getName());

            ImageView imageView = (ImageView) view.findViewById(R.id.delete);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    if(lis.get(position).getFriends().size()>0) {
                        Toast.makeText(Friend_groupActivity.this,"该组内还有好友，不能删除",Toast.LENGTH_SHORT).show();
                        return;
                    }else{
                        db = MyAPP.freindgrouptyopenHelper.getWritableDatabase();
                    db.delete("friendgroup", "owenr=? and name=?", new String[]{MyAPP.name, lis.get(position).getName()});
                    lis.remove(position);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                         runOnUiThread(new Runnable() {
                             @Override
                             public void run() {
                                 adapter.notifyDataSetChanged();
                             }
                         });


                        }
                    }).start();
                }  }
            });


            return view;
        }
    }
    public  void getdata(){





        list.clear();
        Cursor cursor = db.query("friendgroup", null, "owenr=?", new String[]{MyAPP.name}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                dao = new friendgroupDao();
                dao.setName(cursor.getString(cursor.getColumnIndex("name")));
                dao.setNumber(cursor.getString(cursor.getColumnIndex("number")));
                list.add(dao);
            } while (cursor.moveToNext());

        }
        cursor.close();
        List<FriendDao> f=FriendSQLTool.query("","");
        FriendDao d;
        List<FriendDao> l;
        for(int i=0;i<list.size();i++){
            l=new ArrayList<FriendDao>();
            for(int u=0;u<f.size();u++){
                if(list.get(i).getName().equals(f.get(u).getGroupid())){
                    d=new FriendDao();
                    d.setGroupid(f.get(u).getGroupid());
                    d.setName(f.get(u).getName());
                    d.setId(f.get(u).getId());
                    d.setImage(f.get(u).getImage());
                    l.add(d);
                }

            }
            list.get(i).setFriends(l);
        }



        adapter.notifyDataSetChanged();

    }

}
