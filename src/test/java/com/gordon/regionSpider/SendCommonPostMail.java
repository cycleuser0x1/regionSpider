package com.gordon.regionSpider;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SendCommonPostMail {

    public static void main(String[] args) throws IOException {
        final String url = "http://sendcloud.sohu.com/webapi/mail.send.json";
        final String apiUser = "";
        final String apiKey = "";

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httpost = new HttpPost(url);

        List params = new ArrayList();
        // 不同于登录SendCloud站点的帐号，您需要登录后台创建发信子帐号，使用子帐号和密码才可以进行邮件的发送。
        params.add(new BasicNameValuePair("api_user", apiUser));
        params.add(new BasicNameValuePair("api_key", apiKey));
        params.add(new BasicNameValuePair("from", "service@sendcloud.im"));
        params.add(new BasicNameValuePair("fromname", ""));
        params.add(new BasicNameValuePair("to", "493944741@qq.com"));
        params.add(new BasicNameValuePair("subject", "来自SendCloud的第一封邮件！"));
        params.add(new BasicNameValuePair("html", "aaa"));
        params.add(new BasicNameValuePair("resp_email_id", "true"));

        httpost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        // 请求
        HttpResponse response = httpclient.execute(httpost);
        // 处理响应
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) { // 正常返回
            // 读取xml文档
            String result = EntityUtils.toString(response.getEntity());
            System.out.println(result);
        } else {
            System.err.println("error");
        }
        httpclient.getConnectionManager().shutdown();
    }
}