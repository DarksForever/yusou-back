package com.yupi.springbootinit.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.datasource.DataSource;
import com.yupi.springbootinit.datasource.picture.PictureDataSource;
import com.yupi.springbootinit.datasource.post.PostDataSource;
import com.yupi.springbootinit.datasource.user.UserDataSource;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.exception.ThrowUtils;
import com.yupi.springbootinit.model.dto.post.PostQueryRequest;
import com.yupi.springbootinit.model.dto.search.SearchRequest;
import com.yupi.springbootinit.model.dto.user.UserQueryRequest;
import com.yupi.springbootinit.model.entity.Picture;
import com.yupi.springbootinit.model.enums.SearchTypeEnum;
import com.yupi.springbootinit.model.vo.PostVO;
import com.yupi.springbootinit.model.vo.SearchVO;
import com.yupi.springbootinit.model.vo.UserVO;
import com.yupi.springbootinit.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.CompletableFuture;

/**
 * @author liaochangyi
 * @date 2023-12-26  16:59
 */
@Service
@Slf4j
public class SearchServiceImpl implements SearchService {

    @Resource
    private UserDataSource userDataSource;

    @Resource
    private PostDataSource postDataSource;

    @Resource
    private PictureDataSource pictureDataSource;

    @Override
    public SearchVO searchAll(SearchRequest searchRequest, HttpServletRequest request) {
        String searchText = searchRequest.getSearchText();
        String searchType = searchRequest.getSearchType();
        ThrowUtils.throwIf(StringUtils.isBlank(searchType), ErrorCode.PARAMS_ERROR);
        SearchTypeEnum searchTypeEnum = SearchTypeEnum.getEnumByValue(searchType);
        long pageNum = searchRequest.getCurrent();
        long pageSize = searchRequest.getPageSize();
        //查询类型为空，默认为查全部
        if (null == searchTypeEnum) {
            CompletableFuture<Page<Picture>> pictureTask = CompletableFuture.supplyAsync(() -> {
                Page<Picture> picturePage = pictureDataSource.doSearch(searchText, pageNum, pageSize);
                return picturePage;
            });

            CompletableFuture<Page<UserVO>> userTask = CompletableFuture.supplyAsync(() -> {
                Page<UserVO> userVOPage = userDataSource.doSearch(searchText, pageNum, pageSize);
                return userVOPage;
            });

            CompletableFuture<Page<PostVO>> postTask = CompletableFuture.supplyAsync(() -> {
                Page<PostVO> postVOPage = postDataSource.doSearch(searchText, pageNum, pageSize);
                return postVOPage;
            });

            CompletableFuture.allOf(pictureTask, userTask, postTask).join();

            // 等待所有异步任务完成并获取结果
            Page<Picture> picturePage = null;
            try {
                picturePage = pictureTask.get();
                Page<UserVO> userVOPage = userTask.get();
                Page<PostVO> postVOPage = postTask.get();
                SearchVO searchVO = SearchVO.builder()
                        .userVoList(userVOPage.getRecords())
                        .postVoList(postVOPage.getRecords())
                        .pictureList(picturePage.getRecords())
                        .build();
                return searchVO;
            } catch (Exception e) {
                log.error("聚合查询异常", e);
                throw new BusinessException(ErrorCode.OPERATION_ERROR, "查询异常");
            }
        } else {
            SearchVO searchVo = new SearchVO();
            switch (searchTypeEnum) {
                case POST:
                    PostQueryRequest postQueryRequest = new PostQueryRequest();
                    postQueryRequest.setSearchText(searchText);
                    Page<PostVO> postVOPage = postDataSource.doSearch(searchText, pageNum, pageSize);
                    searchVo.setPostVoList(postVOPage.getRecords());
                    break;
                case USER:
                    UserQueryRequest userQueryRequest = new UserQueryRequest();
                    userQueryRequest.setUserName(searchText);
                    Page<UserVO> userVOPage = userDataSource.doSearch(searchText, pageNum, pageSize);
                    searchVo.setUserVoList(userVOPage.getRecords());
                    break;
                case PICTURE:
                    Page<Picture> picturePage = pictureDataSource.doSearch(searchText, pageNum, pageSize);
                    searchVo.setPictureList(picturePage.getRecords());
                    break;
                default:
            }
            return searchVo;
        }
    }
}
