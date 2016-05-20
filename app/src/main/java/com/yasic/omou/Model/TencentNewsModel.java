package com.yasic.omou.Model;

import android.util.Log;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.yasic.omou.Javabean.CallbackBean;
import com.yasic.omou.Javabean.NewsBean;
import com.yasic.omou.Util.OkhttpUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yasic on 2016/5/20.
 */
public class TencentNewsModel implements ITopicModel {
    @Override
    public CallbackBean getNewsList(String url) {
        OkhttpUtil okhttpUtil = OkhttpUtil.getInstance();
        final Request request = new Request.Builder().url(url).build();
        try {
            List<NewsBean> newsBeanList = new ArrayList<>();
            Response response = okhttpUtil.okHttpClient.newCall(request).execute();
            Document document = Jsoup.parse(response.body().string());
            //if (url.equals("http://mil.qq.com/mil_index.htm")){
                //Elements elements = document.select("div.layout");
            //}
            //else {
                Elements elements = document.select("div.layout");
                elements = elements.get(0).select("div.chief");
                Element element = elements.get(0).getElementById("news");
                elements = element.select("div.list");
                elements = elements.get(0).select("div.Q-tplist");
                for (int i = 0; i < elements.size(); i++){
                    newsBeanList.add(new NewsBean(elements.get(i).getElementsByClass("Q-tpWrap").get(0)
                            .select("div.text").get(0).select("em.f14").get(0)
                            .select("a.linkto").text(),
                            elements.get(i).getElementsByClass("Q-tpWrap").get(0)
                                    .select("div.text").get(0).select("em.f14").get(0)
                                    .select("a.linkto").attr("href"),
                            ""));
                }
                return new CallbackBean("0", "", newsBeanList);
                //}
        }catch (Exception e){
            if (e != null){
                Log.i("e", e.getMessage());
            }
            return new CallbackBean("1", e.getMessage(), null);
        }
    }

    @Override
    public CallbackBean getNewsDetail(String url) {
        OkhttpUtil okhttpUtil = OkhttpUtil.getInstance();
        final Request request = new Request.Builder().url(url).build();
        try {
            Response response = okhttpUtil.okHttpClient.newCall(request).execute();
            Document document = Jsoup.parse(response.body().string());
            Elements elements = document.select("div.body-Article-QQ");
            elements = elements.select("div.wrapper");
            elements = elements.select("div.bd");
            elements = elements.select("div.main");
            Element element = elements.get(0).getElementById("C-Main-Article-QQ");
            elements = element.select("div.bd");
            element = elements.get(0).getElementById("Cnt-Main-Article-QQ");
            String article = "";
            elements = element.select("p");
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
