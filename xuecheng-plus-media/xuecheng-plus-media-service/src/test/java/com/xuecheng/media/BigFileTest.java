package com.xuecheng.media;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 朱焕杰
 * @version 1.0
 * @description TODO
 * @date 2023/2/27 19:31
 */
public class BigFileTest {
    //测试文件分块方法
    @Test
    public void testChunk() throws IOException {
        //源文件
        File sourceFile = new File("d:/develop/bigfile_test/nacos.avi");
        //输出路径
        String chunkPath = "d:/develop/bigfile_test/chunk/";
        File chunkFolder = new File(chunkPath);
        //路径不存在则创建
        if (!chunkFolder.exists()) {
            chunkFolder.mkdirs();
        }
        //分块大小
        long chunkSize = 1024 * 1024 * 1;
        //分块数量
        long chunkNum = (long) Math.ceil(sourceFile.length() * 1.0 / chunkSize);
        System.out.println("分块总数：" + chunkNum);
        //缓冲区大小
        byte[] b = new byte[1024];
        //使用RandomAccessFile访问文件
        RandomAccessFile raf_read = new RandomAccessFile(sourceFile, "r");
        //分块
        for (int i = 0; i < chunkNum; i++) {
            //创建分块文件
            File file = new File(chunkPath + i);
            if (file.exists()) {
                file.delete();
            }
            //创建分块文件
            boolean newFile = file.createNewFile();
            if (newFile) {
                //向分块文件中写数据
                RandomAccessFile raf_write = new RandomAccessFile(file, "rw");
                int len = -1;
                while ((len = raf_read.read(b)) != -1) {
                    //边读边写
                    raf_write.write(b, 0, len);
                    if (file.length() >= chunkSize) {
                        //当前分块文件已满
                        break;
                    }
                }
                //关闭流
                raf_write.close();
                System.out.println("完成分块" + i);
            }

        }
        //关闭流
        raf_read.close();
    }

    //测试合并
    @Test
    public void testMerge() throws IOException {
        //源文件
        File sourceFile = new File("D:\\develop\\bigfile_test\\nacos.mp4");

        //分块文件存储路径
        File chunkFolderPath = new File("D:\\develop\\bigfile_test\\chunk\\");
        if (!chunkFolderPath.exists()) {
            chunkFolderPath.mkdirs();
        }
        //合并后的文件
        File mergeFile = new File("D:\\develop\\bigfile_test\\nacos_01.mp4");
        boolean newFile1 = mergeFile.createNewFile();

        //思路，使用流对象读取分块文件，按顺序将分块文件依次向合并文件写数据
        //获取分块文件列表,按文件名升序排序
        File[] chunkFiles = chunkFolderPath.listFiles();
        List<File> chunkFileList = Arrays.asList(chunkFiles);
        //按文件名升序排序
        Collections.sort(chunkFileList, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                return Integer.parseInt(o1.getName()) - Integer.parseInt(o2.getName());
            }
        });
        //创建合并文件的流对象
        RandomAccessFile raf_write = new RandomAccessFile(mergeFile, "rw");
        byte[] b = new byte[1024];
        for (File file : chunkFileList) {
            //读取分块文件的流对象
            RandomAccessFile raf_read = new RandomAccessFile(file, "r");
            int len = -1;
            while ((len = raf_read.read(b)) != -1) {
                //向合并文件写数据
                raf_write.write(b, 0, len);
            }
        }

        //校验合并后的文件是否正确
        FileInputStream sourceFileStream = new FileInputStream(sourceFile);
        FileInputStream mergeFileStream = new FileInputStream(mergeFile);
        String sourceMd5Hex = DigestUtils.md5Hex(sourceFileStream);
        String mergeMd5Hex = DigestUtils.md5Hex(mergeFileStream);
        if (sourceMd5Hex.equals(mergeMd5Hex)) {
            System.out.println("合并成功");
        }
    }

    @Test
    public void test(){
        if(true){
            System.out.println(11);
            try {
                throw new RuntimeException();
            } catch (RuntimeException e) {
                e.printStackTrace();
                System.out.println(22);
            }finally {
                System.out.println(33);
            }
            System.out.println(44);
        }
    }

    @Test
    public void test1() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CountDownLatch countDownLatch = new CountDownLatch(10);
        executorService.execute(()->{
            for (int i = 0; i < 10; i++) {
                System.out.println(i);
                countDownLatch.countDown();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        countDownLatch.await();
    }

}
