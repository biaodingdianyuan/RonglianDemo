package com.example.liuhaifeng.rongliandemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.liuhaifeng.rongliandemo.MyAPP;
import com.example.liuhaifeng.rongliandemo.R;
import com.yuntongxun.ecsdk.ECDevice;
import com.yuntongxun.ecsdk.ECError;
import com.yuntongxun.ecsdk.ECInitParams;
import com.yuntongxun.ecsdk.ECMessage;
import com.yuntongxun.ecsdk.OnChatReceiveListener;
import com.yuntongxun.ecsdk.SdkErrorCode;
import com.yuntongxun.ecsdk.im.ECMessageNotify;
import com.yuntongxun.ecsdk.im.group.ECGroupNoticeMessage;
import java.util.List;

/**
 * Created by liuhaifeng on 2016/12/23.
 */

public class LoginActivity extends AppCompatActivity {
    private EditText username,password;
    private Button sign_in;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        init();
    }

   public void init(){
       username= (EditText) findViewById(R.id.username);
       password= (EditText) findViewById(R.id.password);
       sign_in= (Button) findViewById(R.id.sign_in);
       sign_in.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               // TODO: 2016/12/23 调用登陆接口
               ECInitParams params = ECInitParams.createParams();
               params.setUserid(username.getText().toString());
               params.setPwd(password.getText().toString());
               params.setAppKey("8a216da859204cc9015929b9cf1c059b");
               params.setToken("01ab28ade1f09a72247de099cd345743");
               //设置登陆验证模式：自定义登录方式
               params.setAuthType(ECInitParams.LoginAuthType.PASSWORD_AUTH);
               //LoginMode（强制上线：FORCE_LOGIN  默认登录：AUTO。使用方式详见注意事项）
               params.setMode(ECInitParams.LoginMode.FORCE_LOGIN);
               if(params.validate()) {
                   // 登录函数
                   ECDevice.login(params);
                   MyAPP app=new MyAPP();
                   app.name=username.getText().toString();
               }
               Initialized();
           }
       });
   }

    //初始化
    public void Initialized(){
        //判断SDK是否已经初始化
        if(!ECDevice.isInitialized()) {
 /*  initial: ECSDK 初始化接口
            * 参数：
            *     inContext - Android应用上下文对象
            *     inListener - SDK初始化结果回调接口，ECDevice.InitListener
            *
            * 说明：示例在应用程序创建时初始化 SDK引用的是Application的上下文，
            *       开发者可根据开发需要调整。
            */
            ECDevice.initial(LoginActivity.this, new ECDevice.InitListener() {
                @Override
                public void onInitialized() {
                    // SDK已经初始化成功
                 ;
                    //设置登录回调监听
                    ECDevice.setOnDeviceConnectListener(new ECDevice.OnECDeviceConnectListener() {
                        @Override
                        public void onConnect() {

                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            finish();
                        }
                        @Override
                        public void onDisconnect(ECError ecError) {

                        }
                        @Override
                        public void onConnectState(ECDevice.ECConnectState ecConnectState, ECError ecError) {

                        }
                    });

                }
                @Override
                public void onError(Exception exception) {
                    //在初始化错误的方法中打印错误原因
                    Log.i("","初始化SDK失败"+exception.getMessage());
                }
            });
        }

    }

}
