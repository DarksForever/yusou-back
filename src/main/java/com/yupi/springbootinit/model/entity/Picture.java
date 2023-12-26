package com.yupi.springbootinit.model.entity;

import lombok.Builder;
import lombok.Data;

/**
 * 图片
 *
 * @author liaochangyi
 * @date 2023-12-26  13:56
 */
@Data
@Builder
public class Picture {

    /**
     * 图片url
     */
    private String url;
    /**
     * 图片标题
     */
    private String title;
}
