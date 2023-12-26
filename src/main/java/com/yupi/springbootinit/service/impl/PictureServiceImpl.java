package com.yupi.springbootinit.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.model.entity.Picture;
import com.yupi.springbootinit.service.PictureService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author liaochangyi
 * @date 2023-12-26  14:05
 */
@Service
public class PictureServiceImpl implements PictureService {

    @Override
    public Page<Picture> listPictures(long pageNum, long size, String searchText) {
        long current = (pageNum - 1) * size;
        String url = String.format("https://www.bing.com/images/search?q=%s&first=%s", searchText, current);
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据获取异常");
        }
        Elements newsHeadlines = doc.select(".iuscp.isv");
        List<Picture> pictureList = new ArrayList<>();
        for (Element headline : newsHeadlines) {
            //取图片地址
            String m = headline.select(".iusc").get(0).attr("m");
            Map<String, Object> map = JSONUtil.toBean(m, Map.class);
            String murl = (String) map.get("murl");
            // 取标题
            String title = headline.select(".inflnk").get(0).attr("aria-label");
            Picture picture = Picture.builder().url(murl).title(title).build();
            pictureList.add(picture);
            //图片数量足够，退出循环
            if (pictureList.size() >= size) {
                break;
            }
        }
        Page<Picture> picturePage = new Page<>(pageNum, size);
        picturePage.setRecords(pictureList);
        return picturePage;
    }
}
