package com.example.liuhaifeng.rongliandemo.activity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.liuhaifeng.rongliandemo.MyAPP;
import com.example.liuhaifeng.rongliandemo.R;
import com.example.liuhaifeng.rongliandemo.dao.GroupDao;
import com.example.liuhaifeng.rongliandemo.tool.GroupopenHelper;
import com.yuntongxun.ecsdk.ECDevice;
import com.yuntongxun.ecsdk.ECError;
import com.yuntongxun.ecsdk.ECGroupManager;
import com.yuntongxun.ecsdk.SdkErrorCode;
import com.yuntongxun.ecsdk.im.ECGroup;
import com.yuntongxun.ecsdk.im.ECGroupMatch;

import java.util.List;

/**
 * Created by liuhaifeng on 2016/12/29.
 */

public class CreateGroupActivity extends AppCompatActivity {
    private EditText name, declare;
    private Button create_group,joiningroup;
private ECGroup group;
    private SQLiteDatabase db;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creategroup);
        getSupportActionBar().hide();
        init();


    }

    public void init() {
        name = (EditText) findViewById(R.id.group_name);
        declare = (EditText) findViewById(R.id.group_declare);
        joiningroup= (Button) findViewById(R.id.join_ingroup);
        create_group = (Button) findViewById(R.id.create_group);

        joiningroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //创建查询参数规则（按照群组id检索）
                ECGroupMatch match = new ECGroupMatch(ECGroupMatch.SearchType.GROUPID);
                // 设置检索词（根据匹配规则输入群组I的或者名称）
                match.setkeywords(name.getText().toString());
                // 获得SDK群组管理类
                ECGroupManager groupManager = ECDevice.getECGroupManager();
                // 调用检索群组接口，设置结果回调
                groupManager.searchPublicGroups(match, new ECGroupManager.OnSearchPublicGroupsListener() {
                            @Override
                            public void onSearchPublicGroupsComplete(ECError error , List<ECGroup> groups) {
                                if (error.errorCode == SdkErrorCode.REQUEST_SUCCESS ){
                                    String groupId = name.getText().toString();
                                    // 设置申请加入理由
                                    String declare = "Smith申请加入群组";
                                    // 获得SDK群组管理类
                                    ECGroupManager groupManager = ECDevice.getECGroupManager();
                                    // 调用审请加入群组接口，设置结果回调
                                    groupManager.joinGroup(groupId, declare
                                            , new ECGroupManager.OnJoinGroupListener() {
                                                @Override
                                                public void onJoinGroupComplete(ECError error, String groupId) {
                                                    if((SdkErrorCode.REQUEST_SUCCESS == error.errorCode)|| (SdkErrorCode.MEMBER_ALREADY_EXIST == error.errorCode)) {
                                                        // 申请加入群组成功(SdkErrorCode.MEMBER_ALREADY_EXIST代表
                                                        //申请者已经是群组成员)
                                                        // 根据申请的群组权限（permission字段）来区分
                                                        // 是否直接加入成功或者需要管理员审核

                                                        Toast.makeText(CreateGroupActivity.this , "申请加入群组成功，请等待管理员审核" ,
                                                                Toast.LENGTH_SHORT).show();
                                                        return ;
                                                    }
                                                    // 群组申请失败
                                                    Log.e("ECSDK_Demo", "join group fail , errorCode="
                                                            + error.errorCode);

                                                }


                                            });


                                    return;

                                }
                                // 检索群组取失败
                                Log.e("ECSDK_Demo", "search group fail " + error.errorCode);

                            }


                        });







/*
*         String groupId = name.getText().toString();
                                    // 设置申请加入理由
                                    String declare = "Smith申请加入群组";
                                    // 获得SDK群组管理类
                                    ECGroupManager groupManager = ECDevice.getECGroupManager();
                                    // 调用审请加入群组接口，设置结果回调
                                    groupManager.joinGroup(groupId, declare
                                            , new ECGroupManager.OnJoinGroupListener() {
                                                @Override
                                                public void onJoinGroupComplete(ECError error, String groupId) {
                                                    if((SdkErrorCode.REQUEST_SUCCESS == error.errorCode)|| (SdkErrorCode.MEMBER_ALREADY_EXIST == error.errorCode)) {
                                                        // 申请加入群组成功(SdkErrorCode.MEMBER_ALREADY_EXIST代表
                                                        //申请者已经是群组成员)
                                                        // 根据申请的群组权限（permission字段）来区分
                                                        // 是否直接加入成功或者需要管理员审核

                                                        Toast.makeText(CreateGroupActivity.this , "申请加入群组成功，请等待管理员审核" ,
                                                                Toast.LENGTH_SHORT).show();
                                                        return ;
                                                    }
                                                    // 群组申请失败
                                                    Log.e("ECSDK_Demo", "join group fail , errorCode="
                                                            + error.errorCode);

                                                }


                                            });


                                    return;
* */


            }
        });



        create_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                group= new ECGroup();
                MyAPP.i=MyAPP.i++;
                group.setGroupId(   MyAPP.i+""+MyAPP.name+MyAPP.APPKEY);
                group.setName(name.getText().toString());
                group.setDeclare(declare.getText().toString());
                group.setIsDiscuss(false);
                group.setOwner(MyAPP.name);
                group.setPermission(ECGroup.Permission.NEED_AUTH);
                group.setScope(ECGroup.Scope.TEMP);
                ECGroupManager groupManager = ECDevice.getECGroupManager();
                // 调用创建群组接口，设置创建结果回调
                groupManager.createGroup(group, new ECGroupManager.OnCreateGroupListener() {
                    @Override
                    public void onCreateGroupComplete(ECError ecError, ECGroup ecGroup) {
                        if (ecError.errorCode == SdkErrorCode.REQUEST_SUCCESS) {
                            // 群组/讨论组创建成功
                            // 缓存创建的群组/讨论组到数据库，同时通知UI进行更新
                            db = MyAPP.groupopenHelper.getWritableDatabase();
                            ContentValues values = new ContentValues();
                            values.put("groupid",ecGroup.getGroupId());
                            values.put("name", name.getText().toString());
                            values.put("declare", declare.getText().toString());
                            values.put("owner", MyAPP.name);
                            db.insert("group_data", null, values);
                            finish();


                        }else{
                            Toast.makeText(CreateGroupActivity.this,"创建失败",Toast.LENGTH_SHORT).show();

                        }

                    }


                });


            }
        });

    }


}
