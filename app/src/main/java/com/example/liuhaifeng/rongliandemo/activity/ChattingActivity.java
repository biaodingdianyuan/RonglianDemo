package com.example.liuhaifeng.rongliandemo.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.liuhaifeng.rongliandemo.MyAPP;
import com.example.liuhaifeng.rongliandemo.R;
import com.example.liuhaifeng.rongliandemo.dao.ContactDao;
import com.example.liuhaifeng.rongliandemo.dao.MessageDao;
import com.example.liuhaifeng.rongliandemo.dao.Msg;
import com.example.liuhaifeng.rongliandemo.tool.ContactopenHelper;
import com.example.liuhaifeng.rongliandemo.tool.MessageopenHelper;
import com.yuntongxun.ecsdk.ECChatManager;
import com.yuntongxun.ecsdk.ECDevice;
import com.yuntongxun.ecsdk.ECError;
import com.yuntongxun.ecsdk.ECMessage;
import com.yuntongxun.ecsdk.OnChatReceiveListener;
import com.yuntongxun.ecsdk.im.ECMessageNotify;
import com.yuntongxun.ecsdk.im.ECTextMessageBody;
import com.yuntongxun.ecsdk.im.group.ECGroupNoticeMessage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by liuhaifeng on 2016/12/23.
 */

public class ChattingActivity extends AppCompatActivity {

    private List<ContactDao> list;
    private List<MessageDao> mes_list;
    private ListView chatting_lv;
    private EditText input;
    private Button send;
    private MsgAdapter adapter;
    private TextView title_chat;
    private ImageView left_chat;

