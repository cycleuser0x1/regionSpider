package com.gordon.discountCrawler.parser;

import com.gordon.discountCrawler.model.FetchedPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Created by wwz on 2016/2/25.
 */
@Deprecated
public class UrlParser {
    /**
     * 获取商品的URL
     * @param fetchedPage
     * @return
     */
    public static String parseUrl(FetchedPage fetchedPage) {
        Document doc = Jsoup.parse(fetchedPage.getContent());
        String html = doc.getElementsByTag("script").get(1).html();
        int begin = html.indexOf("\'") + 1;
        int end = html.lastIndexOf("\'");
        return html.substring(begin, end);
    }
}
