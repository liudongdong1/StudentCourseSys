package com.ldd.coursemanage.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.ldd.coursemanage.Dao.StudentDao;
import com.ldd.coursemanage.Dao.UserDao;
import com.ldd.coursemanage.Entity.Student;
import com.ldd.coursemanage.MyApplication;
import com.ldd.coursemanage.R;
import com.ldd.coursemanage.util.EditCheck;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * create an instance of this fragment.
 */
public class TModifyStuFragment extends Fragment {
    static Context context = MyApplication.getInstance();
    private EditText id;
    private EditText name;
    private RadioButton mMaleRb;
    private RadioButton mFamaleRb;
    private EditText age;
    private EditText phone;
    public TModifyStuFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tmodify_stu, container, false);
        id = (EditText)view.findViewById(R.id.id_t);
        Button save = (Button)view.findViewById(R.id.save_t);
        Button cancel = (Button)view.findViewById(R.id.cancel_t);
        name = (EditText)view.findViewById(R.id.name_t);
        mMaleRb = (RadioButton)view.findViewById(R.id.male_rb_t);
        mFamaleRb = (RadioButton)view.findViewById(R.id.famale_rb_t);
        age = (EditText)view.findViewById(R.id.age_t);
        phone = (EditText)view.findViewById(R.id.phone_t);
        save.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String sname = name.getText().toString();
                String ssex ="";
                if (mMaleRb.isChecked()){
                    ssex="男";
                }
                else if (mFamaleRb.isChecked()){
                    ssex="女";
                }
                String sage = age.getText().toString();
                String sphone = phone.getText().toString();
                if(!EditCheck.CheckInt(id.getText().toString(),"学号",10000,99999)){
                    Toast.makeText(v.getContext(),EditCheck.getWarning(),Toast.LENGTH_SHORT).show();
                }
                else if(!EditCheck.CheckString(sname,"名字",6)){
                    Toast.makeText(v.getContext(),EditCheck.getWarning(),Toast.LENGTH_SHORT).show();
                }
                else if(!mMaleRb.isChecked()&&!mFamaleRb.isChecked()){
                    Toast.makeText(v.getContext(),"性别不能为空！",Toast.LENGTH_SHORT).show();
                }
                else if(!EditCheck.CheckInt(sage,"年龄",10,60)){
                    Toast.makeText(v.getContext(),EditCheck.getWarning(),Toast.LENGTH_SHORT).show();
                }
                else if(!EditCheck.CheckString(sphone,"电话",15)){
                    Toast.makeText(v.getContext(),EditCheck.getWarning(),Toast.LENGTH_SHORT).show();
                }
                else {
                    int sid = Integer.parseInt(id.getText().toString());
                    Student student = new Student(sid, sname, ssex, Integer.parseInt(sage), sphone, R.drawable.health);
                    if(writeDatabase(student)){
                        Toast.makeText(v.getContext(),"添加学生信息成功！",Toast.LENGTH_SHORT).show();
                        id.setText("");
                        name.setText("");
                        age.setText("");
                        phone.setText("");
                        mFamaleRb.setChecked(false);
                        mMaleRb.setChecked(false);
                    }else{
                        Toast.makeText(v.getContext(),"写入失败，请重试！（可能原因为学号重复）",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //清空输入框
                id.setText("");
                name.setText("");
                age.setText("");
                phone.setText("");
                mFamaleRb.setChecked(false);
                mMaleRb.setChecked(false);
            }
        });
        return view;
    }
    public boolean writeDatabase(Student student){
        Context context = MyApplication.getInstance();
        StudentDao stuDao = new StudentDao(context);
        UserDao user = new UserDao(context);
        if(stuDao.insertStudent(student)) {
            //插入学生信息后，设置默认登录密码为123456，账号为学号
            user.insertLog(student.getId(),"123456");
            return true;
        }
        else
            return false;
    }

}
