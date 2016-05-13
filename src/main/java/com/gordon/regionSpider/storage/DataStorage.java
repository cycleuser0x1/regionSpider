package com.gordon.regionSpider.storage;

import com.gordon.regionSpider.model.RegionTreeNode;

import java.util.List;

/**
 * Created by wwz on 2016/2/22.
 */
public interface DataStorage {
    void store(List<RegionTreeNode> regionTreeNodeList);
}
