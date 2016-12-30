package com.example.liuhaifeng.rongliandemo.Service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.yuntongxun.ecsdk.ECMessage;
import com.yuntongxun.ecsdk.OnChatReceiveListener;
import com.yuntongxun.ecsdk.booter.ECNotifyReceiver;
import com.yuntongxun.ecsdk.im.ECMessageNotify;
import com.yuntongxun.ecsdk.im.group.ECGroupNoticeMessage;

import java.util.List;

/**
 * Created by liuhaifeng on 2016/12/30.
 */

public class OnChatListener extends ECNotifyReceiver {

    public static final String SERVICE_OPT_CODE = "service_opt_code";
    public static final String MESSAGE_SUB_TYPE  = "message_type";

    /** 来电 */
    public static final int EVENT_TYPE_CALL = 1;
    /** 消息推送 */
    public static final int EVENT_TYPE_MESSAGE = 2;
    /**
     * 创建一个服务Intent
     * @return Intent
     */
    public Intent buildServiceAction(int optCode) {
        Intent notifyIntent = new Intent(getContext(), NotifyService.class);
        notifyIntent.putExtra("service_opt_code" , optCode);
        return notifyIntent;
    }



    /**
     * 创建一个服务Intent
     * @return Intent
     */
    public Intent buildMessageServiceAction(int subOptCode) {
        Intent intent = buildServiceAction(EVENT_TYPE_MESSAGE);
        intent.putExtra(MESSAGE_SUB_TYPE , subOptCode);
        return intent;
    }

    @Override
    public void onReceivedMessage(Context context, ECMessage msg) {
        Intent intent = buildMessageServiceAction(OPTION_SUB_NORMAL);
        intent.putExtra(EXTRA_MESSAGE , msg);
        context.startService(intent);
    }

    @Override
    public void onReceiveGroupNoticeMessage(Context context, ECGroupNoticeMessage notice) {
        Intent intent = buildMessageServiceAction(OPTION_SUB_GROUP);
        intent.putExtra(EXTRA_MESSAGE , notice);
        context.startService(intent);
    }

    @Override
    public void onNotificationClicked(Context context, int i, String s) {

    }
    public static class NotifyService extends Service {
        public static final String TAG = "ECSDK_Demo.NotifyService";
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
        private void receiveImp(Intent intent) {
            if(intent == null) {
                Log.e("", "receiveImp receiveIntent == null");
                return ;
            }
            Log.i("", "intent:action " + intent.getAction());
            int optCode = intent.getIntExtra(SERVICE_OPT_CODE, 0);
            if(optCode == 0) {
                Log.e("", "receiveImp invalid opcode.");
                return ;
            }
//            if(!SDKCoreHelper.isUIShowing()) {
//                SDKCoreHelper.init(this);
//            }
            switch (optCode) {
                case EVENT_TYPE_MESSAGE:
                    dispatchOnReceiveMessage(intent);
                    break;
            }
        }

        // Android Version 2.0以下版本
        @Override
        public void onStart(Intent intent, int startId) {
            super.onStart(intent, startId);
            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.ECLAIR) {
                receiveImp(intent);
            }
        }

        // Android 2.0以上版本回调/同时会执行onStart方法
        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            Log.v("", String.format("onStartCommand flags :%d startId :%d intent %s", flags, startId, intent));
            receiveImp(intent);
            return super.onStartCommand(intent, flags, startId);
        }


        private void dispatchOnReceiveMessage(Intent intent) {
            if(intent == null) {
                Log.e("" , "dispatch receive message fail.");
                return ;
            }
            int subOptCode = intent.getIntExtra(MESSAGE_SUB_TYPE , -1);
            if(subOptCode == -1) {
                return ;
            }
            switch (subOptCode){
                case OPTION_SUB_NORMAL:
                    ECMessage message = intent.getParcelableExtra(EXTRA_MESSAGE);
                   // IMChattingHelper.getInstance().OnReceivedMessage(message);
                    break;
                case OPTION_SUB_GROUP:
                    ECGroupNoticeMessage notice = intent.getParcelableExtra(EXTRA_MESSAGE);
                  //  IMChattingHelper.getInstance().OnReceiveGroupNoticeMessage(notice);
                    break;
                case OPTION_SUB_MESSAGE_NOTIFY:
                    ECMessageNotify notify = intent.getParcelableExtra(EXTRA_MESSAGE);
                   // IMChattingHelper.getInstance().onReceiveMessageNotify(notify);
                    break;
            }
        }
    }
}
