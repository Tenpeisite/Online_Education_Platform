package com.xuecheng.content.service;/**
 * @author 朱焕杰
 * @version 1.0
 * @date 2023/2/22 22:55
 */

import com.xuecheng.content.model.PageParams;
import com.xuecheng.content.model.PageResult;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import io.swagger.annotations.License;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @description 课程管理service
 * @author 朱焕杰
 * @date 2023/2/22 22:55
 * @version 1.0
 */
public interface CourseBaseInfoService {



    /***
     * @description 课程查询
     * @param params 分页参数
     * @param queryCourseParamsDto 查询条件
     * @return com.xuecheng.content.model.PageResult<com.xuecheng.content.model.po.CourseBase>
     * @author 朱焕杰
     * @date 2023/2/22 23:06
    */
    public PageResult<CourseBase> queryCourseBaseList(PageParams params,  QueryCourseParamsDto queryCourseParamsDto);
}
