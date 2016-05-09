import com.gordon.regionSpider.constants.CrawlerParams;
import com.gordon.regionSpider.fetcher.PageFetcher;
import com.gordon.regionSpider.filter.ProductFilter;
import com.gordon.regionSpider.mail.SendMail;
import com.gordon.regionSpider.model.DiscountProduct;
import com.gordon.regionSpider.parser.ContentParser;
import com.gordon.regionSpider.queue.FilteredDiscountProductQueue;
import com.gordon.regionSpider.storage.impl.ListStorage;
import com.gordon.regionSpider.worker.CrawlerWorker;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by wwz on 2016/2/18.
 */
public class CrawlerStart {
    private static final Logger log = Logger.getLogger(CrawlerStart.class.getName());

    public static void main(String[] args) {
        //370501000000
        System.out.println("subUrl:"+"370101000000".substring(6,12));
    }

}
