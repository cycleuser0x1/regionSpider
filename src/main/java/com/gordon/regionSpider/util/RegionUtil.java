package com.gordon.regionSpider.util;

/**
 * Created by Administrator on 2016/5/13.
 */
public class RegionUtil {

    public static String truncateRegionName(String regionName) {
        if (regionName.contains("居民委员会")) {
            return regionName.split("居")[0];
        }
        if (regionName.contains("居委会")) {
            return regionName.split("居")[0];
        }
        if (regionName.contains("办事处")) {
            return regionName.split("办")[0];
        }
        if (regionName.contains("村民委员会")) {
            return regionName.split("民")[0];
        }
        if (regionName.contains("村委会")) {
            return regionName.split("委")[0];
        }
        return regionName;
    }

    public static String regionLvl(String code) {

        if (code.equals("0")) {
            return "0";
        }

        if (code.length() == 2) {
            return "1";
        }

        if (code.substring(4, 6).equals("00")) {
            return "2";
        }
        if (code.substring(6, 9).equals("000")) {
            return "3";
        }
        if (code.substring(9, 12).equals("000")) {
            return "4";
        }
        return "5";
    }
}
