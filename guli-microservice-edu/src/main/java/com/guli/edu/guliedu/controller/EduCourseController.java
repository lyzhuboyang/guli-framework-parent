package com.guli.edu.guliedu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.edu.common.R;
import com.guli.edu.guliedu.entity.EduCourse;
import com.guli.edu.guliedu.entity.query.CourseInfoForm;
import com.guli.edu.guliedu.entity.query.CourseQuery;
import com.guli.edu.guliedu.entity.query.PublishCourseInfo;
import com.guli.edu.guliedu.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author zby
 * @since 2020-09-23
 */
@RestController
@RequestMapping("/guliedu/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    //根据条件查询课程带分页
    @PostMapping("getAllCoursePageCondition/{page}/{limit}")
    public R getAllCoursePageCondition(@PathVariable long page, @PathVariable long limit, @RequestBody CourseQuery courseQuery) {
        Page<EduCourse> pageCourse = new Page<>(page, limit);
        eduCourseService.getAllCoursePageCondition(pageCourse, courseQuery);
        long total = pageCourse.getTotal();
        List<EduCourse> records = pageCourse.getRecords();
        return R.ok().data("total", total).data("items", records);
    }

    //修改课程的方法
    @PostMapping("updateCourse")
    public R updateCourse(@RequestBody CourseInfoForm courseInfoForm) {
        eduCourseService.updateCourseInfo(courseInfoForm);
        return R.ok();
    }


    //添加课程信息（包含课程基本信息和课程描述信息）
    @PostMapping("addCourse")
    public R addCourseInfo(@RequestBody CourseInfoForm courseInfoForm) {
        String courseId = eduCourseService.saveCourseInfo(courseInfoForm);
        return R.ok().data("courseId", courseId);
    }


    //根据课程id查询课程的信息
    @GetMapping("getCourseInfo/{courseId}")
    public R getInfoCourse(@PathVariable String courseId) {
        CourseInfoForm courseInfoForm = eduCourseService.getInfoCourseById(courseId);
        return R.ok().data("courseInfo", courseInfoForm);
    }

    //根据课程id查询课程发布的信息
    @GetMapping("publishCourseInfo/{courseId}")
    public R getPublishCourseInfo(@PathVariable String courseId) {
        PublishCourseInfo publishCourseInfo = eduCourseService.getPublishCourseInfo(courseId);
        return R.ok().data("publishCourseInfo", publishCourseInfo);
    }

    //修改课程状态，实现最终发布
    @PutMapping("finalPublishCourse/{courseId}")
    public R finalPublishCourse(@PathVariable String courseId) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(courseId);
        eduCourse.setStatus("Normal");
        eduCourseService.updateById(eduCourse);
        return R.ok();
    }

    //删除课程的方法
    @DeleteMapping("{courseId}")
    public R deleteCourse(@PathVariable String courseId) {
        eduCourseService.removeCourse(courseId);
        return R.ok();
    }


}

