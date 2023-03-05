package com.xuecheng.media.service;

import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.base.model.RestResponse;
import com.xuecheng.media.model.dto.QueryMediaParamsDto;
import com.xuecheng.media.model.dto.UploadFileParamsDto;
import com.xuecheng.media.model.dto.UploadFileResultDto;
import com.xuecheng.media.model.po.MediaFiles;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.File;
import java.util.List;

/**
 * @author Mr.M
 * @version 1.0
 * @description 媒资文件管理业务类
 * @date 2022/9/10 8:55
 */
public interface MediaFileService {

    /**
     * @param pageParams          分页参数
     * @param queryMediaParamsDto 查询条件
     * @return com.xuecheng.base.model.PageResult<com.xuecheng.media.model.po.MediaFiles>
     * @description 媒资文件查询方法
     * @author Mr.M
     * @date 2022/9/10 8:57
     */
    public PageResult<MediaFiles> queryMediaFiels(Long companyId, PageParams pageParams, QueryMediaParamsDto queryMediaParamsDto);


    /***
     * @description 上传文件通用接口
     * @param companyId 机构id
     * @param uploadFileParamsDto 文件信息
     * @param bytes 文件字节数组
     * @param folder 桶下面的子目录
     * @param objectName 对象名称
     * @return com.xuecheng.media.model.dto.UploadFileResultDto
     * @author 朱焕杰
     * @date 2023/2/26 18:07
     */
    public UploadFileResultDto uploadFile(Long companyId, UploadFileParamsDto uploadFileParamsDto, byte[] bytes, String folder, String objectName);


    public MediaFiles addMediaFilesToDb(Long companyId, String fileId, UploadFileParamsDto uploadFileParamsDto, String bucket, String objectName);


    /**
     * @param fileMd5 文件的md5
     * @description 检查文件是否存在
     */
    public RestResponse<Boolean> checkFile(String fileMd5);

    /**
     * @param fileMd5    文件的md5
     * @param chunkIndex 分块序号
     * @description 检查分块是否存在
     */
    public RestResponse<Boolean> checkChunk(String fileMd5, int chunkIndex);

    /***
     * @description 上传分块
     * @param fileMd5
     * @param chunk
     * @param bytes
     * @return com.xuecheng.base.model.RestResponse
     * @author 朱焕杰
     * @date 2023/2/28 10:21
     */
    public RestResponse uploadChunk(String fileMd5, int chunk, byte[] bytes);

    /***
     * @description 合并分块
     * @param companyId 机构id
     * @param fileMd5 文件MD5
     * @param chunkTotal 分块总数
     * @param uploadFileParamsDto 文件信息
     * @return com.xuecheng.base.model.RestResponse
     * @author 朱焕杰
     * @date 2023/2/28 10:57
     */
    public RestResponse mergechunks(Long companyId, String fileMd5, int chunkTotal, UploadFileParamsDto uploadFileParamsDto);

    /**
     * @param id 文件id
     * @return com.xuecheng.media.model.po.MediaFiles 文件信息
     * @description 根据id查询文件信息
     * @author Mr.M
     * @date 2022/9/13 17:47
     */
    public MediaFiles getFileById(String id);

    /***
     * @description 下载文件
     * @param file
     * @param bucket
     * @param objectName
     * @return java.io.File
     * @author 朱焕杰
     * @date 2023/3/2 12:26
    */
    public File downloadFileFromMinIO(File file, String bucket, String objectName);


    /***
     * @description 上传文件
     * @param filePath 文件路径
     * @param bucket 桶
     * @param objectName 文件名
     * @return void
     * @author 朱焕杰
     * @date 2023/3/2 12:46
    */
    public void addMediaFilesToMinIO(String filePath, String bucket, String objectName) ;

}
