package com.yupi.springbootinit.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 数据源接口
 * 规定数据源接入规范
 *
 * @author liaochangyi
 * @date 2023-12-26  17:16
 */
public interface DataSource<T> {

    /**
     * 搜索
     *
     * @param searchText 搜索条件
     * @param pageNum 分页页码
     * @param pageSize 每页展示条数
     * @return
     */
    Page<T> doSearch(String searchText, long pageNum, long pageSize);
}
