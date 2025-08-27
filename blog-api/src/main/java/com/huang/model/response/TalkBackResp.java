package com.huang.model.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 说说后台Response类
 *
 * @author Ikaros
 * @since 2025/8/27 11:58 星期三
 */
@Data
public class TalkBackResp {
    /**
     * 说说id
     */
    private Integer id;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 说说内容
     */
    private String talkContent;

    /**
     * 图片
     */
    @JsonIgnore
    private String images;

    /**
     * 图片列表
     */
    private List<String> imgList;

    /**
     * 是否置顶 (0否 1是)
     */
    private Boolean isTop;

    /**
     * 说说状态 (1公开 2私密)
     */
    private Integer status;

    /**
     * 发布时间
     */
    private LocalDateTime createTime;

    /**
     * 总记录数（用于分页，不返回给前端）
     */
    @JsonIgnore // 避免返回给前端
    private Long totalCount;
}
