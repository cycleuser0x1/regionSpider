package com.gordon.discountCrawler.fetcher;

import com.gordon.discountCrawler.constants.ContentType;
import com.gordon.discountCrawler.model.FetchedPage;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Created by wwz on 2016/2/18.
 */
public class PageFetcher {

    private static final Logger log = Logger.getLogger(PageFetcher.class.getName());

    private HttpClient client;

    /**
     * 创建HttpClient实例，并初始化链接参数
     */
    public PageFetcher() {
        //设置超时时间
        HttpParams params = new BasicHttpParams();
//        HttpConnectionParams.setConnectionTimeout(params, 10 * 1000);
//        HttpConnectionParams.setSoTimeout(params, 10 * 1000);
        client = new DefaultHttpClient(params);
    }

    /**
     * 关闭HttpClient连接
     */
    public synchronized void close() {
        client.getConnectionManager().shutdown();
    }

    /**
     * 根据url获取页面内容
     *
     * @param url
     * @return
     */
    public FetchedPage getContentFromUrl(String url) {
        String content = null;
        String type = null;
        FetchedPage fetchedPage = null;
        int statusCode = 500;

        //创建get请求，并设置get请求的header
        HttpGet get = new HttpGet(url);
        get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:16.0) Gecko/20100101 Firefox/16.0");
        log.info("request url :" + url);
        HttpResponse response;
        //为了防止请求服务器出现异常，此处要捕获所有异常
        try {
            response = client.execute(get);
            //获得响应状态码
            statusCode = response.getStatusLine().getStatusCode();
            //获得响应体
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                //将响应体转化为文本，并设置字符集
                content = EntityUtils.toString(entity, "UTF-8");
            }
            //获得报文头，分析报文类型
            Header[] headers = response.getHeaders("Content-Type");
            String contentType = headers[0].getValue();
            int start = contentType.lastIndexOf("/") + 1;
            int end = contentType.lastIndexOf(";");
            type = contentType.substring(start, end);
            if (type.equalsIgnoreCase("html") && type != null) {
                fetchedPage = new FetchedPage(url, content, statusCode, ContentType.FETCHEDPAGETYPE_HTML);
            } else if (type.equalsIgnoreCase("json") && type != null) {
                fetchedPage = new FetchedPage(url, content, statusCode, ContentType.FETCHEDPAGETYPE_JSON);
            }
        } catch (Exception e) {
            log.error(e);
        }
        return fetchedPage;
    }
}
