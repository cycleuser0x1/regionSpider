package com.gordon.regionSpider.parser;

import com.gordon.regionSpider.model.FetchedPage;
import com.gordon.regionSpider.model.RegionTreeNode;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wwz on 2016/2/18.
 */
public class ContentParser {
    private static final Logger log = Logger.getLogger(ContentParser.class.getName());

    /**
     * 解析地区页面，返回当前页面的地区节点集合
     * @param fetchedPage
     * @return
     */
    public static List<RegionTreeNode> parseHTML(FetchedPage fetchedPage) {
        //当抓取的页面为空或者返回状态码不是200返回空
        if (fetchedPage == null || fetchedPage.getStatusCode() != 200) {
            return null;
        }
        List<RegionTreeNode> regionNodeList = new ArrayList<RegionTreeNode>();
        Document doc = Jsoup.parse(fetchedPage.getContent());
        //获取当前页面中的地区节点
        Elements tableSet = doc.getElementsByTag("TABLE");
        Element regionTable = tableSet.get(4);
        Elements trSet = regionTable.getElementsByTag("tr");
        for (int i = 1; i < trSet.size(); i++) {
            Element tr = trSet.get(i);
            Elements elements = tr.getAllElements();

            //当子元素数量等于3时，说明为没有子节点的市辖区
            if (elements.size() == 3) {
                Element codeTdTag = elements.get(1);
                Element nameTdTag = elements.get(2);
                String name = nameTdTag.text();
                String code = codeTdTag.text();
                RegionTreeNode regionNode = new RegionTreeNode();
                regionNode.setRegionName(name);
                regionNode.setId(code);
                regionNodeList.add(regionNode);
                System.out.println("code:"+code+";name:"+name);
                continue;
            }

            //当子元素数量等于4时，说明为叶子节点
            if (elements.size() == 4) {
                Element codeTdTag = elements.get(1);
                Element nameTdTag = elements.get(3);
                String name = nameTdTag.text();
                String code = codeTdTag.text();
                RegionTreeNode regionNode = new RegionTreeNode();
                regionNode.setRegionName(name);
                regionNode.setId(code);
                regionNodeList.add(regionNode);
                System.out.println("code:"+code+";name:"+name);
                continue;
            }
            Element codeATag = elements.get(2);
            Element nameATag = elements.get(4);
            String href = codeATag.attr("href");
            String code = codeATag.text();
            String name = nameATag.text();
            RegionTreeNode regionNode = new RegionTreeNode();
            regionNode.setId(code);
            regionNode.setRegionName(name);
            regionNode.setUrl(href);
            regionNodeList.add(regionNode);
            System.out.println("code:"+code+";name:"+name+";href:"+href);
        }
        return regionNodeList;
    }

    /**
     * 解析首页数据，返回首页地区节点集合
     * @param fetchedPage
     * @return
     */
    public static List<RegionTreeNode> parseIndexHTML(FetchedPage fetchedPage) {
        //当抓取的页面为空或者返回状态码不是200返回空
        if (fetchedPage == null || fetchedPage.getStatusCode() != 200) {
            return null;
        }
        List<RegionTreeNode> regionNodeList = new ArrayList<RegionTreeNode>();
        Document doc = Jsoup.parse(fetchedPage.getContent());
        //获取首页中的地区节点
        Elements tableSet = doc.getElementsByTag("TABLE");
        Element regionTable = tableSet.get(4);
        Elements trSet = regionTable.getElementsByTag("tr");
        //table
        for (int i = 3; i < trSet.size(); i++) {
            Element tr = trSet.get(i);
            Elements tdSet = tr.getElementsByTag("td");
            //tr
            for (Element td : tdSet) {
                Element aTag = td.getElementsByTag("a").get(0);
                String href = aTag.attr("href");
                int end = href.indexOf(".");
                String code = href.substring(0, end);
                String name = aTag.text();
                System.out.println("code:"+code+";name:"+name+";href:"+href);
                RegionTreeNode regionNode = new RegionTreeNode();
                regionNode.setId(code);
                regionNode.setRegionName(name);
                regionNode.setUrl(href);
                regionNodeList.add(regionNode);
            }
        }
        return regionNodeList;
    }

}
