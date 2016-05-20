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
import com.yasic.omou.Presenter.SinaNewsPresenter;
import com.yasic.omou.R;
import com.yasic.omou.Util.FootbarViewListenr;

import java.util.List;

/**
 * Created by Yasic on 2016/5/19.
 */
public class SinaNewsView implements BaseViewInterface<Activity, SinaNewsPresenter> {
    private View view;
    private Button btGN, btGJ, btSH;
    private RecyclerView rvSinaNews;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SinaNewsPresenter sinaNewsPresenter;
    private LinearLayout liFootBar;
    private String URL = "http://news.sina.com.cn/china/";

    @Override
    public void init(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_sinanews, container, false);
        btGN = (Button) view.findViewById(R.id.bt_gn);
        btGJ = (Button) view.findViewById(R.id.bt_gj);
        btSH = (Button) view.findViewById(R.id.bt_sh);
        rvSinaNews = (RecyclerView) view.findViewById(R.id.rv_SinaNews);
        liFootBar = (LinearLayout) view.findViewById(R.id.li_Bottom);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sinaNewsPresenter.getNews(URL);
            }
        });
        setOnClickEvent();
    }

    public void setSwipeRefreshLayoutRefreshing(boolean refresh){
        swipeRefreshLayout.setRefreshing(refresh);
    }

    private void setOnClickEvent() {
        btGJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sinaNewsPresenter.getNews("http://news.sina.com.cn/world/");
                URL = "http://news.sina.com.cn/world/";
            }
        });
        btGN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sinaNewsPresenter.getNews("http://news.sina.com.cn/china/");
                URL = "http://news.sina.com.cn/china/";
            }
        });
        btSH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sinaNewsPresenter.getNews("http://news.sina.com.cn/society/");
                URL = "http://news.sina.com.cn/society/";
            }
        });
    }

    public void setRvNewsList(Context context, final List<NewsBean> topicItemBeanList){
        final NewsListAdapter newsListAdapter = new NewsListAdapter(context, topicItemBeanList);
        newsListAdapter.setOnItemClickListener(new NewsListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                sinaNewsPresenter.startNewsInfoPresenter(topicItemBeanList.get(position));
            }

            @Override
            public void onItemLongCick(View v, int position) {

            }
        });
        rvSinaNews.setAdapter(newsListAdapter);
    }

    public void initRvSinaNews(Context context){
        rvSinaNews.setLayoutManager(new LinearLayoutManager(context));
        rvSinaNews.setItemAnimator(new DefaultItemAnimator());
        rvSinaNews.setOnScrollListener(new FootbarViewListenr() {
            @Override
            public void onHide() {
                sinaNewsPresenter.hideFootbar(liFootBar);
            }

            @Override
            public void onShow() {
                sinaNewsPresenter.showFootbar(liFootBar);
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
    public void setPresenter(SinaNewsPresenter sinaNewsPresenter) {
        this.sinaNewsPresenter = sinaNewsPresenter;
    }
}
