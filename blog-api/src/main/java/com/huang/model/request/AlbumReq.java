package com.huang.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 相册Request
 *
 * @author Ikaros
 * @since 2025/8/26 18:57 星期二
 */
@Data
public class AlbumReq {

    /**
     * 相册id
     */
    private Integer id;

    /**
     * 相册名
     */
    @NotBlank(message = "相册名不能为空")
    private String albumName;

    /**
     * 相册描述
     */
    private String albumDesc;

    /**
     * 相册封面
     */
    @NotBlank(message = "相册封面不能为空")
    private String albumCover;

    /**
     * 状态 (1公开 2私密)
     */
    private Integer status;
}
