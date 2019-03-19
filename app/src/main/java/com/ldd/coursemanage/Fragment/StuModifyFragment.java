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

import com.ldd.coursemanage.Adapter.StudentAdapter;
import com.ldd.coursemanage.Dao.StudentDao;
import com.ldd.coursemanage.Entity.Student_info;
import com.ldd.coursemanage.MainActivity;
import com.ldd.coursemanage.MyApplication;
import com.ldd.coursemanage.R;
import com.ldd.coursemanage.StudentChangeActivity;

import java.util.List;

public class StuModifyFragment extends Fragment {
    Context context = MyApplication.getInstance();
    private StudentDao student = new StudentDao(context);
    private List<Student_info> StudentList = student.getStudent(MainActivity.getStudentId());
    private RecyclerView recyclerView;
    private int studentId = MainActivity.getStudentId();
    public StuModifyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stu_modify, container, false);
        //initCourse();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        //StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        StudentAdapter adapter = new StudentAdapter(StudentList);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),layoutManager.getOrientation());
        recyclerView.addItemDecoration(mDividerItemDecoration);

        Button button = (Button) view.findViewById (R.id.click);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                Intent intent = new Intent(context, StudentChangeActivity.class);
                intent.putExtra("id",studentId);
                startActivity(intent);

            }

        });

        // Inflate the layout for this fragment
        return view;
    }
    public void onResume(){
        super.onResume();
        StudentList = student.getStudent(MainActivity.getStudentId());
        StudentAdapter adapter = new StudentAdapter(StudentList);
        recyclerView.setAdapter(adapter);

    }
}
