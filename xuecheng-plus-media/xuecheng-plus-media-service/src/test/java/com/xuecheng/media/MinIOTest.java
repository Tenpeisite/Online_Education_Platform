package com.xuecheng.media;

import io.minio.*;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @author 朱焕杰
 * @version 1.0
 * @description 测试minio上传文件，删除文件，查询文件
 * @date 2023/2/26 16:38
 */
public class MinIOTest {

    static MinioClient minioClient =
            MinioClient.builder()
                    .endpoint("http://192.168.101.65:9000")
                    .credentials("minioadmin", "minioadmin")
                    .build();


    //上传文件
    @Test
    public void upload() {
        try {
            UploadObjectArgs uploadObjectArgs = UploadObjectArgs.builder()
                    .bucket("testbucket")
                    .object("1.mp4")//同一个桶内对象名不能重复
                    .filename("D:\\download\\本地\\1374c8160ea2da8dd33208a9ad369641-c53f1208093e446cb847b7bbdcb30735-2.mp4")
                    .build();
            //上传
            minioClient.uploadObject(uploadObjectArgs);
            System.out.println("上传成功了");
        } catch (Exception e) {
            System.out.println("上传失败");
        }
    }

    @Test
    public void upload2() {
        try {
            UploadObjectArgs uploadObjectArgs = UploadObjectArgs.builder()
                    .bucket("testbucket")
                    .object("/test/1.xlsx")//同一个桶内对象名不能重复
                    .filename("D:\\download\\本地\\userInfo.xlsx")
                    .build();
            //上传
            minioClient.uploadObject(uploadObjectArgs);
            System.out.println("上传成功了");
        } catch (Exception e) {
            System.out.println("上传失败");
        }
    }

    //删除文件
    @Test
    public void delete() {
        try {
            RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder().bucket("testbucket").object("/test/1.xlsx").build();
            minioClient.removeObject(removeObjectArgs);
            System.out.println("删除成功");
        } catch (Exception e) {
            System.out.println("删除失败");
        }
    }

    //查询文件
    @Test
    public void get() {
        GetObjectArgs getObjectArgs = GetObjectArgs.builder().bucket("testbucket").object("1.mp4").build();
        try (
                //try执行完后自动就会把流给关了
                GetObjectResponse inputstream = minioClient.getObject(getObjectArgs);
                FileOutputStream outputStream = new FileOutputStream(new File("D:\\download\\本地\\1.mp4"));
        ) {
            if (inputstream != null) {
                IOUtils.copy(inputstream, outputStream);
            }
        } catch (Exception e) {
            System.out.println("查询失败");
        }
    }
}
