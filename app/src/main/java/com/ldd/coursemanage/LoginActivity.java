package com.ldd.coursemanage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ldd.coursemanage.Dao.UserDao;
import com.ldd.coursemanage.util.EditCheck;
import com.ldd.coursemanage.util.MyDatabaseHelper;
import com.ldd.coursemanage.util.NormalDialog;

/**
 *branch test1 下的修改
 * gsdgsdfg
 * */
public class LoginActivity extends AppCompatActivity {
    private MyDatabaseHelper dbHelper;
    private EditText account;
    private EditText password;
    private Button log;
    private Button sign;
    private NormalDialog normalDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        normalDialog=NormalDialog.getInstance(this);
        dbHelper = new MyDatabaseHelper(this);
        dbHelper.getWritableDatabase();
        account = findViewById(R.id.account);
        password = findViewById(R.id.password);
        log = findViewById(R.id.login);
        sign = findViewById(R.id.signin);
        final UserDao user = new UserDao(this);
        log.setOnClickListener(new View.OnClickListener() {         //点击登录，跳转到MainActivity,传入对应id
            @Override
            public void onClick(View view) {
                user.listAllIdAndPassword();       //Log查看是否错误
                String id_str = account.getText().toString();
                String passwd = password.getText().toString();

                if(!EditCheck.CheckInt(id_str,"账号",0,99999)){
                    normalDialog.showNormalDialog(EditCheck.getWarning());
                }
                else if(!EditCheck.CheckString(passwd,"密码",20)){
                    normalDialog.showNormalDialog(EditCheck.getWarning());
                }
                else if(user.check(Integer.parseInt(id_str),passwd)){              //账号匹配成功,进入MainActivity
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    intent.putExtra("id",Integer.parseInt(id_str));
                    startActivity(intent);
                    finish();
                }else{                              //弹出错误提示框
                    String word = "账号或密码错误！";
                    normalDialog.showNormalDialog(word);
                }

            }
        });
        sign.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(LoginActivity.this,ModifyPasswdActivity.class);
                startActivity(intent);
            }
        });
    }

}

