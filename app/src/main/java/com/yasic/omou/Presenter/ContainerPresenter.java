package com.yasic.omou.Presenter;

import android.support.v7.widget.Toolbar;

import com.yasic.omou.R;
import com.yasic.omou.View.ContainerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yasic on 2016/5/18.
 */
public class ContainerPresenter extends BasePresenterActivity<ContainerView> {
    @Override
    protected void onBindBVI() {
        setSupportActionBar((Toolbar) BVIView.getView().findViewById(R.id.tb_container));
        BVIView.setPresenter(this);
        List<String> tabTitleList = new ArrayList<>();
        tabTitleList.add("新浪新闻");
        tabTitleList.add("腾讯新闻");
        tabTitleList.add("网易新闻");
        List<BasePresenterFragment> basePresenterFragmentList = new ArrayList<>();
        basePresenterFragmentList.add(new SinaNewsPresenter());
        basePresenterFragmentList.add(new TencentNewsPresenter());
        basePresenterFragmentList.add(new NeteaseNewsPresenter());
        BVIView.setViewPagerAndTablayout(tabTitleList, basePresenterFragmentList);
    }


    @Override
    protected Class<ContainerView> getBVIClass() {
        return ContainerView.class;
    }
}
