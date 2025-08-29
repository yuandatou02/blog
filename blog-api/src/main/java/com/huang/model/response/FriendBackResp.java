package com.huang.model.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 友链后台Response
 *
 * @author Ikaros
 * @since 2025/8/30 00:05 星期六
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FriendBackResp extends FriendResp {
    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 总记录数（用于分页，不返回给前端）
     */
    @JsonIgnore // 避免返回给前端
    private Long totalCount;
}
