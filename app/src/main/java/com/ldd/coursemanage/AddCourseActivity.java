package com.ldd.coursemanage;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ldd.coursemanage.Dao.CourseDao;
import com.ldd.coursemanage.Entity.Course;
import com.ldd.coursemanage.util.EditCheck;
import com.ldd.coursemanage.util.NormalDialog;

public class AddCourseActivity extends AppCompatActivity {
    private EditText idText;
    private EditText nameText;
    private EditText creditText;
    private NormalDialog normalDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        normalDialog=NormalDialog.getInstance(this);
        idText = (EditText)findViewById(R.id.studentId);
        nameText = (EditText)findViewById(R.id.courseId);
        creditText = (EditText)findViewById(R.id.credit);
        Button add = (Button)findViewById(R.id.add);
        Button delete = (Button)findViewById(R.id.delete);
        Button cancel = (Button)findViewById(R.id.cancel_a);
        add.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String word;
                if(!EditCheck.CheckInt(idText.getText().toString(),"课程号",10000,99999)){
                    normalDialog.showNormalDialog(EditCheck.getWarning());
                }
                else if(!EditCheck.CheckString(nameText.getText().toString(),"课程名",10)){
                    normalDialog.showNormalDialog(EditCheck.getWarning());
                }
                else if(!EditCheck.CheckInt(creditText.getText().toString(),"学分",1,10)){
                    normalDialog.showNormalDialog(EditCheck.getWarning());
                }
                else if(addDatebase()){
                    word = "添加课程成功！";
                    normalDialog.showNormalDialog(word);
                    idText.setText("");
                    nameText.setText("");
                    creditText.setText("");
                    // finish();
                }else{
                    word = "添加课程失败，请重试！";
                    normalDialog.showNormalDialog(word);
                }
            }
        });
        delete.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String word ;
                if(idText.getText().toString().equals("")){
                    word = "课程号不能为空！";
                    normalDialog.showNormalDialog(word);
                }
                else if(deleteDatabase()){
                    word = "删除课程成功！";
                    normalDialog.showNormalDialog(word);
                    idText.setText("");
                    nameText.setText("");
                    creditText.setText("");
                    // finish();
                }else{
                    word = "删除课程失败，请重试！";
                    normalDialog.showNormalDialog(word);
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                finish();
            }
        });

    }


    /**
     * 向数据库添加课程信息
     */
    public boolean addDatebase(){
        CourseDao courseDao = new CourseDao(MyApplication.getInstance());
        int courseId = Integer.parseInt(idText.getText().toString());
        String courseName = nameText.getText().toString();
        int credit = Integer.parseInt(creditText.getText().toString());
        Course course = new Course(courseId,courseName,credit,R.drawable.health);
        if(courseDao.addCourse(course)){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 在数据库删除课程信息
     */
    public boolean deleteDatabase(){
        CourseDao courseDao = new CourseDao(MyApplication.getInstance());
        int courseId = Integer.parseInt(idText.getText().toString());
        if(courseDao.deleteCourse(courseId)){
            return true;
        }else{
            return false;
        }
    }
}
