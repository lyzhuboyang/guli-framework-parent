package com.guli.edufront.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.edufront.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author zby
 * @since 2020-10-08
 */
public interface EduTeacherService extends IService<EduTeacher> {

    //讲师列表功能（分页）
    Map<String, Object> getListTeacherFront(Page<EduTeacher> pageTeracher);
}
