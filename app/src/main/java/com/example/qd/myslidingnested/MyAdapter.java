package com.example.qd.myslidingnested;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

/**
 * Created by qd on 2018/2/2.
 * 更多分类的adapter
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<String> resultBeanList;
    private Context context;
    private LayoutInflater inflater;
    private int page = 1;

    public MyAdapter(Context context, List<String> resultBeanList) {
        this.resultBeanList = resultBeanList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.adapter_my, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tvContext.setText(position + "");
        if (page == 4 && position == resultBeanList.size() - 1) {
            holder.tv_load.setText("已经全部加载完毕");
            holder.ll_load.setVisibility(View.VISIBLE);//显示底部加载框
            holder.pb_load.setVisibility(View.GONE);//隐藏加载圆圈
        } else {
            if (position == resultBeanList.size() - 1) {
                holder.tv_load.setText("正在加载中");
                holder.ll_load.setVisibility(View.VISIBLE);//显示底部加载框
                holder.pb_load.setVisibility(View.VISIBLE);//显示加载圆圈
            } else {
                holder.ll_load.setVisibility(View.GONE);
            }
        }
    }

    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public int getItemCount() {
        return resultBeanList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvContext, tv_load;
        ProgressBar pb_load;
        LinearLayout ll_load;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvContext = itemView.findViewById(R.id.tvContext);
            pb_load = itemView.findViewById(R.id.pb_load);
            tv_load = itemView.findViewById(R.id.tv_load);
            ll_load = itemView.findViewById(R.id.ll_load);
        }
    }
}
