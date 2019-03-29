package com.example.qd.myslidingnested;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
    private int page = 1;

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
        for (int i = 0; i < 15; i++) {
            mDats.add(i + "");
        }
        myAdapter = new MyAdapter(getActivity(), mDats);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //滑动到底部自动加载
                if (isSlideToBottom(recyclerView)) {
                    //演示模仿请求网络的效果,只加载四页数据
                    if (page < 5) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                page++;
                                for (int i = 0; i < 15; i++) {
                                    mDats.add(i + "");
                                }
                                myAdapter.setPage(page);
                                myAdapter.notifyDataSetChanged();
                            }
                        }, 1500);
                    } else {
                        Toast.makeText(getActivity(), "滑动到第" + page + "页底部了", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public View getScrollableView() {
        return recyclerView;
    }

    private boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }
}
