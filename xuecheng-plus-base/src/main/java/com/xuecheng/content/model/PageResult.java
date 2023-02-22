package com.xuecheng.content.model;/**
 * @author 朱焕杰
 * @version 1.0
 * @date 2023/2/22 14:25
 */

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author 朱焕杰
 * @version 1.0
 * @description 通用分页结果
 * @date 2023/2/22 14:25
 */
@Data
public class PageResult<T> implements Serializable {
    // 数据列表
    private List<T> items;
    //总记录数
    private long counts;
    //当前页码
    private long page;
    //每页记录数
    private long pageSize;

    public PageResult(List<T> items, long counts, long page, long pageSize) {
        this.items = items;
        this.counts = counts;
        this.page = page;
        this.pageSize = pageSize;
    }
}
