package com.guli.edu.guliedu.controller;


import com.guli.edu.common.R;
import com.guli.edu.guliedu.entity.EduSubject;
import com.guli.edu.guliedu.entity.subjectdto.OneSubjectDto;
import com.guli.edu.guliedu.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author zby
 * @since 2020-09-20
 */
@RestController
@RequestMapping("/guliedu/subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    EduSubjectService eduSubjectService;


    //删除分类的方法
    @DeleteMapping("deleteSubjectId/{id}")
    public R removeSubject(@PathVariable String id) {
        boolean flag = eduSubjectService.removeByIdSubject(id);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }


    //返回所有的分类信息，满足要求的结构
    @GetMapping("getAllSubject")
    public R getAllSubjectInfo() {
        List<OneSubjectDto> list = eduSubjectService.findAllSubjcet();
        return R.ok().data("subjectList", list);
    }


    //使用poi读取excel里面的分类数据，添加到数据库里面
    @PostMapping("importData")
    public R importSubjectData(MultipartFile file) {
        //1 获取上传文件
        List<String> msg = eduSubjectService.importDataSubject(file);
        if (msg.size() == 0) {
            return R.ok().message("数据导入成功");
        } else {
            return R.error().message("部分数据导入成功").data("msg", msg);
        }
    }

    //添加一级分类
    @PostMapping("addOneLevel")
    public R addLevelOne(@RequestBody EduSubject eduSubject) {
        //设置parentid是0
        eduSubject.setParentId("0");
        boolean flag = eduSubjectService.saveOne(eduSubject);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //添加二级分类
    @PostMapping("addLevelTwo")
    public R addLevelTwo(@RequestBody EduSubject eduSubject) {
        boolean flag = eduSubjectService.saveTwo(eduSubject);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }


}

