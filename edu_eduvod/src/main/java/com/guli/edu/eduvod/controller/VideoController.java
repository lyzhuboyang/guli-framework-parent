package com.guli.edu.eduvod.controller;

import com.guli.edu.common.R;
import com.guli.edu.eduvod.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VideoController {

    @Autowired
    VideoService videoService;

    //删除多个阿里云视频的方法
    @DeleteMapping("removeMoreVideo")
    public R deleteMoreVideo(@RequestParam("videoListIds") List videoListIds) {//必须加注解，不加会报错
        videoService.removeVideoMore(videoListIds);
        return R.ok();
    }

    //根据视频id删除阿里云视频
    @DeleteMapping("{videoId}")
    public R removeVideoById(@PathVariable String videoId) {
        videoService.deleteVideoAliyun(videoId);
        return R.ok();
    }

    //上传视频到阿里云服务中
    @PostMapping("uploadvideo")
    public R uploadVideoAliYun(MultipartFile file) {
        String videoId = videoService.uploadAliYunVideo(file);
        return R.ok().data("videoId", videoId);
    }

    //根据视频id获取视频凭证
    @GetMapping("getPlayAuthVideo/{vid}")
    public R getPlayAuthVideo(@PathVariable String vid) {
        String playAuth = videoService.getPlayAuthVideo(vid);
        return R.ok().data("playAuth", playAuth);
    }


}
