package com.guli.edu.guliedu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.edu.guliedu.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.edu.guliedu.entity.query.TeacherQuery;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author zby
 * @since 2020-09-10
 */
public interface EduTeacherService extends IService<EduTeacher> {

    //自己创建service的方法实现多条件组合查询带分页
    void getTeacherPageCondition(Page<EduTeacher> pageTeacher, TeacherQuery teacherQuery);
}
