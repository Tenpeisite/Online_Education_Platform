package com.xuecheng.content.model.dto;/**
 * @author 朱焕杰
 * @version 1.0
 * @date 2023/2/22 14:18
 */

import lombok.Data;

/**
 * @author 朱焕杰
 * @version 1.0
 * @description 课程分页请求参数
 * @date 2023/2/22 14:18
 */
@Data
public class QueryCourseParamsDto {
    //审核状态
    private String auditStatus;
    //课程名称
    private String courseName;
    //发布状态
    private String publishStatus;
}
