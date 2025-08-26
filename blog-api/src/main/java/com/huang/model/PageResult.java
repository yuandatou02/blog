package com.huang.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页返回类
 *
 * @author Ikaros
 * @since 2025/8/26 17:31 星期二
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {

    /**
     * 分页结果
     */
    private List<T> recordList;

    /**
     * 总数
     */
    private Long count;

}
