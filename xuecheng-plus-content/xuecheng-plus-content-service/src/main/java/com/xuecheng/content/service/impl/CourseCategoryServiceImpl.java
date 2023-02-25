package com.xuecheng.content.service.impl;/**
 * @author 朱焕杰
 * @version 1.0
 * @date 2023/2/23 11:16
 */

import com.xuecheng.content.mapper.CourseCategoryMapper;
import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import com.xuecheng.content.service.CourseCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 朱焕杰
 * @version 1.0
 * @description 课程分类serviceimpl
 * @date 2023/2/23 11:16
 */
@Service
public class CourseCategoryServiceImpl implements CourseCategoryService {

    @Autowired
    CourseCategoryMapper courseCategoryMapper;


    @Override
    public List<CourseCategoryTreeDto> queryTreeNodes(String id) {
        //得到根节点下所有的子节点
        List<CourseCategoryTreeDto> categoryTreeDtos = courseCategoryMapper.selectTreeNodes(id);

        ////写法一：
        ////定义一个List作为最终返回数据
        //List<CourseCategoryTreeDto> courseCategoryTreeDtos = new ArrayList<>();
        ////为了方便找子结点的父结点，定义一个map
        //HashMap<String, CourseCategoryTreeDto> nodeMap = new HashMap<>();
        ////将数据封装到List中，只包括了根节点的直接下属节点
        //categoryTreeDtos.stream().forEach(item -> {
        //    nodeMap.put(item.getId(), item);
        //    if (item.getParentid().equals(id)) {
        //        courseCategoryTreeDtos.add(item);
        //    }
        //    //找到子结点，放到它的父节点的childrenTreeNodes属性中
        //    CourseCategoryTreeDto father = nodeMap.get(item.getParentid());
        //    if (father != null) {
        //        if (father.getChildrenTreeNodes() == null) {
        //            father.setChildrenTreeNodes(new ArrayList<>());
        //        }
        //        //找到子结点，放到它的父节点的childrenTreeNodes属性中
        //        father.getChildrenTreeNodes().add(item);
        //    }
        //
        //});

        //写法二：
        List<CourseCategoryTreeDto> courseCategoryTreeDtos = categoryTreeDtos.stream()
                //找到父结点
                .filter(item -> item.getParentid().equals(id))
                //找到它们的子结点
                .map(item -> item.setChildrenTreeNodes(getChildren(item, categoryTreeDtos)))
                .collect(Collectors.toList());

        return courseCategoryTreeDtos;
    }

    //找到结点的子结点
    private List<CourseCategoryTreeDto> getChildren(CourseCategoryTreeDto father, List<CourseCategoryTreeDto> categoryTreeDtos) {
        List<CourseCategoryTreeDto> childTreeNodes = categoryTreeDtos.stream().filter(categoryTreeDto -> categoryTreeDto.getParentid().equals(father.getId()))
                //如果有多级的话，递归找到多级子节点
                .map(categoryTreeDto -> categoryTreeDto.setChildrenTreeNodes(getChildren(categoryTreeDto, categoryTreeDtos)))
                .collect(Collectors.toList());
        return CollectionUtils.isEmpty(childTreeNodes) ? null : childTreeNodes;
    }


}
