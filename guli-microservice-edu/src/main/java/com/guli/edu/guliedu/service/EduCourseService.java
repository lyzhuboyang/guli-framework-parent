package com.guli.edu.guliedu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.edu.guliedu.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.edu.guliedu.entity.query.CourseInfoForm;
import com.guli.edu.guliedu.entity.query.CourseQuery;
import com.guli.edu.guliedu.entity.query.PublishCourseInfo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author zby
 * @since 2020-09-23
 */
public interface EduCourseService extends IService<EduCourse> {

    // //添加课程信息（包含课程基本信息和课程描述信息）
    String saveCourseInfo(CourseInfoForm courseInfoForm);

    CourseInfoForm getInfoCourseById(String courseId);

    void updateCourseInfo(CourseInfoForm courseInfoForm);

    //根据课程id查询课程发布的信息
    public PublishCourseInfo getPublishCourseInfo(String courseId);

    //根据条件查询课程带分页
    void getAllCoursePageCondition(Page<EduCourse> pageCourse, CourseQuery courseQuery);

    void removeCourse(String courseId);
}
