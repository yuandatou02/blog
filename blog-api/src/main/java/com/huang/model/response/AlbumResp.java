package com.huang.model.response;

import lombok.Data;

/**
 * 相册Response
 *
 * @author Ikaros
 * @since 2025/8/26 17:30 星期二
 */
@Data
public class AlbumResp {
    /**
     * 相册id
     */
    private Integer id;

    /**
     * 相册名
     */
    private String albumName;

    /**
     * 相册封面
     */
    private String albumCover;

    /**
     * 相册描述
     */
    private String albumDesc;
}
