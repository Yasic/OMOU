package com.yasic.omou.View;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yasic.omou.Presenter.NewsInfoPresenter;
import com.yasic.omou.R;

/**
 * Created by Yasic on 2016/5/19.
 */
public class NewsInfoView implements BaseViewInterface<NewsInfoPresenter,Fragment> {
    private View view;
    private TextView tvNewsTitle, tvArticle;
    private NewsInfoPresenter newsInfoPresenter;
    @Override
    public void init(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.activity_newsinfo, container, false);
        tvNewsTitle = (TextView) view.findViewById(R.id.tv_NewsTitle);
        tvArticle = (TextView) view.findViewById(R.id.tv_Article);
    }

    public void setTitle(String title){
        tvNewsTitle.setText(title);
    }

    public void setInfo(String info){
        tvArticle.setText(info);
    }

    @Override
    public View getView() {
        return view;
    }

    @Override
    public void setPresenter(NewsInfoPresenter newsInfoPresenter) {
        this.newsInfoPresenter = newsInfoPresenter;
    }

    @Override
    public void setPresenter(Fragment fragment) {

    }
}
