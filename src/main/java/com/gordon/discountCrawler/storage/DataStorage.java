package com.gordon.discountCrawler.storage;

import com.gordon.discountCrawler.model.DiscountProduct;

import java.util.List;

/**
 * Created by wwz on 2016/2/22.
 */
public interface DataStorage {
    void store(List<DiscountProduct> discountProductList);
}
