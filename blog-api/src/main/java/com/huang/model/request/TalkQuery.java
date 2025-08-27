package com.huang.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 说说查询条件类
 *
 * @author Ikaros
 * @since 2025/8/27 11:57 星期三
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TalkQuery extends PageQuery {
    /**
     * 状态 (1公开  2私密)
     */
    private Integer status;
}
