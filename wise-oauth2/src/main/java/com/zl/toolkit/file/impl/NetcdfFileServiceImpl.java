package com.zl.toolkit.file.impl;

import com.zl.toolkit.file.AbstractFileService;
import ucar.ma2.Array;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by hlw on 2017/7/31.
 */
public class NetcdfFileServiceImpl extends AbstractFileService {

    /**
     * 读取 单文件数据
     *
     * @param url
     * @return
     */
    public List<String[]> getData(String url, int rows) {

        List<String[]> list = new LinkedList<>();

        NetcdfFile ncfile = null;
        String fileName = null;

        try {
            // 1，读取Nc文件对象
            ncfile = NetcdfFile.open(url);
            // 2，取得Nc文件名称
            String location = ncfile.getLocation();
            if (location != null) {
                String[] files = location.split("_");
                if (files != null && files.length >= 4) {
                    fileName = files[1].substring(2,8) + files[2];
                }
            }

            String[] strArray = null;
            // 3，得到NC文件所有的tab对象
            List<Variable> variableList = ncfile.getVariables();
            if (variableList == null) {
                return null;
            }
            int size = variableList.size();
            for (int i = 0; i < size; i++) {
                Variable variable = variableList.get(i);
                Array array = variable.read();
                for (int j = 0 ; j < array.getSize(); j++) {
                    strArray = new String[size];
                    strArray[i] = String.valueOf(array.getDouble(j));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }  finally {
            try {
                ncfile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return list;
    }
}
