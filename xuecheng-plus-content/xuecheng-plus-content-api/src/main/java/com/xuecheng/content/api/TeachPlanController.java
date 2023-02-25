package com.xuecheng.content.api;

import com.xuecheng.content.model.dto.SaveTeachplanDto;
import com.xuecheng.content.model.dto.TeachPlanDto;
import com.xuecheng.content.service.TeachplanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 朱焕杰
 * @version 1.0
 * @description 课程计划接口
 * @date 2023/2/25 15:02
 */
@Api(value = "课程计划相关接口", tags = "课程计划")
@Slf4j
@RestController
public class TeachPlanController {

    @Autowired
    private TeachplanService teachplanService;

    @GetMapping("/teachplan/{courseId}/tree-nodes")
    public List<TeachPlanDto> getTreeNodes(@PathVariable Long courseId) {
        return teachplanService.findTeachplayTree(courseId);
    }

    @ApiOperation(value = "课程计划创建或修改", notes = "课程计划创建或修改")
    @PostMapping("/teachplan")
    public void saveTeachplan(@RequestBody SaveTeachplanDto dto){
        teachplanService.saveTeachplan(dto);
    }
}