    private List<Msg> msgList = new ArrayList<Msg>();
    ContactDao contactDao = new ContactDao();
    Msg msg;
    String from_name;
    String to_name;
    String ty;
    String groupid;
    MyAPP app = new MyAPP();
    private SQLiteDatabase db;
    private StringBuilder sb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        getSupportActionBar().hide();
        Intent intent = getIntent();
        from_name = intent.getStringExtra("from");
        to_name = intent.getStringExtra("to");
        ty = intent.getStringExtra("ty");
        if(ty.equals("2")){
            groupid=intent.getStringExtra("groupid");
        }
        init();


    }

    public void init() {

        List<MessageDao> m_l=getmes(to_name);
        Msg m;
        for(int i=0;i<m_l.size();i++){
            if(m_l.get(i).getType().equals("TYPE_SENT")){
                m=new Msg(m_l.get(i).getMessage(),Msg.TYPE_SENT);
            }else{
                m=new Msg(m_l.get(i).getMessage(),Msg.TYPE_RECEIVED);
            }
            msgList.add(m);

        }

        adapter = new MsgAdapter(ChattingActivity.this, R.layout.msg_item, msgList);
        chatting_lv = (ListView) findViewById(R.id.chatting_lv);
        input = (EditText) findViewById(R.id.input);
        send = (Button) findViewById(R.id.send);
        chatting_lv.setAdapter(adapter);
        title_chat= (TextView) findViewById(R.id.title_chatting);
        left_chat= (ImageView) findViewById(R.id.left_img_chat);


        title_chat.setText(to_name);

        if(ty.equals("2")){
            left_chat.setVisibility(View.VISIBLE);
            left_chat.setImageResource(R.drawable.add);

        }else{
            left_chat.setVisibility(View.GONE);
        }
        left_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //群组拉人
                startActivity(new Intent(ChattingActivity.this,Group_add_friendActivity.class).putExtra("groupid",groupid).putExtra("from",from_name));
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ECMessage m = ECMessage.createECMessage(ECMessage.Type.TXT);
                if (ty.equals("1")) {
                    m.setTo(MyAPP.APPKEY + "#" + to_name);
                } else if (ty.equals("0")) {
                    m.setTo(to_name);
                }else if(ty.equals("2")){
                    m.setTo(groupid);

                }


                ECTextMessageBody msgBody = new ECTextMessageBody(input.getText().toString());
                m.setBody(msgBody);
                ECChatManager manager = ECDevice.getECChatManager();
                manager.sendMessage(m, new ECChatManager.OnSendMessageListener() {
                    @Override
                    public void onProgress(String s, int i, int i1) {

                    }

                    @Override
                    public void onSendMessageComplete(ECError ecError, ECMessage ecMessage) {
                        if (ecError.errorMsg.equals("")) {
                            String message = input.getText().toString();
                            if (!message.equals("")) {
                                Msg msg = new Msg(message, Msg.TYPE_SENT);
                                msgList.add(msg);
                                SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
                                String data = df.format(new Date()).toString();
                                adapter.notifyDataSetChanged();
                                chatting_lv.setSelection(msgList.size());
                                list=new ArrayList<ContactDao>();
                                contactDao=new ContactDao();
                                contactDao.setId(ecMessage.getTo());
                                contactDao.setImage("");
                                contactDao.setName(to_name);
                                contactDao.setListMessage(ecMessage.getBody().toString());
                                contactDao.setTime(data);
                                list.add(contactDao);
                                intocontact(list);

                                mes_list=new ArrayList<MessageDao>();
                                MessageDao messageDao=new MessageDao();
                                messageDao.setName(to_name);
                                messageDao.setTime(ecMessage.getMsgTime()+"");
                                messageDao.setMessage(ecMessage.getBody()+"");
                                messageDao.setTy("1");
                                messageDao.setType("TYPE_SENT");
                                mes_list.add(messageDao);
                                intomessage(mes_list);

                                input.setText("");
//                                for (int i = 0; i < app.Contact_list.size(); i++) {
//                                    if (app.Contact_list.get(i).getName().equals(ecMessage.getTo())) {
//                                        app.Contact_list.remove(i);
//                                    }
//                                }
//                                contactDao.setId(APPKEY + "#" + to_name);
//                                contactDao.setName(ecMessage.getTo());
//
//                                contactDao.setTime(data);
//                                contactDao.setListMessage(ecMessage.getBody().toString());
//                                app.Contact_list.add(contactDao);
                            }
                        }
                    }
                });
            }

        });


        ECDevice.setOnChatReceiveListener(new OnChatReceiveListener() {
            @Override
            public void OnReceivedMessage(ECMessage ecMessage) {

                if (ecMessage.getType().equals(ECMessage.Type.TXT)) {
                    msg = new Msg(ecMessage.getBody().toString(), Msg.TYPE_RECEIVED);
                    msgList.add(msg);
                    adapter.notifyDataSetChanged();
                    list=new ArrayList<ContactDao>();
                    contactDao = new ContactDao();
                    contactDao.setId(ecMessage.getForm());
                    contactDao.setName(ecMessage.getForm());
                    contactDao.setListMessage(ecMessage.getBody().toString());
                    contactDao.setTime(ecMessage.getMsgTime() + "");
                    list.add(contactDao);
                    mes_list=new ArrayList<MessageDao>();
                    MessageDao messageDao=new MessageDao();
                    messageDao.setName(ecMessage.getForm());
                    messageDao.setTime(ecMessage.getMsgTime()+"");
                    messageDao.setMessage(ecMessage.getBody()+"");
                    messageDao.setTy("1");
                    messageDao.setType("TYPE_RECEIVED");
                    mes_list.add(messageDao);
                    intomessage(mes_list);
                    intocontact(list);

                }
            }

            @Override
            public void onReceiveMessageNotify(ECMessageNotify ecMessageNotify) {

            }

            @Override
            public void OnReceiveGroupNoticeMessage(ECGroupNoticeMessage ecGroupNoticeMessage) {

            }

            @Override
            public void onOfflineMessageCount(int i) {

            }

            @Override
            public int onGetOfflineMessage() {
                return 0;
            }

            @Override
            public void onReceiveOfflineMessage(List<ECMessage> list) {

            }

            @Override
            public void onReceiveOfflineMessageCompletion() {

            }

            @Override
            public void onServicePersonVersion(int i) {

            }

            @Override
            public void onReceiveDeskMessage(ECMessage ecMessage) {

            }

            @Override
            public void onSoftVersion(String s, int i) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
    public void intocontact(List<ContactDao> list){
        db=MyAPP.contactopenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id",list.get(0).getId());
        values.put("name",list.get(0).getName());
        values.put("listmessage",list.get(0).getListMessage());
        values.put("time",list.get(0).getTime());
        values.put("img_url","");
        values.put("ty","1");

        Cursor cursor = db.query("contact",null,"name=?",new String[]{list.get(0).getName().toString()},null,null,null);
        if(cursor.getCount()>0){
            db.update("contact",values,"name=?",new String[]{list.get(0).getName().toString()});
        }else {
            db.insert("contact", null, values);

        }
    }
    public void intomessage(List<MessageDao> list){
        db=MyAPP.messageopenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",list.get(0).getName());
        values.put("message",list.get(0).getMessage());
        values.put("time",list.get(0).getTime());
        values.put("type",list.get(0).getType());
        values.put("ty",list.get(0).getTy());
            db.insert("message", null, values);
    }
    private  List<MessageDao> getmes(String name_k){
        List<MessageDao> list=new ArrayList<MessageDao>();
        MessageDao messageDao;
        db=MyAPP.messageopenHelper.getReadableDatabase();
            Cursor cursor = db.query("message", null, "name=?", new String[]{name_k}, null, null, null);
            if (cursor != null) {
                if(cursor.moveToFirst()) {
                    do {
                        messageDao=new MessageDao();
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String message = cursor.getString(cursor.getColumnIndex("message"));
                        String ty = cursor.getString(cursor.getColumnIndex("ty"));
                        String type = cursor.getString(cursor.getColumnIndex("type"));
                        String time = cursor.getString(cursor.getColumnIndex("time"));
                        messageDao.setName(name);
                        messageDao.setType(type);
                        messageDao.setTy(ty);
                        messageDao.setMessage(message);
                        messageDao.setTime(time);
                        list.add(messageDao);
                    } while (cursor.moveToNext());
                }
            }
            cursor.close();


        return list;
    }

}
