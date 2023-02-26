package com.xuecheng.content.service;

import com.xuecheng.content.model.dto.SaveTeachplanDto;
import com.xuecheng.content.model.dto.TeachPlanDto;

import java.util.List;

/**
 * @author 朱焕杰
 * @version 1.0
 * @description 课程计划service
 * @date 2023/2/25 16:04
 */
public interface TeachplanService {

    public List<TeachPlanDto> findTeachplayTree(Long courseId);

    /***
     * @description 保存课程计划(新增/修改)
     * @param dto
     * @return void
     * @author 朱焕杰
     * @date 2023/2/25 16:30
    */
    public void saveTeachplan(SaveTeachplanDto dto);

    void deleteTeachplan(Long id);
}
