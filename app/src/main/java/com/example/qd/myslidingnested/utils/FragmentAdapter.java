package com.example.qd.myslidingnested.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

import com.example.qd.myslidingnested.MyFragment;

import java.util.List;
/**
 * author: wu
 * date: on 2019/3/6.
 * describe:
 */
public class FragmentAdapter extends BaseFragmentAdapter<MyFragment> {


    public FragmentAdapter(FragmentManager fm, List<MyFragment> fragments) {
        super(fm,fragments);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return "test"+position;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        if(object.getClass().getName().equals(MyFragment.class.getName())){
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }
}
