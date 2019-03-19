package com.ldd.coursemanage.Adapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;

import com.ldd.coursemanage.Fragment.StuChooseFragment;
import com.ldd.coursemanage.Fragment.StuShowFragment;

import java.util.ArrayList;
import java.util.List;




public class ViewPagerAdapter extends FragmentPagerAdapter {
    private  List<Fragment> mFragmentList = new ArrayList<>();
    private FragmentManager fm;
    public int getItemPosition(Object object) {

        if (object instanceof StuShowFragment) {
            ((StuShowFragment)object).update();
        }else if(object instanceof StuChooseFragment){
            ((StuChooseFragment)object).updata();
        }
        return super.getItemPosition(object);
    }

    public void setFragments(List<Fragment> fragments) {
        if (this.mFragmentList != null) {
            FragmentTransaction ft = fm.beginTransaction();
            for (Fragment f : this.mFragmentList) {
                ft.remove(f);
            }
            ft.commit();
            ft = null;
            fm.executePendingTransactions();
        }
        this.mFragmentList = fragments;
        notifyDataSetChanged();
    }
    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
        this.fm = manager;
    }
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment) {
        mFragmentList.add(fragment);
    }



}
