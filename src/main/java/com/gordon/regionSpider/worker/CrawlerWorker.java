package com.gordon.regionSpider.worker;

import com.gordon.regionSpider.constants.CrawlerParams;
import com.gordon.regionSpider.fetcher.PageFetcher;
import com.gordon.regionSpider.filter.ProductFilter;
import com.gordon.regionSpider.model.DiscountProduct;
import com.gordon.regionSpider.model.FetchedPage;
import com.gordon.regionSpider.model.RegionNode;
import com.gordon.regionSpider.parser.ContentParser;
import com.gordon.regionSpider.queue.FilteredDiscountProductQueue;
import com.gordon.regionSpider.storage.DataStorage;
import com.gordon.regionSpider.storage.impl.ListStorage;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by wwz on 2016/2/22.
 */
public class CrawlerWorker implements Runnable {
    private static final Logger Log = Logger.getLogger(CrawlerWorker.class.getName());

    private PageFetcher pageFetcher = new PageFetcher();

    private ContentParser contentParser = new ContentParser();

    private DataStorage dataStorage = ListStorage.getInstance();

    private int threadIndex;

    private Integer page = 1;


    public CrawlerWorker(int i) {
        this.threadIndex = i;
    }


    /**
     * @param regionNode
     */
    public RegionNode loadRegionNode(RegionNode regionNode, String superUrl) {


        String url = subUrl(superUrl) + regionNode.getUrl();

        FetchedPage fetchedPage = pageFetcher.getContentFromUrl(url);

        List<RegionNode> list;
        //首页数据已经预先加载
        if (regionNode.getChildNode() == null) {

            list = contentParser.parseHTML(fetchedPage);

        } else {
            list = regionNode.getChildNode();
        }

        //当list为null时则可能遍历到叶子节点
        if (list != null && list.size() > 0) {

            //将子节点插入
            regionNode.setChildNode(list);

            for (RegionNode node : list) {
                //该节点为叶子节点且为最后一级
                if(node.getUrl() == null && !node.getId().substring(6,12).equals("000000")){
                    break;
                }

                loadRegionNode(node, url);
            }
        }
        return regionNode;
    }

    public static String subUrl(String url) {
        int index = url.lastIndexOf("/") + 1;
        String truncateUrl = url.substring(0, index);
        return truncateUrl;
    }


    public void run() {

    }
}
