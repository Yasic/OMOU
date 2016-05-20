package com.yasic.omou.Model;

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
 * Created by Yasic on 2016/5/19.
 */
public class SinaNewsModel implements ITopicModel {
    @Override
    public CallbackBean getNewsList(String url) {
        OkhttpUtil okhttpUtil = OkhttpUtil.getInstance();
        final Request request = new Request.Builder().url(url).build();
        try {
            List<NewsBean> newsBeanList = new ArrayList<>();
            Response response = okhttpUtil.okHttpClient.newCall(request).execute();
            Document document = Jsoup.parse(response.body().string());
            Elements elements = document.getElementsByClass("content");
            elements = elements.get(0).select("div.wrap");
            elements = elements.get(0).select("div.left");
            elements = elements.get(0).select("div.blk1");
            elements = elements.get(0).select("div.blk12");
            elements = elements.get(0).select("div.blk122");
            elements = elements.select("a");
            for (int i = 0; i < elements.size(); i++){
                newsBeanList.add(new NewsBean(elements.get(i).text(), elements.get(i).attr("href"), ""));
            }
            CallbackBean<List<NewsBean>> callbackBean = new CallbackBean<>("0", "", newsBeanList);
            return callbackBean;
        }catch (Exception e){
            CallbackBean<List<NewsBean>> callbackBean = new CallbackBean<>("1", e.getMessage(), null);
            return callbackBean;
        }
    }

    @Override
    public CallbackBean getNewsDetail(String url) {
        OkhttpUtil okhttpUtil = OkhttpUtil.getInstance();
        final Request request = new Request.Builder().url(url).build();
        try {
            Response response = okhttpUtil.okHttpClient.newCall(request).execute();
            Document document = Jsoup.parse(response.body().string());
            Elements elements = document.select("div.wrap-outer");
            elements = elements.get(0).select("div.wrap-inner");
            elements = elements.get(0).select("div.page-content");
            elements = elements.get(0).select("div.left");
            elements = elements.get(0).select("div.article");
            elements = elements.get(0).select("p");
            String article = "";
            for (int i = 0; i < elements.size(); i++){
                article += elements.get(i).select("p").text() + "\n";
            }
            //Log.i("article", elements.text());
            NewsBean newsBean = new NewsBean("", "", article);
            return new CallbackBean("0", "", newsBean);
        }catch (Exception e){

        }
        return null;
    }
}
