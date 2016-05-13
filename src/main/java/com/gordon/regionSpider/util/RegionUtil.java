package com.gordon.regionSpider.util;

/**
 * Created by Administrator on 2016/5/13.
 */
public class RegionUtil {

    /**
     * 截取地区名称（截掉‘委员会’，‘办事处’字样的地区名）
     * @param regionName
     * @return
     */
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

    /**
     * 根据地区码返回地区级别
     * 分为5个等级（0：最高级别；1：省，直辖市；2：地级市，3：区，县；4：乡镇，街道；5：村）
     * @param code
     * @return
     */
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
