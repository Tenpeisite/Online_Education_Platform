package com.xuecheng.media.service;

import com.xuecheng.media.model.po.MediaProcess;

import java.util.List;

/**
 * @author 朱焕杰
 * @version 1.0
 * @description TODO
 * @date 2023/3/2 11:14
 */
public interface MediaFileProcessService {

    /**
     * @description 获取待处理任务
     * @param shardIndex 分片序号
     * @param shardTotal 分片总数
     * @param count 获取记录数
     * @return java.util.List<com.xuecheng.media.model.po.MediaProcess>
     * @date 2022/9/14 14:49
     */
    public List<MediaProcess> getMediaProcessList(int shardIndex, int shardTotal, int count);

    /**
     * @description 保存任务结果
     * @param taskId  任务id
     * @param status 任务状态
     * @param fileId  文件id
     * @param url url
     * @param errorMsg 错误信息
     * @return void
     * @date 2022/10/15 11:29
     */
    void saveProcessFinishStatus(Long taskId,String status,String fileId,String url,String errorMsg);


}
