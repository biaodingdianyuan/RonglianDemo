package com.example.liuhaifeng.rongliandemo;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.liuhaifeng.rongliandemo.dao.ContactDao;
import com.example.liuhaifeng.rongliandemo.tool.ContactopenHelper;
import com.example.liuhaifeng.rongliandemo.tool.GroupopenHelper;
import com.example.liuhaifeng.rongliandemo.tool.MessageopenHelper;
import com.yuntongxun.ecsdk.ECDevice;
import com.yuntongxun.ecsdk.ECMessage;
import com.yuntongxun.ecsdk.OnChatReceiveListener;
import com.yuntongxun.ecsdk.im.ECMessageNotify;
import com.yuntongxun.ecsdk.im.group.ECGroupNoticeMessage;
import com.yuntongxun.ecsdk.im.group.ECProposerMsg;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaifeng on 2016/12/23.
 */

public class MyAPP extends Application {
    private static MyAPP instance;
    public static String name;
    public  static int i;
    public static String APPKEY = "8a216da859204cc9015929b9cf1c059b";
    public  static List<ContactDao> Contact_list;
    public static ContactopenHelper  contactopenHelper;
    public static GroupopenHelper groupopenHelper;
    public static MessageopenHelper messageopenHelper;


    @Override
    public void onCreate() {
        super.onCreate();
        name=null;
        Contact_list=new ArrayList<ContactDao>();
        contactopenHelper=new ContactopenHelper(getApplicationContext(),"my.db",null,1);
        groupopenHelper=new GroupopenHelper(getApplicationContext(),"my",null,1);
        messageopenHelper=new MessageopenHelper(getApplicationContext(),"db",null,1);


    }
    /**
     * 单例，返回一个实例
     * @return
     */
    public static MyAPP getInstance() {
        if (instance == null) {

        }
        return instance;
    }

        public  static void OnChatReceiveListener(){
            ECDevice.setOnChatReceiveListener(new OnChatReceiveListener() {
                @Override
                public void OnReceivedMessage(ECMessage ecMessage) {

                }

                @Override
                public void onReceiveMessageNotify(ECMessageNotify ecMessageNotify) {

                }

                @Override
                public void OnReceiveGroupNoticeMessage(ECGroupNoticeMessage notice) {
                    if (notice == null) {
                        return;
                    }
                    // 有人申请加入群组（仅限于管理员）
                    if (notice.getType() == ECGroupNoticeMessage.ECGroupMessageType.PROPOSE) {
                        ECProposerMsg proposerMsg = (ECProposerMsg) notice;



                        // 处理申请加入群组请求通知
                    }
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

}
