package com.example.qd.myslidingnested;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qd.myslidingnested.interfaces.ScrollableContainer;

/**
 * author: wu
 * date: on 2019/3/6.
 * describe:
 */
public class MyFragment extends Fragment implements ScrollableContainer {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_main, null);
        return view;
    }

    @Override
    public View getScrollableView() {
        return null;
    }
}
