package com.zl.toolkit.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 集合对象转换工具类
 *
 * @author huanglw
 * @date 2017/8/16
 */
public class CollectionConvertUtils {

    /**
     * 把Map（java.util.Map）对象转换为实体对象
     *
     * @param map 需要转换的map
     * @param clazz 目标javaBean的类对象
     * @return 目标类object
     */
    public static <T> T mapToBean(Map<String, Object> map, Class<T> clazz) {
        try {
            T t = clazz.newInstance();
            Field[] fields = clazz.getDeclaredFields();
            for(Field field : fields){
                if(null != map.get(field.getName())){
                    field.setAccessible(true);
                    field.set(t, map.get(field.getName()));
                }
            }
            return t;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 把实体对象 转换为 Map（java.util.Map）对象
     *
     * @param obj
     * @return
     */
    public static Map<String, Object> beanToMap(Object obj) {
        if(obj == null){
            return null;
        }

        Map<String, Object> map = new HashMap<String, Object>();

        Field[] declaredFields = obj.getClass().getDeclaredFields();
        try {
            for (Field field : declaredFields) {
                field.setAccessible(true);
                map.put(field.getName(), field.get(obj));
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return map;
    }

    /**
     * 将对象直接转换成String类型的 XML输出
     *
     * @param obj 要转换的对象数据
     * @return
     */
    public static String objectToXML(Object obj) {
        // 创建输出流
        StringWriter sw = new StringWriter();
        try {
            // 利用jdk中自带的转换类实现
            JAXBContext context = JAXBContext.newInstance(obj.getClass());

            Marshaller marshaller = context.createMarshaller();
            // 格式化xml输出的格式
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,  Boolean.TRUE);
            // 将对象转换成输出流形式的xml
            marshaller.marshal(obj, sw);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return sw.toString();
    }

    /**
     * 将String类型的xml 转换成对象
     *
     * @param clazz 要转换的类
     * @param xmlStr xml字符串
     * @return
     */
    public static Object xmlToObject(Class clazz, String xmlStr) {
        Object xmlObject = null;
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            // 进行将Xml转成对象的核心接口
            Unmarshaller unmarshaller = context.createUnmarshaller();
            StringReader sr = new StringReader(xmlStr);
            xmlObject = unmarshaller.unmarshal(sr);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return xmlObject;
    }

}
