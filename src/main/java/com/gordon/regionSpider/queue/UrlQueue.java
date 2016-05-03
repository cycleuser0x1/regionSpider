package com.gordon.regionSpider.queue;

import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Administrator on 2016/5/3.
 */
public class UrlQueue {
    private static final Logger LOGGER = Logger.getLogger(UrlQueue.class.getName());
    private static final Queue<String> urlQueue = new LinkedList<String>();

    private UrlQueue() {
    }

    public synchronized static void addElement(String url) {
        urlQueue.offer(url);
    }

    public synchronized static String pollElement() {
        return urlQueue.poll();
    }

    public synchronized static boolean isEmpty() {
        return urlQueue.isEmpty();
    }

    public synchronized static int size(){
        return urlQueue.size();
    }
}
