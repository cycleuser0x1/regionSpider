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


    public void startCrawl1(RegionNode regionNode) {
        int size = regionNode.getChildNode().size();
        if (size > 0) {
            for (RegionNode node : regionNode.getChildNode()) {
                startCrawl1(node);
            }
        }
    }

    /**
     * @param regionNode
     */
    public void loadRegionNode(RegionNode regionNode, String superUrl) {
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

            regionNode.setChildNode(list);

            for (RegionNode node : list) {

                loadRegionNode(node, url);
            }
        }
    }

    public static String subUrl(String url) {
        int index = url.lastIndexOf("/") + 1;
        String truncateUrl = url.substring(0, index);
        return truncateUrl;
    }

    public void run() {

    }
}
