package com.gordon.regionSpider.storage.impl;

import com.gordon.regionSpider.model.DiscountProduct;
import com.gordon.regionSpider.storage.DataStorage;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * 将所有折扣商品信息保存在一个静态的集合中
 * 使用单利模式保证获取到的对象只有一个
 * Created by wwz on 2016/2/22.
 */
public class ListStorage implements DataStorage {
    private static final Logger log = Logger.getLogger(ListStorage.class.getName());
    private static final List<DiscountProduct> discountProductList = new ArrayList<DiscountProduct>();
    private static final ListStorage listStorage = new ListStorage();

    private ListStorage() {

    }

    public static ListStorage getInstance() {
        return listStorage;
    }

    /**
     * 将新抓取的折扣商品信息子集合添加到集合
     *
     * @param discountProductList
     */
    public void store(List<DiscountProduct> discountProductList) {
        this.discountProductList.addAll(discountProductList);
    }

    public static Logger getLog() {
        return log;
    }

    public static List<DiscountProduct> getDiscountProductList() {
        return discountProductList;
    }
}
