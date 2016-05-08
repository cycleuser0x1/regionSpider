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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created by wwz on 2016/2/18.
 */
public class CrawlerStart {
    private static final Logger log = Logger.getLogger(CrawlerStart.class.getName());

    private static File file = new File("D:\\region.txt");

    private static FileWriter fileWriter;

    static {
        try {
            fileWriter = new FileWriter(file.getAbsoluteFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
        regionNode.setpId("0");
        CrawlerWorker crawlerWorker = new CrawlerWorker(1);
        RegionNode regionNode1 = crawlerWorker.loadRegionNode(regionNode,"http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2014/");
        rescursiveRegionTree(regionNode1,"0");
        try {
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSubStr(){
        String code = "210201001000";
        System.out.println(code.substring(6,9));
    }
    private static void rescursiveRegionTree(RegionNode regionNode,String pid){
        List<RegionNode> regionNodeList = regionNode.getChildNode();

        StringBuffer region = new StringBuffer("regionCode:"+regionNode.getId()+";regionName:"+regionNode.getRegionName()+";pid:"+pid);

        try {
            fileWriter(region);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(regionNodeList == null || regionNodeList.size() == 0){
            return;
        }

        for(RegionNode regionNode1:regionNodeList){
            rescursiveRegionTree(regionNode1,regionNode.getId());
        }

    }

    private static void fileWriter(StringBuffer sb) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(sb.toString());
        bufferedWriter.close();
    }

}
