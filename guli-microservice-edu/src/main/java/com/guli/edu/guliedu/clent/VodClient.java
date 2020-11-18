package com.guli.edu.guliedu.clent;

import com.guli.edu.common.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("edu-eduvod")
@Component
public interface VodClient {

    //删除单个阿里云视频
    @DeleteMapping("/eduvod/video/{videoId}")
    public R removeVideoById(@PathVariable("videoId") String videoId);

    //删除多个阿里云视频

    @DeleteMapping("/eduvod/video/removeMoreVideo")
    public R deleteMoreVideo(@RequestParam("videoListIds") List videoListIds);


}
