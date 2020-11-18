package com.guli.edu.guliedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.edu.guliedu.entity.EduTeacher;
import com.guli.edu.guliedu.entity.query.TeacherQuery;
import com.guli.edu.guliedu.mapper.EduTeacherMapper;
import com.guli.edu.guliedu.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author zby
 * @since 2020-09-10
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public void getTeacherPageCondition(Page<EduTeacher> pageTeacher, TeacherQuery teacherQuery) {
        //拼接条件
        //把条件获取出来
        String name = teacherQuery.getName();
        String level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        //判断进行条件拼接
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }

        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level", level);
        }

        if (!StringUtils.isEmpty(begin)) {
            wrapper.gt("begin", begin);
        }

        if (!StringUtils.isEmpty(end)) {
            wrapper.lt("end", end);
        }
        wrapper.orderByDesc("gmt_create");
        //传入参数进行查询，封装分页数据到pageTeacher
        baseMapper.selectPage(pageTeacher, wrapper);
    }
}
