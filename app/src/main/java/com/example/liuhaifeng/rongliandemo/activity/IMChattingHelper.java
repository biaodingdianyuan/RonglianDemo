package com.example.liuhaifeng.rongliandemo.activity;

import com.yuntongxun.ecsdk.ECMessage;
import com.yuntongxun.ecsdk.OnChatReceiveListener;
import com.yuntongxun.ecsdk.im.ECMessageNotify;
import com.yuntongxun.ecsdk.im.group.ECGroupNoticeMessage;

import java.util.List;

/**
 * Created by liuhaifeng on 2016/12/26.
 */

public class IMChattingHelper implements OnChatReceiveListener {
    @Override
    public void OnReceivedMessage(ECMessage ecMessage) {

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


}
