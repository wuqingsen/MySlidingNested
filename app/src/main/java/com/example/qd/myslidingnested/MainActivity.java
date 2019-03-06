package com.example.qd.myslidingnested;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andview.refreshview.XRefreshView;
import com.coorchice.library.SuperTextView;
import com.example.qd.myslidingnested.interfaces.AppBarLayoutObserved;
import com.example.qd.myslidingnested.utils.MyCoordinatorLayout;
import com.example.qd.myslidingnested.utils.MyNestedScrollView;
import com.example.qd.myslidingnested.utils.VerticalLinearLayout;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements AppBarLayoutObserved {
    @BindView(R.id.coordinatorLayout)
    MyCoordinatorLayout coordinatorLayout;
    @BindView(R.id.appbarLayout)
    AppBarLayout appbarLayout;
    @BindView(R.id.nestedScrollView)
    MyNestedScrollView nestedScrollView;
    @BindView(R.id.NestedVerLinearLayout)
    VerticalLinearLayout NestedVerLinearLayout;
    @BindView(R.id.smartTabLayout)
    SmartTabLayout smartTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.xRefreshView)
    XRefreshView xRefreshView;
    private FragmentAdapter fragmentAdapter;
    private List<MyFragment> fragments;
    private List<String> mDataList;
    private HashMap<Integer, SuperTextView> mapTitle;
    private int mStatus = STATUS_EXPANDED;
    public static final int STATUS_EXPANDED = 1;//展开
    public static final int STATUS_COLLAPSED = 2;//收缩
    private int headHeight;
    private int minHeadTopHeight;
    private int lastVerticalOffset = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setList();
        setView();
        setListener();
    }

    private void setList() {
        fragments = new ArrayList<>();
        mapTitle = new HashMap<>();
        mDataList = new ArrayList<>();
        mDataList.add("热门");
        mDataList.add("关注");
        mDataList.add("游戏");
        mDataList.add("音乐");
        mDataList.add("直播");
        mDataList.add("娱乐");
        mDataList.add("性教育");
        mDataList.add("PM2.5");
        for (int i = 0; i < mDataList.size(); i++) {
            fragments.add(new MyFragment());
        }
    }

    private void setView() {
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(fragmentAdapter);
        coordinatorLayout.setCurrentScrollableContainer(fragments.get(0));
        smartTabLayout.setCustomTabView(new MyTabProvider());
        smartTabLayout.setViewPager(mViewPager);
        headHeight = (int) this.getResources().getDimension(R.dimen.headHeight);
        minHeadTopHeight = (int) this.getResources().getDimension(R.dimen.minHeadTopHeight);
    }

    private void setListener() {
        xRefreshView.setPullRefreshEnable(true);
        xRefreshView.setPullLoadEnable(false);
        //防止横向滑动冲突
        xRefreshView.setMoveForHorizontal(true);
        //下拉刷新
        xRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh(boolean isPullDown) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        xRefreshView.stopRefresh(true);
                    }
                }, 1500);
            }
        });
        //标题滑动监听
        appbarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                //verticalOffset 向上滑动得到的值是负的，初始值为0 就是展开状态
                //剩下未滑出屏幕的高度
                int h = headHeight + verticalOffset;
                if (verticalOffset == 0) {
                    //展开状态
                    mStatus = STATUS_EXPANDED;
                } else if (h == minHeadTopHeight) {
                    mStatus = STATUS_COLLAPSED;
                } else {
                    mStatus = 0;
                }
                if (lastVerticalOffset != verticalOffset) {
                    lastVerticalOffset = verticalOffset;
                }
            }
        });
        //viewPager滑动监听
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                coordinatorLayout.setCurrentScrollableContainer(fragments.get(position));
                for (HashMap.Entry<Integer, SuperTextView> entry : mapTitle.entrySet()) {
                    setTitle(false, entry.getValue());
                }
                setTitle(true, mapTitle.get(position));
            }
        });
        coordinatorLayout.setAppBarLayoutObserved(this);
        coordinatorLayout.setxRefreshView(xRefreshView);
        coordinatorLayout.setNestedScrollView(nestedScrollView);

    }

    @Override
    public int getAppBarLayoutStatus() {
        return mStatus;
    }

    /**
     * MyTitle
     */
    private class MyTabProvider implements SmartTabLayout.TabProvider {

        private LayoutInflater inflater;

        public MyTabProvider() {
            inflater = LayoutInflater.from(MainActivity.this);
        }

        @Override
        public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
            View view = inflater.inflate(R.layout.my_tab_layout, container, false);
            SuperTextView superTextView = view.findViewById(R.id.tab_text);
            mapTitle.put(position, superTextView);
            superTextView.setText(mDataList.get(position));
            setTitle(false, superTextView);
            //选中第一项
            if (position == 0) {
                setTitle(true, superTextView);
            }
            return view;
        }
    }

    /**
     * setTitleStyle
     *
     * @param isShowState true：显示对号；否则隐藏
     */
    private void setTitle(boolean isShowState, SuperTextView superTextView) {
        if (isShowState) {
            //字体大小为20，并且加粗
            superTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            superTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            superTextView.setTextColor(Color.parseColor("#FF333333"));
            superTextView.setShowState(true);
        } else {
            superTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            superTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            superTextView.setTextColor(Color.parseColor("#FF999999"));
            superTextView.setShowState(false);
        }
    }
}
