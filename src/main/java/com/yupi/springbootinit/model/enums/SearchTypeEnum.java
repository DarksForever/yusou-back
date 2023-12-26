package com.yupi.springbootinit.model.enums;

import lombok.Getter;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 搜索类型枚举
 *
 * @author liaochangyi
 * @date 2023-12-26  15:35
 */
@Getter
public enum SearchTypeEnum {
    /**
     * 搜索用户
     */
    USER("user", "用户"),
    /**
     * 搜索帖子
     */
    POST("post", "帖子"),
    /**
     * 搜索图片
     */
    PICTURE("picture", "图片");

    /**
     * 枚举值
     */
    private final String value;
    /**
     * 枚举类型说明
     */
    private final String text;

    SearchTypeEnum(String value, String text) {
        this.text = text;
        this.value = value;
    }

    /**
     * 获取值列表
     *
     * @return
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value
     * @return
     */
    public static SearchTypeEnum getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (SearchTypeEnum searchTypeEnum : SearchTypeEnum.values()) {
            if (searchTypeEnum.value.equals(value)) {
                return searchTypeEnum;
            }
        }
        return null;
    }
}
