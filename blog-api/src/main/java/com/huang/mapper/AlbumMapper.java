package com.huang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huang.entity.Album;
import com.huang.model.request.AlbumReq;
import com.huang.model.request.PageQuery;
import com.huang.model.response.AlbumBackResp;
import com.huang.model.response.AlbumResp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 相册 Mapper 接口
 *
 * @author Ikaros
 * @since 2025/8/26 17:38 星期二
 */
@Mapper
public interface AlbumMapper extends BaseMapper<Album> {

    /**
     * 查看相册列表
     *
     * @return 相册列表
     */
    List<AlbumResp> selectAlbumVOList();

    /**
     * 根据id查询相册信息
     *
     * @param albumId 相册id
     * @return 相册
     */
    AlbumReq selectAlbumById(@Param("albumId") Integer albumId);

    /**
     * 查询后台相册列表
     *
     * @param albumQuery 相册查询条件
     * @return 后台相册列表
     */
    List<AlbumBackResp> selectBackAlbumListWithCount(@Param("param") PageQuery albumQuery);
}
