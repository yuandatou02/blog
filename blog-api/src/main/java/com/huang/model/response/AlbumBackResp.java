package com.huang.model.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 相册后台Response
 *
 * @author Ikaros
 * @since 2025/8/26 17:31 星期二
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AlbumBackResp extends AlbumResp {
    /**
     * 状态 (1公开 2私密)
     */
    private Integer status;

    /**
     * 照片数量
     */
    private Long photoCount;

    /**
     * 总记录数（用于分页，不返回给前端）
     */
    @JsonIgnore // 避免返回给前端
    private Long totalCount;
}
