package com.xuecheng.content.service;/**
 * @author 朱焕杰
 * @version 1.0
 * @date 2023/2/23 11:12
 */

import com.xuecheng.content.model.dto.CourseCategoryTreeDto;

import java.util.List;

/**
 * @description 课程分类操作相关service
 * @author 朱焕杰
 * @date 2023/2/23 11:12
 * @version 1.0
 */
public interface CourseCategoryService {

    /***
     * @description 课程分类查询
     * @param id 根节点id
     * @return java.util.List<com.xuecheng.content.model.dto.CourseCategoryTreeDto> 根节点下所有的子节点
     * @author 朱焕杰
     * @date 2023/2/23 11:12
    */
    List<CourseCategoryTreeDto>  queryTreeNodes(String id);
}
