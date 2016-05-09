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

    private static BufferedWriter bufferedWriter;

    static {
        try {
            fileWriter = new FileWriter(file.getAbsoluteFile());
            bufferedWriter = new BufferedWriter(fileWriter);
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
        RegionNode regionNode1 = crawlerWorker.loadRegionNode(regionNode,"http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2014/index.html");
        try {
            traverseRegionTree(regionNode1,"0");
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main1(String[] args) throws IOException {
        String str = "HHHH";
        StringBuffer strBf = new StringBuffer();
        for(char ch : str.toCharArray()){
            bufferedWriter.write(strBf.append(String.valueOf(ch)).toString());
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
    }
    private static void traverseRegionTree(RegionNode regionNode,String pid) throws IOException {
        List<RegionNode> regionNodeList = regionNode.getChildNode();

        StringBuffer region = new StringBuffer("regionCode:"+regionNode.getId()+";regionName:"+regionNode.getRegionName()+";pid:"+pid);

        bufferedWriter.write(region.toString());
        bufferedWriter.newLine();

        if(regionNodeList == null || regionNodeList.size() == 0){
            return;
        }

        for(RegionNode regionNode1:regionNodeList){
            traverseRegionTree(regionNode1,regionNode.getId());
        }

    }


    public static String truncateRegionName(String regionName){
        if(regionName.contains("居委会")){
            return regionName.split("居")[0];
        }
        if(regionName.contains("办事处")){
            return regionName.split("办")[0];
        }
        if(regionName.contains("村委会")){
            return regionName.split("委")[0];
        }
        return regionName;
    }

    public static String regionLvl(String code){

        if(code.equals("0")){
            return "0";
        }

        if(code.length() == 2){
            return "1";
        }

        if(code.substring(4,6).equals("00")){
            return "2";
        }
        if(code.substring(6,9).equals("000")){
            return "3";
        }
        if(code.substring(9,12).equals("000")){
            return "4";
        }
        return "5";
    }

}
