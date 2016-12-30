package com.example.liuhaifeng.rongliandemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.liuhaifeng.rongliandemo.R;
import com.yuntongxun.ecsdk.ECDevice;
import com.yuntongxun.ecsdk.ECError;
import com.yuntongxun.ecsdk.ECGroupManager;
import com.yuntongxun.ecsdk.SdkErrorCode;
import com.yuntongxun.ecsdk.im.ECGroup;
import com.yuntongxun.ecsdk.im.ECGroupMatch;

import java.util.List;

/**
 * Created by liuhaifeng on 2016/12/30.
 */

public class Group_add_friendActivity extends AppCompatActivity {
    private EditText friend_id;
    private Button add,query;
    String from;
    String groupid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupaddfriend);
        getSupportActionBar().hide();
        Intent intent = getIntent();
        from = intent.getStringExtra("from");
        groupid = intent.getStringExtra("groupid");
        init();

    }

    public void init() {
        friend_id = (EditText) findViewById(R.id.friend_id_group);
        add = (Button) findViewById(R.id.add_friend_group);
        query= (Button) findViewById(R.id.query_group);
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//创建查询参数规则（按照群组id检索）
                ECGroupMatch match = new ECGroupMatch(ECGroupMatch.SearchType.GROUPID);
// 设置检索词（根据匹配规则输入群组I的或者名称）
                match.setkeywords(friend_id.getText().toString());
// 获得SDK群组管理类
                ECGroupManager groupManager = ECDevice.getECGroupManager();
// 调用检索群组接口，设置结果回调
                groupManager.searchPublicGroups(match,
                        new ECGroupManager.OnSearchPublicGroupsListener() {
                            @Override
                            public void onSearchPublicGroupsComplete(ECError error
                                    , List<ECGroup> groups) {
                                if (error.errorCode == SdkErrorCode.REQUEST_SUCCESS && groups != null){
                                    // 检索群组成功
                                    // 根据查询到的群组选择是否申请加入群组
                                    return;
                                }
                                // 检索群组取失败
                                Log.e("ECSDK_Demo", "search group fail " +
                                        ", errorCode=" + error.errorCode);

                            }


                        });
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 设置群组ID
                String groupId = groupid;
                // 设置邀请加入理由
                String declare = from+"邀请你加入群组";
                // 设置邀请加入的群组成员（可多选）
                String[] members = new String[]{friend_id.getText().toString()};
                // 是否需要对方确认（NEED_CONFIRM 需要对方验证，FORCE_PULL 不需要对方验证）
                final ECGroupManager.InvitationMode confirm = ECGroupManager.InvitationMode.FORCE_PULL;
                // 获得SDK群组管理类
                ECGroupManager groupManager = ECDevice.getECGroupManager();
                // 调用邀请加入群组接口，设置结果回调
                groupManager.inviteJoinGroup(groupId, declare, members, confirm , new ECGroupManager.OnInviteJoinGroupListener() {
                             @Override
                            public void onInviteJoinGroupComplete(ECError error
                                    , String groupId, String[] members) {
                                if (error.errorCode == SdkErrorCode.REQUEST_SUCCESS) {
                                    // 邀请加入成功 ,1默认是直接就拉进群组
                                    if (confirm == ECGroupManager.InvitationMode.FORCE_PULL) {
                                        // 直接拉进群组，不需要被邀请成员是否同意
                                        return;
                                    }
                                    Toast.makeText(Group_add_friendActivity.this, "邀请加入群组成功，请等待Smith验证",
                                            Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                // 群组邀请成员失败
                                Log.d("ECSDK_Demo", "invite join group fail , errorCode="
                                        + error.errorCode);

                            }
                        });
            }
        });


    }
}
