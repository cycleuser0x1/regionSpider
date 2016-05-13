package com.gordon.regionSpider;

import com.gordon.regionSpider.constants.SpiderParams;
import com.gordon.regionSpider.fetcher.PageFetcher;
import com.gordon.regionSpider.model.FetchedPage;
import com.gordon.regionSpider.model.RegionTreeNode;
import com.gordon.regionSpider.parser.ContentParser;
import com.gordon.regionSpider.util.RegionUtil;
import com.gordon.regionSpider.worker.SpiderWorker;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wwz on 2016/2/18.
 */
public class ConvertFileType {
    private static final Logger log = Logger.getLogger(ConvertFileType.class.getName());


    private static File file = new File("D:\\region.txt");

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


    /**
     * 读取json文件转换为sql文件
     * @throws IOException
     */
    @Test
    public void readRegion() throws IOException {
        File file = new File("D:\\region\\region.json");
        File sqlFile = new File("D:\\region\\baseRegion.sql");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        FileWriter fileWriter = new FileWriter(sqlFile.getAbsoluteFile());
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        while (true) {
            String regionLine = reader.readLine();
            if (regionLine == null || regionLine.equals("")) {
                break;
            }
            String regionCode = regionLine.split(";")[0].split(":")[1];
            String regionName = regionLine.split(";")[1].split(":")[1];
            String regionPid = regionLine.split(";")[2].split(":")[1];
            String date = simpleDateFormat.format(new Date());
            bufferedWriter.write("INSERT INTO `base_region` VALUES ('"+regionCode+"', '"+regionPid+"', '"
                    + RegionUtil.truncateRegionName(regionName)+"', '"+RegionUtil.regionLvl(regionCode)+"', '"+date+"');");
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }
        bufferedWriter.close();
        fileWriter.close();
    }
}
