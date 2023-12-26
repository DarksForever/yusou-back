package com.yupi.springbootinit.service;

import com.yupi.springbootinit.model.dto.search.SearchRequest;
import com.yupi.springbootinit.model.vo.SearchVO;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liaochangyi
 * @date 2023-12-26  17:00
 */
public interface SearchService {

    /**
     * 搜索所有信息
     *
     * @param searchRequest
     * @param request
     * @return
     */
    SearchVO searchAll(SearchRequest searchRequest, HttpServletRequest request);
}
