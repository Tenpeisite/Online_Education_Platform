package com.xuecheng.media.service.jobhandler;

import com.xuecheng.base.utils.Mp4VideoUtil;
import com.xuecheng.media.model.po.MediaProcess;
import com.xuecheng.media.service.MediaFileProcessService;
import com.xuecheng.media.service.MediaFileService;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


@Component
@Slf4j
public class VideoTask {

    @Autowired
    private MediaFileProcessService mediaFileProcessService;

    @Autowired
    private MediaFileService mediaFileService;

    @Value("${videoprocess.ffmpegpath}")
    private String ffmpegpath;

    /**
     * 2、分片广播任务
     */
    @XxlJob("videoJobHander")
    public void videoJobHander() throws Exception {

        // 分片参数
        int shardIndex = XxlJobHelper.getShardIndex();
        // 分片总数
        int shardTotal = XxlJobHelper.getShardTotal();

        //查询待处理任务,一次处理的任务数和cpu核心数一样
        List<MediaProcess> mediaProcessList = mediaFileProcessService.getMediaProcessList(shardIndex, shardTotal, 2);
        if (CollectionUtils.isEmpty(mediaProcessList)) {
            log.debug("查询到的待处理任务为0");
            return;
        }
        //要处理的任务数
        int size = mediaProcessList.size();

        //创建size个线程数量的线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(size);
        //计数器
        CountDownLatch countDownLatch = new CountDownLatch(size);

        //遍历mediaProcessList，将任务放入线程池
        mediaProcessList.forEach(mediaProcess -> {
            //任务执行逻辑
            threadPool.execute(() -> {
                //视频处理状态
                String status = mediaProcess.getStatus();
                //保证幂等性
                if ("2".equals(status)) {
                    log.debug("视频已经处理不用再次处理，视频信息：{}", mediaProcess);
                    //计数器-1
                    countDownLatch.countDown();
                    return;
                }
                //桶
                String bucket = mediaProcess.getBucket();
                //原视频的MD5值
                String fileId = mediaProcess.getFileId();
                //存储路径
                String filePath = mediaProcess.getFilePath();
                //原视频文件名称
                String filename = mediaProcess.getFilename();

                //将要处理的文件下载到服务器上
                File originalFile = null;
                //处理结束的视频文件
                File mp4File = null;

                try {
                    originalFile = File.createTempFile("original", null);
                    mp4File = File.createTempFile("mp4", ".mp4");
                } catch (IOException e) {
                    log.error("处理视频前创建临时文件失效");
                    //计数器-1
                    countDownLatch.countDown();
                    return;
                }

                try {
                    //将原视频下载到本地（avi）
                    originalFile = mediaFileService.downloadFileFromMinIO(originalFile, bucket, filePath);
                } catch (Exception e) {
                    log.error("下载原始文件过程出错：{}，文件信息：{}", e.getMessage(), mediaProcess);
                    //计数器-1
                    countDownLatch.countDown();
                    return;
                }

                //调用工具类将avi转成MP4
                //转换后MP4文件的名称
                String mp4_name = fileId + ".mp4";
                //转换后MP4文件的路径
                String mp4_path = mp4File.getAbsolutePath();
                Mp4VideoUtil videoUtil = new Mp4VideoUtil(ffmpegpath, originalFile.getAbsolutePath(), mp4_name, mp4_path);
                //开始视频转换，成功将返回success,失败则返回失败原因
                String result = videoUtil.generateMp4();
                //默认处理失败
                String statusNew = "3";
                //最终访问路径
                String url = null;
                if ("success".equals(result)) {
                    //转换成功
                    //上传至minIO的路径
                    String objectName = getFilePathByMd5(fileId, ".mp4");
                    try {
                        //上传至minio
                        mediaFileService.addMediaFilesToMinIO(mp4_path, bucket, objectName);
                        //处理成功
                        statusNew = "2";
                        url = "/" + bucket + "/" + objectName;
                    } catch (Exception e) {
                        log.debug("上传文件出错:{}", e.getMessage());
                        //计数器-1
                        countDownLatch.countDown();
                        return;
                    }
                }
                try {
                    //记录任务处理结果
                    mediaFileProcessService.saveProcessFinishStatus(mediaProcess.getId(), statusNew, fileId, url, result);
                    //删除临时文件 originalFile 和 mp4File
                    if(originalFile.exists()){
                        originalFile.delete();
                    }
                    if(mp4File.exists()){
                        mp4File.delete();
                    }
                } catch (Exception e) {
                    log.debug("保存任务处理结果出错：{}",e.getMessage());
                    //计数器-1
                    countDownLatch.countDown();
                    return;
                }

                //计数器-1
                countDownLatch.countDown();
            });
        });
        //阻塞是为了使线程池中的任务都完成，不阻塞的话方法一下子就结束了，任务也没时间执行
        //阻塞到任务执行完成，当"countDownLatch"计数器归零，解除阻塞
        //等待，给一个充裕的超时时间，防止无限等待，到达超时时间还没有处理完成则结束任务
        countDownLatch.await(30,TimeUnit.MINUTES);
    }

    private String getFilePathByMd5(String fileMd5, String fileExt) {
        //将文件MD5值的第一位数作为一级目录，第二位数作为二级目录
        return fileMd5.substring(0, 1) + "/" + fileMd5.substring(1, 2) + "/" + fileMd5 + "/" + fileMd5 + fileExt;
    }
}
