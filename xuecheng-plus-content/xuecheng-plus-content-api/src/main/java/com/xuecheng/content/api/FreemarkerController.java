package com.xuecheng.content.api;

import com.xuecheng.content.service.CoursePublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author 朱焕杰
 * @version 1.0
 * @description TODO
 * @date 2023/3/5 11:56
 */
@Controller
public class FreemarkerController {

    @GetMapping("/testfreemarker")
    public ModelAndView test() {
        ModelAndView modelAndView = new ModelAndView();
        //设置模型数据
        modelAndView.addObject("name", "小明");
        //设置模板名称
        modelAndView.setViewName("test");
        return modelAndView;
    }

}
