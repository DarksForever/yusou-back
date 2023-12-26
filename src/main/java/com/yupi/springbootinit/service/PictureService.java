package com.yupi.springbootinit.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.model.entity.Picture;

/**
 * 图片服务
 *
 * @author liaochangyi
 * @date 2023-12-26  14:05
 */
public interface PictureService {

    /**
     * 分页查询图片
     *
     * @param pageNum
     * @param size
     * @param searchText
     * @return
     */
    Page<Picture> listPictures(long pageNum, long size, String searchText);
}
