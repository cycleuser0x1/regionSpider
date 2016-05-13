import com.gordon.regionSpider.constants.SpiderParams;
import com.gordon.regionSpider.fetcher.PageFetcher;
import com.gordon.regionSpider.model.FetchedPage;
import com.gordon.regionSpider.model.RegionTreeNode;
import com.gordon.regionSpider.parser.ContentParser;
import com.gordon.regionSpider.util.RegionUtil;
import com.gordon.regionSpider.util.TimeUtil;
import com.gordon.regionSpider.worker.SpiderWorker;
import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by wwz on 2016/2/18.
 */
public class SpiderStart {
    private static final Logger log = Logger.getLogger(SpiderStart.class.getName());

    private static PageFetcher pageFetcher = new PageFetcher();

    private static File file = new File("D:\\region\\region.json");

    private static FileWriter fileWriter;

    private static BufferedWriter bufferedWriter;

    static {
        try {
            fileWriter = new FileWriter(file.getAbsoluteFile());
            bufferedWriter = new BufferedWriter(fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        SpiderWorker spiderWorker = new SpiderWorker();
        RegionTreeNode root = spiderWorker.loadRegionNode(initRootNode(), SpiderParams.PAGE_URL);
        //加载所有TreeNode
        log.info("loadingRegion start :" + TimeUtil.getTimeStamp());
        spiderWorker.loadRegionNode(root,SpiderParams.PAGE_URL);
        log.info("loadingRegion end :" + TimeUtil.getTimeStamp());

        //将RegionTree写入文件
        try {
            RegionTreeRecursion(root, "0");
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化根节点
     * @return
     */
    public static RegionTreeNode initRootNode(){
        FetchedPage fetchedPage = pageFetcher.getContentFromUrl(SpiderParams.PAGE_URL);
        List<RegionTreeNode> list = ContentParser.parseIndexHTML(fetchedPage);
        //构造根节点
        RegionTreeNode root = new RegionTreeNode();
        root.setUrl("");
        root.setRegionName("全国");
        root.setId("0");
        root.setChildNode(list);
        root.setpId("0");
        return root;
    }

    /**
     * 从root开始遍历树，将地区信息写入文件
     * @param root
     * @param pid
     * @throws IOException
     */
    private static void RegionTreeRecursion(RegionTreeNode root, String pid) throws IOException {
        List<RegionTreeNode> regionNodeList = root.getChildNode();

        StringBuffer region = new StringBuffer("{'regionCode':" + "'"+root.getId() +"'"+ ",'regionName':" +
                RegionUtil.truncateRegionName(root.getRegionName()) + ",'pid':" + "'"+pid+"'"
                + ",'lvl':" + "'"+RegionUtil.regionLvl(root.getpId())+"'}");

        //写入一条节点数据
        bufferedWriter.write(region.toString());
        bufferedWriter.newLine();

        //叶子节点
        if (regionNodeList == null || regionNodeList.size() == 0) {
            return;
        }

        //所有子节点
        for (RegionTreeNode childNode : regionNodeList) {
            RegionTreeRecursion(childNode, root.getId());
        }

    }

}
