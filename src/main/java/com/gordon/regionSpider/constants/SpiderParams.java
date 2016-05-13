package com.gordon.regionSpider.constants;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by wwz on 2016/2/18.
 */
public class SpiderParams {
    public static String KEYWORD = null;
    public static String MAIl_ADDRESS = null;
    public static String OUTBOX = null;
    public static String PASSWORD = null;
    public static String SMTP_ADDRESS = null;
    public static final int CRAWLER_NUM = 1;
    public static final int INIT_REQUEST_DELAY_TIME = 500;
    public static final int REQUEST_DELAY_TIME = 1000 * 30;
    public static String PAGE_URL;

    static {
        Properties prop = new Properties();
        try {
            prop.load(new InputStreamReader(SpiderParams.class.getClassLoader().getResourceAsStream("config/spider.properties")
                    , "UTF-8"));
            PAGE_URL = prop.getProperty("spider.pageUrl");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
