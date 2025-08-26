package com.huang.model.request;

import com.huang.constants.PageConstant;
import lombok.Data;

/**
 * @author Ikaros
 * @since 2025/8/26 17:28 星期二
 */
@Data
public class PageQuery {
    /**
     * 当前页
     */
    private Integer current;

    /**
     * 条数
     */
    private Integer size;

    /**
     * 搜索内容
     */
    private String keyword;

    public Integer getCurrent() {
        return current == null ? (PageConstant.DEFAULT_CURRENT - 1) * getSize() : (current - 1) * getSize();
    }

    public Integer getSize() {
        return size == null ? PageConstant.MY_SIZE : size;
    }
}
