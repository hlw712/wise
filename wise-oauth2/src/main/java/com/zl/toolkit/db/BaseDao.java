package com.zl.toolkit.db;

import java.util.List;

/**
 * 数据库操作基础接口
 *
 * @author huanglw
 * @date 2017-8-2
 */
public interface BaseDao {

    /**
     * 添加
     *
     * @param t
     * @param <T>
     * @return
     */
    <T>int inset(T t);

    /**
     * 删除
     *
     * @param t
     * @param <T>
     * @return
     */
    <T>int delete(T t);

    /**
     * 修改
     *
     * @param t
     * @param <T>
     * @return
     */
    <T>int update(T t);

    /**
     * 查询
     *
     * @param t
     * @param <E>
     * @param <T>
     * @return
     */
    <E, T>List<E> find(T t);
}
