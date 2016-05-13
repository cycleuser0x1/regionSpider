package com.gordon.regionSpider.model;

import java.util.List;

/**
 * Created by Administrator on 2016/5/3.
 */
public class RegionTreeNode {
    private String url;
    private String regionName;
    private String id;
    private List<RegionTreeNode> childNode;
    private String pId;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<RegionTreeNode> getChildNode() {
        return childNode;
    }

    public void setChildNode(List<RegionTreeNode> childNode) {
        this.childNode = childNode;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }
}
