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
import com.yasic.omou.Presenter.NeteaseNewsPresenter;
import com.yasic.omou.R;
import com.yasic.omou.Util.FootbarViewListenr;

import java.util.List;

/**
 * Created by Yasic on 2016/5/20.
 */
public class NeteaseNewsView implements BaseViewInterface<Activity, NeteaseNewsPresenter> {
    private View view;
    private RecyclerView rvNeteaseNews;
    private Button btGJ, btSH, btGN;
    private LinearLayout liFootBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private NeteaseNewsPresenter neteaseNewsPresenter;
    private String URL = "http://news.163.com/domestic/";

    @Override
    public void init(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_neteasenews, container, false);
        rvNeteaseNews = (RecyclerView) view.findViewById(R.id.rv_NeteaseNews);
        btGJ = (Button) view.findViewById(R.id.bt_gj);
        btGN = (Button) view.findViewById(R.id.bt_gn);
        btSH = (Button) view.findViewById(R.id.bt_sh);
        liFootBar = (LinearLayout) view.findViewById(R.id.li_Bottom);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                neteaseNewsPresenter.getNews(URL);
            }
        });
        setOnClickEvent();
    }

    public void setRvNewsList(Context context, final List<NewsBean> topicItemBeanList){
        final NewsListAdapter newsListAdapter = new NewsListAdapter(context, topicItemBeanList);
        newsListAdapter.setOnItemClickListener(new NewsListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                neteaseNewsPresenter.startNewsInfoPresenter(topicItemBeanList.get(position));
            }

            @Override
            public void onItemLongCick(View v, int position) {

            }
        });
        rvNeteaseNews.setAdapter(newsListAdapter);
    }

    public void setSwipeRefreshLayoutRefreshing(boolean refresh){
        swipeRefreshLayout.setRefreshing(refresh);
    }

    private void setOnClickEvent() {
        btGJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                neteaseNewsPresenter.getNews("http://news.163.com/world/");
                URL = "http://news.163.com/world/";
            }
        });
        btSH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                neteaseNewsPresenter.getNews("http://news.163.com/domestic/");
                URL = "http://news.163.com/domestic/";
            }
        });
        btGN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                neteaseNewsPresenter.getNews("http://news.163.com/shehui/");
                URL = "http://news.163.com/shehui/";
            }
        });
    }


    public void initRvTencentNews(Context context){
        rvNeteaseNews.setLayoutManager(new LinearLayoutManager(context));
        rvNeteaseNews.setItemAnimator(new DefaultItemAnimator());
        rvNeteaseNews.setOnScrollListener(new FootbarViewListenr() {
            @Override
            public void onHide() {
                neteaseNewsPresenter.hideFootbar(liFootBar);
            }

            @Override
            public void onShow() {
                neteaseNewsPresenter.showFootbar(liFootBar);
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
    public void setPresenter(NeteaseNewsPresenter neteaseNewsPresenter) {
        this.neteaseNewsPresenter = neteaseNewsPresenter;
    }
}
