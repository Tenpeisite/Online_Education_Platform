package com.xuecheng.content.model.dto;

import com.xuecheng.content.model.po.Teachplan;
import com.xuecheng.content.model.po.TeachplanMedia;
import lombok.Data;

import java.util.List;

/**
 * @author 朱焕杰
 * @version 1.0
 * @description 课程计划dto
 * @date 2023/2/25 14:59
 */
@Data
public class TeachPlanDto extends Teachplan {
    //关联的媒资信息
    private TeachplanMedia teachplanMedia;

    //子目录
    private List<TeachPlanDto> teachPlanTreeNodes;
}
