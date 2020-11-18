package com.guli.edufront.controller;


import com.guli.edu.common.R;
import com.guli.edufront.client.VodClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author zby
 * @since 2020-10-09
 */
@RestController
@RequestMapping("/edufront/video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private VodClient vodClient;

    @GetMapping("getPlayAuthByVideoId/{id}")
    public R getPlayAuthByVideoId(@PathVariable String id) {
        R playAuthVideo = vodClient.getPlayAuthVideo(id);
        return playAuthVideo;
    }


}

