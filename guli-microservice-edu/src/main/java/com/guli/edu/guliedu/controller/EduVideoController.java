package com.guli.edu.guliedu.controller;


import com.guli.edu.common.R;
import com.guli.edu.guliedu.entity.EduVideo;
import com.guli.edu.guliedu.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author zby
 * @since 2020-09-26
 */
@RestController
@RequestMapping("/guliedu/video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService eduVideoService;

    //添加小节
    @PostMapping("saveVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {
        eduVideoService.save(eduVideo);
        return R.ok();
    }

    //根据小节id进行查询
    @GetMapping("getVideoInfo/{videoId}")
    public R getVideoInfo(@PathVariable("videoId") String videoId) {
        EduVideo eduVideo = eduVideoService.getById(videoId);
        return R.ok().data("eduVideo", eduVideo);
    }

    //根据小节id修改
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo) {
        boolean flag = eduVideoService.updateById(eduVideo);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //删除小节
    @DeleteMapping("{videoId}")
    public R deleteVideo(@PathVariable String videoId) {
        eduVideoService.removeVideo(videoId);
        return R.ok();
    }


}

