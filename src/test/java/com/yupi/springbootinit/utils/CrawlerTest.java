package com.yupi.springbootinit.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yupi.springbootinit.model.entity.Post;
import com.yupi.springbootinit.service.PostService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 爬虫测试类
 *
 * @author liaochangyi
 * @date 2023-12-26  10:23
 */
@SpringBootTest
public class CrawlerTest {

    @Resource
    private PostService postService;

    @Test
    void testFetchPassage() {
        //获取数据
        String json = "{\"current\": 1, \"pageSize\": 8, \"sortField\": \"createTime\", \"sortOrder\": \"descend\", \"category\": \"文章\",\"reviewStatus\": 1}";
        String url = "https://www.code-nav.cn/api/post/search/page/vo";
        String result = HttpRequest.post(url).body(json).execute().body();

        //json转对象
        Map<String, Object> map = JSONUtil.toBean(result, Map.class);
        JSONObject data = (JSONObject) map.get("data");
        JSONArray records = (JSONArray) data.get("records");

        List<Post> postList = new ArrayList<>();
        for (Object record : records) {
            JSONObject tempRecord = (JSONObject) record;
            Post post = new Post();
            post.setTitle(tempRecord.getStr("title"));
            post.setContent(tempRecord.getStr("content"));
            post.setTags(tempRecord.getStr("tags"));
            post.setUserId(tempRecord.getLong("userId"));
            post.setCreateTime(tempRecord.getDate("createTime"));
            post.setUpdateTime(tempRecord.getDate("updateTime"));
            post.setThumbNum(tempRecord.getInt("thumbNum"));
            post.setFavourNum(tempRecord.getInt("favourNum"));
            postList.add(post);
        }
        //数据入库
        boolean saveResult = postService.saveBatch(postList);
        Assertions.assertTrue(saveResult);
    }
}
