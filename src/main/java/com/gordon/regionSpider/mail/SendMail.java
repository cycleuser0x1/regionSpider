package com.gordon.regionSpider.mail;

import com.gordon.regionSpider.constants.CrawlerParams;
import com.gordon.regionSpider.mail.impl.SmtpMailSent;
import com.gordon.regionSpider.model.DiscountProduct;
import com.gordon.regionSpider.queue.FilteredDiscountProductQueue;
import org.apache.log4j.Logger;

/**
 * Created by wwz on 2016/2/25.
 */
public class SendMail implements Runnable {
    private static final Logger log = Logger.getLogger(SendMail.class.getName());

    private static final MailSent mailSent = new SmtpMailSent();


    public void run() {
        boolean flag = true;
        StringBuffer sb = new StringBuffer();
        while (true) {
            DiscountProduct discountProduct = FilteredDiscountProductQueue.pollElement();
            if (discountProduct != null) {
                flag = true;
                sb.append(discountProduct.getTitle() +
                        "&nbsp现价：<span style=\"color:red\">" + discountProduct.getDiscountedPrice() + "</span>" +
                        "&nbsp原价：<span style=\"color:blue\">" + discountProduct.getPrice() + "</span>" +
                        "&nbsp链接地址：<a href=\"" + discountProduct.getUrl() + "\">" + discountProduct.getUrl() + "</a><br/>");
            } else {
                try {
                    if (flag && sb.length() > 0) {
                        try {
                            //TODO 发送邮件
                            mailSent.send(sb.toString());
                        } catch (Exception e) {
                            log.error("邮件发送失败:\n" + sb.toString() + "\n" + e.toString());
                        }
                        sb.setLength(0);
                        System.out.println("wait for detecting...");
                    }
                    flag = false;
                    //等待生产者进程将商品信息加入队列
                    Thread.sleep(CrawlerParams.EMAIL_DELAY);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

}
