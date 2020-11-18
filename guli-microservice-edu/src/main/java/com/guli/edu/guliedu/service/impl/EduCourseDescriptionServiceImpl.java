package com.guli.edu.guliedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.edu.guliedu.entity.EduCourseDescription;
import com.guli.edu.guliedu.mapper.EduCourseDescriptionMapper;
import com.guli.edu.guliedu.service.EduCourseDescriptionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程简介 服务实现类
 * </p>
 *
 * @author zby
 * @since 2020-09-23
 */
@Service
public class EduCourseDescriptionServiceImpl extends ServiceImpl<EduCourseDescriptionMapper, EduCourseDescription> implements EduCourseDescriptionService {

    //3 根据课程id删除描述
    @Override
    public void removeDescriptionByCourseId(String courseId) {
        baseMapper.deleteById(courseId);
    }
}
