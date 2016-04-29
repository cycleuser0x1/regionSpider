package com.gordon.discountCrawler.mail.impl;

import com.gordon.discountCrawler.constants.CrawlerParams;
import com.gordon.discountCrawler.mail.MailSent;
import com.gordon.discountCrawler.util.TimeUtil;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wwz on 2016/3/3.
 */
public class SendCloudMailSent implements MailSent {
    private static final Logger log = Logger.getLogger(SmtpMailSent.class.getName());

    /**
     * 通过SendCloud代发邮件
     *
     * @param msg
     * @throws IOException
     */
    public void send(String msg) throws IOException {
        final String url = "http://sendcloud.sohu.com/webapi/mail.send.json";
        final String apiUser = "";
        final String apiKey = "";

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httpost = new HttpPost(url);

        List params = new ArrayList();
        // 不同于登录SendCloud站点的帐号，您需要登录后台创建发信子帐号，使用子帐号和密码才可以进行邮件的发送。
        params.add(new BasicNameValuePair("api_user", apiUser));
        params.add(new BasicNameValuePair("api_key", apiKey));
        params.add(new BasicNameValuePair("from", "wwz@mailgen.im"));
        params.add(new BasicNameValuePair("fromname", "wwz"));
        params.add(new BasicNameValuePair("to", CrawlerParams.MAIl_ADDRESS));
        params.add(new BasicNameValuePair("subject", TimeUtil.getTimeStamp() + "更新的折扣商品信息"));
        params.add(new BasicNameValuePair("html", msg));
        params.add(new BasicNameValuePair("resp_email_id", "true"));

        httpost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        // 请求
        HttpResponse response = httpclient.execute(httpost);
        // 处理响应
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) { // 正常返回
            // 读取xml文档
            String result = EntityUtils.toString(response.getEntity());
            log.info(result);
        } else {
            log.error("SendCloud mailSent error");
        }
        httpclient.getConnectionManager().shutdown();
    }
}
