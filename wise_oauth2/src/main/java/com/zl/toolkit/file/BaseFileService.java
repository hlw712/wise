package com.zl.toolkit.file;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by hlw on 2017/8/3.
 */
public interface BaseFileService {

    /**
     * 从文件中获取数据
     *
     * @param file 被读取的文件
     * @return
     */
    <T>List<T> getData(File file);


    /**
     * 从文件中获取数据
     *
     * @param filePath 被读取的路径
     * @return
     */
    <T>List<T> getData(String filePath) throws IOException;
}
