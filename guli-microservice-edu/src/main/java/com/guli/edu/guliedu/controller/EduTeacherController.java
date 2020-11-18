package com.guli.edu.guliedu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.edu.common.R;
import com.guli.edu.guliedu.entity.EduTeacher;
import com.guli.edu.guliedu.entity.query.TeacherQuery;
import com.guli.edu.guliedu.service.EduTeacherService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author zby
 * @since 2020-09-10
 */
@RestController
@CrossOrigin//跨域
@RequestMapping("/guliedu/edu-teacher")
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    @GetMapping("login")
    public R login() {
        //返回数据
        //{"code":20000,"data":{"token":"admin"}}
        return R.ok().data("token", "admin");
    }

    //info方法
    @GetMapping("info")
    public R info() {
        return R.ok().data("roles", "[admin]").data("name", "admin").data("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }


    @GetMapping
    @ApiOperation(value = "所有讲师列表")
    public R getAllTeacher() {

        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("gmt_create");
        List<EduTeacher> list = eduTeacherService.list(wrapper);
        return R.ok().data("items", list);
    }


    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("{id}")
    public R deleteTeacherById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id) {
        boolean flag = eduTeacherService.removeById(id);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }


    //讲师分页查询（不带条件）
    @GetMapping("findTeacherPage/{page}/{limit}")
    public R findTeacherPage(@PathVariable long page, @PathVariable long limit) {
        //创建page对象
        //page:当前页 limit :每页记录数
        Page<EduTeacher> pageTeacher = new Page<>(page, limit);
        //调用方法实现分页查询，封装到pageTeacher对象中
        eduTeacherService.page(pageTeacher, null);
        //从pageTeacher中获取分页数据
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();
        return R.ok().data("total", total).data("items", records);
    }

    //多条件组合查询分页
    @PostMapping("findMoreConditionPage/{page}/{limit}")
    public R findMoreConditionPage(@PathVariable long page, @PathVariable long limit, @RequestBody(required = false) TeacherQuery teacherQuery) {

//       //TODO 这段代码只是为了测试，实际运行的时候，这段代码会去掉，模拟异常
//        try {
//            int i = 9/0;
//        } catch (Exception e) {
//            throw new EduException(20001,"执行了");
//        }

        Page<EduTeacher> pageTeacher = new Page<>(page, limit);
        //自己创建service的方法实现多条件组合查询带分页
        eduTeacherService.getTeacherPageCondition(pageTeacher, teacherQuery);
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();
        return R.ok().data("total", total).data("items", records);
    }


    //讲师添加
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean flag = eduTeacherService.save(eduTeacher);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }


    //讲师回显
    @GetMapping("getTeacherInfo/{id}")
    public R getTeacherInfo(@PathVariable String id) {
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return R.ok().data("teacher", eduTeacher);
    }

    //讲师修改
    @PostMapping("updateTeacher")
    public R upadateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean flag = eduTeacherService.updateById(eduTeacher);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }


}

