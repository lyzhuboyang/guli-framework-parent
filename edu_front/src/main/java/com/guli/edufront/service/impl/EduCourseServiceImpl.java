package com.guli.edufront.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.edufront.entity.CourseWebVo;
import com.guli.edufront.entity.EduCourse;
import com.guli.edufront.entity.EduTeacher;
import com.guli.edufront.mapper.EduCourseMapper;
import com.guli.edufront.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author zby
 * @since 2020-10-08
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    //查询课程分页列表
    @Override
    public Map<String, Object> getFrontCourseList(Page<EduCourse> pageCourse) {
        //把分页数据封装到pageCourse对象里面
        baseMapper.selectPage(pageCourse, null);
        //把pageCourse数据获取出来，放到map集合中
        Map<String, Object> map = new HashMap<>();
        long total = pageCourse.getTotal();//总记录数
        List<EduCourse> records = pageCourse.getRecords();//每页的具体数据
        long current = pageCourse.getCurrent();//当前页
        long pages = pageCourse.getPages();//总页数
        long size = pageCourse.getSize();//每页显示的记录数
        boolean hasPrevious = pageCourse.hasPrevious();//是否有上一页
        boolean hasNext = pageCourse.hasNext();//是否有下一页
        //放到map集合中去
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;
    }

    //根据课程id获取课程详情信息
    @Override
    public CourseWebVo getWebCourseInfo(String id) {
        CourseWebVo courseWebVo = baseMapper.getWebCourseInfo(id);
        return courseWebVo;
    }
}
