package com.yupi.springbootinit.model.dto.search;

import com.yupi.springbootinit.common.PageRequest;
import com.yupi.springbootinit.model.enums.SearchTypeEnum;
import lombok.Data;

/**
 * 聚合搜索请求
 *
 * @author liaochangyi
 * @date 2023-12-26  15:35
 */
@Data
public class SearchRequest extends PageRequest {

    /**
     * 搜索内容
     */
    private String searchText;

    /**
     * 搜索类型
     */
    private String searchType;

}
