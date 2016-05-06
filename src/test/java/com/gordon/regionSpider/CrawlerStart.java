package com.gordon.regionSpider;

import com.gordon.regionSpider.constants.CrawlerParams;
import com.gordon.regionSpider.mail.SendMail;
import com.gordon.regionSpider.fetcher.PageFetcher;
import com.gordon.regionSpider.filter.ProductFilter;
import com.gordon.regionSpider.model.DiscountProduct;
import com.gordon.regionSpider.model.FetchedPage;
import com.gordon.regionSpider.model.RegionNode;
import com.gordon.regionSpider.parser.ContentParser;
import com.gordon.regionSpider.queue.FilteredDiscountProductQueue;
import com.gordon.regionSpider.storage.impl.ListStorage;
import com.gordon.regionSpider.worker.CrawlerWorker;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.*;

/**
 * Created by wwz on 2016/2/18.
 */
public class CrawlerStart {
    private static final Logger log = Logger.getLogger(CrawlerStart.class.getName());

    public static void main(String[] args) {
        PageFetcher pageFetcher = new PageFetcher();
        FetchedPage fetchedPage = pageFetcher.getContentFromUrl("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2014/index.html");
        List<RegionNode> list = ContentParser.parseIndexHTML(fetchedPage);
        //构造根节点
        RegionNode regionNode = new RegionNode();
        regionNode.setUrl("");
        regionNode.setRegionName("全国");
        regionNode.setId("0");
        regionNode.setChildNode(list);
        CrawlerWorker crawlerWorker = new CrawlerWorker(1);
        crawlerWorker.loadRegionNode(regionNode,"http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2014/");
    }

    @Test
    public void testSubStr(){
        String code = "210201001000";
        System.out.println(code.substring(6,9));
    }

}
