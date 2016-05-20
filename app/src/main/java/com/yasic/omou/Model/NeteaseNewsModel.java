package com.yasic.omou.Model;

import android.util.Log;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.yasic.omou.Javabean.CallbackBean;
import com.yasic.omou.Javabean.NewsBean;
import com.yasic.omou.Util.OkhttpUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yasic on 2016/5/20.
 */
public class NeteaseNewsModel implements ITopicModel {
    @Override
    public CallbackBean getNewsList(String url) {
        OkhttpUtil okhttpUtil = OkhttpUtil.getInstance();
        final Request request = new Request.Builder().url(url).build();
        try {
            List<NewsBean> newsBeanList = new ArrayList<>();
            Response response = okhttpUtil.okHttpClient.newCall(request).execute();
            Document document = Jsoup.parse(response.body().string());
            if (url.equals("http://news.163.com/world/")){
                /*Elements elements = document.select("div.national_news_wrap");
                elements = elements.get(0).select("div.national_news_content");
                elements = elements.get(0).select("div.m2");
                Log.i("ss1", elements.text() + " " + elements.toString());
                elements = elements.get(0).select("div.area-main");
                Log.i("ss2", elements.text() + " " + elements.toString());
                elements = elements.get(0).select("div.wgt-tab");
                Log.i("ss3", elements.text() + " " + elements.toString());
                elements = elements.get(0).select("div.bd");
                Log.i("ss4", elements.text() + " " + elements.toString());
                elements = elements.get(0).select("div.tab-bd");
                Log.i("ss5", elements.text() + " " + elements.toString());
                elements = elements.get(0).select("div.tab-con");
                Log.i("ss6", elements.text() + " " + elements.toString());
                elements = elements.get(0).select("div.list-item");
                Log.i("ss7", elements.text() + " " + elements.toString());
                elements = elements.get(0).select("div.right-content");
                Log.i("ss8", elements.text() + " " + elements.toString());
                elements = elements.get(0).select("div.item-top");
                Log.i("ss9", elements.text() + " " + elements.toString());
                elements = elements.get(0).select("h2");
                Log.i("ss10", elements.text() + " " + elements.toString());
                elements = elements.get(0).select("a");
                Log.i("ss11", elements.text() + " " + elements.toString());*/
            }else {
                Elements elements = document.select("div.area");
                elements = elements.select("div.area-left");
                elements = elements.select("div.list-item");
                for (int i = 0; i < elements.size(); i++){
                    newsBeanList.add(new NewsBean(elements.get(i).select("div.item-top").get(0).
                            select("h2").get(0).select("a").text(),
                            elements.get(i).select("div.item-top").get(0).
                            select("h2").get(0).select("a").attr("href"), ""));
                }
                return new CallbackBean("0", "", newsBeanList);
            }
        }catch (Exception e){
            if (e != null){
                Log.i("e", e.getMessage());
            }
            return new CallbackBean("1", e.getMessage(), null);
        }
        return null;
    }

    @Override
    public CallbackBean getNewsDetail(String url) {
        OkhttpUtil okhttpUtil = OkhttpUtil.getInstance();
        final Request request = new Request.Builder().url(url).build();
        try {
            Response response = okhttpUtil.okHttpClient.newCall(request).execute();
            Document document = Jsoup.parse(response.body().string());
            Elements elements = document.select("div.post_content");
            elements = elements.get(0).select("div.post_content_main");
            elements = elements.get(0).select("div.post_body");
            elements = elements.get(0).select("div.post_text");
            String article = "";
            elements = elements.get(0).select("p");
            for (int i = 0; i < elements.size(); i++){
                article += elements.get(i).text() + "\n";
            }
            NewsBean newsBean = new NewsBean("", "", article);
            return new CallbackBean("0", "", newsBean);
        }catch (Exception e){
            if (e != null){
                Log.i("e", e.getMessage());
            }
            return new CallbackBean("1", e.getMessage(), null);
        }
    }
}
