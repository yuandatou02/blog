package com.huang.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huang.entity.Album;
import com.huang.entity.Photo;
import com.huang.enums.FilePathEnum;
import com.huang.mapper.AlbumMapper;
import com.huang.mapper.PhotoMapper;
import com.huang.model.PageResult;
import com.huang.model.request.AlbumReq;
import com.huang.model.request.PageQuery;
import com.huang.model.response.AlbumBackResp;
import com.huang.model.response.AlbumResp;
import com.huang.strategy.context.UploadStrategyContext;
import com.huang.utils.BeanCopyUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 相册服务类
 *
 * @author Ikaros
 * @since 2025/8/26 17:38 星期二
 */
@Service
@RequiredArgsConstructor
public class AlbumService extends ServiceImpl<AlbumMapper, Album> {

    private final UploadStrategyContext uploadStrategyContext;

    private final BlogFileService blogFileService;

    private final PhotoMapper photoMapper;

    /**
     * 查询相册列表
     *
     * @return 相册列表
     */
    public List<AlbumResp> listAlbumVO() {
        return baseMapper.selectAlbumVOList();
    }

    /**
     * 查询相册信息
     *
     * @param albumId 相册id
     * @return 相册信息
     */
    public AlbumReq editAlbum(Integer albumId) {
        return baseMapper.selectAlbumById(albumId);
    }

    /**
     * 修改相册
     *
     * @param album 相册信息
     */
    public void updateAlbum(AlbumReq album) {
        // 相册是否存在
        Album existAlbum = baseMapper.selectOne(new LambdaQueryWrapper<Album>()
                .select(Album::getId)
                .eq(Album::getAlbumName, album.getAlbumName()));
        Assert.isTrue(existAlbum == null || existAlbum.getId().equals(album.getId()),
                album.getAlbumName() + "相册已存在");
        // 修改相册
        Album newAlbum = BeanCopyUtils.copyBean(album, Album.class);
        baseMapper.updateById(newAlbum);
    }

    /**
     * 删除相册
     *
     * @param albumId 相册id
     */
    public void deleteAlbum(Integer albumId) {
        // 查询照片数量
        Long count = photoMapper.selectCount(new LambdaQueryWrapper<Photo>()
                .eq(Photo::getAlbumId, albumId));
        Assert.isTrue(count <= 0, "相册下存在照片");
        // 不存在照片则删除
        baseMapper.deleteById(albumId);
    }

    /**
     * 添加相册
     *
     * @param album 相册信息
     */
    public void addAlbum(AlbumReq album) {
        // 相册是否存在
        Album existAlbum = baseMapper.selectOne(new LambdaQueryWrapper<Album>()
                .select(Album::getId)
                .eq(Album::getAlbumName, album.getAlbumName()));
        Assert.isNull(existAlbum, album.getAlbumName() + "相册已存在");
        // 添加新相册
        Album newAlbum = BeanCopyUtils.copyBean(album, Album.class);
        baseMapper.insert(newAlbum);
    }

    /**
     * 上传相册封面
     *
     * @param file 相册封面文件
     * @return 相册封面url
     */
    public String uploadAlbumCover(MultipartFile file) {
        // 上传文件
        String url = uploadStrategyContext.executeUploadStrategy(file, FilePathEnum.PHOTO.getPath());
        blogFileService.saveBlogFile(file, url, FilePathEnum.PHOTO.getFilePath());
        return url;
    }

    /**
     * 分页查询相册信息
     *
     * @param albumQuery 分页查询条件
     * @return 分页结果
     */
    public PageResult<AlbumBackResp> listAlbumBackVO(PageQuery albumQuery) {
        List<AlbumBackResp> albumBackList = baseMapper.selectBackAlbumListWithCount(albumQuery);
        if (albumBackList.isEmpty()) {
            return new PageResult<>();
        }
        return new PageResult<>(albumBackList, albumBackList.getFirst().getTotalCount());
    }
}
