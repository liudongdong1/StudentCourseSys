package com.ldd.coursemanage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ldd.coursemanage.Dao.UserDao;
import com.ldd.coursemanage.Entity.User;
import com.ldd.coursemanage.util.EditCheck;
import com.ldd.coursemanage.util.NormalDialog;


public class ModifyPasswdActivity extends AppCompatActivity {
    private EditText account;
    private EditText passwdOld;
    private EditText passwdNew;
    private NormalDialog normalDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_passwd);
        normalDialog=NormalDialog.getInstance(this);
        account = (EditText)findViewById(R.id.account_m);
        passwdOld = (EditText)findViewById(R.id.passwd_o);
        passwdNew = (EditText)findViewById(R.id.passwd_n);
        Button  commit = (Button)findViewById(R.id.commit_m);
        Button cancel = (Button)findViewById(R.id.cancel_m);
        commit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String word;
                if(!EditCheck.CheckInt(account.getText().toString(),"账号",10000,99999)){
                    normalDialog.showNormalDialog(EditCheck.getWarning());
                }
                else if(!EditCheck.CheckString(passwdOld.getText().toString(),"旧密码",20)) {
                    normalDialog.showNormalDialog(EditCheck.getWarning());
                }
                else if(!EditCheck.CheckString(passwdNew.getText().toString(),"新密码",20)){
                    normalDialog.showNormalDialog(EditCheck.getWarning());
                }
                else {
                    User user = new User(Integer.parseInt(account.getText().toString()),passwdOld.getText().toString());
                    String password = passwdNew.getText().toString();
                    if(writeDatabase(user,password)){
                        word = "修改密码成功！";
                        normalDialog.showNormalDialog(word);
                        //延时1s
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, 1000);
                    }
                    else{
                        word="修改失败！账号与原密码不匹配！请重试：";
                        normalDialog.showNormalDialog(word);
                    }
                }

            }
        });
        cancel.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                finish();
            }
        });
    }
    private boolean writeDatabase(User user,String passwd){
        UserDao userDao = new UserDao(this);
        if(userDao.modifyPasswd(user,passwd))
            return true;
        else
            return false;
    }
}

