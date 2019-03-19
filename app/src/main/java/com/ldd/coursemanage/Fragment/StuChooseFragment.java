package com.ldd.coursemanage.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ldd.coursemanage.Adapter.ScoreAdapter;
import com.ldd.coursemanage.Dao.ScoreDao;
import com.ldd.coursemanage.Entity.Course;
import com.ldd.coursemanage.MainActivity;
import com.ldd.coursemanage.MyApplication;
import com.ldd.coursemanage.R;

import java.util.List;

public class StuChooseFragment extends Fragment {
    static Context context = MyApplication.getInstance();
    private static ScoreDao uncourseDao = new ScoreDao(context);   //未选的课程
    private static RecyclerView recyclerView_undo;
    private static List<Course> undoList;
    private static ScoreAdapter adapter;
    private static TextView emptyText;
    public StuChooseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stu_choose, container, false);
        recyclerView_undo = (RecyclerView) view.findViewById(R.id.recycler_undo);
        emptyText = (TextView) view.findViewById(R.id.empty_text) ;
        undoList = uncourseDao.getUnCourse(MainActivity.getStudentId());
        if(undoList==null){
            emptyText.setVisibility(View.VISIBLE);
            recyclerView_undo.setVisibility(View.GONE);
        }else {
            //StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            recyclerView_undo.setLayoutManager(layoutManager);
            ScoreAdapter adapter = new ScoreAdapter(undoList);
            recyclerView_undo.setAdapter(adapter);
            DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView_undo.getContext(),layoutManager.getOrientation());
            recyclerView_undo.addItemDecoration(mDividerItemDecoration);
        }
        return view;
    }

    /**
     * 刷新数据，点击对应课程在Adapter中调用该函数实现页面刷新
     */
    public static void updata() {
        undoList = uncourseDao.getUnCourse(MainActivity.getStudentId());
        if(undoList==null){
            emptyText.setVisibility(View.VISIBLE);
            recyclerView_undo.setVisibility(View.GONE);
        }else {
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            recyclerView_undo.setLayoutManager(layoutManager);
            ScoreAdapter adapter = new ScoreAdapter(undoList);
            recyclerView_undo.setAdapter(adapter);
            DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView_undo.getContext(),layoutManager.getOrientation());
            recyclerView_undo.addItemDecoration(mDividerItemDecoration);

        }
    }
}
