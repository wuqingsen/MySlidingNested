package com.example.qd.myslidingnested;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qd.myslidingnested.interfaces.ScrollableContainer;
import com.example.qd.myslidingnested.utils.MyCoordinatorLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author: wu
 * date: on 2019/3/6.
 * describe:
 */
public class MyFragment extends Fragment implements ScrollableContainer {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private List<String> mDats;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_main, null);
        ButterKnife.bind(this, view);
        setData();
        return view;
    }

    private void setData() {
        mDats = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            mDats.add(i + "");
        }
        myAdapter = new MyAdapter(getActivity(), mDats);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public View getScrollableView() {
        return null;
    }
}
