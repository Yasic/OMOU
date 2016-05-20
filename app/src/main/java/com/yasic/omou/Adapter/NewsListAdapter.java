package com.yasic.omou.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yasic.omou.Javabean.NewsBean;
import com.yasic.omou.R;

import java.util.List;

/**
 * Created by ESIR on 2016/3/18.
 */
public class NewsListAdapter extends BaseAdapter<NewsListAdapter.MyViewHolder, NewsBean> {

    public NewsListAdapter(Context context, List<NewsBean> newsBeanList) {
        this.context = context;
        this.objectList = newsBeanList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_news, parent,false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if (onItemClickListener != null) {
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(holder.linearLayout,position);
                }
            });
        }
        holder.tvTitle.setText(objectList.get(position).getTitle());
        super.onBindViewHolder(holder, position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTitle;
        private LinearLayout linearLayout;
        public MyViewHolder(View itemView) {
            super(itemView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.li_News);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_NewsTitle);
        }
    }

}
