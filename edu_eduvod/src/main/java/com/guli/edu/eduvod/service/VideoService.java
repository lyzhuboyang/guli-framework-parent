package com.guli.edu.eduvod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VideoService {

    String uploadAliYunVideo(MultipartFile file);

    //根据视频id删除阿里云视频
    void deleteVideoAliyun(String videoId);

    //删除多个阿里云视频的方法
    void removeVideoMore(List videoListIds);

    //根据视频id获取视频凭证
    String getPlayAuthVideo(String vid);
}
