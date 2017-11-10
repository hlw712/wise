package com.zl.toolkit.util;

import com.deyuanyun.pic.common.util.converter.BooleanConverter;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by xu on 2015/11/25.
 */
public class ConvertUtil {
    static Logger logger=Logger.getLogger(ConvertUtil.class);

    /**
     * 将get过来的中文乱码转为utf8
     * @param str
     * @return
     */
    public static String encodeStr(String str){
        try {
            return new String(str.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return str;
        }
    }


    /**使用spring自带的转换值的方法*/
    public static <T,S> void coverWithSpring(S sour,T tar){
        BeanUtils.copyProperties(sour,tar);
    }

    public static Boolean coverToBool(String v){
        BooleanConverter converter=new BooleanConverter(false);
        return converter.converBoo(v);
    }

    public static<T,S> List<T> transform(List<S> source,Class<T> tar)throws Exception{
        List<T> tList=new LinkedList<T>();
        for (S s:source){
            T t=tar.newInstance();
            try {
                copy(s,t);
            } catch (Exception e) {
                logger.error("",e);
                throw new Exception(e);
            }
            tList.add(t);
        }
        return tList;
    }

    public static <T,S> void copy(S source,T tar,String... needid) throws Exception{
        if(tar==null){return;}
        Field[] fields= tar.getClass().getDeclaredFields();
      //  PropertyDescriptor[] fs= BeanUtils.getPropertyDescriptors(tar.getClass());
        for (Field field:fields){
            String fname=field.getName();
            Method T_methodRead=getReadMethodName(fname,tar);
            Method T_methodWrite=getWriteMethodName(fname,tar);
            Method S_methodRead=getReadMethodName(fname,source);
            Method S_methodWrite=getWriteMethodName(fname,source);

            if (S_methodRead==null){continue;}

            Object o=S_methodRead.invoke(source);
            if (o==null||"".equals(o)){
                continue;
            }else {
                if (String.class.equals(o.getClass())){
                    String ao=((String)o).trim();
                    if (ao==null||"".equals(ao)){
                        continue;
                    }
                }
            }

            Class[] cs = T_methodWrite.getParameterTypes();
            Class ct=cs[0];
            Object oo = null;
            try {
                oo = ConvertUtil.convert(ct, o);
            } catch (Exception e) {
                throw new RuntimeException("转换字段"+fname+"出错!"+o.getClass().getName()+"转为"+ct.getName()+">>>"+o,e);
            }
            if(oo==null){
                continue;
            }
            org.apache.commons.beanutils.BeanUtils.setProperty(tar,fname,oo);

            //写入id值
            if (needid==null||needid.length==0) {
                try {
                    if (MyClassUtil.hasField(tar,"id") && MyClassUtil.hasField(source,"priUuid")) {
                        String uuid = CommonUtil.getUUID();
                        org.apache.commons.beanutils.BeanUtils.setProperty(tar, "id", uuid);
                        org.apache.commons.beanutils.BeanUtils.setProperty(source, "priUuid", uuid);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            try {
                if (needid!=null&&needid.length>0&&"id2priUuid".equals(needid[0]) ){
                    String ido = org.apache.commons.beanutils.BeanUtils.getProperty(source,"id");
                    org.apache.commons.beanutils.BeanUtils.setProperty(tar, "priUuid", ido);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**找到读取属性的方法*/
    private static<T> Method getReadMethodName(String name, T t){
        PropertyDescriptor[] s= BeanUtils.getPropertyDescriptors(t.getClass());
        for (PropertyDescriptor p:s){
            if (name.equalsIgnoreCase(p.getName())){
                return p.getReadMethod();
            }
        }
        return null;
    }
    /**获取写值的方法*/
    private static <T> Method getWriteMethodName(String name ,T t){
        PropertyDescriptor[] s= BeanUtils.getPropertyDescriptors(t.getClass());
        for (PropertyDescriptor p:s){
            if (name.equalsIgnoreCase(p.getName())){
                return p.getWriteMethod();
            }
        }
        return null;
    }


    private static Table<Object,Object,Integer> table= HashBasedTable.create();
    static{
        table.put(Short.class,String.class,11);//后一个转为前一个
        table.put(BigDecimal.class,String.class,12);//后一个转为前一个
        table.put(Long.class,String.class,13);//后一个转为前一个
        table.put(Date.class,String.class,14);//后一个转为前一个
        table.put(Integer.class,String.class,15);//后一个转为前一个
        table.put(String.class,BigDecimal.class,16);//后一个转为前一个
        table.put(String.class,Long.class,17);//后一个转为前一个
        table.put(String.class,Short.class,17);//后一个转为前一个
        table.put(String.class,Integer.class,17);//后一个转为前一个
        table.put(String.class,Date.class,18);//后一个转为前一个

        table.put(Boolean.class,String.class,1);//字符串
        table.put(String.class,Boolean.class,2);//布尔型转为字符串型
        table.put(Integer.class,Long.class,3);
        table.put(Long.class,Integer.class,4);
        table.put(int.class,Integer.class,5);
        table.put(Integer.class,int.class,5);
        table.put(long.class,Long.class,5);
        table.put(Long.class,long.class,5);
        table.put(boolean.class,Boolean.class,5);
        table.put(Boolean.class,boolean.class,5);
        table.put(float.class,Float.class,5);
        table.put(Float.class,float.class,5);
        table.put(int.class,Integer.class,5);
        table.put(Integer.class,int.class,6);
        table.put(long.class,Long.class,7);
        table.put(Long.class,long.class,8);
        table.put(boolean.class,Boolean.class,9);
        table.put(Boolean.class,boolean.class,10);
    }
    public static <S> Object convert(Class t, S s) throws Exception{
        if(s.getClass().equals(t)){
            return s;
        }
        Integer i = table.get(t,s.getClass());
        if(i==null){
            throw new Exception("不支持"+s.toString()+"转为"+s.getClass()+"请自己来写一个转换方法!");
        }
        switch (i){
            case 11:{
                return Short.valueOf((String) s);
            }
            case 12:{
                return new BigDecimal((String) s);
            }
            case 13:{
                return Long.valueOf((String)s);
            }
            case 14:{
                return DateUtil.allStr2Date((String) s);
            }
            case 16:{
                return ((BigDecimal)s).toString();
            }
            case 17:{
                return String.valueOf(s);
            }
            case 18:{
                return DateUtil.DateToString((Date)s);
            }
            case 15:{
                String ss=(String)s;
                if (ss.endsWith(".0")){
                    ss=ss.replace(".0","");
                }
                return Integer.valueOf(ss);
            }
            case 2:
                return  s.toString();
            case 1:
                return "true".equals(s)?true:false;
            case 3:
                return Integer.parseInt(((Long)s).toString());
            case 4:
                return Long.parseLong(((Integer)s).toString());
            case 5:
                return s;
            case 6:
                return (Integer) s;
            case 7:
                return ((Long)s).longValue();
            case 8:
                return (Long)s;
            case 9:
                return ((Boolean)s).booleanValue();
            case 10:
                return (Boolean)s;
            default:return null;

        }
    }

    public static <T> T getValue(String value, Class<T> clazz) throws ParseException {
         if (Date.class.getName().equalsIgnoreCase(clazz.getName())) { // 从String类型到java.util.Date
            return (T) DateUtil.allStr2Date(value);
        } else{
            return (T) ConvertUtils.convert(value, clazz);
        }
    }


}
