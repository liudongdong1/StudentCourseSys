package com.ldd.coursemanage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.ldd.coursemanage.Adapter.ViewPagerAdapter;
import com.ldd.coursemanage.Fragment.StuChooseFragment;
import com.ldd.coursemanage.Fragment.StuModifyFragment;
import com.ldd.coursemanage.Fragment.StuShowFragment;
import com.ldd.coursemanage.Fragment.TInputSourseFragment;
import com.ldd.coursemanage.Fragment.TModifyCourseFragment;
import com.ldd.coursemanage.Fragment.TModifyStuFragment;
import com.ldd.coursemanage.util.MyDatabaseHelper;

public class MainActivity extends AppCompatActivity {
    //dbHelper
    private MyDatabaseHelper dbHelper;
    private static int studentId;
    BottomNavigationView bottomNavigationView;
    //This is our viewPager
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    StuShowFragment stuShowFragment;
    StuChooseFragment stuChooseFragment;
    StuModifyFragment stuModifyFragment;
    TInputSourseFragment tInputSourseFragment;
    TModifyCourseFragment tModifyCourseFragment;
    TModifyStuFragment tModifyStuFragment;
    MenuItem prevMenuItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        studentId = intent.getIntExtra("id",-1);
        //创建或打开数据库
        dbHelper = new MyDatabaseHelper(this);
        dbHelper.getWritableDatabase();
        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        //Initializing the bottomNavigationView
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                }
                else
                {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                Log.d("page", "onPageSelected: "+position);
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



        setupViewPager(viewPager);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_first:
                                viewPager.setCurrentItem(0);
                                break;
                            case R.id.action_second:
                                viewPager.setCurrentItem(1);
                                break;
                            case R.id.action_third:
                                viewPager.setCurrentItem(2);
                                break;
                        }
                        return false;
                    }
                });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.switch_item:
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.quit_item:
                finish();
                break;
            default:
        }
        return true;
    }

    public static int  getStudentId(){
        return studentId;
    }
    public Context context(){return this;}

    /**
     * @function 更具登陆者Id信息，将对应的Fragment加载到ViewPagerAdapter中
     * */
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        if(getStudentId()==0){
            tModifyCourseFragment=new TModifyCourseFragment();
            tInputSourseFragment=new TInputSourseFragment();
            tModifyStuFragment=new TModifyStuFragment();
            adapter.addFragment(tModifyCourseFragment);
            adapter.addFragment(tInputSourseFragment);
            adapter.addFragment(tModifyStuFragment);
        }else {
            stuShowFragment = new StuShowFragment();
            stuChooseFragment= new StuChooseFragment();
            stuModifyFragment = new StuModifyFragment();
            adapter.addFragment(stuShowFragment);
            adapter.addFragment( stuChooseFragment);
            adapter.addFragment(stuModifyFragment);
        }
        viewPager.setAdapter(adapter);
    }
}
