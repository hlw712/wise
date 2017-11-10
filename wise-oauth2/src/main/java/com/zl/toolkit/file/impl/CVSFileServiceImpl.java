package com.zl.toolkit.file.impl;

import com.zl.toolkit.file.AbstractFileService;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

/**
 * CVS文件 操作工具类
 *
 * @author huanglw
 * @date 2017/8/16
 */
public class CVSFileServiceImpl extends AbstractFileService {

    @Override
    public List<String[]> getData(File file) {

        InputStreamReader isr = null;
        List<String[]> list = null;
        int i = 1;
        try {
            isr = new InputStreamReader(new FileInputStream(file));//待处理数据的文件路径
            BufferedReader reader = new BufferedReader(isr);
            String line = null;
            list = new LinkedList<>();
            while((line=reader.readLine())!=null){
                // 给每行添加0，是防止数据为空的时候，split不会把该字段截取出来
                line = line+",0";
                String item[] = line.split(",");
                list.add(new String[]{item[0], item[1], item[3], item[5], item[7], item[9], item[11], item[13], item[15], item[17],
                        item[19], item[21], item[23] , item[25], item[26], item[27], item[29], item[35], item[36], item[37],
                        item[38], item[39], item[40], item[41] , item[44], item[46], item[48], item[50], item[52], item[54],
                        item[55], item[60], item[64], item[70], item[71], item[77], item[89], item[103], item[127], item[139],
                        item[153], item[156], item[211], item[233], item[251], item[252], item[257], item[265], item[267], item[268],
                        item[269], item[270], item[275]
                });
                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("----------------------------------》错误前报错,第："+i+" 行");
            e.printStackTrace();
        } finally {
            try {
                if (isr != null) {
                    isr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }


}
