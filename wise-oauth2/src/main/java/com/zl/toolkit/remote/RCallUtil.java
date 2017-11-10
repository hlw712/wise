package com.zl.toolkit.remote;

import org.rosuda.JRI.REXP;
import org.rosuda.JRI.Rengine;

/**
 * 通过JRI方式调用R程序
 *
 * @author huanglw
 * @date 2017/8/9
 */
public class RCallUtil {

    /**
     * 初始化 R解析类
     *
     * @param filePath
     * @return
     */
    public static Rengine initRengine(String filePath) {

        // 初始化R解析类
        Rengine engine = new Rengine(null, false, null);
        System.out.print("初始化R解析类，R文件加载中......");

        // 等待解析类初始化完毕
        if (!engine.waitForR()) {
            System.out.print("Rengine created, waiting for R");
            return null;
        }

        // 将文件全路径复制给R中的一个变量
        engine.assign("fileName", filePath);
        // 在R中执行文件。执行后，文件中的两个函数加载到R环境中，后续可以直接调用
        engine.eval("source(fileName)");
        System.out.print("R文件已加载完成！");

        return engine;
    }

    /**
     * 调用R函数，返回结果对象
     *
     * @param engine R文件名
     * @param rFunName R函数名
     * @return
     */
    public static REXP getREXP(Rengine engine, String rFunName){

        try {
            // 直接调用无参的函数，将结果保存到一个对象中
            System.out.print("调用R函数，将结果返回保存到REXP对象中");
            return engine.eval(rFunName);
        } finally {
            if (engine != null) {
                engine.end();
            }
        }
    }
}
