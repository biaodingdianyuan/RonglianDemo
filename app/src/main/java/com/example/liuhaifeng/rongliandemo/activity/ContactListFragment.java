package com.example.liuhaifeng.rongliandemo.activity;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.liuhaifeng.rongliandemo.MyAPP;
import com.example.liuhaifeng.rongliandemo.R;
import com.example.liuhaifeng.rongliandemo.dao.ContactDao;
import com.example.liuhaifeng.rongliandemo.dao.FriendDao;
import com.example.liuhaifeng.rongliandemo.dao.data;
import com.example.liuhaifeng.rongliandemo.tool.ContactopenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaifeng on 2016/12/29.
 */

public class ContactListFragment extends Fragment {
   private ListView contactlv;
    private List<ContactDao> list;
    private  ContactDao contactDao;
    ContactAdapter adapter;
    ContactopenHelper openHelper;
    private SQLiteDatabase db;
    data d=new data
            ();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      View view=inflater.inflate(R.layout.fragment_contactlist,null);
        contactlv= (ListView) view.findViewById(R.id.lv_contact);
        list=new ArrayList<ContactDao>();
        adapter=new ContactAdapter(list);
        contactlv.setAdapter(adapter);
       for(int i=0;i<getdata().size();i++){
           contactDao=new ContactDao();
           contactDao.setImage(getdata().get(i).getImage());
           contactDao.setId(getdata().get(i).getId());
           contactDao.setListMessage(getdata().get(i).getListMessage());
           contactDao.setTime(getdata().get(i).getTime());
           contactDao.setName(getdata().get(i).getName());
           list.add(contactDao);

       }





       adapter.notifyDataSetChanged();
        contactlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(getActivity(),ChattingActivity.class).putExtra("from", MyAPP.name).putExtra("to",list.get(i).getName()).putExtra("ty","0"));
            }
        });




        return view;
    }

    class  ContactAdapter extends BaseAdapter{
        private List<ContactDao> list;
        public ContactAdapter( List<ContactDao> list) {
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
            view=getActivity().getLayoutInflater().inflate(R.layout.contactlist_item,null);
            TextView name= (TextView) view.findViewById(R.id.user_name_item);
            TextView listmessage= (TextView) view.findViewById(R.id.listmeg_item);
            TextView time= (TextView) view.findViewById(R.id.listtime);
            listmessage.setText(list.get(i).getListMessage());
            time.setText(list.get(i).getTime());
            name.setText(list.get(i).getName().toString());
            return view;
        }
    }
    public  List<ContactDao> getdata(){
        openHelper=new ContactopenHelper(getActivity(),"my.db",null,1);
        ContactDao c;
        List<ContactDao> l=new ArrayList<ContactDao>();
        db=openHelper.getReadableDatabase();
        Cursor cursor=db.query("contact",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do {
                c=new ContactDao();
                String id=cursor.getString(cursor.getColumnIndex("id"));
            String name=cursor.getString(cursor.getColumnIndex("name"));
            String listmessage=cursor.getString(cursor.getColumnIndex("listmessage"));
            String time=cursor.getString(cursor.getColumnIndex("time"));
            String img_url=cursor.getString(cursor.getColumnIndex("img_url"));
            String ty=cursor.getString(cursor.getColumnIndex("ty"));
                c.setImage(img_url);
                c.setId(id);
                c.setName(name);
                c.setTime(time);
                c.setListMessage(listmessage);

              l.add(c);

        }while (cursor.moveToNext());

        }


        return l;
    }

    @Override
    public void onResume() {
        super.onResume();
        list.clear();
        for(int i=0;i<getdata().size();i++){
            contactDao=new ContactDao();
            contactDao.setImage(getdata().get(i).getImage());
            contactDao.setId(getdata().get(i).getId());
            contactDao.setListMessage(getdata().get(i).getListMessage());
            contactDao.setTime(getdata().get(i).getTime());
            contactDao.setName(getdata().get(i).getName());
            list.add(contactDao);

        }
        adapter.notifyDataSetChanged();
    }
}
