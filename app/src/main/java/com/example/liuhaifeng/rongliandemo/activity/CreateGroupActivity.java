package com.example.liuhaifeng.rongliandemo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.liuhaifeng.rongliandemo.MyAPP;
import com.example.liuhaifeng.rongliandemo.R;
import com.example.liuhaifeng.rongliandemo.dao.GroupDao;
import com.yuntongxun.ecsdk.ECDevice;
import com.yuntongxun.ecsdk.ECError;
import com.yuntongxun.ecsdk.ECGroupManager;
import com.yuntongxun.ecsdk.SdkErrorCode;
import com.yuntongxun.ecsdk.im.ECGroup;

/**
 * Created by liuhaifeng on 2016/12/29.
 */

public class CreateGroupActivity extends AppCompatActivity {
    private EditText name,declare;
    private Button create_group;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creategroup);



    }
    public  void init(){
        name= (EditText) findViewById(R.id.group_name);
        declare= (EditText) findViewById(R.id.group_declare);
        create_group= (Button) findViewById(R.id.create_group);
        create_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GroupDao group=new GroupDao();
                group.setName(name.getText().toString());
                group.setDeclare(declare.getText().toString());
                group.setIsdiscuss(false);
                group.setOwner(MyAPP.name);
                group.setPermission(ECGroup.Permission.NEED_AUTH);
                group.setScope(ECGroup.Scope.TEMP);
                ECGroupManager groupManager = ECDevice.getECGroupManager();
// 调用创建群组接口，设置创建结果回调

                groupManager.createGroup(group, new ECGroupManager.OnCreateGroupListener(){
                    @Override
                    public void onCreateGroupComplete(ECError ecError, ECGroup ecGroup) {
                        if(ecError.errorCode == SdkErrorCode.REQUEST_SUCCESS) {
                            // 群组/讨论组创建成功
                            // 缓存创建的群组/讨论组到数据库，同时通知UI进行更新
                            return ;
                        }
                        // 群组/讨论组创建失败
                        Log.e("ECSDK_Demo" , "create group fail , errorCode="
                                + ecError.errorCode);
                    }


                });



            }
        });

    }


}
