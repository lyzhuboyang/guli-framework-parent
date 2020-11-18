package com.guli.edufront.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.edufront.entity.EduTeacher;
import com.guli.edufront.mapper.EduTeacherMapper;
import com.guli.edufront.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author zby
 * @since 2020-10-08
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    //把分页数据放到map集合
    @Override
    public Map<String, Object> getListTeacherFront(Page<EduTeacher> pageTeracher) {
        //把分页数据封装到pageTeacher对象里面
        baseMapper.selectPage(pageTeracher, null);
        //把pageTeacher数据获取出来，放到map集合中
        Map<String, Object> map = new HashMap<>();
        long total = pageTeracher.getTotal();//总记录数
        List<EduTeacher> records = pageTeracher.getRecords();//每页的具体数据
        long current = pageTeracher.getCurrent();//当前页
        long pages = pageTeracher.getPages();//总页数
        long size = pageTeracher.getSize();//每页显示的记录数
        boolean hasPrevious = pageTeracher.hasPrevious();//是否有上一页
        boolean hasNext = pageTeracher.hasNext();//是否有下一页
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
}
