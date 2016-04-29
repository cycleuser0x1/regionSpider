package com.gordon.regionSpider.mail;

import javax.mail.MessagingException;
import java.io.IOException;

/**
 * Created by wwz on 2016/3/3.
 */
public interface MailSent {
    void send(String msg) throws MessagingException, IOException;
}
