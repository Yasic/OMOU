package com.yasic.omou.View;

import android.app.Activity;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.yasic.omou.Adapter.NewsListAdapter;
import com.yasic.omou.Javabean.NewsBean;
import com.yasic.omou.Presenter.TencentNewsPresenter;
import com.yasic.omou.R;
import com.yasic.omou.Util.FootbarViewListenr;

import java.util.List;

/**
 * Created by Yasic on 2016/5/19.
 */
public class TencentNewsView implements BaseViewInterface<Activity, TencentNewsPresenter> {
    View view;
    private RecyclerView rvTencentNews;
    private Button btGJ, btSH, btJS;
    private LinearLayout liFootBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TencentNewsPresenter tencentNewsPresenter;
    private String URL = "http://news.qq.com/world_index.shtml";

    @Override
    public void init(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_tencentnews, container, false);
        rvTencentNews = (RecyclerView) view.findViewById(R.id.rv_TencentNews);
        btGJ = (Button) view.findViewById(R.id.bt_gj);
        btJS = (Button) view.findViewById(R.id.bt_js);
        btSH = (Button) view.findViewById(R.id.bt_sh);
        liFootBar = (LinearLayout) view.findViewById(R.id.li_Bottom);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                tencentNewsPresenter.getNews(URL);
            }
        });
        setOnClickEvent();
    }

    public void setRvNewsList(Context context, final List<NewsBean> topicItemBeanList){
        final NewsListAdapter newsListAdapter = new NewsListAdapter(context, topicItemBeanList);
        newsListAdapter.setOnItemClickListener(new NewsListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                tencentNewsPresenter.startNewsInfoPresenter(topicItemBeanList.get(position));
            }

            @Override
            public void onItemLongCick(View v, int position) {

            }
        });
        rvTencentNews.setAdapter(newsListAdapter);
    }

    public void setSwipeRefreshLayoutRefreshing(boolean refresh){
        swipeRefreshLayout.setRefreshing(refresh);
    }

    private void setOnClickEvent() {
        btGJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tencentNewsPresenter.getNews("http://news.qq.com/world_index.shtml");
                URL = "http://news.qq.com/world_index.shtml";
            }
        });
        btSH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tencentNewsPresenter.getNews("http://news.qq.com/society_index.shtml");
                URL = "http://news.qq.com/society_index.shtml";
            }
        });
        btJS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tencentNewsPresenter.getNews("http://mil.qq.com/mil_index.htm");
                URL = "http://mil.qq.com/mil_index.htm";
            }
        });
    }


    public void initRvTencentNews(Context context){
        rvTencentNews.setLayoutManager(new LinearLayoutManager(context));
        rvTencentNews.setItemAnimator(new DefaultItemAnimator());
        rvTencentNews.setOnScrollListener(new FootbarViewListenr() {
            @Override
            public void onHide() {
                tencentNewsPresenter.hideFootbar(liFootBar);
            }

            @Override
            public void onShow() {
                tencentNewsPresenter.showFootbar(liFootBar);
            }
        });
    }

    @Override
    public View getView() {
        return view;
    }

    @Override
    public void setPresenter(Activity activity) {

    }

    @Override
    public void setPresenter(TencentNewsPresenter tencentNewsPresenter) {
        this.tencentNewsPresenter = tencentNewsPresenter;
    }
}
