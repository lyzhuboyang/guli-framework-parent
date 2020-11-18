package com.guli.edu.guliedu.mapper;

import com.guli.edu.guliedu.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guli.edu.guliedu.entity.query.PublishCourseInfo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author zby
 * @since 2020-09-23
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    //根据课程id查询发布时的确认的课程信息
    public PublishCourseInfo getPublishCourseInfo(String courseId);

}
