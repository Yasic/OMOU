package com.yasic.omou.Presenter;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.yasic.omou.Javabean.CallbackBean;
import com.yasic.omou.Javabean.NewsBean;
import com.yasic.omou.Model.SinaNewsModel;
import com.yasic.omou.View.SinaNewsView;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Yasic on 2016/5/19.
 */
public class SinaNewsPresenter extends BasePresenterFragment<SinaNewsView> {
    private SinaNewsModel sinaNewsModel = new SinaNewsModel();

    @Override
    protected void onBindBVI() {
        BVIView.setPresenter(this);
        BVIView.initRvSinaNews(getActivity().getApplication());
        sinaNewsModel.getNewsList("http://news.sina.com.cn/china/");
        BVIView.getView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                BVIView.getView().getViewTreeObserver().removeGlobalOnLayoutListener(this);
                BVIView.setSwipeRefreshLayoutRefreshing(true);
            }
        });
        getNews("http://news.sina.com.cn/china/");
    }

    public void hideFootbar(final View view) {
        ViewPropertyAnimator animator = view.animate().
                translationY(view.getHeight()).
                setInterpolator(new FastOutSlowInInterpolator()).setDuration(200);
        animator.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                showFootbar(view);
            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.start();
    }


    public void showFootbar(final View view) {
        ViewPropertyAnimator animator = view.animate().
                translationY(0).
                setInterpolator(new FastOutSlowInInterpolator()).setDuration(200);
        animator.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {
                hideFootbar(view);
            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.start();

    }

    public void startNewsInfoPresenter(NewsBean newsBean){
        Intent intent = new Intent(getActivity(), NewsInfoPresenter.class);
        Bundle bundle = new Bundle();
        bundle.putString("URL", newsBean.getUrl());
        bundle.putString("TITLE", newsBean.getTitle());
        bundle.putString("TYPE", "1");
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void getNews(final String url) {
        BVIView.setSwipeRefreshLayoutRefreshing(true);
        Observable.create(new Observable.OnSubscribe<CallbackBean<List<NewsBean>>>() {
            @Override
            public void call(Subscriber<? super CallbackBean<List<NewsBean>>> subscriber) {
                CallbackBean<List<NewsBean>> callbackBean = sinaNewsModel.getNewsList(url);
                subscriber.onNext(callbackBean);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<CallbackBean<List<NewsBean>>>() {
                    @Override
                    public void call(CallbackBean<List<NewsBean>> callbackBean) {
                        if (callbackBean.getCode().equals("0")) {
                            BVIView.setRvNewsList(getActivity().getApplicationContext(), callbackBean.getResponse());
                        } else {
                            if (callbackBean.getErrorMessage() == null || callbackBean.getErrorMessage().equals("")) {
                                Toast.makeText(getContext(), "貌似网络出现了错误？", Toast.LENGTH_LONG).show();
                            }
                            Toast.makeText(getContext(), callbackBean.getErrorMessage(), Toast.LENGTH_LONG).show();
                        }
                        BVIView.setSwipeRefreshLayoutRefreshing(false);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        try{
                            BVIView.setSwipeRefreshLayoutRefreshing(false);
                        }catch (Exception e){

                        }
                    }
                });
    }

    @Override
    protected Class<SinaNewsView> getBVIClass() {
        return SinaNewsView.class;
    }
}
