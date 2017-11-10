package com.zl.toolkit.db;

import java.util.List;

/**
 * 数据库操作抽象实现类
 *
 * @author huanglw
 * @date 2017-8-2
 */
public abstract class AbstractBaseDao implements BaseDao {
    /**
     * 添加
     *
     * @param t
     * @param <T>
     * @return
     */
    public <T>int inset(T t) {
        return -1;
    }

    /**
     * 删除
     *
     * @param t
     * @param <T>
     * @return
     */
    public <T>int delete(T t) {
        return -1;
    }

    /**
     * 修改
     *
     * @param t
     * @param <T>
     * @return
     */
    public <T>int update(T t) {
        return -1;
    }

    /**
     * 查询
     *
     * @param t
     * @param <E>
     * @param <T>
     * @return
     */
    public <E, T>List<E> find(T t) {
        return null;
    }
}
