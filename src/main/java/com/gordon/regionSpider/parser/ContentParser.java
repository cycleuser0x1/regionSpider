package com.gordon.regionSpider.parser;

import com.gordon.regionSpider.constants.CrawlerParams;
import com.gordon.regionSpider.model.DiscountProduct;
import com.gordon.regionSpider.model.FetchedPage;
import com.gordon.regionSpider.util.TimeUtil;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wwz on 2016/2/18.
 */
public class ContentParser {
    private static final Logger log = Logger.getLogger(ContentParser.class.getName());

    /**
     * 初始化运行爬虫时抓取全部折扣商品信息
     *
     * @param fetchedPage
     * @return
     */
    public static List<DiscountProduct> parseHTML(FetchedPage fetchedPage) {
        if (fetchedPage == null) {
            return null;
        }
        Document doc = Jsoup.parse(fetchedPage.getContent());
        //获取折扣商品元素集合
        Elements productElements = doc.getElementsByClass("normal");
        List<DiscountProduct> discountProductList = new ArrayList<DiscountProduct>();
        //将页面元素转化为对象保存到集合中
        for (Element element : productElements) {
            String id = element.attr("id");
            Date releasedTime = TimeUtil.parseTime(element.select(".t").text());
            String title = element.select(".i").text();
            Double discountedPrice = Double.parseDouble(element.select(".p").text());
            Double price = Double.parseDouble(element.select(".h").text());
            String url = CrawlerParams.PRODUCT_URL + id.substring(2);
            discountProductList.add(new DiscountProduct(id, releasedTime, title, discountedPrice, price, url));
        }
        return discountProductList;
    }

}
