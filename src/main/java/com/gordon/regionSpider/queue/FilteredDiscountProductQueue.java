package com.gordon.regionSpider.queue;

import com.gordon.regionSpider.model.DiscountProduct;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by wwz on 2016/2/25.
 */
public class FilteredDiscountProductQueue {
    private static final Logger log = Logger.getLogger(FilteredDiscountProductQueue.class.getName());
    //使用生产者-消费者模型，使用队列
    private static final Queue<DiscountProduct> productQueue = new LinkedList<DiscountProduct>();

    private FilteredDiscountProductQueue() {
    }

    public synchronized static void addElement(DiscountProduct discountProduct) {
        productQueue.offer(discountProduct);
    }

    public synchronized static DiscountProduct pollElement() {
        return productQueue.poll();
    }

    public synchronized static boolean isEmpty() {
        return productQueue.isEmpty();
    }

    public synchronized static int size(){
        return productQueue.size();
    }

}
