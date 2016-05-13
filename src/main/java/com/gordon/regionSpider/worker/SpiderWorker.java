package com.gordon.regionSpider.worker;

import com.gordon.regionSpider.fetcher.PageFetcher;
import com.gordon.regionSpider.model.FetchedPage;
import com.gordon.regionSpider.model.RegionTreeNode;
import com.gordon.regionSpider.parser.ContentParser;
import com.gordon.regionSpider.storage.DataStorage;
import com.gordon.regionSpider.storage.impl.ListStorage;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by wwz on 2016/2/22.
 */
public class SpiderWorker  {
    private static final Logger Log = Logger.getLogger(SpiderWorker.class.getName());

    private PageFetcher pageFetcher = new PageFetcher();

    private ContentParser contentParser = new ContentParser();

    private int threadIndex;

    private Integer page = 1;


    /**
     * 递归访问页面，并构造子节点
     * @param regionNode
     * @param superUrl
     * @return
     */
    public RegionTreeNode loadRegionNode(RegionTreeNode regionNode, String superUrl) {


        String url = subUrl(superUrl) + regionNode.getUrl();

        FetchedPage fetchedPage = pageFetcher.getContentFromUrl(url);

        List<RegionTreeNode> list;
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

            for (RegionTreeNode node : list) {
                //该节点为叶子节点且为最后一级
                if(node.getUrl() == null && !node.getId().substring(6,12).equals("000000")){
                    break;
                }

                loadRegionNode(node, url);
            }
        }
        return regionNode;
    }

    /**
     * 截取上级url
     * @param url
     * @return
     */
    public static String subUrl(String url) {
        int index = url.lastIndexOf("/") + 1;
        String truncateUrl = url.substring(0, index);
        return truncateUrl;
    }


}
