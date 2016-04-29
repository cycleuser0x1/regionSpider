package com.gordon.regionSpider.filter;

import com.gordon.regionSpider.constants.CrawlerParams;
import com.gordon.regionSpider.model.DiscountProduct;

import java.util.logging.Logger;

/**
 * Created by wwz on 2016/2/23.
 */
public class ProductFilter {
    private static final Logger Log = Logger.getLogger(ProductFilter.class.getName());

    public static boolean isMatch(DiscountProduct discountProduct) {
        if (discountProduct.getTitle().toLowerCase().contains(getKeyWord().toLowerCase()))
            return true;
        else
            return false;
    }

    public static String getKeyWord() {
        return CrawlerParams.KEYWORD;
    }
}
