package com.example.qd.myslidingnested;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    }

    @Override
    public int getItemCount() {
        return resultBeanList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvContext;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvContext = itemView.findViewById(R.id.tvContext);
        }
    }
}
