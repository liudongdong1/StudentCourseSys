package com.ldd.coursemanage.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ldd.coursemanage.Adapter.CourseSecAdapter;
import com.ldd.coursemanage.Dao.ScoreDao;
import com.ldd.coursemanage.Entity.CourseSec;
import com.ldd.coursemanage.MainActivity;
import com.ldd.coursemanage.MyApplication;
import com.ldd.coursemanage.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StuShowFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class StuShowFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    static Context context = MyApplication.getInstance();
    private static ScoreDao courseDao = new ScoreDao(context);
    private static RecyclerView recyclerView;
    private static TextView emptyText;
    private static List<CourseSec> CourseList;

    private OnFragmentInteractionListener mListener;

    public StuShowFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stu_show, container, false);
        // initCourse();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        emptyText = (TextView) view.findViewById(R.id.empty_text);      //用于在无数据时进行提示
        CourseList = courseDao.getScore(MainActivity.getStudentId());      //获取已选课程的List
        if(CourseList==null){
            //若数据为空，则显示emptyText,隐藏recyclerView
            emptyText.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }else {
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);       //线性布局
            recyclerView.setLayoutManager(layoutManager);                               //RecyclerView加载线性布局
            CourseSecAdapter adapter = new CourseSecAdapter(CourseList);                //创建课程类适配器，参数为刚查询到的存有课程信息的CourseList
            recyclerView.setAdapter(adapter);                                           //将适配器加载入recyclerView中
            //为RecyclerView中的每一项加分割线
            DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),layoutManager.getOrientation());
            recyclerView.addItemDecoration(mDividerItemDecoration);
        }
        // Inflate the layout for this fragment
        return view;
    }
    /**
     * 刷新数据，点击对应课程在Adapter中调用该函数实现页面刷新
     * 内容为重新查询数据库中的数据放入recyclerView
     */
    public static void update() {
        CourseList = courseDao.getScore(MainActivity.getStudentId());
        if(CourseList==null){
            emptyText.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }else {
            emptyText.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(layoutManager);
            CourseSecAdapter adapter = new CourseSecAdapter(CourseList);
            recyclerView.setAdapter(adapter);
            DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),layoutManager.getOrientation());
            recyclerView.addItemDecoration(mDividerItemDecoration);
        }
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
