package com.guli.edufront.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.edu.common.R;
import com.guli.edufront.entity.EduCourse;
import com.guli.edufront.entity.EduTeacher;
import com.guli.edufront.service.EduCourseService;
import com.guli.edufront.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author zby
 * @since 2020-10-08
 */
@RestController
@RequestMapping("/edufront/teacher")
@CrossOrigin
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService courseService;

    //讲师列表功能（分页）
    //前台系统中没有用到elementUI，所以分页需要把所有数据都返回过去
    @GetMapping("getFrontTeacherList/{page}/{limit}")
    public R getFrontTeacherList(@PathVariable long page,
                                 @PathVariable long limit) {
        //创建page对象
        Page<EduTeacher> pageTeracher = new Page<>(page, limit);
        Map<String, Object> map = teacherService.getListTeacherFront(pageTeracher);
        return R.ok().data(map);
    }

    //讲师详情信息
    @GetMapping("getFrontTeacherInfo/{id}")
    public R getFrontTeacherInfo(@PathVariable String id) {
        //根据讲师id查询讲师基本信息
        EduTeacher teacher = teacherService.getById(id);
        //根据讲师id查询所讲课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id", id);
        List<EduCourse> list = courseService.list(wrapper);
        return R.ok().data("teacher", teacher).data("courseList", list);
    }

}

