package com.guli.edu.guliedu.controller;


import com.guli.edu.common.R;
import com.guli.edu.guliedu.entity.EduChapter;
import com.guli.edu.guliedu.entity.chaptervideodto.ChapterDto;
import com.guli.edu.guliedu.service.EduChapterService;
import com.guli.edufront.client.ChapterVideoInfoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author zby
 * @since 2020-09-26
 */
@RestController
@RequestMapping("/guliedu/chapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    EduChapterService eduChapterService;

    //返回当前课程章节和小节的所有数据
    @GetMapping("getChapterVideoByCourseId/{courseId}")
    public R getChapterVideoByCourseId(@PathVariable String courseId) {
        List<ChapterDto> list = eduChapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("chaptervideolist", list);
    }

    //添加章节
    @PostMapping("saveChapter")
    public R addChapter(@RequestBody EduChapter eduChapter) {
        boolean flag = eduChapterService.save(eduChapter);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //根据章节id进行查询
    @GetMapping("getChapterInfo/{chapterId}")
    public R getChapterInfo(@PathVariable String chapterId) {
        EduChapter eduChapter = eduChapterService.getById(chapterId);
        return R.ok().data("eduChapter", eduChapter);
    }

    //章节修改的方法
    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter) {
        eduChapterService.updateById(eduChapter);
        return R.ok();
    }

    //章节删除的方法
    @DeleteMapping("{chapterId}")
    public R deleteChapter(@PathVariable String chapterId) {
        //删除章节，如果章节里面有小节，不进行删除
        eduChapterService.removeChapter(chapterId);
        return R.ok();
    }

}

