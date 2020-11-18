package com.guli.edu.guliedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.edu.guliedu.entity.EduCourse;
import com.guli.edu.guliedu.entity.EduCourseDescription;
import com.guli.edu.guliedu.entity.query.CourseInfoForm;
import com.guli.edu.guliedu.entity.query.CourseQuery;
import com.guli.edu.guliedu.entity.query.PublishCourseInfo;
import com.guli.edu.guliedu.handler.EduException;
import com.guli.edu.guliedu.mapper.EduCourseMapper;
import com.guli.edu.guliedu.service.EduChapterService;
import com.guli.edu.guliedu.service.EduCourseDescriptionService;
import com.guli.edu.guliedu.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.edu.guliedu.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author zby
 * @since 2020-09-23
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    //注入描述的Servcie
    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    @Autowired
    private EduChapterService eduChapterService;

    @Autowired
    private EduVideoService eduVideoService;


    //添加课程信息（包含课程基本信息和课程描述信息）
    @Override
    public String saveCourseInfo(CourseInfoForm courseInfoForm) {

        //1 把课程基本信息，添加课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoForm, eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if (insert <= 0) {
            throw new EduException(20001, "添加课程信息失败");
        }
        //2 把课程描述，添加到描述表
        String courseId = eduCourse.getId();
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        String description = courseInfoForm.getDescription();
        if (!StringUtils.isEmpty(eduCourseDescription)) {
            eduCourseDescription.setId(courseId);//课程和描述是一对一的关系，描述id和课程id是一样的
            eduCourseDescription.setDescription(description);
            boolean save = eduCourseDescriptionService.save(eduCourseDescription);
        }
        return courseId;
    }

    //根据课程id查询课程的信息
    @Override
    public CourseInfoForm getInfoCourseById(String courseId) {
        //1查询课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoForm courseInfoForm = new CourseInfoForm();
        BeanUtils.copyProperties(eduCourse, courseInfoForm);

        //2查询课程对应的描述
        EduCourseDescription eduCourseDescription = eduCourseDescriptionService.getById(courseId);
        String description = eduCourseDescription.getDescription();
        if (!StringUtils.isEmpty(description)) {
            courseInfoForm.setDescription(description);
        }
        return courseInfoForm;
    }

    //修改课程的方法
    @Override
    public void updateCourseInfo(CourseInfoForm courseInfoForm) {
        //修改课程的基本信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoForm, eduCourse);
        int result = baseMapper.updateById(eduCourse);
        if (result <= 0) {
            throw new EduException(20001, "修改失败");
        }
        //修改描述信息
        String description = courseInfoForm.getDescription();
        //描述不能为空，如果描述可以为空，if判断去掉
        if (!StringUtils.isEmpty(description)) {
            EduCourseDescription eduCourseDescription = new EduCourseDescription();
            eduCourseDescription.setDescription(description);
            eduCourseDescription.setId(eduCourse.getId());
            boolean flag = eduCourseDescriptionService.updateById(eduCourseDescription);
        }
    }

    //根据课程id查询课程发布的信息
    @Override
    public PublishCourseInfo getPublishCourseInfo(String courseId) {
        PublishCourseInfo publishCourseInfo = baseMapper.getPublishCourseInfo(courseId);
        return publishCourseInfo;
    }

    //根据条件查询课程带分页
    @Override
    public void getAllCoursePageCondition(Page<EduCourse> pageCourse, CourseQuery courseQuery) {
        //把条件获取出来
        String title = courseQuery.getTitle();
        String status = courseQuery.getStatus();
        //判断条件进行条件拼接
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(title)) {
            queryWrapper.like("title", title);
        }
        if (!StringUtils.isEmpty(status)) {
            queryWrapper.eq("status", status);
        }
        //传入参数进行查询，封装分页数据到pageCourse
        baseMapper.selectPage(pageCourse, queryWrapper);
    }

    //删除课程的方法
    @Override
    public void removeCourse(String courseId) {
        //1 根据课程id删除小节（包含视频）
        eduVideoService.removeVideoByCourseId(courseId);
        //2 根据框课程id删除章节
        eduChapterService.removeChapterByCourseId(courseId);
        //3 根据课程id删除描述
        eduCourseDescriptionService.removeDescriptionByCourseId(courseId);
        //4 根据课程id 删除课程
        //分析，123可以删除失败，4必须删除成功。
        baseMapper.deleteById(courseId);
    }
}
