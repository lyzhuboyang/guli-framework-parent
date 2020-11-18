package com.guli.edufront.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.edu.common.R;
import com.guli.edufront.client.ChapterVideoInfoClient;
import com.guli.edufront.entity.CourseWebVo;
import com.guli.edufront.entity.EduCourse;
import com.guli.edufront.service.EduCourseService;
import com.guli.edufront.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author zby
 * @since 2020-10-08
 */
@RestController
@RequestMapping("/edufront/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private ChapterVideoInfoClient chapterVideoInfoClient;


    //根据课程id获取课程详情信息
    @GetMapping("getFrontCourseInfo/{id}")
    public R getFrontCourseInfo(@PathVariable String id) {
        //根据课程id获取课程相关信息
        CourseWebVo ourseWebVo = courseService.getWebCourseInfo(id);
        //根据课程id获取章节和小节信息
        List chaptervideolist = (List) chapterVideoInfoClient.getChapterVideoByCourseId(id).getData().get("chaptervideolist");
        return R.ok().data("ourseWebVo", ourseWebVo).data("chaptervideolist", chaptervideolist);
    }

    //查询课程分页列表
    @GetMapping("getFrontCourseList/{page}/{limit}")
    public R getFrontCourseList(@PathVariable long page, @PathVariable long limit) {
        //泛型擦除 ：泛型只是作用在源代码阶段，java文件编译之后，泛型就不存在了
        Page<EduCourse> pageCourse = new Page<>(page, limit);
        Map<String, Object> map = courseService.getFrontCourseList(pageCourse);
        return R.ok().data(map);
    }

}

