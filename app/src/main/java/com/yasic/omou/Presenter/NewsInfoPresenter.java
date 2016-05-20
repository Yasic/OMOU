package com.yasic.omou.Presenter;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.yasic.omou.Javabean.CallbackBean;
import com.yasic.omou.Javabean.NewsBean;
import com.yasic.omou.Model.NeteaseNewsModel;
import com.yasic.omou.Model.SinaNewsModel;
import com.yasic.omou.Model.TencentNewsModel;
import com.yasic.omou.View.NewsInfoView;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Yasic on 2016/5/19.
 */
public class NewsInfoPresenter extends BasePresenterActivity<NewsInfoView> {
    SinaNewsModel sinaNewsModel = new SinaNewsModel();
    TencentNewsModel tencentNewsModel = new TencentNewsModel();
    NeteaseNewsModel neteaseNewsModel = new NeteaseNewsModel();
    @Override
    protected void onBindBVI() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String url = bundle.getString("URL");
        String title = bundle.getString("TITLE");
        String type = bundle.getString("TYPE");
        BVIView.setPresenter(this);
        BVIView.setTitle(title);
        switch (type){
            case "1":
                getSinaNewsInfo(url);
                break;
            case "2":
                getTencentNewsInfo(url);
                break;
            case "3":
                getNetEaseNewsInfo(url);
                break;
        }
    }

    private void getNetEaseNewsInfo(final String url) {
        Observable.create(new Observable.OnSubscribe<CallbackBean<NewsBean>>() {
            @Override
            public void call(Subscriber<? super CallbackBean<NewsBean>> subscriber) {
                CallbackBean<NewsBean> callbackBean = neteaseNewsModel.getNewsDetail(url);
                subscriber.onNext(callbackBean);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<CallbackBean<NewsBean>>() {
                    @Override
                    public void call(CallbackBean<NewsBean> callbackBean) {
                        if (callbackBean.getCode().equals("0")) {
                            BVIView.setInfo(callbackBean.getResponse().getDetail());
                        } else {
                            if (callbackBean.getErrorMessage() == null || callbackBean.getErrorMessage().equals("")) {
                                Toast.makeText(getApplicationContext(), "貌似网络出现了错误？", Toast.LENGTH_LONG).show();
                            }
                            Toast.makeText(NewsInfoPresenter.this, callbackBean.getErrorMessage(), Toast.LENGTH_LONG).show();
                        }
                        //BVIView.setProgressBarGone();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        try{
                            //BVIView.setProgressBarGone();
                        }catch (Exception e){

                        }
                    }
                });
    }

    private void getTencentNewsInfo(final String url) {
        Observable.create(new Observable.OnSubscribe<CallbackBean<NewsBean>>() {
            @Override
            public void call(Subscriber<? super CallbackBean<NewsBean>> subscriber) {
                CallbackBean<NewsBean> callbackBean = tencentNewsModel.getNewsDetail(url);
                subscriber.onNext(callbackBean);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<CallbackBean<NewsBean>>() {
                    @Override
                    public void call(CallbackBean<NewsBean> callbackBean) {
                        if (callbackBean.getCode().equals("0")) {
                            BVIView.setInfo(callbackBean.getResponse().getDetail());
                        } else {
                            if (callbackBean.getErrorMessage() == null || callbackBean.getErrorMessage().equals("")) {
                                Toast.makeText(getApplicationContext(), "貌似网络出现了错误？", Toast.LENGTH_LONG).show();
                            }
                            Toast.makeText(NewsInfoPresenter.this, callbackBean.getErrorMessage(), Toast.LENGTH_LONG).show();
                        }
                        //BVIView.setProgressBarGone();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        try{
                            //BVIView.setProgressBarGone();
                        }catch (Exception e){

                        }
                    }
                });
    }

    public void getSinaNewsInfo(final String url) {
        Observable.create(new Observable.OnSubscribe<CallbackBean<NewsBean>>() {
            @Override
            public void call(Subscriber<? super CallbackBean<NewsBean>> subscriber) {
                CallbackBean<NewsBean> callbackBean = sinaNewsModel.getNewsDetail(url);
                subscriber.onNext(callbackBean);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<CallbackBean<NewsBean>>() {
                    @Override
                    public void call(CallbackBean<NewsBean> callbackBean) {
                        if (callbackBean.getCode().equals("0")) {
                            BVIView.setInfo(callbackBean.getResponse().getDetail());
                        } else {
                            if (callbackBean.getErrorMessage() == null || callbackBean.getErrorMessage().equals("")) {
                                Toast.makeText(getApplicationContext(), "貌似网络出现了错误？", Toast.LENGTH_LONG).show();
                            }
                            Toast.makeText(NewsInfoPresenter.this, callbackBean.getErrorMessage(), Toast.LENGTH_LONG).show();
                        }
                        //BVIView.setProgressBarGone();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        try{
                            //BVIView.setProgressBarGone();
                        }catch (Exception e){

                        }
                    }
                });
    }

    @Override
    protected Class<NewsInfoView> getBVIClass() {
        return NewsInfoView.class;
    }
}
