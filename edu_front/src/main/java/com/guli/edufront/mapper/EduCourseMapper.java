package com.guli.edufront.mapper;

import com.guli.edufront.entity.CourseWebVo;
import com.guli.edufront.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author zby
 * @since 2020-10-08
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    //根据课程id获取课程相关信息
    CourseWebVo getWebCourseInfo(String id);
}
