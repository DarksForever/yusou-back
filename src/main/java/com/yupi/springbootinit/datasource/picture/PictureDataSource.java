package com.yupi.springbootinit.datasource.picture;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.datasource.DataSource;
import com.yupi.springbootinit.model.entity.Picture;
import com.yupi.springbootinit.service.PictureService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 图片数据源
 *
 * @author liaochangyi
 * @date 2023-12-26  17:20
 */
@Component
public class PictureDataSource implements DataSource<Picture> {

    @Resource
    private PictureService pictureService;

    @Override
    public Page<Picture> doSearch(String searchText, long pageNum, long pageSize) {
        return pictureService.listPictures(pageNum, pageSize, searchText);
    }
}
