package com.xuecheng.content.service;/**
 * @author 朱焕杰
 * @version 1.0
 * @date 2023/2/22 22:55
 */

import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.EditCourseDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;

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

    /***
     * @description 新增课程
     * @param companyId 培训机构id
     * @param addCourseDto 新增课程信息
     * @return com.xuecheng.content.model.dto.CourseBaseInfoDto 课程基本信息和营销信息
     * @author 朱焕杰
     * @date 2023/2/23 15:54
    */
    public CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto addCourseDto);

    /***
     * @description 查询课程基本信息
     * @param courseId
     * @return com.xuecheng.content.model.dto.CourseBaseInfoDto
     * @author 朱焕杰
     * @date 2023/2/25 13:35
    */
    CourseBaseInfoDto getCourseBaseInfo(Long courseId);

    /***
     * @description 修改课程
     * @param companyId
     * @param dto
     * @return com.xuecheng.content.model.dto.CourseBaseInfoDto
     * @author 朱焕杰
     * @date 2023/2/25 14:12
    */
    public CourseBaseInfoDto updateCourseBase(Long companyId, EditCourseDto dto);
}
