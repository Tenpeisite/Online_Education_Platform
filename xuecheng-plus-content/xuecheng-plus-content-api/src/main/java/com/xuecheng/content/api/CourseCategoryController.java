package com.xuecheng.content.api;/**
 * @author 朱焕杰
 * @version 1.0
 * @date 2023/2/23 10:35
 */

import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import com.xuecheng.content.service.CourseCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 朱焕杰
 * @version 1.0
 * @description 课程分类接口
 * @date 2023/2/23 10:35
 */
@RestController
@Slf4j
@Api(value = "课程分类相关接口", tags = "课程分类")
public class CourseCategoryController {

    @Autowired
    CourseCategoryService courseCategoryService;


    @ApiOperation(value = "课程分类查询接口", notes = "查询课程树形分类")
    @GetMapping("/course-category/tree-nodes")
    public List<CourseCategoryTreeDto> queryTreeNodes() {
        List<CourseCategoryTreeDto> courseCategoryTreeDtos = courseCategoryService.queryTreeNodes("1");
        return courseCategoryTreeDtos;
    }


}
