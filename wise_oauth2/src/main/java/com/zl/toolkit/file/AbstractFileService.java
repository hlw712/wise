package com.zl.toolkit.file;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by hlw on 2017/8/3.
 */
public abstract class AbstractFileService implements BaseFileService {

    /**
     * 读取目录下所有的文件
     *
     * @param path 文件所在路径
     * @return
     */
    public static List<File> readFiles(String path) {

        List<File> list = new LinkedList<File>();
        File file = new File(path);
        if (!file.isDirectory()) {
            list.add(file);
            System.out.println("文件");
            System.out.println("path=" + file.getPath());
        } else if (file.isDirectory()) {
            System.out.println("文件夹");
            String[] fileList = file.list();
            for (int i = 0; i < fileList.length; i++) {
                File readfile = new File(path + "\\" + fileList[i]);
                if (!readfile.isDirectory()) {
                    System.out.println("path=" + readfile.getPath());
                    list.add(readfile);
                } else if (readfile.isDirectory()) {
                    readFiles(path + "\\" + fileList[i]);
                }
            }
        }
        return list;
    }

    @Override
    public <T>List<T> getData(File file) {
        return null;
    }

    @Override
    public <T>List<T> getData(String filePath) {
        return null;
    }
}
