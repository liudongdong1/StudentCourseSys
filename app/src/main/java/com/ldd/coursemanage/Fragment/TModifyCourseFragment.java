package com.ldd.coursemanage.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ldd.coursemanage.Adapter.CourseAdapter;
import com.ldd.coursemanage.AddCourseActivity;
import com.ldd.coursemanage.Dao.CourseDao;
import com.ldd.coursemanage.Entity.Course;
import com.ldd.coursemanage.MyApplication;
import com.ldd.coursemanage.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TModifyCourseFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TModifyCourseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TModifyCourseFragment extends Fragment {
    static Context context = MyApplication.getInstance();
    private CourseDao course = new CourseDao(context);
    private static RecyclerView recyclerView;
    private static TextView emptyText;
    private Button add;
    private static List<Course> CourseList;
    public TModifyCourseFragment(){}
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tmodify_course, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        emptyText = (TextView) view.findViewById(R.id.empty_text);
        add = (Button) view.findViewById(R.id.add);
        CourseList = course.getCourse();
        if(CourseList==null){
            emptyText.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }else{
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(layoutManager);
            CourseAdapter adapter = new CourseAdapter(CourseList);
            recyclerView.setAdapter(adapter);
            DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),layoutManager.getOrientation());
            recyclerView.addItemDecoration(mDividerItemDecoration);
        }

        add.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                //跳转到添加课程页面
                Intent intent = new Intent(context, AddCourseActivity.class);
                startActivity(intent);
            }
        });



        return view;
    }
    public void onResume(){
        super.onResume();
        CourseList = course.getCourse();
        if(CourseList==null){
            emptyText.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }else{
            emptyText.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(layoutManager);
            CourseAdapter adapter = new CourseAdapter(CourseList);
            recyclerView.setAdapter(adapter);
            DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),layoutManager.getOrientation());
            recyclerView.addItemDecoration(mDividerItemDecoration);
        }
    }
}
