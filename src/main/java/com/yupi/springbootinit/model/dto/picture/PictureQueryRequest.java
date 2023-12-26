package com.yupi.springbootinit.model.dto.picture;

import com.yupi.springbootinit.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 分页查询图片请求
 *
 * @author liaochangyi
 * @date 2023-12-26  14:12
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PictureQueryRequest extends PageRequest implements Serializable {
    /**
     * 查询条件
     */
    private String searchText;

    private static final long serialVersionUID = 1L;
}
