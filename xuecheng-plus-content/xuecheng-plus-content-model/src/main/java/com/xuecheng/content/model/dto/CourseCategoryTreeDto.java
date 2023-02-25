package com.xuecheng.content.model.dto;

import com.xuecheng.content.model.po.CourseCategory;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author 朱焕杰
 * @version 1.0
 * @date 2023/2/23 10:33
 */

/**
 * @description 课程分类
 * @author 朱焕杰
 * @date 2023/2/23 10:33
 * @version 1.0
 */
@Data
@Accessors(chain = true)
public class CourseCategoryTreeDto extends CourseCategory {
    private List<CourseCategoryTreeDto> childrenTreeNodes;
}
