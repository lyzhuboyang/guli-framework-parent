package com.guli.edufront.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.edufront.entity.CourseWebVo;
import com.guli.edufront.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author zby
 * @since 2020-10-08
 */
public interface EduCourseService extends IService<EduCourse> {

    //查询课程分页列表
    Map<String, Object> getFrontCourseList(Page<EduCourse> pageCourse);

    //根据课程id获取课程详情信息
    CourseWebVo getWebCourseInfo(String id);
}
