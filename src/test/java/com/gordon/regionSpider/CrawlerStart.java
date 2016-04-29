package com.gordon.regionSpider;

import com.gordon.regionSpider.constants.CrawlerParams;
import com.gordon.regionSpider.mail.SendMail;
import com.gordon.regionSpider.fetcher.PageFetcher;
import com.gordon.regionSpider.filter.ProductFilter;
import com.gordon.regionSpider.model.DiscountProduct;
import com.gordon.regionSpider.parser.ContentParser;
import com.gordon.regionSpider.queue.FilteredDiscountProductQueue;
import com.gordon.regionSpider.storage.impl.ListStorage;
import com.gordon.regionSpider.worker.CrawlerWorker;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created by wwz on 2016/2/18.
 */
public class CrawlerStart {
    private static final Logger log = Logger.getLogger(CrawlerStart.class.getName());

    public static void main(String[] args) {
        final PageFetcher pageFetcher = new PageFetcher();
        final List<DiscountProduct> list = new ArrayList<DiscountProduct>();
        Timer timer = new Timer();
        CrawlerWorker crawlerWorker = new CrawlerWorker(1);
        crawlerWorker.startCrawl();
        System.out.println("done...");
        for (DiscountProduct discountProduct : ListStorage.getDiscountProductList()) {
            if(ProductFilter.isMatch(discountProduct)){
                FilteredDiscountProductQueue.addElement(discountProduct);
            }
        }
        new Thread(crawlerWorker).start();
        new Thread(new SendMail()).start();
        timer.schedule(new TimerTask() {
            //定时清空保存商品信息的集合,只保留前两页的商品信息
            @Override
            public void run() {
                list.clear();
                for (int i = 1; i < 3; i++) {
                    list.addAll(ContentParser.parseHTML(pageFetcher.getContentFromUrl(CrawlerParams.PAGE_URL + Integer.toString(i))));
                }
                ListStorage.getDiscountProductList().retainAll(list);
            }
        }, CrawlerParams.ONE_DAY, CrawlerParams.ONE_DAY);
    }

}
