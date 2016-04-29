package com.gordon.regionSpider.constants;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by wwz on 2016/2/18.
 */
public class CrawlerParams {
    public static String KEYWORD = null;
    public static String MAIl_ADDRESS = null;
    public static String OUTBOX = null;
    public static String PASSWORD = null;
    public static String SMTP_ADDRESS = null;
    public static final int CRAWLER_NUM = 1;
    public static final int INIT_REQUEST_DELAY_TIME = 500;
    public static final int REQUEST_DELAY_TIME = 1000 * 30;
    public static final int ONE_DAY = 1000 * 60 * 60 * 24;
    public static final String PAGE_URL = "http://www.joyj.com/real_all/p-";
    public static final String PRODUCT_URL = "http://www.joyj.com/real_go/";
    public static final int EMAIL_DELAY = 30000;

    static {
        Properties prop = new Properties();
        try {
            prop.load(new InputStreamReader(CrawlerParams.class.getClassLoader().getResourceAsStream("config/crawler.properties")
                    , "UTF-8"));
            KEYWORD = prop.getProperty("crawler.keyword");
            MAIl_ADDRESS = prop.getProperty("crawler.mailAddress");
            OUTBOX = prop.getProperty("crawler.outbox");
            PASSWORD = prop.getProperty("crawler.password");
            SMTP_ADDRESS = prop.getProperty("mail.smtp.host");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
