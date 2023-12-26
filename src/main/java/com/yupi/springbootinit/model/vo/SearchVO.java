package com.yupi.springbootinit.model.vo;

import com.yupi.springbootinit.model.entity.Picture;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 聚合搜索结果视图
 *
 * @author liaochangyi
 * @date 2023-12-26  15:31
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchVO {

    /**
     * 脱敏用户信息集合
     */
    private List<UserVO> userVoList;
    /**
     * 脱敏帖子信息集合
     */
    private List<PostVO> postVoList;
    /**
     * 图片信息集合
     */
    private List<Picture> pictureList;
}
